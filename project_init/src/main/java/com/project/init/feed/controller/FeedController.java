package com.project.init.feed.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.init.feed.dao.IDao;
import com.project.init.feed.dao.UserDao;
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	private UserDao udao;
	
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}
	
	@RequestMapping("")
	public String feed(Model model) {
		logger.info("feed page " + Constant.username + " >>>>");
		
		model.addAttribute("id", Constant.username);
		
		return "feed/main";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto> getAllPlans() {
		// feed-calendar ���ӽ� ��� ���� ��������
		logger.info("getAllPlans() >>>>");
		
		ArrayList<PlanDto> result = dao.selectAllPlan(Constant.username);
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> modifyModal(@RequestBody String planNum) {
		// event �� Ŭ���Ͽ� modalâ ����� ��, planDt ��� ���� ��������
		logger.info("modifyModal("+ planNum +") in >>>>");
		
		ArrayList<PlanDto2> result= dao.selectPlanDt(planNum, Constant.username);
		
		logger.info("modifyPlans("+ planNum +") result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "modify_plan.do", produces="application/text; charset=UTF-8")
	public String modifyPlan(HttpServletRequest request) {
		// modalâ���� ������ �������� planMst, planDt ���� �����ϱ�
		logger.info("modifyPlans("+ request.getParameter("planNum") +") in >>>>");

		String result= null;
		
		result = dao.modifyPlanMst(request, Constant.username);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_plan.do", produces="application/text; charset=UTF-8")
	public String deletePlan(@RequestBody String planNum) {
		// modalâ���� �����ϸ� planMst, planDt ��� �����ϱ�
		logger.info("modifyPlans("+ planNum +") in >>>>");
		
		String result = dao.deletePlan(planNum, Constant.username);
		
		return result;
	}

}
