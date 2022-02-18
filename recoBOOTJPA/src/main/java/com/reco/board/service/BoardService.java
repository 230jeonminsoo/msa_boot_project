package com.reco.board.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reco.board.dao.BoardDAOInterface;
import com.reco.board.vo.Board;
import com.reco.board.vo.Comment;
import com.reco.dto.PageDTO;
import com.reco.dto.PageDTO2;
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
	
	
	
	public PageDTO2<Board> addBrd(Board b) throws AddException,FindException{
		int brdIdx =  dao.addBrd(b);
		PageDTO2<Board> PageDTO2 = findBrdByIdx(brdIdx);
		return PageDTO2;
		
	}
	
	
	public PageDTO2<Board> addCmt(Comment comment) throws AddException, FindException{
//		int brdIdx = dao.addCmt(comment);
		dao.addCmt(comment);
		PageDTO2<Board> PageDTO2 =findBrdByIdx(comment.getBrd().getBrdIdx());
		//PageDTO2<Board> PageDTO2 = findBrdByIdx(brdIdx);
		return PageDTO2;
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
	
	
	public PageDTO2<Board> findBrdByIdx(int brdIdx) throws FindException{
		PageDTO2<Board> pageDTO2 =  findBrdByIdx(brdIdx, 1);
		System.out.println("findBrdByIdxservice" + pageDTO2);
		return pageDTO2;
	}
	
	public PageDTO2<Board> findBrdByIdx(int brdIdx, int cp) throws FindException{
		String url= "/brddetail";
		int totalCnt = dao.findCmtCount(brdIdx);
		Board b =dao.findBrdByIdx(brdIdx, cp, PageDTO2.CNT_PER_PAGE);
		//System.out.println("serviceBoard" + b);
		List<Comment> comments = b.getComments();
		PageDTO2<Board> pageDTO2 = new PageDTO2<>(url, cp, totalCnt, b, comments);
		//System.out.println("servicepageDTO2" +pageDTO2);
		return pageDTO2;
	
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
	

	//자유게시판 닉네임 검색
	public PageDTO<Board> findBrdByUNickName(String uNickname, int currentPage, int cntperpage) throws FindException{
		String url = "/mybrd/"+ uNickname;
		List<Board> list = dao.findBrdByUNickName(uNickname, currentPage, PageDTO.CNT_PER_PAGE);
		int totalCnt = dao.findCountUNickName(uNickname);
		PageDTO<Board> pageDTO = new PageDTO<>(url, currentPage, totalCnt, list);
		return pageDTO;
	}
	
	
	//마이페이지 내가쓴 댓글 검색
	public PageDTO2<Board> findCmtByUNickName(String uNickname, int currentPage, int cntperpage) throws FindException{
		String url = "/mycmt/"+ uNickname;
		int totalCnt = dao.findCmtCountUNickName(uNickname); //내가쓴 총 댓글수
		List<Comment> comments = dao.findCmtByUNickName(uNickname, currentPage, PageDTO.CNT_PER_PAGE);//내가쓴 댓글들
		ArrayList<String> brdTitleList = new ArrayList<String>();
//		int index=0;
//		for(Comment comment: comments ) {
//			int brdIdx = comment.getBrd().getBrdIdx();
//			String brdTitle = dao.findBrdTitle(brdIdx);
//			brdTitleList.add(index, brdTitle);
//			index++;
//		}
		PageDTO2<Board> commentPageDTO = new PageDTO2<>(url, currentPage, totalCnt, brdTitleList, comments);
		return commentPageDTO;
	}
	
	
	
	
	public void modifyBrd(Board b) throws ModifyException, FindException{
//		int brdIdx = dao.modifyBrd(b);
//		PageDTO2<Board> board = findBrdByIdx(brdIdx);
//		return board;
	    dao.modifyBrd(b);
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
