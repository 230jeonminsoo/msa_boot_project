package com.reco.customer.vo;
/**
 * 고객객체용 클래스이다
 * 고객정보(인덱스, 이름, 닉네임, 이메일, 비밀번호)
 * 
 */
public class Customer{
	private int uIdx;
	private String uName;
	private String uNickName;
	private String uEmail;
	private String uPwd;
	private int uAuthCode;
	
	public Customer(){}
	
	/**
	 * 고객정보를 초기화한다.
	 * @param idx
	 * @param name
	 * @param nickname
	 * @param email
	 * @param pwd
	 * @param uAuthCode
	 */
	public Customer(int uIdx, String uName, String uNickName, String uEmail, String uPwd, int uAuthCode) {
		super();
		this.uIdx = uIdx;
		this.uName = uName;
		this.uNickName = uNickName;
		this.uEmail = uEmail;
		this.uPwd = uPwd;
		this.uAuthCode = uAuthCode;
	}

	/**
	 * 고객정보를 출력한다
	 */
	@Override
	public String toString() {
		return "Customer [uIdx=" + uIdx + ", uName=" + uName + ", uNickName=" + uNickName + ", uEmail=" + uEmail + ", uPwd=" + uPwd + ", uAuthCode=" + uAuthCode +"]";
	}

	public int getUIdx() {
		return uIdx;
	}

	public void setUIdx(int uIdx) {
		this.uIdx = uIdx;
	}

	public String getUName() {
		return uName;
	}

	public void setUName(String uName) {
		this.uName = uName;
	}

	public String getUNickName() {
		return uNickName;
	}

	public void setUNickName(String uNickName) {
		this.uNickName = uNickName;
	}

	public String getUEmail() {
		return uEmail;
	}

	public void setUEmail(String uEmail) {
		this.uEmail = uEmail;
	}

	public String getUPwd() {
		return uPwd;
	}

	public void setUPwd(String uPwd) {
		this.uPwd = uPwd;
	}
	
	public int getUAuthCode() {
		return uAuthCode;
	}
	
	public void setUAuthCode(int uAuthCode) {
		this.uAuthCode = uAuthCode;
	}
}