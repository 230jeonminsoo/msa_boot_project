package com.reco.customer.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reco.board.dao.BoardDAOInterface;
import com.reco.board.vo.Board;
import com.reco.calendar.dao.CalendarDAOInterface;
import com.reco.calendar.vo.CalInfo;
import com.reco.customer.dao.CustomerDAOInterface;
import com.reco.customer.service.CustomerService;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.dao.NoticeDAOInterface;
import com.reco.notice.vo.Notice;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private CustomerDAOInterface dao;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CalendarDAOInterface daoCal;
	@Autowired
	private BoardDAOInterface daoBrd;
	@Autowired
	private NoticeDAOInterface daoNtc;

	
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
				resultMsg = "????????? ??????????????????.";			
			}else {
				session.setAttribute("loginInfo", c);
				resultMsg = "????????? ??????";
				status = 1;
			}
		}catch (FindException e) {
			resultMsg = "????????? ??????";
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
			resultMsg = "?????? ???????????? ??????????????????";
		}catch (FindException e) {
			e.printStackTrace();
			status = 1;
			resultMsg = "??????????????? ??????????????????";
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
		logger.info("??????????????? ????????? c"+c.getUName()+c.getUNickName()+c.getUEmail()+c.getUPwd());
		String resultMsg = "";
		int status = 0;
		try {
			service.signup(c);
			status = 1;
			resultMsg = "????????????";	
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
		logger.info("kakaosignup??????????????? ????????????" +c.getUNickName()+c.getUEmail()+c.getUPwd());
		String resultMsg = "";
		int status = 0;
		try {
			service.signup(c);
			c.setUIdx(dao.findByEmail(email).getUIdx());
			status = 1;
			resultMsg = "????????????";	
			session.setAttribute("loginInfo", c);
		    session.setAttribute("myPage", session); 
		    session.setAttribute("kakaoAccountRemove", code);
		}catch (AddException e) {
			e.printStackTrace();
			resultMsg = e.getMessage();
		} catch (FindException e) {
			e.printStackTrace();
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
//		logger.info("kakaosignup??????????????? ????????????" +c.getUNickName()+c.getUEmail()+c.getUPwd());
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
			resultMsg = "????????????";	
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
			resultMsg = "????????????";
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
			logger.info("????????? ????????? ?????????"+resultMsg);
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

        System.out.println("????????? ?????? : " + phoneNumber);
        System.out.println("???????????? : " + numStr);
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
			resultMsg = "?????? ???????????? ??????????????????";
		}catch (FindException e) {
			e.printStackTrace();
			status = 1;
			resultMsg = "??????????????? ??????????????????";
		}
		Map<String,Object> returnMap = new HashMap<>();
		returnMap.put("status",status);
		returnMap.put("resultMsg", resultMsg);
		return returnMap;
	}
	
//	@GetMapping("/kakaoemaildupchk")
//	@ResponseBody
//	public Customer kakaoEmailDupChk(String uEmail){ //???????????? ???????????? ????????? ????????????
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
	public void kakaologout(@RequestParam(value="code", required=false) String code, HttpSession session, Model model) throws FindException, AddException, ModifyException, RemoveException {
		String redirect_uri = "http://localhost:9998/recoBOOTJPA/kakaoexit";
		int carIdx=0;
		String access_Token = service.getAccessToken(code, redirect_uri);
		Customer c = (Customer)session.getAttribute("loginInfo");
		String uNickName = c.getUNickName();
		System.out.println("uNickName???" + uNickName);
		
		daoBrd.removeCmtAllFromDB(uNickName);
		
		List<Board> boards = daoBrd.findBrdByUNickNameMy(uNickName);
		for(Board board : boards) {
			daoBrd.removeBrd(board.getBrdIdx());
		}
		
		
		if(c.getUAuthCode() == 0) {
		daoNtc.removeNtcAllFromDB(uNickName);
		}
		//int uIdx = c.getUIdx();
		
		CalInfo calinfo = new CalInfo();
		calinfo.setCustomer(c);
		 for(carIdx=0; carIdx<5; carIdx++) {
			 calinfo.setCalIdx(carIdx);
			 daoCal.removeCal(calinfo);
		 }
		 
		
		 
		 HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
		 String uEmail = (String)userInfo.get("email");
		 int uIdx = dao.findByEmail(uEmail).getUIdx();
		 service.withdraw(uIdx);
		 
		 service.disconnectUserInfo(access_Token);
		 
		 session.removeAttribute("loginInfo"); 
		 session.removeAttribute("myPage"); 
		 session.removeAttribute("kakaoAccountRemove"); 
		 
		
		 //int pwdInt = (int)userInfoPwd.get("pwd");
		 //String uPwd = Integer.toString(pwdInt);
		 //service.findAndDeleteCustomerByPwd(uPwd);
	}
	
	
	@RequestMapping(value="/kakaologin")
	public String kakaologin(@RequestParam(value="code", required=false) String code, HttpSession session, Model model) throws FindException, AddException, ModifyException {
		session.removeAttribute("loginInfo"); 
		session.removeAttribute("myPage");
		 
		System.out.println("code : " + code);
		
		String redirect_uri = "http://localhost:9998/recoBOOTJPA/kakaologin";
        String access_Token = service.getAccessToken(code, redirect_uri);
        System.out.println("controller access_token : " + access_Token);
        HashMap<String, Object> userInfo = service.getUserInfo(access_Token);
        System.out.println("kakaologin Controller : " + userInfo);
        
        // ???????????? userInfo??? ?????????????????? ???????????? ????????? ??? ????????? ?????? ???????????? ?????? ??????
        //if (userInfo.get("email") != null) {
    	String uEmail = (String)userInfo.get("email");
    	String uNickName = (String)userInfo.get("nickname");
    	int id = (int)userInfo.get("id"); // ????????? ???????????? ?????????
    	String idString = Integer.toString(id);
    	int uAuthCode = 1;
    	
    	Customer c = new Customer();
//    	c = service.kakaoEmailDupChk(uEmail);
//    	System.out.println("c???" + c);
//		if(c == null) { //????????? ?????? ??????
//		
//	    	Customer NickExist = service.kakaonickdupchk(uNickName);//????????? ????????????	    	
//	    	
//	    	if(NickExist == null) { //??????????????? ????????????
//	    		Customer c2 = new Customer();
//	    		c2.setUEmail(uEmail);
//	    		c2.setUPwd(idString); 
//	    		c2.setUNickName(uNickName);
//	    		service.signup(c2); //???????????? ????????? ??????
//	    		
//	    	}else { // ?????????????????? ??????
//	    		model.addAttribute("email", uEmail);
//	    		model.addAttribute("pwd", idString);
//	    		model.addAttribute("code", code);
//	    		
//	    		return "index.jsp";
//	    	}	    	
//		}else {//????????? ?????? ?????????
//			String nickname = c.getUNickName();
//			c.setUEmail(uEmail);
//			c.setUPwd(idString); 
//			c.setUNickName(nickname);
//		}
    	
    	try {//???????????? = ????????? ????????? ????????? ??????????????? ??????
    		service.emaildupchk(uEmail);
    		String nickname = dao.findByEmail(uEmail).getUNickName();
    		int uIdx = dao.findByEmail(uEmail).getUIdx();
    		c.setUIdx(uIdx);
			c.setUEmail(uEmail);
			c.setUPwd(idString); 
			c.setUNickName(nickname);
			c.setUAuthCode(uAuthCode);
			dao.restoreStatus(uIdx);
    	}catch(FindException e) {//??? ?????? ?????? ???????????????
	    	    		
	    	try { //?????? ????????? ??? ?????????????????? ??????
	    		service.nickdupchk(uNickName);//????????? ????????????	
	    		model.addAttribute("email", uEmail);
	    		model.addAttribute("pwd", idString);
	    		model.addAttribute("code", code);
	    		return "index.jsp";
	    		
	    	}catch(FindException e2){ // ?????????????????? ?????? ??????	
	    		Customer c2 = new Customer();
	    		c2.setUEmail(uEmail);
	    		c2.setUPwd(idString); 
	    		c2.setUNickName(uNickName);
	    		logger.info("?????? ?????? ???????????? : "+idString);
	    		logger.info("?????? ?????? ???????????? : "+uEmail);
	    		service.signup(c2); //???????????? ????????? ?????? 
	    		c2.setUIdx(dao.findByEmail(uEmail).getUIdx());
	    		session.setAttribute("loginInfo", c2);
	    	    session.setAttribute("myPage", session);
	    	    session.setAttribute("kakaoAccountRemove", code);
	    	}	
    	}
		
        session.setAttribute("loginInfo", c);
        session.setAttribute("myPage", session); 
        session.setAttribute("kakaoAccountRemove", code);
        //session.setAttribute("access_Token", access_Token);//?????? loginInfo??? ???????????? ???????????? ???
    	
        return "index.jsp";
	}
}


