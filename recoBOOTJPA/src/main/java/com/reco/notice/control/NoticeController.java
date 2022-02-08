package com.reco.notice.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reco.customer.vo.Customer;
import com.reco.dto.PageDTO;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.service.NoticeService;
import com.reco.notice.vo.Notice;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService service;
	

	private Logger log = LoggerFactory.getLogger(NoticeService.class.getName());
	
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
			mnv.setViewName("failresult.jsp");
		}
		return mnv;
	}
	//공지사항을 수정하는 컨트롤러
	
	//공지사항을 삭제하는 컨트롤러
	@GetMapping("ntcremove")
	public String noticeRemove(int ntcIdx, Model model) {
		try {
			service.removeNtc(ntcIdx);
			return "noticelistresult.jsp";
		} catch (RemoveException e) {
			System.out.println(e.getMessage());
			model.addAttribute("status", 0);
			model.addAttribute("msg", e.getMessage());
			return "failresult.jsp";
		}
	}
	
	//공지사항을 검색하는 컨트롤러
	@GetMapping(value = {"ntcsearch/{word}","ntcsearch/{word}/{currentPage}"})
	public String noticeSearch(@PathVariable Optional<String> word, String f,@PathVariable Optional<Integer> currentPage ,Model model) {
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
				pageDTO = service.findNtcByTitle(w,cp); 
				model.addAttribute("pageDTO", pageDTO);
				return "noticelistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				return "failresult.jsp";
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
				model.addAttribute("pageDTO", pageDTO);
				return "noticelistresult.jsp";
			} catch (FindException e) {
				e.printStackTrace();
				return "failresult.jsp";
			}	
		}
	}
	
	@PostMapping("ntcmodify")
	public String noticeModify(int ntcIdx,String ntcTitle, String ntcContent, String ntcAttachment, Model model) {
		Notice n = new Notice();
		n.setNtcIdx(ntcIdx);
		n.setNtcTitle(ntcTitle);
		n.setNtcContent(ntcContent);
		n.setNtcAttachment(ntcAttachment);
		try {
			Notice notice = service.modifyNtc(n);
			model.addAttribute("n", notice);
			return "noticedetailresult.jsp";
		} catch (ModifyException e) {
			e.printStackTrace();
			return "failresult.jsp";
		} catch (FindException e) {
			e.printStackTrace();
			return "failresult.jsp";
		}
	}
}
