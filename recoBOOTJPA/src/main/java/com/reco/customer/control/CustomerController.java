package com.reco.customer.control;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reco.customer.service.CustomerService;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@GetMapping("/emaildupchk")
	@ResponseBody
	public Map<String, Object> emaildupchk(String email){
		String resultMsg = "";
		int status = 0;
		try {
			service.emaildupchk(email);
			resultMsg = "이미 사용중인 아이디입니다";
		}catch (FindException e) {
			e.printStackTrace();
			status = 1;
			resultMsg = "사용가능한 아이디입니다";
		}
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg", resultMsg);
		return returnMap;
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(String email, String pwd, HttpSession session){
		session.removeAttribute("loginInfo"); 
		String resultMsg = "";
		int status = 0;
		try {
			Customer c = service.login(email, pwd);
			session.setAttribute("loginInfo", c);
			resultMsg = "로그인 성공";
			status = 1;
			if(c.getuStatus() == 0) {
				throw new FindException("탈퇴한 사용자입니다.");
			}
		}catch (FindException e) {
			resultMsg = "로그인 실패";
		}
		
		//return resultMsg;
//		return "{\"status\":"+status+", \"msg\":\""+resultMsg+"\"}";
		
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("msg",resultMsg);
		return returnMap;		
	}
	
	@RequestMapping("/logout")
	public ResponseEntity logout(HttpSession session) {
		session.removeAttribute("loginInfo");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/nickdupchk")
	public Map<String, Object> nickdupchk(String nickname){
		String resultMsg = "";	
		int status = 0;
		try {
			service.nickdupchk(nickname);
			resultMsg = "이미 사용중인 닉네임입니다";
		}catch (FindException e) {
			e.printStackTrace();
			status = 1;
			resultMsg = "사용가능한 닉네임입니다";
		}
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg", resultMsg);
		return returnMap;		
	}
	
	@PostMapping("/signup")
	public Map<String,Object> signup(Customer c) {
		String resultMsg = "";
		int status = 0;
		try {
			service.signup(c);
			status = 1;
			resultMsg = "가입성공";	
		}catch (AddException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg",resultMsg);
		return returnMap;
	}
}
