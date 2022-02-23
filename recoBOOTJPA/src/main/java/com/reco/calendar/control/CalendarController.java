package com.reco.calendar.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.servlet.ModelAndView;

import com.reco.calendar.service.CalendarService;
import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class CalendarController {

	@Autowired
	private CalendarService service;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Logger log = LoggerFactory.getLogger(CalendarService.class.getName()); 
	
	
	
	//캘린더 썸네일리스트를 보는 컨트롤러 
	@GetMapping("callist")
	public String calInfoList(
							  HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
		
		try {
			List<CalInfo> list = service.findCalsByUIdx(uIdx);
			model.addAttribute("list", list);
			return "callistresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			model.addAttribute("list", new ArrayList<CalInfo>());
		}
		return "callistresult.jsp";
	}
	
	
	//캘린서 제목과 썸네일을 추가하는 컨트롤러 
	@PostMapping("caladd")
	public Object calAdd(@RequestParam(value = "calCategory", required=false) String calCategory
						,@RequestParam(value = "calThumbnail") MultipartFile multipartFile
						, HttpSession session, Model model
						)  {
		logger.info("caladd컨트롤러 multipartFile.getSize()=" + multipartFile.getSize() + ", multipartFile.getOriginalFileName()=" + multipartFile.getOriginalFilename());

		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();

		CalInfo ci = new CalInfo();
		ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
		ci.setCalCategory(calCategory);
		ci.setCalThumbnail(multipartFile.getOriginalFilename());
		
		ModelAndView mnv = new ModelAndView();
		//게시글내용 DB에 저장
		try {
			CalInfo calinfo = service.addCal(ci); //calInfo를 service의 add()메소드 인자로 사용
			model.addAttribute("ci", calinfo);
			
			List<CalInfo> list = service.findCalsByUIdx(uIdx);	
			mnv.addObject("list", list);
			
			int calIdx = calinfo.getCalIdx(); //calIdx값 받아오기 성공!
			logger.info("컨트롤러 caladd1= calIdx" + calIdx + ",calCategory=" +calinfo.getCalCategory() + ", calThumbnail=" + calinfo.getCalThumbnail());
			
			//파일 경로생성
			String saveDirectory = "c:\\reco\\calendar";
			//파일을 저장할 폴더가 없다면 만들기. 있다면 만들지 않음			
			if ( ! new File(saveDirectory).exists()) {
				logger.info("업로드 실제경로생성");
				new File(saveDirectory).mkdirs(); // 상위디렉토리생성
			}

			//썸네일 생성(이미지파일저장)
			File thumbnailFile = null;
			long imageFileSize = multipartFile.getSize();
			if(imageFileSize > 0) {

				//이미지파일 저장하기
				String imageOriginFileName = multipartFile.getOriginalFilename(); //이미지파일원본이름얻기
				logger.info("이미지 파일이름:" + imageOriginFileName +", 파일크기: " + multipartFile.getSize());


				//저장할 파일이름을 지정한다 ex) 파일명 : cal_post__uIdx_calIdx
				String imageFileName = "cal_post_" + uIdx  + "_" + calIdx + ".jpg"; //calIdx 값 = list.size()

				//이미지 파일생성
				File savedImagefile = new File(saveDirectory, imageFileName);

				try {
					FileCopyUtils.copy(multipartFile.getBytes(), savedImagefile);
					logger.info("calAdd controller 이미지 파일저장:" + savedImagefile.getAbsolutePath());

					//파일형식 확인. image가 아니면 실패
					String contentType = multipartFile.getContentType();
					if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
						mnv.setViewName("failresult.jsp");
					}

					//이미지파일인 경우 썸네일파일을 만듦
					String thumbnailName =  "s_"+ imageFileName; //섬네일 파일명은 s_cal_post_uIdx_calIdx
					thumbnailFile = new File(saveDirectory,thumbnailName);
					FileOutputStream thumbnailOS;
					thumbnailOS = new FileOutputStream(thumbnailFile);
					InputStream imageFileIS = multipartFile.getInputStream();
					int width = 500;
					int height = 500;
					Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
					logger.info("calAdd controller 썸네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 썸네일파일 크기:" + thumbnailFile.length());

					} catch (IOException e2) {
						e2.printStackTrace();
						mnv.setViewName("failresult.jsp");
					}
					
				} //end if(imageFileSize > 0 )
				
				logger.info("컨트롤러 caladd2= uIdx=" + uIdx + ", calIdx=" + calIdx + ",calCategory=" +calinfo.getCalCategory() + ", calThumbnail=" + calinfo.getCalThumbnail());
				
			mnv.setViewName("index.jsp");
		} catch (AddException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		}
		return mnv;
	}





@PostMapping("calInfomodify")
public Object calInfoModify(@RequestParam(value = "calIdx")int calIdx,
							@RequestParam(value = "calCategory", required=false) String calCategory
						   ,@RequestParam(value = "calThumbnail") MultipartFile multipartFile
						   , HttpSession session, Model model )  { 
		logger.info("calInfoModify컨트롤러 multipartFile.getSize()=" + multipartFile.getSize() + ", multipartFile.getOriginalFileName()=" + multipartFile.getOriginalFilename());
	
		Customer c = (Customer)session.getAttribute("loginInfo");
		int uIdx = c.getUIdx();
	
		CalInfo ci = new CalInfo();
		ci.setCustomer(c); //calinfo의 고객정보는 로그인된 Customer타입의 c로 채워줌
		ci.setCalCategory(calCategory);
		ci.setCalThumbnail(multipartFile.getOriginalFilename());
		ci.setCalIdx(calIdx);
		
		ModelAndView mnv = new ModelAndView();
		try {
			CalInfo calinfo = service.modifyCal(ci);
			model.addAttribute("ci", calinfo);
			
			List<CalInfo> list = service.findCalsByUIdx(uIdx);	
			mnv.addObject("list", list);			
			
//			model.addAttribute("msg", "수정 성공");
//			model.addAttribute("status", 1);
			logger.info("컨트롤러 calInfoModify1= calIdx" + calIdx + ",calCategory=" +calinfo.getCalCategory() + ", calThumbnail=" + calinfo.getCalThumbnail());
			
			//파일 경로생성
			String saveDirectory = "c:\\reco\\calendar";
			//파일을 저장할 폴더가 없다면 만들기. 있다면 만들지 않음			
			if ( ! new File(saveDirectory).exists()) {
				logger.info("Controller calInfomodify 업로드 실제경로생성");
				new File(saveDirectory).mkdirs(); // 상위디렉토리생성
			}

			//썸네일 생성(이미지파일저장)
			File thumbnailFile = null;
			long imageFileSize = multipartFile.getSize();
			if(imageFileSize > 0) {

				//이미지파일 저장하기
				String imageOriginFileName = multipartFile.getOriginalFilename(); //이미지파일원본이름얻기
				logger.info("calInfoModify controller 이미지 파일이름:" + imageOriginFileName +", 파일크기: " + multipartFile.getSize());


				//저장할 파일이름을 지정한다 ex) 파일명 : cal_post__uIdx_calIdx
				String imageFileName = "cal_post_" + uIdx  + "_" + calIdx + ".jpg"; 

				//이미지 파일생성
				File savedImagefile = new File(saveDirectory, imageFileName);

				try {
					FileCopyUtils.copy(multipartFile.getBytes(), savedImagefile);
					logger.info("calInfoModify controller 이미지 파일저장:" + savedImagefile.getAbsolutePath());

					//파일형식 확인. image가 아니면 실패
					String contentType = multipartFile.getContentType();
					if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
						mnv.setViewName("failresult.jsp");
					}

					//이미지파일인 경우 썸네일파일을 만듦
					String thumbnailName =  "s_"+ imageFileName; //섬네일 파일명은 s_cal_post_uIdx_calIdx
					thumbnailFile = new File(saveDirectory,thumbnailName);
					FileOutputStream thumbnailOS;
					thumbnailOS = new FileOutputStream(thumbnailFile);
					InputStream imageFileIS = multipartFile.getInputStream();
					int width = 500;
					int height = 500;
					Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
					logger.info("calInfoModify controller 썸네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 썸네일파일 크기:" + thumbnailFile.length());

					} catch (IOException e2) {
						e2.printStackTrace();
						mnv.setViewName("failresult.jsp");
					}
					
				} //end if(imageFileSize > 0 )
				
				logger.info("컨트롤러 calInfoModify2= uIdx=" + uIdx + ", calIdx=" + calIdx + ",calCategory=" +calinfo.getCalCategory() + ", calThumbnail=" + calinfo.getCalThumbnail());
				
			mnv.setViewName("index.jsp");
		} catch (ModifyException e) {
			e.printStackTrace();
//			model.addAttribute("status", 0);
//			model.addAttribute("msg", "수정 실패");
			mnv.setViewName("failresult.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		}
		return mnv;
}




//캘린더 달력을 보는 컨트롤러 
@GetMapping("calpostlist")
public String CalPostList (@RequestParam(value = "calIdx")int calIdx,
						   @RequestParam(value="calCategory")String calCategory,
		                   String calDate,
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



//캘린더 글 작성 추가하는 컨트롤러 
@PostMapping("calpostAdd") //calpost작성url
public Object calpostAdd(
						 @RequestParam(value = "calMemo") String calMemo, 
						 @RequestParam(value = "calDate") String calDate,
						 @RequestParam(value = "calMainImg") MultipartFile multipartFile,
						 @RequestParam(value = "calIdx") Integer calIdx,
						 @RequestPart (required = false) MultipartFile letterFiles,
						 @RequestPart (required = false) MultipartFile imageFile,
						 HttpSession session, Model model) {
	
	String filenameString= StringUtils.cleanPath(multipartFile.getOriginalFilename());
	//logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());
	
	Customer c = (Customer)session.getAttribute("loginInfo");
	int uIdx = c.getUIdx();
	
	
	CalInfo calinfo = new CalInfo();
	calinfo.setCustomer(c);
	calinfo.setCalIdx(calIdx);
	
	
	CalPost cp = new CalPost();
	cp.setCalMemo(calMemo);
	cp.setCalDate(calDate);
	cp.setCalMainImg(filenameString);
	cp.setCalinfo(calinfo);
	logger.info("요청전달데이터 calIdx=" + calIdx + ", calMemo=" + calMemo + ",calDate=" + calDate + ", calMainImg=" + filenameString);
	
	ModelAndView mnv = new ModelAndView();
	//게시글내용 DB에 저장
	try {
		CalPost calpost = service.addCalPost(cp);
		model.addAttribute("cp", calpost);
	
		List<CalPost> list = service.findCalsByDate(calinfo,calDate);
		mnv.addObject("list", list);

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

			//저장할 파일이름을 지정한다 ex) 저장파일명 : cal_(UIdx)_(CalIdx)_(CalDate)
			String imageFileName = "cal_"+ uIdx  + "_" + calIdx + "_" + calDate + ".jpg" ; //파일이름("선택날짜.확장자") 

			//이미지파일생성
			File savedImageFile = new File(saveDirectory, imageFileName);	
			try {
				FileCopyUtils.copy(multipartFile.getBytes(), savedImageFile);
				logger.info("이미지 파일저장:" + savedImageFile.getAbsolutePath());

				//파일형식 확인
				String contentType = multipartFile.getContentType();
				if(!contentType.contains("image/")) { //이미지파일형식이 아닌 경우
					mnv.setViewName("failresult.jsp");
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
			}
		}//end if(imageFileSize > 0 )
		
		if(thumbnailFile != null) {
			
			try {
				//이미지 썸네일다운로드하기
				HttpHeaders responseHeaders = new HttpHeaders();
				responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
				responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
				responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
				logger.info("썸네일파일 다운로드");
			}catch (IOException e) {
				mnv.setViewName("failresult.jsp");
			}
		}
		mnv.setViewName("calpostlistresult.jsp");
	} catch (AddException e) {
		e.printStackTrace();
		model.addAttribute("msg", e.getMessage());
		mnv.setViewName("failresult.jsp");
	} catch (FindException e) {
		e.printStackTrace();
		model.addAttribute("msg", e.getMessage());
		mnv.setViewName("failresult.jsp");
	}
	return mnv;
}

//캘린더 글 작성 수정하는 컨트롤러
@PostMapping("CalPostModify") //서블릿url 경로
public Object calpostmodify(
		 @RequestParam(value = "calMemo") String calMemo, 
		 @RequestParam(value = "calDate") String calDate,
		 @RequestParam(value = "calMainImg") MultipartFile multipartFile,
		 @RequestParam(value = "calIdx") Integer calIdx,
		 @RequestPart (required = false) MultipartFile letterFiles,
		 @RequestPart (required = false) MultipartFile imageFile,
		 HttpSession session, Model model) {

   String filenameString= StringUtils.cleanPath(multipartFile.getOriginalFilename());
//logger.info("imageFile.getSize()=" + imageFile.getSize() + ", imageFile.getOriginalFileName()=" + imageFile.getOriginalFilename());

   Customer c = (Customer)session.getAttribute("loginInfo");
   int uIdx = c.getUIdx();


    CalInfo calinfo = new CalInfo();
    calinfo.setCustomer(c);
    calinfo.setCalIdx(calIdx);


    CalPost cp = new CalPost();
    cp.setCalMemo(calMemo);
    cp.setCalDate(calDate);
    cp.setCalMainImg(filenameString);
    cp.setCalinfo(calinfo);
    logger.info("요청전달데이터 calinfo=" + calinfo + ", calMainImg=" + filenameString);


     ModelAndView mnv = new ModelAndView();
    //게시글내용 DB에 저장
    try {
          CalPost calpost = service.addCalPost(cp);
          model.addAttribute("cp", calpost);

          List<CalPost> list = service.findCalsByDate(calinfo,calDate);
          mnv.addObject("list", list);

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
	       mnv.setViewName("failresult.jsp");
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
           }
         }//end if(imageFileSize > 0 )

           if(thumbnailFile != null) {

               try {
          //이미지 썸네일다운로드하기
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
            responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
            responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
            logger.info("썸네일파일 다운로드");
             }catch (IOException e) {
            mnv.setViewName("failresult.jsp");
               }
             }
            mnv.setViewName("calpostlistresult.jsp");
            } catch (AddException e) {
              e.printStackTrace();
              model.addAttribute("msg", e.getMessage());
              mnv.setViewName("failresult.jsp");
             } catch (FindException e) {
              e.printStackTrace();
              model.addAttribute("msg", e.getMessage());
              mnv.setViewName("failresult.jsp");
              }
             return mnv;
              }
              

////캘린더 글 작성 삭제하는 컨트롤러
//@GetMapping("")



@GetMapping("/calendardownloadimage")
public ResponseEntity<?> downloadImage(String imageFileName) {
	String SaveDirectory = "c:\\reco\\calendar";
	File thumbnailFile = new File(SaveDirectory, imageFileName);
	HttpHeaders responseHeaders = new HttpHeaders();
	try {
		//이미지 썸네일다운로드하기
		responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
		responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
		responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+ URLEncoder.encode("a", "UTF-8"));
		logger.info("calendardownloadimage컨트롤러 썸네일파일 다운로드");
		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders, HttpStatus.OK);
	}catch (IOException e) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}	

}

}