package com.reco.customer.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reco.exception.FindException;
@SpringBootTest
class CustomerDAOOracleTest {

	@Autowired
	private CustomerDAOInterface dao;
	
	@Test
	void testFindByEmail() {
		String email = "";
		try {
			dao.findByEmail(email);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFindByNick() {
		String nick = "";
		try {
			dao.findByNick(nick);
		} catch (FindException e) {
			e.printStackTrace();
		}
	}
}
