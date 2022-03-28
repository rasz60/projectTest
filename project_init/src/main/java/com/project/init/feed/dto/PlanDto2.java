package com.project.init.feed.dto;


public class PlanDto2 {
	
	private int planDtNum;
	private int planNum;
	private String placeName;
	private String planDay;
	private String planDate;
	private String startTime;
	private String endTime;
	private String theme;
	private String latitude;
	private String longitude;
	private String address;
	private String category;
	private String transportation;
	private String details;
	
	public PlanDto2() {
		super();
	}

	public PlanDto2(int planDtNum, int planNum, String placeName, String planDay, String planDate, String startTime,
			String endTime, String theme, String latitude, String longitude, String address,
			String category, String transportation, String details) {
		super();
		this.planDtNum = planDtNum;
		this.planNum = planNum;
		this.placeName = placeName;
		this.planDay = planDay;
		this.planDate = planDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.theme = theme;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.category = category;
		this.transportation = transportation;
		this.details = details;
	}

	public int getPlanDtNum() {
		return planDtNum;
	}

	public void setPlanDtNum(int planDtNum) {
		this.planDtNum = planDtNum;
	}

	public int getPlanNum() {
		return planNum;
	}

	public void setPlanNum(int planNum) {
		this.planNum = planNum;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlanDay() {
		return planDay;
	}

	public void setPlanDay(String planDay) {
		this.planDay = planDay;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

		
}
