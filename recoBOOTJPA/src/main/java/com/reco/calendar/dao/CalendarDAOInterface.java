package com.reco.calendar.dao;

import java.util.Date;
import java.util.List;

import com.reco.calendar.vo.CalInfo;
import com.reco.calendar.vo.CalPost;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;

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
	 * @return 
	 * @throws ModifyException
	 */
	public CalInfo modifyCal(CalInfo calinfo) throws ModifyException;
	
	
	
	/**
	 * 고객의 캘린더를 삭제한다 
	 * @param calinfo
	 * @throws RemoveException
	 */
	public CalInfo removeCal(CalInfo calinfo) throws RemoveException;
	
	
	/**
	 * 고객의 캘린더 글을 추가한다
	 * @param calpost
	 * @throws AddException
	 * @throws FindException 
	 * @throws AddException 
	 */
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
	public List<CalPost> findCalsByDate(CalInfo calinfo, CalPost calpost) throws FindException;
	

	/**
	 * 고객의 캘린더글을 수정한다
	 * @param calpost
	 * @return 
	 * @throws ModifyException
	 */
	public CalPost modifyCalPost(CalPost calpost) throws ModifyException;
	
	
	/**
	 * 고객의 캘린더글을 삭제한다 
	 * @param calDate
	 * @throws RemoveException
	 */
	public CalPost removeCalPost(String calDate) throws RemoveException;


	/**
	 * 날짜에 해당하는 캘린더 글 검색한다 
	 * @param uidx
	 * @param calIdx
	 * @param dt 날짝값 : '2022-3-5'
	 * @return
	 * @throws FindException
	 */
	public CalPost findByDate(int uidx, int calIdx, String dt) throws FindException;
	
	
	public CalPost findByDate(int uidx, int calIdx, Date dt) throws FindException;


	public void removeCalPost(int uIdx, int calIdx, String calDate) throws RemoveException;
	
	


	





	

	
}