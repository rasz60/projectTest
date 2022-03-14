package com.project.init.feed.dao;

import java.util.ArrayList;

import com.project.init.feed.dto.PlanDto;

public interface IDao {
	
	void insertPlan(PlanDto dto);
	
	ArrayList<PlanDto> selectAllPlan();

}
