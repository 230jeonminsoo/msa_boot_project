package com.reco.dto;

import java.util.List;

import com.reco.board.vo.Comment;


public class PageDTO<T> {
	private String url; //ex. board/list
	private int currentPage = 1; //현재페이지
	static public int CNT_PER_PAGE = 5; //페이지별 목록수
	static public int CNT_PER_PAGE_GROUP = 3; //페이지그룹 페이지수
	private int totalCnt; //총목록수 19
	private int totalPage; //총페이지수
	private int startPage; //시작페이지
	private int endPage;  //끝페이지
	private List<T> list; //ex. 게시글목록
	
	

	public PageDTO() {
		super();
	}
		
		
	public PageDTO(String url, 
			       int totalCnt, //
			       List<T> list) {
		this(url, 1 , totalCnt, list);
	}
	
	public PageDTO(String url, 
				   int currentPage,
			       int totalCnt, 
			       List<T> list) {
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
		this.list = list;
	}

	public PageDTO(String url, int currentPage
			, int totalCnt, int totalPage,
			int startPage, int endPage, List<T> list) {
		super();
		this.url = url;
		this.currentPage = currentPage;
		this.totalCnt = totalCnt;
		this.totalPage = totalPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.list = list;
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


	public List<T> getList() {
		return list;
	}


	public void setList(List<T> list) {
		this.list = list;
	}
	
	

}
