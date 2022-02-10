package com.reco.board.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reco.board.service.BoardService;
import com.reco.board.vo.Board;
import com.reco.customer.vo.Customer;
import com.reco.dto.PageDTO;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

import net.coobird.thumbnailator.Thumbnailator;

public class BoardController {
	
	@Autowired
	private BoardService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Logger log = LoggerFactory.getLogger(BoardService.class.getName());
	
	
	//자유게시판 게시글 추가
	@PostMapping("brdadd")
	public String boardAdd( @RequestPart (required = false) List<MultipartFile> letterFiles
							,@RequestPart (required = false) MultipartFile imageFile
							,int intBrdType,String brdTitle,String brdContent,String brdAttachment,HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		
		String brdUNickName = c.getUNickName();
		Board b = new Board();
		b.setBrdIdx(intBrdType);
		b.setBrdTitle(brdTitle);
		b.setBrdContent(brdContent);
		b.setBrdAttachment(brdAttachment);
		b.setBrdUNickName(brdUNickName);
		
		try{
			Board board = service.addBrd(b);
			model.addAttribute("b", board);
			
			//파일을 저장할 폴더가 없다면 만들기. 있다면 만들지 않음
			String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\boardimages";
			if ( ! new File(saveDirectory).exists()) {
				logger.info("업로드 실제경로생성");
				new File(saveDirectory).mkdirs();
			}
			
			int wroteBoardNo = board.getBrdIdx();//저장된 글번호
			long imageFileSize = imageFile.getSize();
			
			//이미지파일 저장
			File thumbnailFile = null;
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
					String contentType = imageFile.getContentType();
					if(!contentType.contains("image/")) { 
						return "failresult.jsp";
					}
					
					
					//이미지파일인 경우 섬네일파일을 만듦
					String thumbnailName =  "reco_board_"+ wroteBoardNo+"_image_"+imageFileName; //섬네일 파일명은 reco_notice_글번호_image_원본이름
					thumbnailFile = new File(saveDirectory,thumbnailName);
					FileOutputStream thumbnailOS;
					thumbnailOS = new FileOutputStream(thumbnailFile);
					InputStream imageFileIS = imageFile.getInputStream();
					int width = 100;
					int height = 100;
					Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
					logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기::" + thumbnailFile.length());
					
			    	//letterFiles도 저장
					if(letterFiles != null) {
						for(int i = 0; i<letterFiles.size();i++) {
							
						long letterFileSize = letterFiles.size();
							if(letterFileSize > 0) {
								String letterOriginalFileName = letterFiles.get(i).getOriginalFilename();//letter파일 원본이름 얻기
								logger.info("레터 파일이름:" + letterFiles.get(i).getOriginalFilename()+" 파일크기: " + letterFiles.get(i).getSize());
								//저장할 파일 이름 지정한다 ex) reco_board_글번호_letter_XXXX_원본이름
								String letterName = "reco_board_"+wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginalFileName;
								//letter파일 생성
								File file2 = new File(saveDirectory, letterName);
									try {
										FileCopyUtils.copy(letterFiles.get(i).getBytes(), file2);
									} catch (IOException e2) {
										e2.printStackTrace();		
										return "failresult.jsp";
									}
							}//end if(letterFileSize > 0) 
						}//end for
					}//end if(letterFiles != null)						
						
				} catch (IOException e2) {
					e2.printStackTrace();
					//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			
			return "boarddetailresult.jsp";
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
	
	
	//자유게시판 상세페이지 
		@GetMapping("brddetail")
		public Object boardDetail(int brdIdx, Model model) {
			try {
				Board b = service.findBrdByIdx(brdIdx);
				model.addAttribute("b", b);
				String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\boardimages";
				File dir = new File(saveDirectory);
				
				//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
				String[] letterFileNames = dir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_board_"+brdIdx+"_letter_");
					}
				});
				if(letterFileNames.length>0) {
					model.addAttribute("letters", letterFileNames);
				}
				
				//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
				String[] imageFiles = dir.list(new FilenameFilter() {		
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_board_"+brdIdx+"_image_");
					}
				});
				
				if(imageFiles.length > 0) {
					model.addAttribute("image", imageFiles[0]);
				}
				return "boarddetailresult.jsp";
			} catch (FindException e) {
				model.addAttribute("msg",e.getMessage());
				return "failresult.jsp";
			}
		}
		
		
		//자유게시판 목록
		@GetMapping(value = {"brdlist", "brdlist/{currentPage}"})
		public Object boardList(@PathVariable Optional<Integer> currentPage) {
			ModelAndView mnv = new ModelAndView();
			try {
				PageDTO<Board> pageDTO;
				if(currentPage.isPresent()) {
					log.info("컨트롤러 현재페이지"+currentPage);
					int cp = currentPage.get();
					pageDTO= service.findBrdAll(cp);
					log.info("리스트"+pageDTO.getList()+"현재페이지"+pageDTO.getCurrentPage());
					
				}else {
					pageDTO= service.findBrdAll();
				}
				mnv.addObject("pageDTO", pageDTO);
				mnv.setViewName("boardlistresult.jsp");
			} catch (FindException e) {
				e.printStackTrace();
				mnv.addObject("msg", e.getMessage());
				mnv.setViewName("failresult.jsp");
			}
			return mnv;
		}
	
	
		
		@PostMapping("brdmodify")
		public String boardModify(int brdIdx,int brdType, String brdTitle, String brdContent, String brdAttachment, Model model) {
			Board b = new Board();
			b.setBrdIdx(brdIdx);
			b.setBrdType(brdType);
			b.setBrdTitle(brdTitle);
			b.setBrdContent(brdContent);
			b.setBrdAttachment(brdAttachment);
						
			try {
				Board board= service.modifyBrd(b);
				model.addAttribute("b", board);
				return "boarddetailresult.jsp";
			} catch (ModifyException e) {
				e.printStackTrace();
				return "failresult.jsp";
			} catch (Exception e) { //왜 findException 오류나는지
				e.printStackTrace();
				return "failresult.jsp";
			}
		}

		
		@GetMapping("brdremove")
		public String boardRemove(int brdIdx, Model model) {
			try {
				service.removeBrd(brdIdx);
				return "boardlistresult.jsp";
			} catch (RemoveException e) {
				System.out.println(e.getMessage());
				model.addAttribute("status", 0);
				model.addAttribute("msg", e.getMessage());
				return "failresult.jsp";
			}
		}
		
		
		//자유게시판 검색
		@GetMapping(value = {"brdsearch/{word}","brdsearch/{word}/{currentPage}"})
		public String boardSearch(@PathVariable Optional<String> word, String f,@PathVariable Optional<Integer> currentPage ,Model model) {
			PageDTO<Board> pageDTO;
			if(f.equals("brd_title")) {
				try {
					String w = "";
					if(word.isPresent()) { 
						w = word.get();
					}
					int cp = 1;
					if(currentPage.isPresent()) { //currentPage
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByTitle(w,cp); 
					model.addAttribute("pageDTO", pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}
				
			}else if(f.equals("brd_content")) {
				try {
					String w = "";
					if(word.isPresent()) { 
						w = word.get();
					}
					int cp = 1;
					if(currentPage.isPresent()) { //currentPage
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByWord(w,cp); 
					model.addAttribute("pageDTO", pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}	
			}else {
				f = "brd_UNickName";
				try {
					String w = "";
					if(word.isPresent()) { 
						w = word.get();
					}
					int cp = 1;
					if(currentPage.isPresent()) { //currentPage
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByUNickName(w,cp); 
					model.addAttribute("pageDTO", pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}	
			}		
		}
		
		
		
		@GetMapping("/download")
		public ResponseEntity<Resource>  download(String fileName) throws UnsupportedEncodingException {
			logger.info("첨부파일 다운로드");
			//파일 경로생성
			String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\boardimages";
			
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
		
		
		@GetMapping("/downloadimage") 
		 public ResponseEntity<?> downloadImage(String imageFileName) throws UnsupportedEncodingException{
			 String saveDirectory = "C:\\230\\msa_boot_project\\recoBOOTJPA\\src\\main\\resources\\static\\boardimages";
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