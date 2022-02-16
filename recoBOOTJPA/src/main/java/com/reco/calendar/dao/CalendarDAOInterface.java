package com.reco.calendar.dao;

import java.util.List;

import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.vo.Notice;

public interface CalendarDAOInterface {
	/**
	 * 고객의 캘린더 추가한다  
	 * @param calendarInfo
	 * @throws AddException
	 */
	public CalInfo addCal(CalInfo calinfo) throws AddException;


	/**
	 * 고객번호와 캘린더 번호로 캘린더들을 가져온다. 
	 * @param uIdx
	 * @param calIdx
	 * @return
	 * @throws FindException
	 */
	public List<CalInfo> findCalsByUIdx(int uIdx) throws FindException;
	
	
	/**
	 * 고객의 캘린더 기본정보(카테고리, 썸네일)를 수정한다
	 * @param calinfo
	 * @throws ModifyException
	 */
//	public void modifyCal(CalInfo calinfo) throws ModifyException;
	
	/**
	 * 고객의 캘린더 글을 추가한다
	 * @param calpost
	 * @throws AddException
	 * @throws FindException 
	 * @throws AddException 
	 */
	
	
//	public void removeCal(CalInfo calinfo) throws RemoveException;
	
	
	public CalPost addCalPost(CalPost calpost) throws AddException;
	
	/**
	 * 캘린더를 년/월 기준으로 한달을 가져온다
	 * @param uIdx
	 * @param calIdx
	 * @param year
	 * @param month
	 * @return
	 * @throws FindException
	 */
	public List<CalPost> findCalsByDate(CalInfo calinfo, String calDate) throws FindException;
	
	/**
	 * 고객의 캘린더글을 수정한다
	 * @param calpost
	 * @throws ModifyException
	 */
	
	
	/**
	 * 
	 * @param calpost
	 * @throws ModifyException
	 */
	public void modifyCalPost(CalPost calpost) throws ModifyException;
	
	
	/**
	 * 고객의 캘린더글을 삭제한다 
	 * @param calDate
	 * @throws RemoveException
	 */
	public void removeCalPost(String calDate) throws RemoveException;





	

	
}