package com.project.init.feed.dao;

import java.util.ArrayList;
import java.util.Map;

import com.project.init.feed.dto.PlanDto2;
import com.project.init.feed.dto.UserDto;

public interface AdminIDao {
	// 장소 별 통계	
	ArrayList<PlanDto2> selectDashBoardPlaces(Map<String, String> map);
	
	// 년도,월,일 별 회원수 통계
	ArrayList<UserDto> selectDashBoardUser(Map<String, String> map);
	
	// 회원 성별 통계
	ArrayList<UserDto> selectDashBoardUserGender();
	
	// 회원 연령별 통계
	ArrayList<UserDto> selectDashBoardUserAge();
}
