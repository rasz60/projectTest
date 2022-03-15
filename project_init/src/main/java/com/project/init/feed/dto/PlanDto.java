package com.project.init.feed.dto;

public class PlanDto {
	private int planNum;
	private String planName;
	private String startDate;
	private String endDate;

	public PlanDto() {
		super();
	}

	public PlanDto(int planNum, String planName, String startDate, String endDate) {
		super();
		this.planNum = planNum;
		this.planName = planName;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public int getPlanNum() {
		return planNum;
	}

	public void setPlanNum(int planNum) {
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
	
}
