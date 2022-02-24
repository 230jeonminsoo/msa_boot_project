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
import com.reco.notice.vo.Notice;

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
		return(dao.addCal(calinfo));
	}
	
	
	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException{
		System.out.println("in calendarservice dao=" + dao);
		return dao.findCalsByUIdx(uIdx);
	}
	
	
	public CalInfo modifyCal(CalInfo calinfo) throws ModifyException{
		return dao.modifyCal(calinfo);
	}
	
	public CalInfo removeCal(CalInfo calinfo) throws RemoveException {
		return dao.removeCal(calinfo);
	}
	
	public CalPost addCalPost(CalPost calpost) throws AddException {
			return dao.addCalPost(calpost);
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
