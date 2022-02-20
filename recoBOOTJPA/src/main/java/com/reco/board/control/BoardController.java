package com.reco.board.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.reco.board.service.BoardService;
import com.reco.board.vo.Board;
import com.reco.board.vo.Comment;
import com.reco.customer.vo.Customer;
import com.reco.dto.PageDTO;
import com.reco.dto.PageDTO2;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

import net.coobird.thumbnailator.Thumbnailator;
@Controller
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Logger log = LoggerFactory.getLogger(BoardService.class.getName());
	
	@Autowired
	private NoticeService NoticeService;
	
	//자유게시판 게시글 추가
	@PostMapping("brdadd")
	public Object boardAdd( @RequestPart (required = false) MultipartFile letterFiles
							,@RequestPart (required = false) MultipartFile imageFile
							,int brdType,String brdTitle,String brdContent,String brdAttachment,HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		
		String brdUNickName = c.getUNickName();
		Board b = new Board();
		b.setBrdType(brdType);
		b.setBrdTitle(brdTitle);
		b.setBrdContent(brdContent);
		b.setBrdUNickName(brdUNickName);
		if(letterFiles != null) {
			b.setBrdAttachment(letterFiles.getOriginalFilename());
		}else {
			b.setBrdAttachment(brdAttachment); //?
		}
		
		ModelAndView mnv = new ModelAndView();
		
		try{
			PageDTO2<Board> PageDTO2 = service.addBrd(b);
			Board board = PageDTO2.getBoard();
//			int exbrd = board.getBrdIdx();
//			System.out.println("테스트" + exbrd);
			mnv.addObject("PageDTO2", PageDTO2);
			logger.info("컨트롤러 addbrd 1:" + board.getBrdIdx() + board.getBrdTitle() + board.getBrdContent());
			
			//파일을 저장할 폴더가 없다면 만들기. 있다면 만들지 않음
			String saveDirectory = "C:\\reco\\boardimages";
			if ( ! new File(saveDirectory).exists()) {
				logger.info("업로드 실제경로생성");
				new File(saveDirectory).mkdirs(); //파일경로가 없다면 만듬
			}
			
			int wroteBoardNo = board.getBrdIdx();//저장된 글번호
					
			//이미지파일 저장
			File thumbnailFile = null;
			if(imageFile !=null) {
				long imageFileSize = imageFile.getSize();
					if(imageFileSize != 0) {
						String imageFileName = imageFile.getOriginalFilename(); //업로드할 이미지 파일의 이름가져옴
						logger.info("이미지파일 이름:" + imageFileName +" 이미지파일 사이즈 " + imageFile.getSize());
						
						//업로드할  이미지 파일의 이름을 새로생성
						String fileName ="reco_board_"+wroteBoardNo + "_image_" + UUID.randomUUID() + "_" + imageFileName;
						//이미지파일 생성
						File file = new File(saveDirectory, fileName);
						
						
						try {
							FileCopyUtils.copy(imageFile.getBytes(), file);
							
							//이미지파일의 타입가져와서 image가 아니면 실패
							if(imageFile !=null) {
								String contentType = imageFile.getContentType();
								if(!contentType.contains("image/")) {  //이미지파일형식이 아닌 경우
									mnv.setViewName("failresult.jsp");
								}
							}
						
					
						//이미지파일인 경우 섬네일파일을 만듦
						String thumbnailName =  "reco_board_"+ wroteBoardNo+"_image_"+imageFileName; //섬네일 파일명은 reco_notice_글번호_image_원본이름
						thumbnailFile = new File(saveDirectory,thumbnailName); //저장결로에 섬네일파일 만듬
						FileOutputStream thumbnailOS;
						thumbnailOS = new FileOutputStream(thumbnailFile);
						InputStream imageFileIS = imageFile.getInputStream();
						int width = 300;
						int height = 300;
						Thumbnailator.createThumbnail(imageFileIS, thumbnailOS, width, height);
						} catch (IOException e) {
							e.printStackTrace();
						}logger.info("섬네일파일 저장:" + thumbnailFile.getAbsolutePath() + ", 섬네일파일 크기::" + thumbnailFile.length());
					}
			  }
		
	    	//letterFiles도 저장
			if(letterFiles != null) {
				long letterFileSize = letterFiles.getSize();
					if(letterFileSize > 0) {
						String letterOriginalFileName = letterFiles.getOriginalFilename();//letter파일 원본이름 얻기
						logger.info("레터 파일이름:" + letterFiles.getOriginalFilename()+" 파일크기: " + letterFiles.getSize());
						//저장할 파일 이름 지정한다 ex) reco_board_글번호_letter_XXXX_원본이름
						String letterName = "reco_board_"+wroteBoardNo + "_letter_" + UUID.randomUUID() + "_" + letterOriginalFileName;
						//letter파일 생성
						File file2 = new File(saveDirectory, letterName);
							try {
								FileCopyUtils.copy(letterFiles.getBytes(), file2);
							} catch (IOException e2) {
								e2.printStackTrace();		
								mnv.setViewName("failresult.jsp");
							}
					}//end if(letterFileSize > 0) 
				}//end for
			//end if(letterFiles != null)						
			logger.info("컨트롤러 addbrd 2:" + board.getBrdIdx() + board.getBrdTitle() + board.getBrdContent());	
			File dir = new File(saveDirectory);
			if(b.getBrdAttachment() !=null) {
				//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
				String[] letterFileNames = dir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_board_"+board.getBrdIdx()+"_letter_");//b.getBrdAttachment());
					}
				});
				if(letterFileNames.length>0) {
					mnv.addObject("letter", letterFileNames[0]);
				}
			}		
			
			//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
			String[] imageFiles = dir.list(new FilenameFilter() {		
				@Override
				public boolean accept(File dir, String name) {
					return name.contains("reco_board_"+board.getBrdIdx()+"_image_");
				}
			});
			
			if(imageFiles.length > 0) {
				mnv.addObject("image", imageFiles[0]);
			}
		
			mnv.setViewName("boarddetailresult.jsp");
		} catch(AddException e){
			e.getStackTrace();
			model.addAttribute("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		}
		return mnv;
	}
	
	
	//자유게시판 상세페이지 
		@GetMapping(value={"brddetail", "brddetail/{currentPage}"}) //model실패
		public Object boardDetail(@PathVariable Optional<Integer> currentPage, 
								  int brdIdx) {
			
			ModelAndView mnv = new ModelAndView();
			try {
				PageDTO2<Board> pageDTO2;
				if(currentPage.isPresent()) {
					log.info("컨트롤러 현재페이지"+currentPage);
					log.info("컨트롤러brdIdx" + brdIdx);
					int cp = currentPage.get();
					pageDTO2 = service.findBrdByIdx(brdIdx,cp);
					log.info("댓글리스트"+pageDTO2.getComments()+"현재페이지"+pageDTO2.getCurrentPage());
					
				}else {
					pageDTO2= service.findBrdByIdx(brdIdx);
					log.info("findBrdByIdx콘트롤러pageDTO2" + pageDTO2);
				}
				mnv.addObject("PageDTO2", pageDTO2);
				String saveDirectory = "C:\\reco\\boardimages";
				File dir = new File(saveDirectory);
				Board b = pageDTO2.getBoard();
				if(b.getBrdAttachment() !=null) {
				//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
				String[] letterFileNames = dir.list(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_board_"+brdIdx+"_letter_");
					}
				});
				if(letterFileNames.length>0) {
					mnv.addObject("letter", letterFileNames[0]);
				}
			}
				
				//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
				String[] imageFiles = dir.list(new FilenameFilter() {	
					
					@Override
					public boolean accept(File dir, String name) {
						return name.contains("reco_board_"+brdIdx+"_image_");
					}
				});
				
				if(imageFiles.length>0) {
					mnv.addObject("image", imageFiles[0]);
				}
				mnv.setViewName("boarddetailresult.jsp");
			} catch (FindException e) {
				mnv.addObject("msg",e.getMessage());
				mnv.setViewName("failresult.jsp");
			}
			return mnv;
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
				mnv.setViewName("boardlistresult.jsp");
			}
			return mnv;
		}
	
		
		
		@PostMapping("brdmodify") //상세검색 문제 부분
		public String boardModify(@RequestPart (required = false) MultipartFile letterFiles,
								int brdIdx,int brdType, String brdTitle, String brdContent, String brdAttachment, Model model) throws FindException {
			try {
				Board b = new Board();
				b.setBrdIdx(brdIdx);
				b.setBrdType(brdType);
				b.setBrdTitle(brdTitle);
				b.setBrdContent(brdContent);
				
				
				
				
				//원래 DB에 저장된 첨부파일 이름 가져오기
				PageDTO2<Board> getFileNameDTO = service.findBrdByIdx(brdIdx);
				String originAttachment = getFileNameDTO.getBoard().getBrdAttachment();
				logger.info("컨트롤러 오리지널 파일 네임"+letterFiles.getOriginalFilename());
				
				if(letterFiles.getOriginalFilename() == "") { //attachment no 
					b.setBrdAttachment(originAttachment);
					logger.info("컨트롤러"+originAttachment);
				}else { //attachment 
					b.setBrdAttachment(letterFiles.getOriginalFilename());
				}
					service.modifyBrd(b);
					PageDTO2<Board> PageDTO2 = service.findBrdByIdx(brdIdx);
					Board board = PageDTO2.getBoard();
					
					model.addAttribute("PageDTO2", PageDTO2);
					//데이터베이스에 내용저장 끝
					
					//첨부파일이 바뀔시 저장시작
					String saveDirectory = "C:\\reco\\boardimages";
					int wroteBoardNo = board.getBrdIdx();//저장된 글번호
					
					
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
					
					File dir = new File(saveDirectory);
					if(board.getBrdAttachment() !=null) {
						//첨부파일 저장소에서 letters이름 가져와서 returnMap에 넣기
						String[] letterFileNames = dir.list(new FilenameFilter() {
							
							@Override
							public boolean accept(File dir, String name) {
								return name.contains("reco_notice_"+board.getBrdIdx()+"_letter_");//b.getBrdAttachment());
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
							return name.contains("reco_board_"+brdIdx+"_image_");
						}
					});
					
					if(imageFiles.length>0) {
						model.addAttribute("image", imageFiles[0]);
					}
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
				PageDTO<Board> pageDTO;
				pageDTO = service.findBrdAll();
				model.addAttribute("pageDTO", pageDTO);
				return "boardlistresult.jsp";
			} catch (RemoveException | FindException e) {
				System.out.println(e.getMessage());
				model.addAttribute("msg", e.getMessage());
				return "boardlistresult.jsp";
			}
		}
		
		
		@GetMapping({"boardfilter/{intBrdType}", "boardfilter/{intBrdType}/{currentPage}"})
		public String boardFilter(@PathVariable int intBrdType, @PathVariable Optional<Integer> currentPage, Model model) { //f는 brdType
			
			PageDTO<Board> pageDTO;
			if(intBrdType == 0) {
				try {
					int cp = 1;
					if(currentPage.isPresent()) {
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByType(intBrdType,cp); 
					model.addAttribute("pageDTO",pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}
			}else if(intBrdType == 1) {
				try {
					int cp = 1;
					if(currentPage.isPresent()) {
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByType(intBrdType,cp); 
					model.addAttribute("pageDTO",pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}
			}else if(intBrdType == 2) {
				try {
					int cp = 1;
					if(currentPage.isPresent()) {
						cp = currentPage.get();
					}
					pageDTO = service.findBrdByType(intBrdType,cp); 
					model.addAttribute("pageDTO",pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}
			}else {
				intBrdType = 3;
				try {
					int cp = 1;
					if(currentPage.isPresent()) {
						cp = currentPage.get();
					}
					pageDTO = service.findBrdAll(); 
					model.addAttribute("pageDTO",pageDTO);
					return "boardlistresult.jsp";
				} catch (FindException e) {
					e.printStackTrace();
					return "failresult.jsp";
				}
			}
		}
			
		
		//자유게시판 검색
		@GetMapping(value = {"brdsearch/{word}/{f}","brdsearch/{word}/{f}/{currentPage}"})
		public Object boardSearch(@PathVariable Optional<String> word, @PathVariable String f,@PathVariable Optional<Integer> currentPage ,Model model) {
			ModelAndView mnv = new ModelAndView();
			PageDTO<Board> pageDTO;
			logger.info("search값" + f);
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
					pageDTO = service.findBrdByTitle(w,f,cp); 
					mnv.addObject("pageDTO", pageDTO);
					mnv.setViewName("boardlistresult.jsp");
				} catch (FindException e) {
					e.printStackTrace();
					mnv.addObject("msg", e.getMessage());
					mnv.setViewName("boardlistresult.jsp");
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
					pageDTO = service.findBrdByWord(w,f,cp); 
					mnv.addObject("pageDTO", pageDTO);
					mnv.setViewName("boardlistresult.jsp");
				} catch (FindException e) {
					e.printStackTrace();
					mnv.addObject("msg", e.getMessage());
					mnv.setViewName("boardlistresult.jsp");
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
					pageDTO = service.findBrdByUNickName(w,f,cp); 
					mnv.addObject("pageDTO", pageDTO);
					mnv.setViewName("boardlistresult.jsp");
				} catch (FindException e) {
					e.printStackTrace();
					mnv.addObject("msg", e.getMessage());
					mnv.setViewName("boardlistresult.jsp");
				}
			}
			return mnv;
		}
		
	
		
		
		@GetMapping("/boarddownload")
		public ResponseEntity<Resource>  download(String fileName) throws UnsupportedEncodingException {
			logger.info("첨부파일 다운로드");
			//파일 경로생성
			String saveDirectory = "C:\\reco\\boardimages";
			
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
		
		
		@GetMapping("/boarddownloadimage") 
		 public ResponseEntity<?> downloadImage(String imageFileName) throws UnsupportedEncodingException{
			 String saveDirectory = "C:\\reco\\boardimages";
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
		
		
		@PostMapping("cmtadd")
		public String commentAdd(int brdIdx, String cmtContent, int cmtParentIdx, HttpSession session, Model model) {
			Customer c = (Customer)session.getAttribute("loginInfo");
			String cmtUNickName = c.getUNickName();
			Comment comment = new Comment();
			//comment.setBrdIdx(brdIdx);
			Board brd = new Board();
			brd.setBrdIdx(brdIdx);
			comment.setBrd(brd);
			comment.setCmtContent(cmtContent);	
			comment.setCmtParentIdx(cmtParentIdx);	
			comment.setCmtUNickName(cmtUNickName);
			
			try{
				PageDTO2<Board> PageDTO2 = service.addCmt(comment);
			    model.addAttribute("PageDTO2", PageDTO2);
			    return "boarddetailresult.jsp";
			}catch(AddException | FindException e){
				e.getStackTrace();
				return "failresult.jsp";
			}
		}
		
		@GetMapping("cmtmodify") //상세검색 문제 부분
		public String commentModify(int brdIdx, int cmtIdx, String cmtContent, Model model) {
			Comment comment = new Comment();
			//comment.setBrdIdx(brdIdx);
			Board brd = new Board();
			brd.setBrdIdx(brdIdx);
			comment.setBrd(brd);
			comment.setCmtIdx(cmtIdx);
			comment.setCmtContent(cmtContent);
			
			try {
				service.modifyCmt(comment);
				PageDTO2<Board> PageDTO2 = service.findBrdByIdx(brdIdx);
				model.addAttribute("PageDTO2", PageDTO2);
				return "boarddetailresult.jsp";
			} catch (ModifyException e) {
				e.getStackTrace();
				return "failresult.jsp";
			} catch (FindException e) {
				e.getStackTrace();
				return "failresult.jsp";
			}			
		}
		
		
		@GetMapping("cmtremove") //상세검색 문제 부분
		public String commentRemove(int brdIdx, int cmtIdx, Model model) {
			try {
				service.removeCmt(brdIdx, cmtIdx);
				PageDTO2<Board> PageDTO2 = service.findBrdByIdx(brdIdx);
				model.addAttribute("PageDTO2", PageDTO2);
				return "boarddetailresult.jsp";
			} catch (RemoveException e) {
				e.getStackTrace();
				return"failresult.jsp";
			} catch (FindException e) {
				e.getStackTrace();
				return"failresult.jsp";
			}
		}
		
		//마이페이지에서 체크된 자유게시판글을 삭제하는 컨트롤러
		@GetMapping("mybrdremove")
		public String boardRemove(int brdIdx0, Optional<Integer> brdIdx1, Optional<Integer> brdIdx2, Optional<Integer> brdIdx3, Optional<Integer> brdIdx4, Model model) throws FindException {
			String uNickname = service.findBrdByIdx(brdIdx0).getBoard().getBrdUNickName();
			ModelAndView mnv = new ModelAndView();
			PageDTO<Notice> noticePageDTO;
			PageDTO<Board> boardPageDTO;
			PageDTO2<Board> commentPageDTO;
			int cp = 1;
			
			try {			
					service.removeBrd(brdIdx0);
					if(brdIdx1.isPresent()) {
						service.removeBrd(brdIdx1.get());
					}
					if(brdIdx2.isPresent()) {
						service.removeBrd(brdIdx2.get());
					}
					if(brdIdx3.isPresent()) {
						service.removeBrd(brdIdx3.get());
					}
					if(brdIdx4.isPresent()) {
						service.removeBrd(brdIdx4.get());
					}
				noticePageDTO = NoticeService.findNtcByNickname(uNickname, cp, PageDTO.CNT_PER_PAGE);
				mnv.addObject("noticePageDTO", noticePageDTO);
				boardPageDTO = service.findBrdByUNickName(uNickname, cp, PageDTO.CNT_PER_PAGE);				
				mnv.addObject("boardPageDTO",boardPageDTO);
				commentPageDTO = service.findCmtByUNickName(uNickname, cp, PageDTO2.CNT_PER_PAGE);
				mnv.addObject("commentPageDTO", commentPageDTO);
					return "mycommunity.jsp";
			} catch (RemoveException e) {
				System.out.println(e.getMessage());
//				model.addAttribute("msg", e.getMessage());
				return "mycommunity.jsp";
			}
		}
		
		
		//마이페이지 자유게시판글 페이징 컨트롤러
		@GetMapping("mybrd/{uNickname}/{currentPage}")
		public Object myBrd(@PathVariable String uNickname, @PathVariable int currentPage ,Model model){
			ModelAndView mnv = new ModelAndView();
			PageDTO<Notice> noticePageDTO;
			PageDTO<Board> boardPageDTO;
			PageDTO2<Board> commentPageDTO;
			int cp = 1;
			try {
				boardPageDTO = service.findBrdByUNickName(uNickname, currentPage, PageDTO.CNT_PER_PAGE);
				mnv.addObject("boardPageDTO", boardPageDTO);
				
				noticePageDTO = NoticeService.findNtcByNickname(uNickname, cp, PageDTO.CNT_PER_PAGE);
				mnv.addObject("noticePageDTO", noticePageDTO);
								
				commentPageDTO = service.findCmtByUNickName(uNickname, cp, PageDTO2.CNT_PER_PAGE);
				mnv.addObject("commentPageDTO", commentPageDTO);
				
				mnv.setViewName("mycommunity.jsp");
			} catch (FindException e) {
				e.printStackTrace();
				mnv.addObject("msg", e.getMessage());
				mnv.setViewName("mycommunity.jsp");
			}
			return mnv;
		}
		
		//마이페이지 댓글 페이징 컨트롤러
				@GetMapping("mycmt/{uNickname}/{currentPage}")
				public Object mycmt(@PathVariable String uNickname, @PathVariable int currentPage ,Model model){
					ModelAndView mnv = new ModelAndView();
					PageDTO2<Board> commentPageDTO;

					try {
						commentPageDTO = service.findCmtByUNickName(uNickname, currentPage, PageDTO.CNT_PER_PAGE);
						mnv.addObject("commentPageDTO", commentPageDTO);
						mnv.setViewName("mycommunity.jsp");
					} catch (FindException e) {
						e.printStackTrace();
						mnv.addObject("msg", e.getMessage());
						mnv.setViewName("mycommunity.jsp");
					}
					return mnv;
				}
				
				
				//마이페이지에서 체크된 댓글을 삭제하는 컨트롤러
				@GetMapping("mycmtremove")
				public String commentRemove(Optional<String> brdIdx,  Optional<String> cmtIdx) {
					
					
					//String uNickname = service.findCmt
//					ModelAndView mnv = new ModelAndView();
//					PageDTO<Notice> noticePageDTO;
//					PageDTO<Board> boardPageDTO;
//					PageDTO2<Board> commentPageDTO;
//					int cp = 1;
//					
					int brdIdxMy0 = 0;
					int cmtIdxMy0 = 0;
					
					try {			
						 	if(brdIdx.isPresent()) {
						 		brdIdxMy0 = Integer.parseInt(brdIdx.get());
						 	}
							if(cmtIdx.isPresent()) {
								cmtIdxMy0 = Integer.parseInt(cmtIdx.get());
						 	}

						 	System.out.println("콘트롤러값 brdIdx0체크:" + brdIdxMy0);
						 	System.out.println("콘트롤러값 cmtIdxMy0체크:" + cmtIdxMy0);
							//service.removeCmt(brdIdx, cmtIdx);
//							if((brdIdxMy1.isPresent() & cmtIdxMy1.isPresent())) {
//								service.removeCmt(brdIdxMy1.get(), cmtIdxMy1.get());
//							}
//							if((brdIdxMy2.isPresent() & cmtIdxMy2.isPresent())) {
//								service.removeCmt(brdIdxMy2.get(), cmtIdxMy2.get());
//							}
//							if((brdIdxMy3.isPresent() & cmtIdxMy3.isPresent())) {
//								service.removeCmt(brdIdxMy3.get(), cmtIdxMy3.get());
//							}
//							if((brdIdxMy4.isPresent() & cmtIdxMy4.isPresent())) {
//								service.removeCmt(brdIdxMy4.get(), cmtIdxMy4.get());
//							}
							//PageDTO<Board> pageDTO;
							
							//pageDTO = service.findBrdAll();
							//logger.info("mybrdremove컨트롤러 pageDTO값" + pageDTO);
							//model.addAttribute("boardPageDTO", pageDTO);
							return "mycommunity.jsp";
					} catch (Exception e) { // | FindException RemoveException
						System.out.println(e.getMessage());
//						model.addAttribute("msg", e.getMessage());
						return "mycommunity.jsp";
					}
				}
}
