package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.init.feed.dto.PlanDto;

public interface IDao {
	
	void insertPlan(PlanDto dto);
	
	PlanDto selectPlan(int planNum);
	
	
	ArrayList<PlanDto> selectAllPlan();

	String updatePlan(PlanDto dto);
	
	String deletePlan(String planNum);
	
	String insertMap(Model model, HttpServletRequest request);
}
