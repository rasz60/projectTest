package com.project.init.dao;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;

public interface PlanIDao {

	// 紐⑤�� �대깽�� 媛��몄�ㅺ린
	ArrayList<PlanMstDto> selectAllPlan(String userId);

	// planNum�쇰� planDt 媛� 媛��몄�ㅺ린
	ArrayList<PlanDtDto> selectPlanDt(String planNum, String userId);
	
	// modal李쎌���� ������ �댁�� 諛���
	String modifyPlanMst(HttpServletRequest request, String userId);
	
	// modal李쎌���� ������ �댁�� 諛��� /*鍮��⑥�⑥��*/
	String deletePlan(String planNum, String userId);
	
	// planNum�쇰� planMst 媛� 媛��몄�ㅺ린
	PlanMstDto selectPlanMst(String planNum, String userId);	
	
	// planDt insert
	String insertPlanDtDo(HttpServletRequest request, String userId, Model model);
	
	// planDt modify (update, insert, delete)
	String detailModifyDo(HttpServletRequest request, String userId);
	
}
