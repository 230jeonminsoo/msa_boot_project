package com.reco.board.dao;

import java.util.List;

import com.reco.board.vo.Board;
import com.reco.board.vo.Comment;
import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;




public interface BoardDAOInterface {
	
	
	/**
	 * 자유게시판글 목록을 모두 불러온다
	 * @return 자유게시판
	 * @throws FindException  발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                        검색할 수 없는 경우 예외발생한다
	 *                        또 자유게시판글이 하나도 없는경우에도 발생함
	 */
	public List<Board> findBrdAll() throws FindException;
	
	/**
	 * 각페이지의 자유게시판 게시글목록을 불러온다.
	 * @param currentPage
	 * @param cntperpage
	 * @return
	 * @throws FindException
	 */
	public List<Board> findBrdAll(int currentPage, int cntperpage) throws FindException;
	
	/**
	 * 자유게시판 상세보기를 한다
	 * @param brdIdx 자유게시판 글번호
	 * @return 클릭한 자유게시판 글
	 * @throws FindException   발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                         검색할 수 없는 경우 예외발생한다
	 */
	public Board findBrdByIdx(int intBrdIdx) throws FindException;  
	
	/**
	 * 글제목에 해당하는 글들을 검색한다
	 * @param word 자유게시판 글제목에 포함될 단어
	 * @return  제목에 해당하는 자유게시판 글목록 반환
	 * @throws FindException
	 */
	public List<Board> findBrdByTitle(String word, int currentPage, int cntperpage) throws FindException;
	
	/**
	 * 글제목+내용에 해다하는 글들을 검색한다
	 * @param word 자유게시판 글제목+내용에 포함될 단어
	 * @return 제목+내용에 자유게시판 글목록 반환
	 * @throws FindException  발생경우는 자유게시판글이 없는경우에 예외발생한다
	 *                        검색할 수 없는 경우 예외발생한다
	 */
	public List<Board> findBrdByWord(String word, int currentPage, int cntperpage) throws FindException;
	
		
	/**
	 * 저장소의 자유게시판 분류에 따라 글목록을 불러온다.
	 * @param intBrdType 
	 * @return List<Board>
	 * @throws FindException
	 */
	public List<Board> findBrdByType(int intBrdType, int currentPage, int cntperpage) throws FindException;	
	
	/**
	 * 닉네임에 해당하는 글들을 검색한다
	 * @param word 자유게시판 닉네임에 포함될 단어
	 * @return  닉네임에 해당하는 자유게시판 글목록 반환
	 * @throws FindException
	 */
	public List<Board> findBrdByUNickName(String word, int currentPage, int cntperpage) throws FindException;
	
	
	/**
	 * 저장소에 자유게시판글을 추가한다.
	 * @param Board
	 * @throws AddException
	 */
	public Board addBrd(Board board) throws AddException,FindException;
	
	
	/**
	 * 저장소의 자유게시판글에 댓글을 등록한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public Board addCmt(Comment comment) throws AddException;
	
	
	/**
	 * 저장소의 자유게시판글을 수정한다.
	 * @param Board
	 * @throws ModifyException
	 */
	public Board modifyBrd(Board board) throws ModifyException;
	
	
	/**
	 * 저장소의 자유게시판 글에달린 댓글을 수정한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public void modifyCmt(Comment comment) throws ModifyException;
	
	
	/**
	 * 저장소의 자유게시판글을 삭제한다.
	 * @param brdIdx
	 * @throws RemoveException
	 */
	public void removeBrd(int brdIdx) throws RemoveException;	
	
	
	/**
	 * 저장소의 자유게시판 글에달린 댓글을 삭제한다.
	 * @param cmtIdx
	 * @throws RemoveException
	 */
	public void removeCmt(int brdIdx, int cmtIdx) throws RemoveException;
	
	
	//public Comment findCmtByIdx(int brdIdx, int cmtIdx) throws FindException;
	
	
	/**
	 * 전체 게시글 개수를 반환한다.
	 * @return
	 * @throws FindException
	 */
	public int findCount() throws FindException;
	
	
	/**
	 * 제목과 내용을 검색한 글의 게시글 개수를 반환한다.
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public int findCountWord(String word) throws FindException;
	
	/**
	 * 제목을 검색한 글의 게시글 개수를 반환한다.
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public int findCountTitle(String word) throws FindException;
	
	/**
	 * 게시글 타입에 해당하는 글의 게시글 갯수 반환
	 * @param intBrdType
	 * @return
	 * @throws FindException
	 */
	public int findCountType(int intBrdType) throws FindException;
	
	/**
	 * 작성자 닉네임에 해당하는 글의 게시글 객수 반환
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public int findCountUNickName(String word) throws FindException;
	
	
}
