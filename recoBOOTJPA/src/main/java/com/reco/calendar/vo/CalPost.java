package com.reco.calendar.vo;
/*
 * 캘린더 글쓰기 객체용 클래스
 * 캘린더 글쓰기 정보( 글인덱스, 지정날짜, 리뷰내용, 대표사진, 서브사진 3개, 캘린더번호(카테고리용), 회원번호)
 */
import java.util.Date;

import com.reco.customer.vo.Customer;
import com.reco.calendar.vo.CalInfo;

public class CalPost {

//	private int calPostIdx;
	private CalInfo calinfo;
	private String calDate;
	private String calMemo;
	private String calMainImg; 
	private Date calPostCreateAt;


	public CalPost() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CalPost(CalInfo calinfo, String calDate, String calMemo, String calMainImg, 
			 Date calPostCreateAt) {
		super();
		this.calinfo = calinfo;
		this.calDate = calDate;
		this.calMemo = calMemo;
		this.calMainImg = calMainImg;
		this.calPostCreateAt = calPostCreateAt;
	}



	public CalInfo getCalInfo() {
		return calinfo;
	}


	public void setCalInfo(CalInfo calinfo) {
		this.calinfo = calinfo;
	}

	public String getCalDate() {
		return calDate;
	}


	public void setCalDate(String calDate) {
		this.calDate = calDate;
	}


	public String getCalMemo() {
		return calMemo;
	}


	public void setCalMemo(String calMemo) {
		this.calMemo = calMemo;
	}


	public String getCalMainImg() {
		return calMainImg;
	}


	public void setCalMainImg(String calMainImg) {
		this.calMainImg = calMainImg;
	}


	public Date getCalPostCreateAt() {
		return calPostCreateAt;
	}


	public void setCalPostCreateAt(Date calPostCreateAt) {
		this.calPostCreateAt = calPostCreateAt;
	}

}
