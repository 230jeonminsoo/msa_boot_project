package com.reco.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.dao.NoticeDAOInteface;
import com.reco.notice.vo.Notice;


@Service
public class NoticeService {

	@Autowired
	private NoticeDAOInteface dao;
	
	
	public NoticeService(NoticeDAOInteface dao) {
		this.dao = dao;
	}
	
	public void setDao(NoticeDAOInteface dao) {
		this.dao = dao;
	}
	
	//관리자만 글 수정,삭제 ,추가 가능하게 바꿔야함.
	public Notice addNtc(Notice n) throws FindException,AddException{
		return(dao.addNtc(n));
	}
	
	public List<Notice> findNtcAll() throws FindException {
		return dao.findNtcAll();
	}
	
	public Notice findNtcByIdx(int ntcIdx) throws FindException {
		try {
			Notice n=dao.findNtcByIdx(ntcIdx);
			return n;
		} catch (FindException e) {
			throw new FindException("해당글이 없습니다.");
		}
	}
	
	//공지사항 제목 검색
	public List<Notice> findNtcByTitle(String word) throws FindException{
		return dao.findNtcByTitle(word);
	}
	
	//공지사항 제목+내용 검색
	public List<Notice> findNtcByWord(String word) throws FindException{
		return dao.findNtcByWord(word);
	}
	
	public Notice modifyNtc(Notice n) throws ModifyException, FindException{
		return(dao.modifyNtc(n));
	}
	
	public void removeNtc(int ntcIdx) throws RemoveException{
		dao.removeNtc(ntcIdx);
	}
}
