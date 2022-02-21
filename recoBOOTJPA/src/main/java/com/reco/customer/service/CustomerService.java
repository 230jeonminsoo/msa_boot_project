package com.reco.customer.service;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.customer.dao.CustomerDAOInterface;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDAOInterface dao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public Customer login(String uEmail, String uPwd) throws FindException{
		try {
			Customer c = dao.findByEmail(uEmail);
				if(c.getUPwd().equals(uPwd)) {
					return c;
				}
				throw new FindException();
		}catch(FindException e) {
			throw new FindException("로그인 실패");
		}
	}
	
	public void signup(Customer c) throws AddException{
		dao.add(c);
	}
	
	public void emaildupchk(String uEmail) throws FindException{
		dao.findByEmail(uEmail);
	}
	
	public void nickdupchk(String uNickName) throws FindException{
		dao.findByNick(uNickName);
	}
	
	public void withdraw(int uIdx) throws ModifyException{
		dao.modifyStatus(uIdx);
	}
	
	public void modifypwd(int uIdx, String pwd) throws ModifyException{
		dao.modifyPwd(uIdx,pwd);
	} 
	
	public void findPwd(String email, String password) throws ModifyException,FindException{
		try {	
			Customer c= dao.findByEmail(email);
			if(c!=null) {
				dao.findPwd(email,password);
			}else {
				throw new FindException("이메일에 해당하는 회원이 없습니다.");
			}
		} catch (FindException e) {
			throw new FindException(e.getMessage());
		}	
		
	}
	
	public Customer findByNameAndRRN(String name, String RRN) throws FindException{
		try {
			Customer c = dao.findByNameAndRRN(name, RRN);
			if(c!=null) {
				return c;
			}
			throw new FindException();
		}catch(FindException e) {
			throw new FindException(e.getMessage());
		}
	}
	
	 public void certifiedPhoneNumber(String phoneNumber, String numStr) {

	        String api_key = "NCSD2AAHIAUW3DUN";
	        String api_secret = "2BQ7T5VHYNUYYCTBVYQQ08UMXOPLTMK6";
	        Message coolsms = new Message(api_key, api_secret);

	        // 4 params(to, from, type, text) are mandatory. must be filled
	        HashMap<String, String> params = new HashMap<String, String>();
	        params.put("to", phoneNumber);    // 수신전화번호
	        params.put("from", "01071354330");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
	        params.put("type", "SMS");
	        params.put("text", "핫띵크 휴대폰인증 테스트 메시지 : 인증번호는" + "["+numStr+"]" + "입니다.");
	        params.put("app_version", "test app 1.2"); // application name and version

	        try {
	            JSONObject obj = (JSONObject) coolsms.send(params);
	            System.out.println(obj.toString());
	        } catch (CoolsmsException e) {
	            System.out.println(e.getMessage());
	            System.out.println(e.getCode());
	        }

	    }
}



