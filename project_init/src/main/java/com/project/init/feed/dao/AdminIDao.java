package com.project.init.feed.dao;

import java.util.ArrayList;
import java.util.Map;

import com.project.init.feed.dto.PlanDto2;
import com.project.init.feed.dto.UserDto;

public interface AdminIDao {
	ArrayList<PlanDto2> selectDashBoardPlaces(Map<String, String> map);
	
	ArrayList<UserDto> selectDashBoardUser(Map<String, String> map);
}
