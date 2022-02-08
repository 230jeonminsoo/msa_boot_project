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
		
		@GetMapping("/noticewritePage")
		public String noticewrite() {
			return "noticewrite.jsp";
		}
		
		@GetMapping("/noticemodifyPage")
		public String noticemodify() {
			return "noticemodify.jsp";
		}
}
