package com.whereru.main.BoardDTO;

import java.sql.Timestamp;

public class CommentsDTO {
	private String commentNo; //������ ����
	private String postNo; //�Խù� ��ȣ
	private String email; //useremail
	private String content; //����
	private Timestamp time;//�ð�
	private int likes;//�� Ƚ
	private int reply;
	



	public CommentsDTO() {
		super();
	}

	
	
	public CommentsDTO(String postNo, String content) {
		super();
		this.postNo = postNo;
		this.content = content;
	}
	public int getReply() {
		return reply;
	}
	
	
	
	public void setReply(int reply) {
		this.reply = reply;
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
	
	
}
