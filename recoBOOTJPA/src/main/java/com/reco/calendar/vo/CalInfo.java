package com.reco.calendar.vo;

import java.util.Date; 

import com.reco.customer.vo.Customer;

/*
 * 캘린더 객체용 클래스
 * 캘린더 정보(캘린더인덱스, 
 */
public class CalInfo {
	private Customer customer; //uIdx대신 has
	private int calIdx; //캘린더 번호
	private String calCategory;
	private String calThumbnail;
	private Date calCreateAt;
	public CalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CalInfo(Customer customer, int calIdx, String calCategory, String calThumbnail, Date calCreateAt) {
		super();
		this.customer = customer;
		this.calIdx = calIdx;
		this.calCategory = calCategory;
		this.calThumbnail = calThumbnail;
		this.calCreateAt = calCreateAt;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getCalIdx() {
		return calIdx;
	}
	public void setCalIdx(int calIdx) {
		this.calIdx = calIdx;
	}
	public String getCalCategory() {
		return calCategory;
	}
	public void setCalCategory(String calCategory) {
		this.calCategory = calCategory;
	}
	public String getCalThumbnail() {
		return calThumbnail;
	}
	public void setCalThumbnail(String calThumbnail) {
		this.calThumbnail = calThumbnail;
	}
	public Date getCalCreateAt() {
		return calCreateAt;
	}
	public void setCalCreateAt(Date calCreateAt) {
		this.calCreateAt = calCreateAt;
	}
	

	
	
}
