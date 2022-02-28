package com.reco.customer.service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.google.gson.JsonElement;
//import com.google.gson.JsonParser;

import org.json.simple.JSONObject; // JSON객체 생성
import org.json.simple.parser.JSONParser; // JSON객체 파싱
import org.json.simple.parser.ParseException; // 예외처리

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
	        params.put("text", "RECO 휴대폰인증 테스트 메시지 : 인증번호는" + "["+numStr+"]" + "입니다.");
	        params.put("app_version", "test app 1.2"); // application name and version

	        try {
	            JSONObject obj = (JSONObject) coolsms.send(params);
	            System.out.println(obj.toString());
	        } catch (CoolsmsException e) {
	            System.out.println(e.getMessage());
	            System.out.println(e.getCode());
	        }

	    }
	 
	 
	 
	 
	 
	  public String getAccessToken (String authorize_code, String redirect_uri) {
	        String access_Token = "";
	        String refresh_Token = "";
	        String reqURL = "https://kauth.kakao.com/oauth/token";
	        
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=2177f6a7b4ac54c3449ddca01ead7def");
	            sb.append("&redirect_uri=" + redirect_uri);
	            sb.append("&code=" + authorize_code);
	            bw.write(sb.toString());
	            bw.flush();
	            
	            //    결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	 
	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	            
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);
	            

	            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            //JsonParser parser = new JsonParser();
	           // JsonElement element = parser.parse(result);
	            
          
                JSONParser parser = new JSONParser(); // 파싱 작업을 하기 위한 객체 생성
				JSONObject jsonObj = (JSONObject) parser.parse(result);
				access_Token = (String)jsonObj.get("access_token");
				refresh_Token = (String)jsonObj.get("refresh_token");
         
	            System.out.println("access_token : " + access_Token);
	            System.out.println("refresh_token : " + refresh_Token);
	            
	            br.close();
	            bw.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        
	        return access_Token;
	    }
	  

	  
	  public void disconnectUserInfo (String access_Token) {
		  // HashMap<String, Object> userInfo = new HashMap<>();
		  String reqURL = "https://kapi.kakao.com/v1/user/unlink";
		  try {
		        URL url = new URL(reqURL);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("POST");
		        conn.setDoOutput(true);
		        
		        //  요청에 필요한 Header에 포함될 내용
		        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
		        
		       
		        //	결과 코드가 200이라면 성공
		        int responseCode = conn.getResponseCode();
		        System.out.println("responseCode : " + responseCode);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        
		        String line = "";
		        String result = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }
		        System.out.println("response body : " + result);
		        
		        JSONParser parser = new JSONParser(); // 파싱 작업을 하기 위한 객체 생성
		        JSONObject jsonObj = (JSONObject) parser.parse(result);
//		        Long pwdLong = (Long)jsonObj.get("id");
//		        int pwd = pwdLong.intValue();
//		        userInfo.put("pwd", pwd);
		  }catch (Exception e) {
		        e.printStackTrace();
		  }
//		  return userInfo;
	  }
	  
	  public HashMap<String, Object> getUserInfo (String access_Token) {
		    
		    //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		    HashMap<String, Object> userInfo = new HashMap<>();
		    String reqURL = "https://kapi.kakao.com/v2/user/me";
		    try {
		        URL url = new URL(reqURL);
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("POST");
		        
		        //  요청에 필요한 Header에 포함될 내용
		        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
		        
		        //	결과 코드가 200이라면 성공
		        int responseCode = conn.getResponseCode();
		        System.out.println("responseCode : " + responseCode);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        
		        String line = "";
		        String result = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }
		        System.out.println("response body : " + result);
		        
		        JSONParser parser = new JSONParser(); // 파싱 작업을 하기 위한 객체 생성
		        JSONObject jsonObj = (JSONObject) parser.parse(result);
		        System.out.println("jsonObj서비스"+jsonObj);
		       
		        JSONObject properties = (JSONObject)jsonObj.get("properties");
		        String nickname = (String)properties.get("nickname");
		        System.out.println("nickname서비스"+ nickname);	
		        
		        JSONObject kakao_account = (JSONObject)jsonObj.get("kakao_account");
		        String email = (String)kakao_account.get("email");
		        System.out.println("email서비스" + email);
		        
		        Long idLong = (Long)jsonObj.get("id");
		        int id = idLong.intValue();
		        System.out.println("id서비스" + id);
		        
//		        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
//		        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//		        
//		        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//		        String email = kakao_account.getAsJsonObject().get("email").getAsString();
		        
		        userInfo.put("nickname", nickname);
		        userInfo.put("email", email);
		        userInfo.put("id", id);
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    
		    return userInfo; //닉네임과 이메일 들어있음(이메일은 경우에따라 없을 수도 있음)
		}

	  
//	  public Customer kakaoEmailDupChk(String uEmail) throws FindException{
//			return dao.kakaoEmailDupChk(uEmail);
//		}
//		
//	  public Customer kakaonickdupchk(String uNickName) throws FindException{
//			return dao.findByKakaoNick(uNickName);
//		}
	  public void findAndDeleteCustomerByPwd(String uPwd) throws FindException{
		  dao.findAndDeleteCustomerByPwd(uPwd);
	  }
	  
}



