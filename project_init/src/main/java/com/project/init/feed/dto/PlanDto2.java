package com.project.init.feed.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanDto2 {
	private static final Logger logger = LoggerFactory.getLogger(PlanDto2.class);
	
	private String planNum;
	private String planName;
	private String startDate;
	private String endDate;
	private String theme;
	private String latitude; //����
	private String longitude; //�浵
	private String placeName; //����̸�
	private String category; //ī�װ�
	private String placecount; //������ ��� ����
	private String address; //�ּ�
	public PlanDto2() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlanDto2(String planNum, String planName, String startDate, String endDate, String theme, String latitude,
			String longitude, String placeName, String category, String placecount, String address) {
		super();
		this.planNum = planNum;
		this.planName = planName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.theme = theme;
		this.latitude = latitude;
		this.longitude = longitude;
		this.placeName = placeName;
		this.category = category;
		this.placecount = placecount;
		this.address = address;
	}

	public String getPlanNum() {
		return planNum;
	}
	public void setPlanNum(String planNum) {
		this.planNum = planNum;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPlacecount() {
		return placecount;
	}
	public void setPlacecount(String placecount) {
		this.placecount = placecount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public static Logger getLogger() {
		return logger;
	}
	
	
}
