package com.reco.notice.dao;

import java.util.List;

import com.reco.exception.AddException;
import com.reco.exception.FindException;
import com.reco.exception.ModifyException;
import com.reco.exception.RemoveException;
import com.reco.notice.vo.Notice;



public interface NoticeDAOInterface {
	

	
	/**
	 * 공지사항 첫 페이지를 불러온다.
	 * @param ntcidx
	 * @return
	 */
	public List<Notice> findNtcAll() throws FindException;  
	
	
	/**
	 * 각페이지의 공지사항을 불러온다.
	 * @param currentPage
	 * @param cntperpage
	 * @return
	 * @throws FindException
	 */
	public List<Notice> findNtcAll(int currentPage, int cntperpage) throws FindException;
	
	/**
	 * 공지사항 상세보기를 한다.
	 * @param ntcidx
	 * @return
	 */
	public Notice findNtcByIdx(int ntcIdx) throws FindException;
	
	
	/**
	 * 글제목에 해당하는 글들을 검색한다
	 * @param word 공지사항 글제목에 포함될 단어
	 * @return  제목에 해당하는 공지사항 글목록 반환
	 * @throws FindException
	 */
	public List<Notice> findNtcByTitle(String word, int currentPage, int cntperpage) throws FindException;
	
	/**
	 * 단어를 포함한 제목이나 내용을 갖는 글을 반환한다. 
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public List<Notice> findNtcByWord(String word, int currentPage, int cntperpage) throws FindException; 
	
	
	
	/**
	 * 저장소에 공지사항글을 추가한다.
	 * @param community
	 * @throws AddException
	 */
	public Notice addNtc(Notice notice) throws AddException,FindException;
	
	
	
	/**
	 * 저장소의 공지사항글을 수정한다.
	 * @param community
	 * @throws ModifyException
	 */
	public Notice modifyNtc(Notice notice) throws ModifyException,FindException;
	
	
	
	/**
	 * 저장소의 공지사항글을 삭제한다.
	 * @param ntcidx
	 * @throws RemoveException
	 */
	public void removeNtc(int ntcIdx) throws RemoveException;
	
	
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
	 * 제목과 내용을 검색한 글의 게시글 개수를 반환한다.
	 * @param word
	 * @return
	 * @throws FindException
	 */
	public int findCountTitle(String word) throws FindException;
	
	
}
