package com.project.init.feed.dao;

import com.project.init.feed.dto.UserDto;

public interface UserIDao {
	
	public int emailCheck(String id);
	
	public int nickCheck(String nick);
	
	public String join(String UEmail, String UPw, String UNickName, String UBirth, String UGender, String UPst, String UAddr);
	
	public UserDto login(String uId);
	
	public void userVisit(String uId);

	
	
}
