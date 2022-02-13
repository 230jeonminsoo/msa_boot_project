package com.reco.control;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

		@GetMapping("/")
		public String index() {
			return "index.jsp";
		}
		
//		@GetMapping("/noticewritepage")
//		public String noticewrite() {
//			return "noticewrite.jsp";
//		}
		
		@GetMapping("/noticemodifypage")
		public String noticemodify() {
			return "noticemodify.jsp";
		}
		
		@GetMapping("/boardwritepage")
		public String boardwite() {
			return "boardwrite.jsp";
		}
		
		@GetMapping("/boardmodifypage")
		public String boardmodify() {
			return "boardmodify.jsp";
		}
		
		@GetMapping("/pwdcheck")
		public String pwdcheck(HttpSession session) {
			if(session.getAttribute("myPage") == null) {
				return "pwdcheck.jsp";
			}else {
				return "mycallist.jsp";
			}
		}
		
		@GetMapping("/mycallist")
		public String mycallist(HttpSession session) {
			session.setAttribute("myPage", session);
			return "mycallist.jsp";
		}		
		
		/*
		 * @GetMapping("/mycommunity") public String mycommunity() {
		 *  return "mycommunity.jsp"; 
		 *  }
		 */
		
		@GetMapping("/myprivate")
		public String myprivate() {
			return "myprivate.jsp";
		}
		
}
