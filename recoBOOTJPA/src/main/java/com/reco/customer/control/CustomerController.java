package com.reco.customer.control;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.swing.DesktopManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reco.customer.dao.CustomerDAOInterface;
import com.reco.customer.service.CustomerService;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private CustomerDAOInterface dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(String email, String pwd, HttpSession session){
		session.removeAttribute("loginInfo"); 
		session.removeAttribute("myPage");
		String resultMsg = "";
		int status = 0;
		Customer c = new Customer();
		try {
			c = service.login(email, pwd);
			if(c.getuStatus() == 0) {
				resultMsg = "탈퇴한 사용자입니다.";			
			}else {
				session.setAttribute("loginInfo", c);
				resultMsg = "로그인 성공";
				status = 1;
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
		session.removeAttribute("myPage");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/nickdupchk")
	@ResponseBody
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
	@ResponseBody
	public Map<String,Object> signup(String name, String nickname, String email,String pwd) {
		Customer c = new Customer();
		c.setUName(name);
		c.setUNickName(nickname);
		c.setUEmail(email);
		c.setUPwd(pwd);
		logger.info("컨트롤러로 전달된 c"+c.getUName()+c.getUNickName()+c.getUEmail()+c.getUPwd());
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
	
	

	@PostMapping("/kakaosignup")
	@ResponseBody
	public Map<String,Object> kakaosignup(String nickname, String code, String email,String pwd, HttpSession session) {
		Customer c = new Customer();
		c.setUNickName(nickname);
		c.setUEmail(email);
		c.setUPwd(pwd);
		logger.info("kakaosignup컨트롤러로 전달된값" +c.getUNickName()+c.getUEmail()+c.getUPwd());
		String resultMsg = "";
		int status = 0;
		try {
			service.signup(c);
			status = 1;
			resultMsg = "가입성공";	
			session.setAttribute("loginInfo", c);
		    session.setAttribute("myPage", session); 
		}catch (AddException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("code",code);
		returnMap.put("status",status);
		returnMap.put("resultMsg",resultMsg);
		return returnMap;
	}
	
//
//	@PostMapping("/kakaosignup")
//	@ResponseBody
//	public String kakaosignup(String nickname, String email,String pwd) {
//		Customer c = new Customer();
//		c.setUNickName(nickname);
//		c.setUEmail(email);
//		c.setUPwd(pwd);
//		logger.info("kakaosignup컨트롤러로 전달된값" +c.getUNickName()+c.getUEmail()+c.getUPwd());
//		try {
//			service.signup(c);
//		}catch (AddException e) {
//			e.printStackTrace();
//		}
//		return "index.jsp";
//	}
//	
	@GetMapping("/modifypwd")
	@ResponseBody
	public Map<String,Object> modifypwd(int uIdx, String pwd) {
		String resultMsg = "";
		int status = 0;
		try {
			service.modifypwd(uIdx,pwd);
			status = 1;
			resultMsg = "변경성공";	
		}catch (ModifyException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg",resultMsg);
		return returnMap;		
	}
	
	@GetMapping("/withdraw")
	@ResponseBody
	public Map<String,Object> withdraw(int uIdx) {
		String resultMsg = "";
		int status = 0;
		try {
			service.withdraw(uIdx);
			status = 1;
			resultMsg = "탈퇴성공";
		}catch (ModifyException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg",resultMsg);
		return returnMap;		
	}
	
	@GetMapping("/findPwd")
	@ResponseBody
	public Map<String,Object> findPwd(String email, String password){
		String resultMsg = "";
		int status = 0;
		try {
			service.findPwd(email,password);
			status = 1;
		} catch (FindException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();	
			logger.info("컨트롤 리설트 메시디"+resultMsg);
		} catch (ModifyException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();	
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg",resultMsg);
		return returnMap;		
	}
	
	@GetMapping("/findByNameAndRRN")
	@ResponseBody
	public Map<String,Object> findByNameAndRRN(String name, String rrn, Model model){
		int status = 0;
		Customer c = new Customer();
		Map<String, Object> returnMap = new HashMap<>();
		try {
			c= service.findByNameAndRRN(name, rrn);
			status = 1;
			returnMap.put("email",c.getUEmail());
			return returnMap;
		} catch (FindException e) {
			e.printStackTrace();	
			returnMap.put("status",status);
			returnMap.put("resultMsg",e.getMessage());
			return returnMap;
		}			
	}
	
	@GetMapping("/check/sendSMS")
    public @ResponseBody
    String sendSMS(String phoneNumber) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        service.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }
	
	
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
	
//	@GetMapping("/kakaoemaildupchk")
//	@ResponseBody
//	public Customer kakaoEmailDupChk(String uEmail){ //가입위한 매개변수 추가로 받아야함
//		Customer c = new Customer();
//		c.setUName(name);
//		c.setUNickName(nickname);
//		c.setUEmail(uEmail);
//		c.setUPwd(pwd);
//		
//		Customer c = service.kakaoEmailDupChk(uEmail);
//		return c;
//		
//	}
	
	@RequestMapping(value="/kakaoexit")
	public void kakaologout(@RequestParam(value="code", required=false) String code, HttpSession session, Model model) throws FindException, AddException {
		String redirect_uri = "http://localhost:9998/recoBOOTJPA/kakaoexit";
		String access_Token = service.getAccessToken(code, redirect_uri);
		 HashMap<String, Object> userInfo = service.disconnectUserInfo(access_Token);
		 session.removeAttribute("loginInfo"); 
		 session.removeAttribute("myPage"); 
		 int pwdInt = (int)userInfo.get("pwd");
		 String uPwd = Integer.toString(pwdInt);
		 service.findAndDeleteCustomerByPwd(uPwd);
		
		 
	}
	
	@RequestMapping(value="/kakaologin")
	public String kakaologin(@RequestParam(value="code", required=false) String code, HttpSession session, Model model) throws FindException, AddException {
		session.removeAttribute("loginInfo"); 
		session.removeAttribute("myPage");
		 
		System.out.println("code : " + code);
		
		String redirect_uri = "http://localhost:9998/recoBOOTJPA/kakaologin";
        String access_Token = service.getAccessToken(code, redirect_uri);
        System.out.println("controller access_token : " + access_Token);
        HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
        System.out.println("kakaologin Controller : " + userInfo);
        
        // 전달받은 userInfo에 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        //if (userInfo.get("email") != null) {
    	String uEmail = (String)userInfo.get("email");
    	String uNickName = (String)userInfo.get("nickname");
    	int id = (int)userInfo.get("id"); // 어떻게 활용할지 고민중
    	String idString = Integer.toString(id);
    	
    	Customer c = new Customer();
//    	c = service.kakaoEmailDupChk(uEmail);
//    	System.out.println("c값" + c);
//		if(c == null) { //가입이 안된 경우
//		
//	    	Customer NickExist = service.kakaonickdupchk(uNickName);//닉네임 중복확인	    	
//	    	
//	    	if(NickExist == null) { //닉네임중복 아닌경우
//	    		Customer c2 = new Customer();
//	    		c2.setUEmail(uEmail);
//	    		c2.setUPwd(idString); 
//	    		c2.setUNickName(uNickName);
//	    		service.signup(c2); //회원가입 서비스 호출
//	    		
//	    	}else { // 닉네임중복인 경우
//	    		model.addAttribute("email", uEmail);
//	    		model.addAttribute("pwd", idString);
//	    		model.addAttribute("code", code);
//	    		
//	    		return "index.jsp";
//	    	}	    	
//		}else {//가입이 이미 된경우
//			String nickname = c.getUNickName();
//			c.setUEmail(uEmail);
//			c.setUPwd(idString); 
//			c.setUNickName(nickname);
//		}
    	
    	try {//찾은경우 = 가입이 된경우 로그인 인포세션에 넣기
    		service.emaildupchk(uEmail);
    		String nickname = dao.findByEmail(uEmail).getUNickName();
    		c.setUIdx(dao.findByEmail(uEmail).getUIdx());
			c.setUEmail(uEmail);
			c.setUPwd(idString); 
			c.setUNickName(nickname);
    	}catch(FindException e) {//못 찾은 경우 가입시키기
	    	    		
	    	try { //가입 시키기 전 닉네임중복인 경우
	    		service.nickdupchk(uNickName);//닉네임 중복확인	
	    		model.addAttribute("email", uEmail);
	    		model.addAttribute("pwd", idString);
	    		model.addAttribute("code", code);
	    		
	    		return "index.jsp";
	    	}catch(FindException e2){ // 닉네임중복인 아닌 경우	
	    		Customer c2 = new Customer();
	    		c2.setUEmail(uEmail);
	    		c2.setUPwd(idString); 
	    		c2.setUNickName(uNickName);
	    		logger.info("닉넴 중복 아닌경우 : "+idString);
	    		logger.info("닉넴 중복 아닌경우 : "+uEmail);
	    		service.signup(c2); //회원가입 서비스 호출 
	    		c2.setUIdx(dao.findByEmail(uEmail).getUIdx());
	    		session.setAttribute("loginInfo", c2);
	    	    session.setAttribute("myPage", session); 
	    	}	
    	}
		
        session.setAttribute("loginInfo", c);
        session.setAttribute("myPage", session); 
        //session.setAttribute("access_Token", access_Token);//세션 loginInfo로 사용해서 필요없을 듯
    	
        return "index.jsp";
	}
}


