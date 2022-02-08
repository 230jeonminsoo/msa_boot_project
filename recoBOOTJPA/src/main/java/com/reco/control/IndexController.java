package com.reco.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

		@GetMapping("/")
		public String index() {
			return "index.jsp";
		}
		
		@GetMapping("/noticewritepage")
		public String noticewrite() {
			return "noticewrite.jsp";
		}
		
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
		
}
