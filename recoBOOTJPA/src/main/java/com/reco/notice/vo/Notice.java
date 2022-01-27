package com.reco.notice.vo;

import java.util.Date;

public class Notice {
	private int ntcIdx;
	private String ntcUNickName;
	private String ntcTitle;
	private String ntcContent;
	private String ntcAttachment;
	private int ntcViews;
	private Date ntcCreateAt;
	//private int brdThumbUp; 
	
	//멤버변수들의 접근제어자를 private으로 하면 setter, getter 메소드들이 필요하고 생성자를 통해서 초기화하는 작업도 필요하다.
    //매개변수없는 생성자 만듬
	public Notice() {	  
	}
	
	public Notice(int ntcIdx, String ntcUNickName, String ntcTitle, String ntcContent, String ntcAttachment, int ntcViews, Date ntcCreateAt) {
		super();
		this.ntcIdx = ntcIdx;
		this.ntcUNickName = ntcUNickName;
		this.ntcTitle = ntcTitle;
		this.ntcContent = ntcContent;
		this.ntcAttachment = ntcAttachment;
		this.ntcViews = ntcViews;
		this.ntcCreateAt = ntcCreateAt;
	}
	
	public int getNtcIdx() {
		return ntcIdx;
	}

	public void setNtcIdx(int ntcIdx) {
		this.ntcIdx = ntcIdx;
	}
	
	
	
	public String getNtcUNickName() {
		return ntcUNickName;
	}

	public void setNtcUNickName(String ntcUNickName) {
		this.ntcUNickName = ntcUNickName;
	}

	public String getNtcTitle() {
		return ntcTitle;
	}

	public void setNtcTitle(String ntcTitle) {
		this.ntcTitle = ntcTitle;
	}
	
	public String getNtcContent() {
		return ntcContent;
	}

	public void setNtcContent(String ntcContent) {
		this.ntcContent = ntcContent;
	}
	
	public String getNtcAttachment() {
		return ntcAttachment;
	}

	public void setNtcAttachment(String ntcAttachment) {
		this.ntcAttachment = ntcAttachment;
	}
	
	public int getNtcViews() {
		return ntcViews;
	}

	public void setNtcViews(int ntcViews) {
		this.ntcViews = ntcViews;
	}
	
	public Date getNtcCreateAt() {
		return ntcCreateAt;
	}

	public void setNtcCreateAt(Date ntcCreateAt) {
		this.ntcCreateAt = ntcCreateAt;
	}
	
}
