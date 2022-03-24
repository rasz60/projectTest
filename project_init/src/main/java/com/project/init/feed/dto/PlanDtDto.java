package com.project.init.feed.dto;


public class PlanDtDto {
	
	private int planDtNum;
	private int planNum;
	private String planDate;
	private String placeName;
	private String startTime;
	private String endTime;
	private String transpotation;
	private String details;
	public PlanDtDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PlanDtDto(int planDtNum, int planNum, String planDate, String placeName, String startTime, String endTime,
			String transpotation, String details) {
		super();
		this.planDtNum = planDtNum;
		this.planNum = planNum;
		this.planDate = planDate;
		this.placeName = placeName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.transpotation = transpotation;
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
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
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
	public String getTranspotation() {
		return transpotation;
	}
	public void setTranspotation(String transpotation) {
		this.transpotation = transpotation;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	

}
