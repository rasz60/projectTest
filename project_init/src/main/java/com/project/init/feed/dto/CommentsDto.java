package com.project.init.feed.dto;

import java.sql.Timestamp;

public class CommentsDto {

	private String commentNo; 
	private String postNo; 
	private String email;  
	private String content; 
	private Timestamp time; 
	private int likes;
	private int reply;
	private int grp;
	private int grpl;
	private int grps;
	
	public CommentsDto(String commentNo, String postNo, String email, String content, Timestamp time, int likes,
			int reply, int grp, int grpl, int grps) {
		super();
		this.commentNo = commentNo;
		this.postNo = postNo;
		this.email = email;
		this.content = content;
		this.time = time;
		this.likes = likes;
		this.reply = reply;
		this.grp = grp;
		this.grpl = grpl;
		this.grps = grps;
	}
	
	
	public CommentsDto() {
		super();
	}


	public CommentsDto(String postNo, int grp, String content, int grpl, int grps) {
		super();
		this.postNo = postNo;
		this.content = content;
		this.grp = grp;
		this.grpl = grpl;
		this.grps = grps;
	}

	public CommentsDto(String postNo, String content,int grpl) {
		super();
		this.postNo = postNo;
		this.content = content;
		this.grpl = grpl;
	}
	public int getReply() {
		return reply;
	}
	public String getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(String commentNo) {
		this.commentNo = commentNo;
	}
	public String getPostNo() {
		return postNo;
	}
	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getGrp() {
		return grp;
	}
	public void setGrp(int grp) {
		this.grp = grp;
	}
	public int getGrpl() {
		return grpl;
	}
	public void setGrpl(int grpl) {
		this.grpl = grpl;
	}
	public int getGrps() {
		return grps;
	}
	public void setGrps(int grps) {
		this.grps = grps;
	}
	public void setReply(int reply) {
		this.reply = reply;
	}
}
