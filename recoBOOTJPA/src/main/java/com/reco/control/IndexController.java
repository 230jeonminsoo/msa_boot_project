package com.reco.control;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.reco.board.service.BoardService;
import com.reco.board.vo.Board;
import com.reco.calendar.service.CalendarService;
import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.customer.vo.Customer;
import com.reco.dto.PageDTO;
import com.reco.dto.PageDTO2;
import com.reco.exception.FindException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

@Controller
public class IndexController {

	
		@Autowired
		private NoticeService Noticeservice;	
		
		private Logger logger = LoggerFactory.getLogger(this.getClass());
		@Autowired
		private BoardService Boardservice;
		
		@Autowired
		private CalendarService Calendarservice;
		
		@GetMapping("/")
		public String index() {
			return "index.jsp";
		}
		
//		@GetMapping("/noticewritepage")
//		public String noticewrite() {
//			return "noticewrite.jsp";
//		}
		
		@GetMapping("/noticemodifypage")
		public String noticemodify(int ntcIdx,Model model) {
			String saveDirectory = "C:\\reco\\noticeimages";
			File dir = new File(saveDirectory);
			//첨부파일 저장소에서 images이름 가져와서 returnMap에 넣기
			String[] imageFiles = dir.list(new FilenameFilter() {		
				@Override
				public boolean accept(File dir, String name) {
					return name.contains("reco_notice_"+ntcIdx+"_image_");
				}
			});
			
			if(imageFiles.length>0) {
				model.addAttribute("image", imageFiles[0]);
			}
			return "noticemodify.jsp";
		}
		
		@GetMapping("/kakaopopup")
		public String kakaopopup(String code, String email, String pwd, Model model) {
			model.addAttribute("email",email);
			model.addAttribute("pwd",pwd);
			model.addAttribute("code", code);
			return "kakaosignup.jsp";
		}
		
		
		@GetMapping("/boardwritepage")
		public String boardwite() {
			return "boardwrite.jsp";
		}
		
		@GetMapping("/boardmodifypage")
		public String boardmodify(int brdIdx,Model model) {
			String saveDirectory = "C:\\reco\\noticeimages";
			File dir = new File(saveDirectory);
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
			return "boardmodify.jsp";
		}
		
		@GetMapping("/pwdcheck")
		public String pwdcheck(HttpSession session) {
			if(session.getAttribute("myPage") == null) {
				return "pwdcheck.jsp";
			}else {
				return "myprivate.jsp";
			}
		}
		
		//마이페이지 접속 후 첫화면
		//마이캘린더 보는 컨트롤러 
		@GetMapping("/mycallist")
		public Object mycallist(HttpSession session, Model model) {
			ModelAndView mnv = new ModelAndView();
			
			//test12
			Customer c = (Customer)session.getAttribute("loginInfo");
			if(c != null){
				CalInfo calinfo = new CalInfo();
				calinfo.setCustomer(c);
				
				try {
					//생성된 캘린더 리스트 가져오기 
					List<CalInfo> list = Calendarservice.findCalsByUIdx( c.getUIdx());
					mnv.addObject("list", list);
					mnv.addObject("calinfo", calinfo);
					//mnv.setViewName("mycallist.jsp");
				} catch (FindException e) {
					e.printStackTrace();
					mnv.addObject("msg", e.getMessage());
					mnv.addObject("list", new ArrayList<CalInfo>());
					//mnv.setViewName("mycallist.jsp");
				}
			}
			mnv.setViewName("mycallist.jsp");
			return mnv;
		}	
				
		@GetMapping("/calInfomodifypage")
		public String calInfomodify() {
			return "calInfomodify.jsp";
		}
		
		/*
		 * @GetMapping("/mycommunity") public String mycommunity() {
		 *  return "mycommunity.jsp"; 
		 *  }
		 */
		
		//나의 공지사항 글 보는 컨트롤러
		@GetMapping("mycommunity/{uNickname}")
		public Object myCommunity(@PathVariable String uNickname, @PathVariable Optional<Integer> currentPage ,Model model){
			ModelAndView mnv = new ModelAndView();
			PageDTO<Notice> noticePageDTO;
			PageDTO<Board> boardPageDTO;
			PageDTO2<Board> commentPageDTO;
			int cp = 1;
			
			//공지사항 글 가져와서 넣기
			try {		
				if(currentPage.isPresent()) { //currentPage
					cp = currentPage.get();
				}
				
				noticePageDTO = Noticeservice.findNtcByNickname(uNickname, cp, PageDTO.CNT_PER_PAGE);
				mnv.addObject("noticePageDTO", noticePageDTO);
			} catch (FindException e) {
				e.printStackTrace();
				logger.info("공지사항 컨트롤 익셉션 메세지 "+e.getMessage());
				mnv.addObject("msg", e.getMessage());	
			}	
			
			//자유게시판 글 가져와서 넣기
			try {		
				if(currentPage.isPresent()) { //currentPage
					cp = currentPage.get();
				}
				boardPageDTO = Boardservice.findBrdByUNickNameMy(uNickname, cp, PageDTO.CNT_PER_PAGE);
				mnv.addObject("boardPageDTO",boardPageDTO);
			} catch (FindException e1) {
				e1.printStackTrace();
				mnv.addObject("msg1", e1.getMessage());	
			}
			
			//댓글 가져와서 넣기
			try {		
				if(currentPage.isPresent()) { //currentPage
					cp = currentPage.get();
				}
				commentPageDTO = Boardservice.findCmtByUNickName(uNickname, cp, PageDTO2.CNT_PER_PAGE);			
				mnv.addObject("commentPageDTO", commentPageDTO);
			} catch (FindException e2) {
				e2.printStackTrace();
				mnv.addObject("msg2", e2.getMessage());		
			}
			
			//jsp페이지 넣고 mnv로 감.
			mnv.setViewName("mycommunity.jsp");
			return mnv;
		}
		
		@GetMapping("/myprivate")
		public String myprivate(HttpSession session) {
			session.setAttribute("myPage", session);
			return "myprivate.jsp";
		}
		
		@GetMapping("/findEmailPage")
		public String findEmailPage() {
			return "findEmail.jsp";
		}
		

		@GetMapping("/calpostwritepage")
		public String calpostwrite() {
			return "calpostwrite.jsp";
		}
		
		@GetMapping("/calpostmodifypage")
		public String calpostmodify() {
			return "calpostmodify.jsp";
		}
		
		
		
		
		
		
	
		@Controller
		public class CustomErrorController implements ErrorController {

			@GetMapping("/error")
			public String redirectRoot() {

				return "index.jsp";
			}
						
			public String getErrorPath() {
				return "/error";
			}

		}
}
