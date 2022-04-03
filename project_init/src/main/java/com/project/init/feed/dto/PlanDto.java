package com.project.init.feed.dto;

public class PlanDto {
	
	
	private int planNum;
	private String userId;
	private String planName;
	private String startDate;
	private String endDate;
	private String dateCount;
	private String eventColor;

	public PlanDto() {
		super();
	}

	public PlanDto(int planNum, String userId, String planName, String startDate, String endDate, String dateCount,
			String eventColor) {
		super();
		this.planNum = planNum;
		this.userId = userId;
		this.planName = planName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dateCount = dateCount;
		this.eventColor = eventColor;
	}

	public int getPlanNum() {
		return planNum;
	}

	public void setPlanNum(int planNum) {
		this.planNum = planNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getDateCount() {
		return dateCount;
	}

	public void setDateCount(String dateCount) {
		this.dateCount = dateCount;
	}

	public String getEventColor() {
		return eventColor;
	}

	public void setEventColor(String eventColor) {
		this.eventColor = eventColor;
	}

}
