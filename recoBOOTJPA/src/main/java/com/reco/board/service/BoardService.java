package com.reco.board.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.board.dao.BoardDAOInterface;
import com.reco.board.vo.Board;
import com.reco.board.vo.Comment;
import com.reco.dto.PageDTO;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.dao.NoticeDAOInterface;
import com.reco.notice.vo.Notice;


@Service
public class BoardService {
	
	@Autowired
	private BoardDAOInterface dao;
	
	
	public BoardService(BoardDAOInterface dao) {
		this.dao = dao;
	}
	
	public void setDao(BoardDAOInterface dao) {
		this.dao = dao;
	}
	
	
	
	public Board addBrd(Board b) throws AddException,FindException{
		return dao.addBrd(b);
	}
	
	
	public Board addCmt(Comment comment) throws AddException{
		return dao.addCmt(comment);
	}
	
	
	//자유게시판 글 목록 출력
	public PageDTO<Board> findBrdAll() throws FindException{
		return findBrdAll(1);
	}
	
	
	public PageDTO<Board> findBrdAll(int currentPage) throws FindException {
		String url= "/brdlist";
		int totalCnt = dao.findCount();
		List<Board> list = dao.findBrdAll(currentPage, PageDTO.CNT_PER_PAGE);
		PageDTO<Board> pageDTO= new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
	
	
	public Board findBrdByIdx(int brdIdx) throws FindException{
		try {
			Board b =dao.findBrdByIdx(brdIdx);	
			return b;
		} catch (FindException e) {
			throw new FindException("해당글이 없습니다.");
		}
	}
	
	
	//자유게시판 제목 검색
	public PageDTO<Board> findBrdByTitle(String word, String f, int currentPage) throws FindException{
		String url = "/brdsearch/"+word+"/"+f;
		List<Board> list = dao.findBrdByTitle(word, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountTitle(word);
		PageDTO<Board> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
			
			
	//자유게시판 제목+내용 검색
	public PageDTO<Board> findBrdByWord(String word, String f, int currentPage) throws FindException{
		String url = "/brdsearch/"+word+"/"+f;
		List<Board> list = dao.findBrdByWord(word, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountWord(word);
		PageDTO<Board> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;	
	}
	
	
	//자유게시판 글 분류별 목록 출력
	public PageDTO<Board> findBrdByType(int intBrdType, int currentPage) throws FindException{
		String url = "/brdsearch/"+intBrdType;
		List<Board> list = dao.findBrdByType(intBrdType, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountType(intBrdType);
		PageDTO<Board> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;	
	}
	

	//자유게시판 닉네임 검색
	public PageDTO<Board> findBrdByUNickName(String word, String f, int currentPage) throws FindException{
		String url = "/brdsearch/"+word+"/"+f;
		List<Board> list = dao.findBrdByUNickName(word, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountUNickName(word);
		PageDTO<Board> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
	

	public Board modifyBrd(Board b) throws ModifyException{
		return dao.modifyBrd(b);
	}
	
	public void modifyCmt(Comment comment) throws ModifyException{
		dao.modifyCmt(comment);
	}
	
	public void removeBrd(int brdIdx) throws RemoveException{
		dao.removeBrd(brdIdx);
	}
	
	
	public void removeCmt(int brdIdx, int cmtIdx) throws RemoveException{
		dao.removeCmt(brdIdx,cmtIdx);
	}
	

	/*
	 * public Comment findCmtByIdx(int brdIdx, int cmtIdx) throws FindException{
	 * Comment comment = dao.findCmtByIdx(brdIdx, cmtIdx); return comment; }
	 */
}
