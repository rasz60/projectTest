package com.whereru.main.BoardDTO;

import java.sql.Timestamp;

public class MainDTO {
	private String postNo;
	private String email;
	private String plan;
	private String withUser;
	private String titleImage;
	private String images;
	private String title;
	private String content;
	private int likes;
	private String hashtag;
	private Timestamp regDate;
	private String location;
	private int views;
	private String authority;

	public MainDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MainDTO(String postNo,String email, String title,String content,String location,String titleImage,String images) {
		super();
		this.postNo = postNo;
		this.email = email;
		this.titleImage = titleImage;
		this.images = images;
		this.title = title;
		this.content = content;
		this.location = location;
		
	}
	
	public MainDTO(String email, String title,String content,String location,String titleImage,String images) {
		super();
		this.email = email;
		this.titleImage = titleImage;
		this.images = images;
		this.title = title;
		this.content = content;
		this.location = location;
		
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

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getWithUser() {
		return withUser;
	}

	public void setWithUser(String withUser) {
		this.withUser = withUser;
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
}