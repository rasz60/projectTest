package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

public interface IDao {
	
	// 모든 이벤트 가져오기
	ArrayList<PlanDto> selectAllPlan();

	// planNum으로 planDt 값 가져오기
	ArrayList<PlanDto2> selectPlanDt(String planNum);
	
	// modal창에서 수정한 내용 반영
	String modifyPlanMst(HttpServletRequest request);
	
	// modal창에서 삭제한 내용 반영 /*비효율적*/
	String deletePlan(String planNum);
	
	// planNum으로 planMst 값 가져오기
	PlanDto selectPlanMst(String planNum);	
	
	// planDt insert
	String insertPlanDtDo(HttpServletRequest request, Model model);
	
	// planDt modify (update, insert, delete)
	String detailModifyDo(HttpServletRequest request);
	
}
