package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

public interface IDao {
	
	// ��� �̺�Ʈ ��������
	ArrayList<PlanDto> selectAllPlan();
	
	// planNum���� planDt �� ��������
	ArrayList<PlanDto2> selectPlanDt(String planNum);
	
	// modalâ���� ������ ���� �ݿ�
	String modifyPlanMst(HttpServletRequest request);
	
	// modalâ���� ������ ���� �ݿ�
	String deletePlan(String planNum);
	
	
	// planNum���� planMst �� ��������
	PlanDto selectPlanMst(String planNum);	
	
	
	String insertPlanDtDo(HttpServletRequest request, Model model);
	
	String detailModifyDo(HttpServletRequest request);
	
}
