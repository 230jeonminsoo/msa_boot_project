package com.reco.customer.dao;

import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;

public interface CustomerDAOInterface {

	/**
	 * 저장소에 회원을 추가한다.
	 * @param c
	 * @throws AddException
	 */
	public void add(Customer c) throws AddException;
	
	/**
	 * 이메일에 해당하는 회원을 찾아 반환한다. 
	 * @param uEmail
	 * @return 회원객체
	 * @throws FindException
	 */
	public Customer findByEmail(String uEmail) throws FindException;
	
	/**
	 * 닉네임에 해당하는 회원을 찾아 반환한다.
	 * @param uNickName
	 * @return 회원객체
	 * @throws FindException
	 */
	public Customer findByNick(String uNickName) throws FindException;
	
	/**
	 * 회원상태를 변경한다(탈퇴)
	 * @param uIdx
	 * @throws ModifyException
	 */
	public void modifyStatus(int uIdx) throws ModifyException;
	
	/**
	 * 회원의 비밀번호를 변경한다.
	 * @param c
	 * @throws ModifyException
	 */
	public void modifyPwd(int uIdx, String pwd) throws ModifyException;
	
	/**
	 * 새로운 비밀번호를 입력된 이메일로 보낸다.
	 * @param email
	 * @throws ModifyException
	 */
	public void findPwd(String email, String password) throws ModifyException;
	
	/**
	 * 이름과 주민번호로 고객을 찾는다.
	 * @param email
	 * @param password
	 * @throws FindException
	 */
	public Customer findByNameAndRRN(String name, String RRN) throws FindException;
	
}
