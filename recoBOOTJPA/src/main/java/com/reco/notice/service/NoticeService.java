package com.reco.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.dto.PageDTO;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.dao.NoticeDAOInterface;
import com.reco.notice.vo.Notice;


@Service
public class NoticeService {

	@Autowired
	private NoticeDAOInterface dao;
	
	
	public NoticeService(NoticeDAOInterface dao) {
		this.dao = dao;
	}
	
	public void setDao(NoticeDAOInterface dao) {
		this.dao = dao;
	}
	
	//관리자만 글 수정,삭제 ,추가 가능하게 바꿔야함.
	public Notice addNtc(Notice n) throws FindException,AddException{
		return(dao.addNtc(n));
	}

	
	public PageDTO<Notice> findNtcAll() throws FindException {
		return findNtcAll(1);
	}
	
	public PageDTO<Notice> findNtcAll(int currentPage) throws FindException {
		String url= "/ntclist";
		int totalCnt = dao.findCount();
		List<Notice> list = dao.findNtcAll(currentPage, PageDTO.CNT_PER_PAGE);
		PageDTO<Notice> pageDTO= new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
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
	public  PageDTO<Notice> findNtcByTitle(String word, int currentPage) throws FindException{
		String url = "/ntcsearch/"+word;
		List<Notice> list = dao.findNtcByTitle(word, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountTitle(word);
		PageDTO<Notice> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
	
	//공지사항 제목+내용 검색
	public  PageDTO<Notice> findNtcByWord(String word, int currentPage) throws FindException{
		String url = "/ntcsearch/"+word;
		List<Notice> list = dao.findNtcByWord(word, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountWord(word);
		PageDTO<Notice> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
	
	public Notice modifyNtc(Notice n) throws ModifyException, FindException{
		return(dao.modifyNtc(n));
	}
	
	public void removeNtc(int ntcIdx) throws RemoveException{
		dao.removeNtc(ntcIdx);
	}
}
