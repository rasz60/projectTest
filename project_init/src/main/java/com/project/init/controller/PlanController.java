package com.project.init.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.init.dao.PlanIDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.util.Constant;

@Controller
@RequestMapping("/plan")
public class PlanController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private PlanIDao dao;
	private UserDao udao;
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}

	@RequestMapping("")
	public String planmstDo(PlanMstDto dto, Model model) {
		logger.info("planmst.do(" + dto.getPlanName() + ") in >>>>");

		model.addAttribute("plan", dto);
		model.addAttribute("id", Constant.username);

		return "plan/plan_detail";
	}
	
	// planDt 占쏙옙占쏙옙占쏙옙 占쏙옙치占쏙옙 db占쏙옙 insert
	@ResponseBody
	@RequestMapping(value = "detail.do", produces = "application/text; charset=UTF-8")
	public String detailDo(HttpServletRequest request, Model model) {
		logger.info("detail.do() in >>>>");
		
		String result = dao.insertPlanDtDo(request, Constant.username, model);
		
		return result;
	}

	
	// modal창占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙
	@RequestMapping("detail_modify")
	public String detail_modify(String planNum, Model model) {
		logger.info("detail_modify(" + planNum + ") in >>>>");
		
		// planNum占쏙옙占쏙옙 planMst占쏙옙 planDt占쏙옙 占쏙옙占쏙옙占쏙옙 model占쏙옙 占쏙옙티占� 占쏙옙占쏙옙
		PlanMstDto result1= dao.selectPlanMst(planNum, Constant.username);
		model.addAttribute("plan1", result1);
				
		ArrayList<PlanDtDto> result2= dao.selectPlanDt(planNum, Constant.username);
		model.addAttribute("plan2", result2);
		
		return "plan/plan_modify";
	}
	
	
	// modal창占쏙옙占쏙옙 占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙치占쏙옙 db insert
	@ResponseBody
	@RequestMapping(value="detail_modify.do", produces="application/text; charset=UTF-8")
	public String detailModifyDo(HttpServletRequest request) {
		logger.info("detail_modify(" + request.getParameter("planNum") + ") in >>>>");
		
		String result = dao.detailModifyDo(request, Constant.username);
		
		logger.info("detail_modify(" + request.getParameter("planNum") + ") result : " + result);
		return result;
	}
	
}
