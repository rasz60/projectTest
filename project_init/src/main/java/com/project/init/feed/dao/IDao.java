package com.project.init.feed.dao;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;


import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

public interface IDao {
	
	// 紐⑤뱺 �씠踰ㅽ듃 媛��졇�삤湲�
	ArrayList<PlanDto> selectAllPlan(String userId);

	// planNum�쑝濡� planDt 媛� 媛��졇�삤湲�
	ArrayList<PlanDto2> selectPlanDt(String planNum, String userId);
	
	// modal李쎌뿉�꽌 �닔�젙�븳 �궡�슜 諛섏쁺
	String modifyPlanMst(HttpServletRequest request, String userId);
	
	// modal李쎌뿉�꽌 �궘�젣�븳 �궡�슜 諛섏쁺 /*鍮꾪슚�쑉�쟻*/
	String deletePlan(String planNum, String userId);
	
	// planNum�쑝濡� planMst 媛� 媛��졇�삤湲�
	PlanDto selectPlanMst(String planNum, String userId);	
	
	// planDt insert
	String insertPlanDtDo(HttpServletRequest request, String userId, Model model);
	
	// planDt modify (update, insert, delete)
	String detailModifyDo(HttpServletRequest request, String userId);
	
	//main 맵 전체 마커 표시
	ArrayList<PlanDto2> selectPlanList(); 
	
	//mian 맵 필터
	ArrayList<PlanDto2> filter(Map<String, String> map);
	
	ArrayList<PlanDto2> selectPlanDtMap(Map<String, String> map);
	
}
