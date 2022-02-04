package com.reco.calendar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.calendar.dao.CalendarDAOInterface;
import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;

@Service
public class CalendarService {
	
	@Autowired
	private CalendarDAOInterface dao;
	
	public CalendarService(CalendarDAOInterface dao) {
		this.dao = dao;
	}
	
	public void setDao(CalendarDAOInterface dao) {
		this.dao = dao;
	}

	public CalInfo addCal(CalInfo calinfo) throws AddException{
		try {
			CalInfo calinfo1 = dao.addCal(calinfo);
			return calinfo1;
		} catch (AddException e) {
			throw new AddException("고객번호에 해당하는 cal_info행이 없습니다");
		}
	}

	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException{
		System.out.println("in calendarservice dao=" + dao);
		return dao.findCalsByUIdx(uIdx);
	}
	
//	public void modifyCal(CalInfo calinfo) throws ModifyException{
//		dao.modifyCal(calinfo);
//	}
	
	public CalPost addCalPost(CalPost calpost) throws AddException{
		dao.addCalPost(calpost);
		return calpost;
	}
	
	public List<CalPost> findCalsByDate(CalInfo calinfo , String calDate) throws FindException{
		return dao.findCalsByDate(calinfo , calDate);
	}
	
	public void modifyCalPost(CalPost calpost) throws ModifyException{
		dao.modifyCalPost(calpost);
	}
	
	
	public void removeCalPost(String calDate) throws RemoveException{
		dao.removeCalPost(calDate);
	}


	
}
