package com.reco.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.customer.dao.CustomerDAOInterface;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDAOInterface dao;

	
	
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
}



