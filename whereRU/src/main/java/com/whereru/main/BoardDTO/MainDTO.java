package com.whereru.main.BoardDTO;

public class MainDTO {
	private String boardNum;
	private String nickname;
	private String title;
	private String content;
	private String location;
	private String titleImg;
	private String filenames;
	
	public MainDTO() {
		super();
	}
	
	public MainDTO(String nickname, String title, String content, String location, String titleImg,
			String filenames) {
		super();
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.location = location;
		this.titleImg = titleImg;
		this.filenames = filenames;
	}
	
	public MainDTO(String boardNum,String nickname, String title, String content, String location, String titleImg,
			String filenames) {
		super();
		this.boardNum = boardNum;
		this.nickname = nickname;
		this.title = title;
		this.content = content;
		this.location = location;
		this.titleImg = titleImg;
		this.filenames = filenames;
	}
	public String getBoardNum() {
		return boardNum;
	}
	public void setBoardNum(String boardNum) {
		this.boardNum = boardNum;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getTitleImg() {
		return titleImg;
	}
	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	public String getFilenames() {
		return filenames;
	}
	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}



}