package com.reco.customer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;


@Repository
public class CustomerDAOOracle implements CustomerDAOInterface {

	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void add(Customer customer) throws AddException {
		SqlSession session = null;
		
		try {
			session = sqlSessionFactory.openSession(); //Connection : DB연결
			session.insert("com.reco.customer.CustomerMapper.add",customer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AddException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}

	}

	@Override
	public Customer findByEmail(String uEmail) throws FindException {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Customer c= session.selectOne("com.reco.customer.CustomerMapper.findByEmail",uEmail);
			
			if(c != null) {				
				return c;
			}else {
				throw new FindException("이메일에 해당하는 고객이 없습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

	@Override
	public Customer findByNick(String uNickName) throws FindException {
		
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Customer c= session.selectOne("com.reco.customer.CustomerMapper.findByNick", uNickName);
			if(c != null) {				
				return c;
			}else {
				throw new FindException("닉네임에 해당하는 고객이 없습니다");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

	@Override
	public void modifyStatus(int uIdx) throws ModifyException {
		
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			session.update("com.reco.customer.CustomerMapper.modifyStatus",uIdx);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}
	
	@Override
	public void modifyPwd(int uIdx, String pwd) throws ModifyException {
		
		SqlSession session = null;
		try {
			Customer c = new Customer();
			c.setUIdx(uIdx);
			c.setUPwd(pwd);
			session = sqlSessionFactory.openSession();
			session.update("com.reco.customer.CustomerMapper.modifyPwd",c);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ModifyException(e.getMessage());
		}finally {
			if(session !=null) {
				session.close();
			}
		}
	}

}
