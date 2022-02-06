package com.reco.calendar.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.customer.vo.Customer;
import com.reco.exception.AddException;

@SpringBootTest
class CalendarDAOOracleTest {

	@Autowired
	
	private CalendarDAOInterface dao;
	private Logger log = 
			LoggerFactory.getLogger(getClass());
	@Test
	public void testaddCalPost() {
		CalPost calPost = new CalPost();
		CalInfo calInfo = new CalInfo();
		Customer c = new Customer();
		calInfo.setCustomer(c);
		int uIdx = 2; //김정은
		int calIdx = 4; //4번째캘린더
		c.setUIdx(uIdx);
		calInfo.setCalIdx(calIdx);
	
		String calDate = "22/02/07";
		String calMemo = "입력할께요";// calmemo수정
		String calMainImg = "test.jpg";
		
		try {
			dao.addCalPost(calPost);
		} catch (AddException e1) {
			e1.printStackTrace();
		}
	}
}