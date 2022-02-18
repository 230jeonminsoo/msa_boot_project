package com.reco.dto;

import java.util.ArrayList;
import java.util.List;

import com.reco.board.vo.Board;
import com.reco.board.vo.Comment;


public class PageDTO2<T> {
	private String url; //ex. board/list
	private int currentPage = 1; //현재페이지
	static public int CNT_PER_PAGE = 5; //페이지별 목록수
	static public int CNT_PER_PAGE_GROUP = 3; //페이지그룹 페이지수
	private int totalCnt; //총목록수 19
	private int totalPage; //총페이지수
	private int startPage; //시작페이지
	private int endPage;  //끝페이지
	private Board board; // 게시글
	private List<Comment> comments; //ex. 게시글목록
	private ArrayList<String> brdTitleList; //내가쓴 댓글에 해당하는 게시글 제목들이 들어갈 리스트

	public PageDTO2() {
		super();
	}
		
	
	public PageDTO2(String url, 
		       int totalCnt, 
		       ArrayList<String> brdTitleList,
		       List<Comment> comments) {
	this(url, 1 , totalCnt, brdTitleList, comments);
}
	
	public PageDTO2(String url, 
			   int currentPage,
		       int totalCnt, 
		       ArrayList<String> brdTitleList,
		       List<Comment> comments) {
	super();
	this.url = url;
	this.currentPage = currentPage;
	this.totalCnt = totalCnt;
	this.totalPage = (int)Math.ceil((double)totalCnt/CNT_PER_PAGE);
//	this.startPage = ((currentPage/(cntPerPageGroup+1))*cntPerPageGroup)+1;
	this.startPage = (currentPage-1)/CNT_PER_PAGE_GROUP*CNT_PER_PAGE_GROUP+1;
	
//	if(currentPage == totalPage) {
//		this.endPage = totalPage;		
//	}else {
//		this.endPage = ((currentPage/(cntPerPageGroup+1))+1)*cntPerPageGroup;
//	}
	
	this.endPage = startPage+CNT_PER_PAGE_GROUP-1;
	if(endPage>totalPage) {
		this.endPage = totalPage;	
	}
	this.brdTitleList = brdTitleList;
	this.comments = comments;
}
	
	public PageDTO2(String url, int currentPage
			, int totalCnt, int totalPage,
			int startPage, int endPage, ArrayList<String> brdTitleList, List<Comment> comments) {
		super();
		this.url = url;
		this.currentPage = currentPage;
		this.totalCnt = totalCnt;
		this.totalPage = totalPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.brdTitleList = brdTitleList;
		this.comments = comments;
	}
	
	
	
	
	
	
	
	public PageDTO2(String url, 
			       int totalCnt, 
			       Board board,
			       List<Comment> comments) {
		this(url, 1 , totalCnt, board, comments);
	}
	
	public PageDTO2(String url, 
				   int currentPage,
			       int totalCnt, 
			       Board board,
			       List<Comment> comments) {
		super();
		this.url = url;
		this.currentPage = currentPage;
		this.totalCnt = totalCnt;
		this.totalPage = (int)Math.ceil((double)totalCnt/CNT_PER_PAGE);
//		this.startPage = ((currentPage/(cntPerPageGroup+1))*cntPerPageGroup)+1;
		this.startPage = (currentPage-1)/CNT_PER_PAGE_GROUP*CNT_PER_PAGE_GROUP+1;
		
//		if(currentPage == totalPage) {
//			this.endPage = totalPage;		
//		}else {
//			this.endPage = ((currentPage/(cntPerPageGroup+1))+1)*cntPerPageGroup;
//		}
		
		this.endPage = startPage+CNT_PER_PAGE_GROUP-1;
		if(endPage>totalPage) {
			this.endPage = totalPage;	
		}
		this.board = board;
		this.comments = comments;
	}

	public PageDTO2(String url, int currentPage
			, int totalCnt, int totalPage,
			int startPage, int endPage, Board board, List<Comment> comments) {
		super();
		this.url = url;
		this.currentPage = currentPage;
		this.totalCnt = totalCnt;
		this.totalPage = totalPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.board = board;
		this.comments = comments;
	}
	
	
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getTotalCnt() {
		return totalCnt;
	}


	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}


	public int getTotalPage() {
		return totalPage;
	}


	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	

	public Board getBoard() {
		return board;
	}


	public void setBoard(Board board) {
		this.board = board;
	}


	public List<Comment> getComments() {
		return comments;
	}


	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	
	
	public ArrayList<String> getBrdTitleList() {
		return brdTitleList;
	}


	public void setBrdTitleList(ArrayList<String> brdTitleList) {
		this.brdTitleList = brdTitleList;
	}


	@Override
	public String toString() {
		return "PageDTO2 [url=" + url + ", currentPage=" + currentPage + ", totalCnt=" + totalCnt + ", totalPage="
				+ totalPage + ", startPage=" + startPage + ", endPage=" + endPage + ", board=" + board + ", comments="
				+ comments + "]";
	}

	
	
	
}
