package com.project.init.feed.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.feed.dao.IDao;
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;


@Controller
@RequestMapping("/plan")
public class PlanController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	

	// feed-calendar 페이지에서 생성한 일정을 dto로 만들어서 plan 페이지로 이동
	@RequestMapping("")
	public String planmstDo(PlanDto dto, Model model) {
		logger.info("planmst.do(" + dto.getPlanName() + ") in >>>>");

		model.addAttribute("plan", dto);
		
		return "plan/mappage2";
	}
	
	// planDt 생성을 마치고 db로 insert
	@ResponseBody
	@RequestMapping(value = "detail.do", produces = "application/text; charset=UTF-8")
	public String detailDo(HttpServletRequest request, Model model) {
		logger.info("detail.do() in >>>>");
		
		String result = dao.insertPlanDtDo(request, model);
		
		return result;
	}

	
	// modal창에서 상세 일정 수정을 눌렀을 때
	@RequestMapping("detail_modify")
	public String detail_modify(String planNum, Model model) {
		logger.info("detail_modify(" + planNum + ") in >>>>");
		
		// planNum으로 planMst와 planDt를 가져와 model에 담아서 보냄
		PlanDto result1= dao.selectPlanMst(planNum);
		model.addAttribute("plan1", result1);
				
		ArrayList<PlanDto2> result2= dao.selectPlanDt(planNum);
		model.addAttribute("plan2", result2);
		
		return "plan/plan_modify";
	}
	
	
	// modal창에서 상세 일정 수정을 마치고 db insert
	@ResponseBody
	@RequestMapping(value="detail_modify.do", produces="application/text; charset=UTF-8")
	public String detailModifyDo(HttpServletRequest request) {
		logger.info("detail_modify(" + request.getParameter("planNum") + ") in >>>>");
		
		String result = dao.detailModifyDo(request);
		
		logger.info("detail_modify(" + request.getParameter("planNum") + ") result : " + result);
		return result;
	}
	
}
