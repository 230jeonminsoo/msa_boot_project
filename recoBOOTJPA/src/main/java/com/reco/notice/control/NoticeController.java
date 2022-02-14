package com.reco.notice.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reco.customer.vo.Customer;
import com.reco.dto.PageDTO;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Logger log = LoggerFactory.getLogger(NoticeService.class.getName());
	
	//공지사항을 추가하는 컨트롤러
	@PostMapping("ntcadd")
	public String noticeAdd( @RequestPart (required = false) MultipartFile letterFiles
							,@RequestPart (required = false) MultipartFile imageFile
							,String ntcTitle,String ntcContent,String ntcAttachment,HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		
		String ntcUNickName = c.getUNickName();
		Notice n = new Notice();
		n.setNtcTitle(ntcTitle);
		n.setNtcContent(ntcContent);
		if(letterFiles != null) {
			n.setNtcAttachment(letterFiles.getOriginalFilename());
		}else {
			n.setNtcAttachment(ntcAttachment);
		}
		n.setNtcUNickName(ntcUNickName);
		
		try{
			Notice notice = service.addNtc(n);
			model.addAttribute("n", notice);
			logger.info("컨트롤러 addntc 1 "+notice.getNtcIdx() + notice.getNtcTitle()+notice.getNtcContent());
			String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\images\\noticeimages";
			int wroteBoardNo = notice.getNtcIdx();//저장된 글번호
			//파일을 저장할 폴더가 없다면 만들기. 있다면 만들지 않음	
			if ( ! new File(saveDirectory).exists()) {
				logger.info("업로드 실제경로생성");
				new File(saveDirectory).mkdirs();
			}
	
			//이미지파일 저장
			File thumbnailFile = null;
			if(imageFile !=null) {
				long imageFileSize = imageFile.getSize();
					if(imageFileSize != 0) {
						String imageFileName = imageFile.getOriginalFilename(); //업로드할 이미지 파일의 이름가져옴
						logger.info("이미지파일 이름:" + imageFileName +" 이미지파일 사이즈 " + imageFile.getSize());
						
						//업로드할  이미지 파일의 이름을 새로생성
						String fileName ="reco_notice_"+wroteBoardNo + "_image_" + UUID.randomUUID() + "_" + imageFileName;
						//이미지파일 생성
						File file = new File(saveDirectory, fileName);
						
						try {	
							FileCopyUtils.copy(imageFile.getBytes(), file);
							
							//이미지파일의 타입가져와서 image가 아니면 실패
							if(imageFile !=null) {
								String contentType = imageFile.getContentType();
								if(!contentType.contains("image/")) { 
									return "failresult.jsp";
								}
							}	
							
							//이미지파일인 경우 섬네일파일을 만듦
							String thumbnailName =  "reco_notice_"+ wroteBoardNo+"_image_"+imageFileName; //섬네일 파일명은 reco_notice_글번호_image_원본이름
							thumbnailFile = new File(saveDirectory,thumbnailName);
							FileOutputStream thumbnailOS;
							thumbnailOS = new FileOutputStream(thumbnailFile);
							InputStream imageFileIS = imageFile.getInputStream();
							int width = 300;
							int height = 300;
							Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
						} catch (IOException e) {
							e.printStackTrace();
							//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
						}	logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기::" + thumbnailFile.length());
					}
				}										
		
	    	//letterFiles도 저장
			if(letterFiles != null) {
				long letterFileSize = letterFiles.getSize();
				if(letterFileSize > 0) {
					String letterOriginalFileName = letterFiles.getOriginalFilename();//letter파일 원본이름 얻기
					logger.info("레터 파일이름:" + letterFiles.getOriginalFilename()+" 파일크기: " + letterFiles.getSize());
					//저장할 파일 이름 지정한다 ex) reco_notice_글번호_letter_XXXX_원본이름
					String letterName = "reco_notice_"+wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginalFileName;
					//letter파일 생성
					File file2 = new File(saveDirectory, letterName);
						try {
							FileCopyUtils.copy(letterFiles.getBytes(), file2);
						} catch (IOException e2) {
							e2.printStackTrace();		
							return "failresult.jsp";
						}
				}//end if(letterFileSize > 0) 
			}//end if(letterFiles != null)	
			logger.info("컨트롤러 addntc 2"+notice.getNtcIdx() + notice.getNtcTitle()+notice.getNtcContent());
			File dir = new File(saveDirectory);
			if(n.getNtcAttachment() !=null) {
				//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
				String[] letterFileNames = dir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_notice_"+notice.getNtcIdx()+"_letter_");//n.getNtcAttachment());
					}
				});
				if(letterFileNames.length>0) {
					model.addAttribute("letter", letterFileNames[0]);
				}
			}		
			
			//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
			String[] imageFiles = dir.list(new FilenameFilter() {		
				@Override
				public boolean accept(File dir, String name) {
					return name.contains("reco_notice_"+notice.getNtcIdx()+"_image_");
				}
			});
			
			if(imageFiles.length > 0) {
				model.addAttribute("image", imageFiles[0]);
			}
			
			return "noticedetailresult.jsp";
		} catch(AddException e){
			e.getStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "failresult.jsp";
		}
		
	}
	
	//공지사항을 자세히 보는 컨트롤러
	@GetMapping("ntcdetail")
	public Object noticeDetail(int ntcIdx, Model model) {
		try {
			Notice n = service.findNtcByIdx(ntcIdx);
			model.addAttribute("n", n);
			String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\images\\noticeimages";
			File dir = new File(saveDirectory);
			if(n.getNtcAttachment() !=null) {
				//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
				String[] letterFileNames = dir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_notice_"+ntcIdx+"_letter_");//n.getNtcAttachment());
					}
				});
				if(letterFileNames.length>0) {
					model.addAttribute("letter", letterFileNames[0]);
				}
			}
			//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
			String[] imageFiles = dir.list(new FilenameFilter() {		
				@Override
				public boolean accept(File dir, String name) {
					return name.contains("reco_notice_"+ntcIdx+"_image_");
				}
			});
			
			if(imageFiles.length > 0) {
				model.addAttribute("image", imageFiles[0]);
			}
			return "noticedetailresult.jsp";
		} catch (FindException e) {
			model.addAttribute("msg",e.getMessage());
			return "failresult.jsp";
		}
	}
	
	//공지사항리스트를 보는 컨트롤러
	@GetMapping(value = {"ntclist", "ntclist/{currentPage}"})
	public Object noticeList(@PathVariable Optional<Integer> currentPage) {
		ModelAndView mnv = new ModelAndView();
		try {
			PageDTO<Notice> pageDTO;
			if(currentPage.isPresent()) {
				log.info("컨트롤러 현재페이지"+currentPage);
				int cp = currentPage.get();
				pageDTO= service.findNtcAll(cp);
				log.info("리스트"+pageDTO.getList()+"현재페이지"+pageDTO.getCurrentPage());
				
			}else {
				pageDTO= service.findNtcAll();
			}
			mnv.addObject("pageDTO", pageDTO);
			mnv.setViewName("noticelistresult.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			mnv.addObject("msg", e.getMessage());
			mnv.setViewName("noticelistresult.jsp");
		}
		return mnv;
	}
	
	//나의 공지사항 글 보는 컨트롤러
	@GetMapping(value = {"myntc/{uNickname}", "myntc/{uNickname}/{currentPage}"})
	public Object myNtc(@PathVariable String uNickname, @PathVariable Optional<Integer> currentPage ,Model model){
		ModelAndView mnv = new ModelAndView();
		PageDTO<Notice> pageDTO;

		try {
			int cp = 1;
			if(currentPage.isPresent()) { //currentPage
				cp = currentPage.get();
			}
			pageDTO = service.findNtcByNickname(uNickname, cp, PageDTO.CNT_PER_PAGE);
			mnv.addObject("noticePageDTO", pageDTO);
			mnv.setViewName("mycommunity.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			mnv.addObject("msg", e.getMessage());
			mnv.setViewName("mycommunity.jsp");
		}
		return mnv;
	}
	
	
	//공지사항을 삭제하는 컨트롤러
	@GetMapping("ntcremove")
	public String noticeRemove(int ntcIdx, Model model) {
		try {
				service.removeNtc(ntcIdx);
				PageDTO<Notice> pageDTO;
				pageDTO = service.findNtcAll();
				model.addAttribute("pageDTO", pageDTO);
				return "noticelistresult.jsp";
		} catch (RemoveException | FindException e) {
			System.out.println(e.getMessage());
			model.addAttribute("msg", e.getMessage());
			return "noticelistresult.jsp";
		}
	}
	
	//마이페이지에서 공지사항을 삭제하는 컨트롤러
	@GetMapping("myntcremove")
	public String noticeRemove(int ntcIdx0, Optional<Integer> ntcIdx1, Optional<Integer> ntcIdx2, Optional<Integer> ntcIdx3, Optional<Integer> ntcIdx4, Model model) {
		try {			
				service.removeNtc(ntcIdx0);
				if(ntcIdx1.isPresent()) {
					service.removeNtc(ntcIdx1.get());
				}
				if(ntcIdx2.isPresent()) {
					service.removeNtc(ntcIdx2.get());
				}
				if(ntcIdx3.isPresent()) {
					service.removeNtc(ntcIdx3.get());
				}
				if(ntcIdx4.isPresent()) {
					service.removeNtc(ntcIdx4.get());
				}
				PageDTO<Notice> pageDTO;
				pageDTO = service.findNtcAll();
				model.addAttribute("noticePageDTO", pageDTO);
				return "mycommunity.jsp";
		} catch (RemoveException | FindException e) {
			System.out.println(e.getMessage());
			model.addAttribute("msg", e.getMessage());
			return "mycommunity.jsp";
		}
	}
	
	
	//공지사항을 검색하는 컨트롤러
	@GetMapping(value = {"ntcsearch/{word}/{f}","ntcsearch/{word}/{f}/{currentPage}"})
	public Object noticeSearch(@PathVariable Optional<String> word, @PathVariable String f,@PathVariable Optional<Integer> currentPage ,Model model) {
		ModelAndView mnv = new ModelAndView();
		PageDTO<Notice> pageDTO;
		if(f.equals("ntc_title")) {
			try {
				String w = "";
				if(word.isPresent()) { 
					w = word.get();
				}
				int cp = 1;
				if(currentPage.isPresent()) { //currentPage
					cp = currentPage.get();
				}
				pageDTO = service.findNtcByTitle(w,f,cp); 
				mnv.addObject("pageDTO", pageDTO);
				mnv.setViewName("noticelistresult.jsp");
			} catch (FindException e) {
				e.printStackTrace();
				mnv.addObject("msg", e.getMessage());
				mnv.setViewName("noticelistresult.jsp");
			}
			
		}else {
			f = "ntc_content"; 		
			try {
				String w = "";
				if(word.isPresent()) { 
					w = word.get();
				}
				int cp = 1;
				if(currentPage.isPresent()) { //currentPage
					cp = currentPage.get();
				}
				pageDTO = service.findNtcByWord(w,cp); 
				mnv.addObject("pageDTO", pageDTO);
				mnv.setViewName("noticelistresult.jsp");
			} catch (FindException e) {
				e.printStackTrace();
				mnv.addObject("msg", e.getMessage());
				mnv.setViewName("noticelistresult.jsp");
			}	
		}
		return mnv;
	}
	
	//공지사항을 수정하는 컨트롤러(일반파일만 수정가능)
	@PostMapping("ntcmodify")
	public String noticeModify(@RequestPart (required = false) MultipartFile letterFiles,
								int ntcIdx,String ntcTitle, String ntcContent, String ntcAttachment, Model model) {

		try {
			Notice n = new Notice();
			n.setNtcIdx(ntcIdx);
			n.setNtcTitle(ntcTitle);
			n.setNtcContent(ntcContent);
			
			//원래 DB에 저장된 첨부파일 이름 가져오기
			String originAttachment = service.findNtcByIdx(ntcIdx).getNtcAttachment();
			
			if(letterFiles == null) {//첨부파일 삭제한경우
				n.setNtcAttachment(ntcAttachment);
			}else if(letterFiles.getOriginalFilename() == originAttachment) {//첨부파일이 기존과 같을경우
				n.setNtcAttachment(originAttachment);
			}else {//첨부파일이 바뀔경우
				n.setNtcAttachment(letterFiles.getOriginalFilename());
			}
			Notice notice = service.modifyNtc(n);
			model.addAttribute("n", notice);
			//데이터베이스에 내용저장 끝
			
			//첨부파일이 바뀔시 저장시작
			String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\images\\noticeimages";
			int wroteBoardNo = notice.getNtcIdx();//저장된 글번호
			
			
	    	//letterFiles 저장
			if(letterFiles != null) {
				long letterFileSize = letterFiles.getSize();
				if(letterFileSize > 0) {
					String letterOriginalFileName = letterFiles.getOriginalFilename();//letter파일 원본이름 얻기
					logger.info("레터 파일이름:" + letterFiles.getOriginalFilename()+" 파일크기: " + letterFiles.getSize());
					//저장할 파일 이름 지정한다 ex) reco_notice_글번호_letter_XXXX_원본이름
					String letterName = "reco_notice_"+wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginalFileName;
					//letter파일 생성
					File file2 = new File(saveDirectory, letterName);
						try {
							FileCopyUtils.copy(letterFiles.getBytes(), file2);
						} catch (IOException e2) {
							e2.printStackTrace();		
							return "failresult.jsp";
						}
				}//end if(letterFileSize > 0) 
			}//end if(letterFiles != null)	
			
			return "noticedetailresult.jsp";
		} catch (ModifyException e) {
			e.printStackTrace();
			return "failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			return "failresult.jsp";
		}	
	}
	
	@GetMapping("/noticedownload")
	public ResponseEntity<Resource>  download(String fileName) throws UnsupportedEncodingException {
		logger.info("첨부파일 다운로드");
		//파일 경로생성
		String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\images\\noticeimages";
		
		//HttpHeaders : 요청/응답헤더용 API
		HttpHeaders headers = new HttpHeaders();	
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream;charset=UTF-8");
		//다운로드시 파일이름 결정
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//Resource : 자원(파일, URL)용 API
			//다운로드할 파일의 실제 경로 얻기
			File f = new File(saveDirectory, fileName);		
			Resource resource = new FileSystemResource(f);
			try {
				logger.info("첨부파일이름: " + resource.getFilename());
				logger.info("첨부파일resource.contentLength()=" + resource.contentLength());
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			ResponseEntity<Resource> responseEntity  =  
					new ResponseEntity<>(resource, headers, HttpStatus.OK);
			return responseEntity;
	}
	
	
	
	 @GetMapping("/noticedownloadimage") 
	 public ResponseEntity<?> downloadImage(String imageFileName) throws UnsupportedEncodingException{
		 String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\images\\noticeimages";
		 File thumbnailFile = new File(saveDirectory,imageFileName);
		 HttpHeaders responseHeaders = new HttpHeaders();
		 try {
			 responseHeaders.set(HttpHeaders.CONTENT_LENGTH, thumbnailFile.length()+"");
			 responseHeaders.set(HttpHeaders.CONTENT_TYPE, Files.probeContentType(thumbnailFile.toPath()));
			 responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+URLEncoder.encode("a", "UTF-8"));
			 logger.info("섬네일파일 다운로드");
			 return new ResponseEntity<>(FileCopyUtils.copyToByteArray(thumbnailFile), responseHeaders, HttpStatus.OK);//success function 호출
		 }catch(IOException e) {
			 return new ResponseEntity<>("이미지 다운로드 실패",HttpStatus.INTERNAL_SERVER_ERROR);
		 }
	}
}
