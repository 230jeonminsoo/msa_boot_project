package com.reco.calendar.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reco.calendar.service.CalendarService;
import com.reco.calendar.vo.CalInfo;
import com.reco.customer.vo.Customer;
import com.reco.exception.FindException;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class CalendarController {

	@Autowired
	private CalendarService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	//캘린서 제목과 썸네일을 추가하는 컨트롤러 
	@PostMapping("caladd")
	public ResponseEntity<?> calAdd(@RequestPart(required=false) List<MultipartFile> letterFiles
								   ,@RequestPart(required=false) MultipartFile imageFile
								   ,CalInfo calInfo
								   ,HttpSession session, Model model)  {
		logger.info("요청전달데이터 Category=" + calInfo.getCalCategory() + ", Thumbnail=" + calInfo.getCalThumbnail());
		logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());
		
		
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
		
		
		//파일 경로생성
				String saveDirectory = "d:\\files";
				if ( ! new File(saveDirectory).exists()) {
					logger.info("업로드 실제경로생성");
					new File(saveDirectory).mkdirs();
				}
				
//				int calThumbnailNo = c.getBoardNo(); //저장된 글의 글번호
				
				File thumbnailFile = null;
				long imageFileSize = imageFile.getSize();
				if(imageFileSize > 0) {
				
					//이미지파일 저장하기
					String imageOriginFileName = imageFile.getOriginalFilename(); //이미지파일원본이름얻기
					logger.info("이미지 파일이름:" + imageOriginFileName +", 파일크기: " + imageFile.getSize());
					
					//저장할 파일이름을 지정한다 ex) 글번호_image_XXXX_원본이름
					String imageFileName = "cal_post_" + uIdx  + "_image_" + UUID.randomUUID() + "_" + imageOriginFileName;
					
					//이미지 파일생성
					File savedImagefile = new File(saveDirectory, imageFileName);
					
					thumbnailFile = null;
					try {
						FileCopyUtils.copy(imageFile.getBytes(), savedImagefile);
						logger.info("이미지 파일저장:" + savedImagefile.getAbsolutePath());
						
						//섬네일파일 생성 
						//파일형식 확인
						String contentType = imageFile.getContentType();
						if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
							return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
						}
						
						//이미지파일인 경우 섬네일파일을 만듦
						String thumbnailName =  "s_"+ imageFileName; //섬네일 파일명은 s_글번호_XXXX_원본이름
						thumbnailFile = new File(saveDirectory,thumbnailName);
						FileOutputStream thumbnailOS;
						thumbnailOS = new FileOutputStream(thumbnailFile);
						InputStream imageFileIS = imageFile.getInputStream();
						int width = 100;
						int height = 100;
						Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
						logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기:" + thumbnailFile.length());
						
						
					} catch (IOException e2) {
						e2.printStackTrace();
						return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} //end if(imageFileSize > 0 )
					
			
//					//letterFiles도 저장하기 
//					if(letterFiles != null) {
//						for(MultipartFile  letterFile: letterFiles) {
//							long letterFileSize = letterFile.getSize();
//							if(imageFileSize > 0) {
//								String letterOriginFileName = letterFile.getOriginalFilename(); //자소서 파일원본이름 얻기
//								//지원서 파일들 저장하기 
//								logger.info("지원서 파일이름: " + letterOriginFileName + ", 파일크기 : " + letterFile.getSize());
//								//저장할 파일이름을 지정한다 ex) 글번호_letter_XXXX_원본이름
//								String letterfileName = wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginFileName;
//								File savedLetterFile = new File(saveDirectory, letterfileName); //파일생성
//								try {
//									FileCopyUtils.copy(letterFile.getBytes(), savedLetterFile);
//									logger.info("지원서 파일저장:" + savedLetterFile.getAbsolutePath());
//								}catch (IOException e) {
//									e.printStackTrace();
//									return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//								}
//							} //end if(imageFileSize > 0 )
//						}
//					}
//					
					if(thumbnailFile != null) {
						//ResponseEntity 응답상태(200번, 404번 등), 응답헤더설정(쿠키, 컨텐트 LENGTH등 ), 응답내용설정 
						try {
							//이미지 썸네일다운로드하기
							HttpHeaders responseHeaders = new HttpHeaders();
							responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
					    	responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
						   	responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
							logger.info("섬네일파일 다운로드");
					    	return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders, HttpStatus.OK);
						}catch (IOException e) {
							return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
						}
			
				}return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	
	
	
	
	
	//캘린더 썸네일리스트를 보는 컨트롤러 
	@GetMapping("callist")
	public ModelAndView calInfoList(HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
		
		ModelAndView mnv = new ModelAndView();
		try {
			//비지니스로직호출
				List<CalInfo> list = service.findCalsByUIdx(uIdx);
				//응답할 결과 요청속성에 설정
				mnv.addObject("list", list);		
				mnv.setViewName("callistresult.jsp");
			} catch (FindException e) {
				e.printStackTrace();
				mnv.addObject("msg", e.getMessage());
				mnv.addObject("list", new ArrayList<CalInfo>());		
			}
			return mnv;
		
	 }
}

//CalAddServlet

//protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	
//	HttpSession session = request.getSession();
//	Customer c = (Customer)session.getAttribute("loginInfo");		
//	String path = "";
//	
//	int uIdx = c.getUIdx();
//	
//	/*--파일 업로드가능한 파일(확장자가 존재)인가 확인 작업 --*/		
//	String saveDirectory = "d:\\files"; //파일이 저장될 경로 
//	
//	//이미지가 저장될 경로가 현재배포된 프로젝트내부의 경로로 설정되면
//	//프로젝트가 리로드(코드변경,새로고침 )될때마다 배포된 기존프로젝트는 제거되었다가 이클립스폴더내용이 붙여넣기 되는것이다.
//	ServletContext sc = getServletContext();
////	String saveDirectory = sc.getRealPath("images\\calimages");
////	String saveDirectory = "D:\\230\\myWeb\\msa_project\\src\\main\\webapp\\images\\calimages"; //"D:\\files"
//	System.out.println("in CalAddservlet saveDirectory:" + saveDirectory);
//	
//	Collection<Part> parts = request.getParts(); //업로드된 파일들 얻기 
//	System.out.println("in CallAddServlet parts.size()=" + parts.size());
//	String extension = null; //확장자
//	Part part = null;
//	for(Part p: parts) {
//		if("calThumbnail".equals(p.getName())) { //name이 calThumbnail인 파일인 경우                              
//			String fileName = p.getSubmittedFileName(); //실제 업로드된 파일이름
//			int extensionIndex = fileName.lastIndexOf('.');
//			if(extensionIndex != -1) { //확장자가 존재하면					
//				extension = fileName.substring(extensionIndex+1); //확장자 
//				System.out.println("확장자:" + extension);
//				part = p;
//				break;					
//			}
//		}
//	}
//	
//	if(part == null) {
//		request.setAttribute("msg", "첨부할 파일이 없거나 확장자가 없습니다");
//		path="failresult.jsp";
//	}else {
//		String calCategory = request.getParameter("calCategory");
//		String calThumbnail =extension; //request.getParameter("calThumbnail");
//		
//		CalInfo ci = new CalInfo();
//		ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
//		ci.setCalCategory(calCategory);
//		ci.setCalThumbnail(calThumbnail);
//	
//		try {
//			CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
//			System.out.println(calinfo);
//			
//			/*--파일 업로드작업 --*/
//			String saveFileName = "cal_post_" + uIdx + "_" + calinfo.getCalIdx() + "." + extension;
//			part.write(saveDirectory+"\\" + saveFileName); //파일 저장하기 
//			
//			//request.setAttribute("ci", calinfo);
//			List<CalInfo> list = service.findCalsByUIdx(uIdx);	
//			request.setAttribute("list", list);
//			path="index.jsp";
//		} catch (AddException | FindException e ) {
//			System.out.println(e.getMessage());
//			//resultmsg = "캘린더 생성 실패:" + e.getMessage();
//			e.printStackTrace();
//			request.setAttribute("msg", e.getMessage());
//			path="failresult.jsp";
//		}
//	}		
//	
//	RequestDispatcher rd = request.getRequestDispatcher(path);
//	rd.forward(request, response);
//		
//		
//}


//CalInfoListServlet
//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	HttpSession session = request.getSession();
//	/*--샘플 로그인된 정보 -- */
////	Customer sampleCustomer = new Customer();
////	sampleCustomer.setuIdx(3);
////	session.setAttribute("loginInfo", sampleCustomer);
//	/*-------------------*/
//	
//	Customer c = (Customer)session.getAttribute("loginInfo");
//	int uIdx = c.getUIdx();
//	String path = "";
//	
//	try {
//	
//	//비지니스로직호출
//		List<CalInfo> list = service.findCalsByUIdx(uIdx);
//		
//		//응답할 결과 요청속성에 설정
//		request.setAttribute("list", list);			
//	} catch (FindException e) {
//		e.printStackTrace();
//		//path = "failresult.jsp";
//		request.setAttribute("list", new ArrayList<CalInfo>());		
////		request.setAttribute("msg", e.getMessage());
//	}
//	path="callistresult.jsp";
//	//VIEWER로 이동
//	RequestDispatcher rd = request.getRequestDispatcher(path);
//	rd.forward(request, response);
//}

