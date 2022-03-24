package com.project.init.feed.dto;

public class CommentDto {
	
	private int commentNum;
	private String comments;
	private int mNum;
	private int gNum;
	private int iNum;
	
	public CommentDto() {
		super();
	}

	public CommentDto(int commentNum, String comments, int mNum, int gNum, int iNum) {
		super();
		this.commentNum = commentNum;
		this.comments = comments;
		this.mNum = mNum;
		this.gNum = gNum;
		this.iNum = iNum;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getmNum() {
		return mNum;
	}

	public void setmNum(int mNum) {
		this.mNum = mNum;
	}

	public int getgNum() {
		return gNum;
	}

	public void setgNum(int gNum) {
		this.gNum = gNum;
	}

	public int getiNum() {
		return iNum;
	}

	public void setiNum(int iNum) {
		this.iNum = iNum;
	}
	
	
	
	
	
	
	
}
