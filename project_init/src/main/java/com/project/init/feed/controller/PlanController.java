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
import com.project.init.feed.dao.UserDao;
import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;
import com.project.init.util.Constant;


@Controller
@RequestMapping("/plan")
public class PlanController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);
	
	@Autowired
	private IDao dao;
	private UserDao udao;
	@Autowired
	public void setUdao(UserDao udao) {
		this.udao = udao;
		Constant.udao = udao;
	}

	// feed-calendar ���������� ������ ������ dto�� ���� plan �������� �̵�
	@RequestMapping("")
	public String planmstDo(PlanDto dto, Model model) {
		logger.info("planmst.do(" + dto.getPlanName() + ") in >>>>");

		model.addAttribute("plan", dto);
		model.addAttribute("id", Constant.username);

		return "plan/mappage2";
	}
	
	// planDt ������ ��ġ�� db�� insert
	@ResponseBody
	@RequestMapping(value = "detail.do", produces = "application/text; charset=UTF-8")
	public String detailDo(HttpServletRequest request, Model model) {
		logger.info("detail.do() in >>>>");
		
		String result = dao.insertPlanDtDo(request, Constant.username, model);
		
		return result;
	}

	
	// modalâ���� �� ���� ������ ������ ��
	@RequestMapping("detail_modify")
	public String detail_modify(String planNum, Model model) {
		logger.info("detail_modify(" + planNum + ") in >>>>");
		
		// planNum���� planMst�� planDt�� ������ model�� ��Ƽ� ����
		PlanDto result1= dao.selectPlanMst(planNum, Constant.username);
		model.addAttribute("plan1", result1);
				
		ArrayList<PlanDto2> result2= dao.selectPlanDt(planNum, Constant.username);
		model.addAttribute("plan2", result2);
		
		return "plan/plan_modify";
	}
	
	
	// modalâ���� �� ���� ������ ��ġ�� db insert
	@ResponseBody
	@RequestMapping(value="detail_modify.do", produces="application/text; charset=UTF-8")
	public String detailModifyDo(HttpServletRequest request) {
		logger.info("detail_modify(" + request.getParameter("planNum") + ") in >>>>");
		
		String result = dao.detailModifyDo(request, Constant.username);
		
		logger.info("detail_modify(" + request.getParameter("planNum") + ") result : " + result);
		return result;
	}
	
}
