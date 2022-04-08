package com.project.init.feed.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.context.request.AbstractRequestAttributesScope;
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
		// feed-calendar 占쏙옙占쌈쏙옙 占쏙옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
		logger.info("getAllPlans() >>>>");
		
		ArrayList<PlanDto> result = dao.selectAllPlan(Constant.username);
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> modifyModal(@RequestBody String planNum) {
		// event 占쏙옙 클占쏙옙占싹울옙 modal창 占쏙옙占쏙옙占� 占쏙옙, planDt 占쏙옙占� 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
		logger.info("modifyModal("+ planNum +") in >>>>");
		
		ArrayList<PlanDto2> result= dao.selectPlanDt(planNum, Constant.username);
		
		logger.info("modifyPlans("+ planNum +") result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "modify_plan.do", produces="application/text; charset=UTF-8")
	public String modifyPlan(HttpServletRequest request) {
		// modal창占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 planMst, planDt 占쏙옙占쏙옙 占쏙옙占쏙옙占싹깍옙
		logger.info("modifyPlans("+ request.getParameter("planNum") +") in >>>>");

		String result= null;
		
		result = dao.modifyPlanMst(request, Constant.username);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_plan.do", produces="application/text; charset=UTF-8")
	public String deletePlan(@RequestBody String planNum) {
		// modal창占쏙옙占쏙옙 占쏙옙占쏙옙占싹몌옙 planMst, planDt 占쏙옙占� 占쏙옙占쏙옙占싹깍옙
		logger.info("modifyPlans("+ planNum +") in >>>>");
		
		String result = dao.deletePlan(planNum, Constant.username);
		
		return result;
	}
	
	@RequestMapping("feedMap")
	public String feedMap() {
		logger.info("feedMap() in >>>>");
		return "feed/map";
	}
	
	@RequestMapping("feedPost")
	public String feedPost() {
		logger.info("feedPost() in >>>>");
		return "feed/post";
	}
	
	@RequestMapping("feedInfo")
	public String feedInfo() {
		logger.info("feedMap() in >>>>");
		return "feed/user_info";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlansMap.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> getAllPlansMap(HttpServletRequest request, Model model) {
		
		logger.info("getAllPlansMap() >>>>");
		String userId = request.getParameter("userId");
		String value1 = request.getParameter("value1");
		String value2 = request.getParameter("value2");
		String value3 = request.getParameter("value3");
		String value4 = request.getParameter("value4");
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		map.put("value1", value1);
		map.put("value2", value2);
		map.put("value3", value3);
		map.put("value4", value4);
		ArrayList<PlanDto2> result = dao.selectPlanDtMap(map);

		request.setAttribute("selectPlanDtMap", result);
		return result;
	}
	
}
