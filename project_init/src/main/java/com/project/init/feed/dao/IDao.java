package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.init.feed.dto.CommentDto;
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

public interface IDao {
	
	void insertPlan(PlanDto dto);
	
	PlanDto selectPlanMst(String planNum);
	
	
	ArrayList<PlanDto> selectAllPlan();

	String insertMap(Model model, HttpServletRequest request);
	
	String insertPlanDtDo(HttpServletRequest request, Model model);
	
	
	
	String insertMcomment(CommentDto dto);
	ArrayList<CommentDto> selectComments();

	String modifyPlanMst(HttpServletRequest request);

	ArrayList<PlanDto2> selectPlanDt(String planNum);
	String deletePlan(String planNum);
}
