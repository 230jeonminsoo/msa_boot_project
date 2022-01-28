package com.reco.notice.control;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.RemoveException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;
	
	//공지사항을 추가하는 컨트롤러
	@PostMapping("ntcadd")
	public String noticeAdd(String ntcTitle,String ntcContent,String ntcAttachment,HttpSession session, Model model) {
		Customer c = (Customer)session.getAttribute("loginInfo");
		
		String ntcUNickName = c.getUNickName();
		Notice n = new Notice();
		n.setNtcTitle(ntcTitle);
		n.setNtcContent(ntcContent);
		n.setNtcAttachment(ntcAttachment);
		n.setNtcUNickName(ntcUNickName);
		
		try{
			Notice notice = service.addNtc(n);
			model.addAttribute("n", notice);
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
	public String noticeDetail(int ntcIdx, Model model) {
		try {
			Notice n = service.findNtcByIdx(ntcIdx);
			model.addAttribute("n", n);
			return "noticedetailresult.jsp";
		} catch (FindException e) {
			model.addAttribute("msg",e.getMessage());
			return "failresult.jsp";
		}
	}
	
	//공지사항리스트를 보는 컨트롤러
	@GetMapping("ntclist")
	public ModelAndView noticeList() {
		ModelAndView mnv = new ModelAndView();
		try {
			List<Notice> list=service.findNtcAll();
			mnv.addObject("list", list);
			mnv.setViewName("noticelistresult.jsp");
		} catch (FindException e) {
			e.printStackTrace();
			mnv.addObject("msg", e.getMessage());
			mnv.setViewName("failresult.jsp");
		}
		return mnv;
	}
	//공지사항을 수정하는 컨트롤러
	
	//공지사항을 삭제하는 컨트롤러
	@DeleteMapping("ntcremove")
	public String noticeRemove(int ntcIdx, Model model) {
		try {
			service.removeNtc(ntcIdx);
			return "./ntclist";
		} catch (RemoveException e) {
			System.out.println(e.getMessage());
			model.addAttribute("status", 0);
			model.addAttribute("msg", e.getMessage());
			return "failresult.jsp";
		}
	}
	
	//공지사항을 검색하는 컨트롤러
	@GetMapping("ntcsearch")
	public String noticeSearch(String f, String q, Model model) {
		if(f.equals("ntc_title")) {
			try {
				List<Notice> list = service.findNtcByTitle(q); 
				model.addAttribute("list", list);
				return "noticelistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				return "failresult.jsp";
			}
			
		}else {
			f = "ntc_content"; 		
			try {
				List<Notice> list = service.findNtcByWord(q); 
				model.addAttribute("list", list);
				return "noticelistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				return "failresult.jsp";
			}	
		}
	}
}
