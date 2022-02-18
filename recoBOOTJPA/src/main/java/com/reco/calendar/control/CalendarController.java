package com.reco.calendar.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.reco.calendar.service.CalendarService;
import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class CalendarController {

	@Autowired
	private CalendarService service;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Logger log = LoggerFactory.getLogger(CalendarService.class.getName()); 

	//캘린서 제목과 썸네일을 추가하는 컨트롤러 
	@PostMapping("caladd")
	public String calAdd(@RequestParam(value = "calCategory", required=false) String calCategory
						,@RequestParam(value = "calThumbnail") MultipartFile multipartFile
						,@RequestPart(required=false) List<MultipartFile> letterFiles
						,@RequestPart(required=false) MultipartFile imageFile
						, HttpSession session, Model model
						)  {
		String filenameString= StringUtils.cleanPath(multipartFile.getOriginalFilename());
		//logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());


		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();

		CalInfo ci = new CalInfo();
		ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
		ci.setCalCategory(calCategory);
		ci.setCalThumbnail(filenameString);
		logger.info("요청전달데이터 calCategory=" + calCategory + ", calThumbnail=" + filenameString);

		//게시글내용 DB에 저장
		try {
			CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
			model.addAttribute("ci", calinfo);
			System.out.println(calinfo);
			
		} catch (AddException e1) {
			e1.printStackTrace();
			return "failresult.jsp";
		}


		//파일 경로생성
		String saveDirectory = "c:\\reco\\calendar";
		if ( ! new File(saveDirectory).exists()) {
			logger.info("업로드 실제경로생성");
			new File(saveDirectory).mkdirs(); // 상위디렉토리생성
		}

		//썸네일 생성
		File thumbnailFile = null;
		long imageFileSize = multipartFile.getSize();
		if(imageFileSize > 0) {

			//이미지파일 저장하기
			String imageOriginFileName = multipartFile.getOriginalFilename(); //이미지파일원본이름얻기
			logger.info("이미지 파일이름:" + imageOriginFileName +", 파일크기: " + multipartFile.getSize());


			//저장할 파일이름을 지정한다 ex) 테이블이름 : cal_post__uIdx_calIdx
			String imageFileName = "cal_post_" + uIdx  + "_" + ci.getCalIdx() + ".jpg";

			//이미지 파일생성
			File savedImagefile = new File(saveDirectory, imageFileName);

			try {
				FileCopyUtils.copy(multipartFile.getBytes(), savedImagefile);
				logger.info("이미지 파일저장:" + savedImagefile.getAbsolutePath());

				//파일형식 확인
				String contentType = multipartFile.getContentType();
				if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
					//					return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					return "failresult.jsp";
				}

				//이미지파일인 경우 섬네일파일을 만듦
				String thumbnailName =  "s_"+ imageFileName; //섬네일 파일명은 s_글번호_XXXX_원본이름
				thumbnailFile = new File(saveDirectory,thumbnailName);
				FileOutputStream thumbnailOS;
				thumbnailOS = new FileOutputStream(thumbnailFile);
				InputStream imageFileIS = multipartFile.getInputStream();
				int width = 500;
				int height = 500;
				Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
				logger.info("썸네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 썸네일파일 크기:" + thumbnailFile.length());

				} catch (IOException e2) {
					e2.printStackTrace();
					//				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					return "failresult.jsp";
				}
				
			} //end if(imageFileSize > 0 )
			

			if(thumbnailFile != null) {
				//ResponseEntity 응답상태(200번, 404번 등), 응답헤더설정(쿠키, 컨텐트 LENGTH등 ), 응답내용설정 
//				return "failresult.jsp";
//			}
//		
			try {
				//이미지 썸네일다운로드하기
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
				responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
				responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
				logger.info("썸네일파일 다운로드");
				//				return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders, HttpStatus.OK);
			}catch (IOException e) {
				//				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				return "failresult.jsp";
			}
			
		}//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			return "callistresult.jsp";
	}
	
//CalAddServlet

//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	
//		HttpSession session = request.getSession();
//		Customer c = (Customer)session.getAttribute("loginInfo");		
//		String path = "";
//	
//		int uIdx = c.getUIdx();
//	
//		/*--파일 업로드가능한 파일(확장자가 존재)인가 확인 작업 --*/		
//		String saveDirectory = "d:\\files"; //파일이 저장될 경로 
//	
//		//이미지가 저장될 경로가 현재배포된 프로젝트내부의 경로로 설정되면
//		//프로젝트가 리로드(코드변경,새로고침 )될때마다 배포된 기존프로젝트는 제거되었다가 이클립스폴더내용이 붙여넣기 되는것이다.
//		ServletContext sc = getServletContext();
////		String saveDirectory = sc.getRealPath("images\\calimages");
////		String saveDirectory = "D:\\230\\myWeb\\msa_project\\src\\main\\webapp\\images\\calimages"; //"D:\\files"
//		System.out.println("in CalAddservlet saveDirectory:" + saveDirectory);
//	
//		Collection<Part> parts = request.getParts(); //업로드된 파일들 얻기 
//		System.out.println("in CallAddServlet parts.size()=" + parts.size());
//		String extension = null; //확장자
//		Part part = null;
//		for(Part p: parts) {
//			if("calThumbnail".equals(p.getName())) { //name이 calThumbnail인 파일인 경우                              
//				String fileName = p.getSubmittedFileName(); //실제 업로드된 파일이름
//				int extensionIndex = fileName.lastIndexOf('.');
//				if(extensionIndex != -1) { //확장자가 존재하면					
//					extension = fileName.substring(extensionIndex+1); //확장자 
//					System.out.println("확장자:" + extension);
//					part = p;
//					break;					
//				}
//			}
//		}
//	
//		if(part == null) {
//			request.setAttribute("msg", "첨부할 파일이 없거나 확장자가 없습니다");
//			path="failresult.jsp";
//		}else {
//			String calCategory = request.getParameter("calCategory");
//			String calThumbnail =extension; //request.getParameter("calThumbnail");
//			
//			CalInfo ci = new CalInfo();
//			ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
//			ci.setCalCategory(calCategory);
//			ci.setCalThumbnail(calThumbnail);
//	
//			try {
//				CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
//				System.out.println(calinfo);
//				
//				/*--파일 업로드작업 --*/
//				String saveFileName = "cal_post_" + uIdx + "_" + calinfo.getCalIdx() + "." + extension;
//				part.write(saveDirectory+"\\" + saveFileName); //파일 저장하기 
//				
//				//request.setAttribute("ci", calinfo);
//				List<CalInfo> list = service.findCalsByUIdx(uIdx);	
//				request.setAttribute("list", list);
//				path="index.jsp";
//			} catch (AddException | FindException e ) {
//				System.out.println(e.getMessage());
//				//resultmsg = "캘린더 생성 실패:" + e.getMessage();
//				e.printStackTrace();
//				request.setAttribute("msg", e.getMessage());
//				path="failresult.jsp";
//			}
//		}		
//	
//		RequestDispatcher rd = request.getRequestDispatcher(path);
//		rd.forward(request, response);
//			
//			
//}




//캘린더 썸네일리스트를 보는 컨트롤러 
@GetMapping("callist")
public String calInfoList(HttpSession session, Model model) {
	Customer c = (Customer)session.getAttribute("loginInfo");
	int uIdx = c.getUIdx();
	
	CalInfo ci = new CalInfo();
	try {
		//비지니스로직호출
		List<CalInfo> list = service.findCalsByUIdx(uIdx);
		//응답할 결과 요청속성에 설정
		model.addAttribute("list", list);	
//		session.setAttribute("CalendarInfo", ci);
		return "callistresult.jsp";
	} catch (FindException e) {
		e.printStackTrace();
		model.addAttribute("msg", e.getMessage());
		model.addAttribute("list", new ArrayList<CalInfo>());		
	}
	return "index.jsp";

}



//캘린더 달력을 보는 컨트롤러 
@GetMapping("calpostlist")
public String CalPostList (@RequestParam(value = "calIdx")int calIdx,
						   @RequestParam(value="calCategory")String calCategory,
		                   String calDate,
		//@RequestParam(value = "dateValue")String calDate,
		HttpSession session, Model model) {
	Customer c = (Customer)session.getAttribute("loginInfo");

	CalInfo calinfo = new CalInfo();
	calinfo.setCustomer(c);
	calinfo.setCalIdx(calIdx);
	
	
	//String calIdx =  request.getParameter("calIdx");
	

	//요청전달데이터로 년/월정보가 없으면 오늘날짜기준의 년/월값으로 설정한다
	//String calDate = request.getParameter("dateValue");  
	if(calDate == null ||calDate.equals("")) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		calDate = sdf.format(new Date());
	}


	System.out.println("in CalendarController CalPostList calIdx = " + calIdx +", calDate=" + calDate );

	try {
		List<CalPost> list = service.findCalsByDate(calinfo,calDate);
		model.addAttribute("list", list);	
		model.addAttribute("calinfo",calinfo);
		
		return "calpostlistresult.jsp";
	} catch (FindException e) {
		e.printStackTrace();
		model.addAttribute("msg", e.getMessage());
		return "calpostlistresult.jsp";	
	}

}



//	String calDate = request.getParameter("calDate");
//	String calMemo = request.getParameter("calMemo");
//	CalPost cp1 = new CalPost();
//	cp1.setCalinfo(calinfo);
//	cp1.setCalMemo(calMemo);
//	cp1.setCalDate(calDate);
//	cp1.setCalMainImg(calMainImg);
//	

@PostMapping("calpostAdd") //calpost작성url
public String calpostAdd(@RequestParam(value = "calMemo") String calMemo, 
						 @RequestParam(value = "calDate") String calDate,
						 @RequestParam(value = "calMainImg") MultipartFile multipartFile,
						 @RequestPart (required = false) MultipartFile letterFiles,
						 @RequestPart (required = false) MultipartFile imageFile,
						 HttpSession session, Model model) {

	String filenameString= StringUtils.cleanPath(multipartFile.getOriginalFilename());
	//logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());
	
	
	Customer c = (Customer)session.getAttribute("loginInfo");
	int uIdx = c.getUIdx();
	
	
	CalInfo calinfo = new CalInfo();
	int calIdx = calinfo.getCalIdx();
	calinfo.setCustomer(c);
	calinfo.setCalIdx(calIdx);
	
	
	CalPost cp = new CalPost();
	cp.setCalMemo(calMemo);
	cp.setCalDate(calDate);
	cp.setCalMainImg(filenameString);
	cp.setCalinfo(calinfo);
	
	System.out.println("in CalendarController calpostAdd calIdx = " + calIdx +", calDate=" + calDate );
	//게시글내용 DB에 저장
	try {
		CalPost calpost = service.addCalPost(cp);
		model.addAttribute("cp", calpost);
	} catch (AddException e1) {
		e1.printStackTrace();
	}

	//파일 경로 생성
	String saveDirectory = "c:\\reco\\calendar"; // d:\\files\\calendar
	if ( ! new File(saveDirectory).exists()) {
		logger.info("업로드 실제경로생성");
		new File(saveDirectory).mkdirs(); // 상위디렉토리생성
	}

	//썸네일 생성
	File thumbnailFile = null;
	long imageFileSize = multipartFile.getSize();
	if(imageFileSize > 0) {
		//이미지파일 저장하기
		String imageOrignFileName = multipartFile.getOriginalFilename(); //이미지파일원본이름얻기
		logger.info("이미지 파일이름:" + imageOrignFileName +", 파일크기: " + multipartFile.getSize());


//		calIdx = cp.getCalinfo().getCalIdx();
		System.out.println("값확인용> uIdx= "+ uIdx + ", calIdx= " + calIdx + ", calDate= " + calDate);
		//저장할 파일이름을 지정한다 ex) 저장파일명 : cal_(UIdx)_(CalIdx)_(CalDate)
		String imageFileName = "cal_"+ uIdx  + "_" + calIdx + "_" + calDate + ".jpg" ; //파일이름("선택날짜.확장자") //calMainImg를 .jpg 저장하는법 찾기 


		//이미지파일생성
		File savedImageFile = new File(saveDirectory, imageFileName);	
		try {
			FileCopyUtils.copy(multipartFile.getBytes(), savedImageFile);
			logger.info("이미지 파일저장:" + savedImageFile.getAbsolutePath());

			//파일형식 확인
			String contentType = multipartFile.getContentType();
			if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
				return "failresult.jsp";
			}

			//이미지파일인 경우 섬네일파일을 만듦
			String thumbnailName =  "s_"+imageFileName; //섬네일 파일명은 s_글번호_XXXX_원본이름
			thumbnailFile = new File(saveDirectory,thumbnailName);
			FileOutputStream thumbnailOS;
			thumbnailOS = new FileOutputStream(thumbnailFile);
			InputStream imageFileIS = multipartFile.getInputStream();
			int width = 500;
			int height = 500;
			Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
			logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기:" + thumbnailFile.length());

		} catch (IOException e2) {
			e2.printStackTrace();
			return "failresult.jsp";
		}
	}
	if(thumbnailFile == null) {
		return "failresult.jsp";
	}
	try {
		//이미지 썸네일다운로드하기
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
		responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
		responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+URLEncoder.encode("a", "UTF-8"));
		logger.info("섬네일파일 다운로드");

	}catch(Exception e) {
		return "failresult.jsp";
	}
	return "calpostlistresult.jsp";
}




//
//@WebServlet("/CalPostAdd") //서블릿url 경로
//@MultipartConfig (
//		maxFileSize=1024*1024*3, //3mb,최대파일크기
//		location="d:\\files") // 파일저장위치
//
//public class CalPostAddServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private CalendarService service = CalendarService.getInstance();
//
//	
//	/**get요청에 대한 응답을 주는 메소드*/
//	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		HttpSession session = request.getSession();
//		Customer c = (Customer)session.getAttribute("loginInfo");//로그인 세션에 저장된 loginInfo 불러오기	
//    	
//		CalInfo calinfo = new CalInfo();
//		calinfo.setCustomer(c);
//		
//	    String path = "";
//        String saveDirectory = "d:\\files"; //calpostwrite에서 등록한 파일이 저장될 경로
//		
//	    //모든 서블릿이 사용할 수 있도록 ServletContet 인스턴스에 저장
//	    ServletContext sc = getServletContext();
//	  
//	    System.out.println("in CalPostAddservlet saveDirectory:" + saveDirectory);
//	 // getParts()->업로드 데이터에 접근을 통해 Body에 넘어온 데이터들을 각각의 Part로 쪼개어 리턴
//	    Collection<Part> parts = request.getParts(); 
//		System.out.println("in CalPostAddServlet parts.size()=" + parts.size());	
//		String extension = null; //확장자
//		Part part = null;
//		for(Part p: parts) {
//			if("calMainImg".equals(p.getName())) { //name이 calMainImg인 경우
//				String fileName = p.getSubmittedFileName(); //실제 업로드된 파일이름
//	 // 확장자 얻기 - lastIndexOf 마침표가 있는 마지막 위치를 찾은뒤 확장자를 얻어온다
//				int extensionIndex = fileName.lastIndexOf('.'); 
//				if(extensionIndex != -1) { //확장자가 존재하면					
//					extension = fileName.substring(extensionIndex+1); //확장자 
//					System.out.println("확장자:" + extension);
//					part = p;
//					break;					
//				}
//			}
//		}
//		if(part == null) {
//			request.setAttribute("msg", "첨부할 파일이 없거나 확장자가 없습니다");
//			path="failresult.jsp";
//	// 폼값 받기
//		}else {
//			String calDate = request.getParameter("calDate");
//			String calMemo = request.getParameter("calMemo");
//			String calMainImg =extension;
//			CalPost cp1 = new CalPost();
//			cp1.setCalinfo(calinfo);
//			cp1.setCalMemo(calMemo);
//			cp1.setCalDate(calDate);
//			cp1.setCalMainImg(calMainImg);
//			
//		
//	   
//		try {
//			CalPost calpost= service.addCalPost(cp1);
//			System.out.println(calpost);
//			
//			String saveFileName = "cal_"+ calinfo.getCustomer().getUIdx() + "_" + calinfo.getCalIdx() + "_" + calDate+ "." + extension; //파일이름("선택날짜.확장자")
//			part.write(saveDirectory+"\\" + saveFileName); //파일 저장하기 
//			
//			List<CalPost> list = service.findCalsByDate(calinfo,calDate);
//			request.setAttribute("list", list);
//			path="index.jsp";
//		
//		} catch (AddException | FindException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();   
//            request.setAttribute("msg", e.getMessage());
//			path="failresult.jsp";
//		}
//		
//		}	
//	
//		RequestDispatcher rd = request.getRequestDispatcher(path);
//		rd.forward(request, response);
//	}
//
//}




//@GetMapping("calendar/downloadimage")
//public ResponseEntity<?> downloadImage(String imageFileName) {
//	String SaveDirectory = "c:\\reco\\calendar";
//	File thumbnailFile = new File(SaveDirectory, imageFileName);
//	HttpHeaders responseHeaders = new HttpHeaders();
//	try {
//		//이미지 썸네일다운로드하기
//		responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
//		responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
//		responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
//		logger.info("섬네일파일 다운로드");
//		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders, HttpStatus.OK);
//	}catch (IOException e) {
//		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	}	
//
//}

}
