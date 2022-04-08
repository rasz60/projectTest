package com.project.init.feed.dto;

import java.sql.Timestamp;

public class UserDto {
	private String userEmail;
	private String userPw;
	private String userNick;
	private String userBirth;
	private int userAge;
	private String userGender;
	private int userPst;
	private String userAddress;
	private String userProfileImg;
	private String userProfileMsg;
	private String userFollower;
	private String userFollowing;
	private String userAuthority;
	private Timestamp visitDate;
	private Timestamp joinDate;
	
	public UserDto() {
		super();
	}

	public UserDto(String userEmail, String userPw, String userNick, String userBirth, int userAge, String userGender,
			int userPst, String userAddress, String userProfileImg, String userProfileMsg, String userFollower,
			String userFollowing, String userAuthority, Timestamp visitDate, Timestamp joinDate) {
		super();
		this.userEmail = userEmail;
		this.userPw = userPw;
		this.userNick = userNick;
		this.userBirth = userBirth;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userPst = userPst;
		this.userAddress = userAddress;
		this.userProfileImg = userProfileImg;
		this.userProfileMsg = userProfileMsg;
		this.userFollower = userFollower;
		this.userFollowing = userFollowing;
		this.userAuthority = userAuthority;
		this.visitDate = visitDate;
		this.joinDate = joinDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserBirth() {
		return userBirth;
	}

	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public int getUserPst() {
		return userPst;
	}

	public void setUserPst(int userPst) {
		this.userPst = userPst;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserProfileImg() {
		return userProfileImg;
	}

	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}

	public String getUserProfileMsg() {
		return userProfileMsg;
	}

	public void setUserProfileMsg(String userProfileMsg) {
		this.userProfileMsg = userProfileMsg;
	}

	public String getUserFollower() {
		return userFollower;
	}

	public void setUserFollower(String userFollower) {
		this.userFollower = userFollower;
	}

	public String getUserFollowing() {
		return userFollowing;
	}

	public void setUserFollowing(String userFollowing) {
		this.userFollowing = userFollowing;
	}

	public String getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}

	public Timestamp getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Timestamp visitDate) {
		this.visitDate = visitDate;
	}

	public Timestamp getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Timestamp joinDate) {
		this.joinDate = joinDate;
	}
	
	

}