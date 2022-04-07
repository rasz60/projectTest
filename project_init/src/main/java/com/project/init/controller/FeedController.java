package com.project.init.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.command.ICommand;
import com.project.init.dao.PlanIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private PlanIDao dao;
	private ICommand comm;
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
		
		return "feed/feed_calendar";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanMstDto> getAllPlans() {
		logger.info("getAllPlans() >>>>");
		
		ArrayList<PlanMstDto> result = dao.selectAllPlan(Constant.username);
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDtDto> modifyModal(@RequestBody String planNum, Model model) {
		logger.info("modifyModal("+ planNum +") in >>>>");
		
		ArrayList<PlanDtDto> result= dao.selectPlanDt(planNum, Constant.username);
		
		logger.info("modifyPlans("+ planNum +") result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "modify_plan.do", produces="application/text; charset=UTF-8")
	public String modifyPlan(HttpServletRequest request) {
		logger.info("modifyPlans("+ request.getParameter("planNum") +") in >>>>");

		String result= null;
		
		result = dao.modifyPlanMst(request, Constant.username);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_plan.do", produces="application/text; charset=UTF-8")
	public String deletePlan(@RequestBody String planNum) {
		logger.info("modifyPlans("+ planNum +") in >>>>");
		
		String result = dao.deletePlan(planNum, Constant.username);
		
		return result;
	}
	
	@RequestMapping("feedMap")
	public String feedMap() {
		logger.info("feedMap() in >>>>");
		return "feed/feed_map";
	}
	
	@RequestMapping("feedPost")
	public String feedPost() {
		logger.info("feedPost() in >>>>");
		return "feed/feed_post";
	}
	
	@RequestMapping("feedInfo")
	public String feedInfo() {
		logger.info("feedMap() in >>>>");
		return "feed/feed_user_info";
	}
	
	
	
}
