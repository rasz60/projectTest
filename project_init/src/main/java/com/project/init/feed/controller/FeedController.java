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
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	
	@RequestMapping("")
	public String feed() {
		logger.info("feed page >>>>");
		return "feed/main";
	}
	
	@ResponseBody
	@RequestMapping(value="getAllPlans.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto> getAllPlans() {
		// feed-calendar 접속시 모든 일정 가져오기
		logger.info("getAllPlans() >>>>");
		
		ArrayList<PlanDto> result = dao.selectAllPlan();
				
		logger.info("getAllPlans() result.isEmpty() ? " + result.isEmpty());

		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "modify_modal.do", produces="application/json; charset=UTF-8")
	public ArrayList<PlanDto2> modifyModal(@RequestBody String planNum) {
		// event 블럭 클릭하여 modal창 띄웠을 때, planDt 모든 내용 가져오기
		logger.info("modifyModal("+ planNum +") in >>>>");
		
		ArrayList<PlanDto2> result= dao.selectPlanDt(planNum);
		
		logger.info("modifyPlans("+ planNum +") result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "modify_plan.do", produces="application/text; charset=UTF-8")
	public String modifyPlan(HttpServletRequest request) {
		// modal창에서 수정된 내용으로 planMst, planDt 내용 수정하기
		logger.info("modifyPlans("+ request.getParameter("planNum") +") in >>>>");

		String result= null;
		
		result = dao.modifyPlanMst(request);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "delete_plan.do", produces="application/text; charset=UTF-8")
	public String deletePlan(@RequestBody String planNum) {
		// modal창에서 삭제하면 planMst, planDt 모두 삭제하기
		logger.info("modifyPlans("+ planNum +") in >>>>");
		
		String result = dao.deletePlan(planNum);
		
		return result;
	}

}
