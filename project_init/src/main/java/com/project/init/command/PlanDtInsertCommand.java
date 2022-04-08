package com.project.init.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.project.init.dao.PlanIDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.util.Constant;

public class PlanDtInsertCommand implements ICommand {

	private static final Logger logger = LoggerFactory.getLogger(PlanDtInsertCommand.class);
	
	@Autowired
	private PlanIDao planDao = Constant.pdao;
	
	@Override
	public void execute(HttpServletRequest request, Model model) {
		logger.info("PlanDtInsertCommand in >>> ");
		
		String result = null;
		
		// request에서 넘어온 parameter를 planMstDto로 파싱하는 메서드 실행
		PlanMstDto mstDto = Constant.planMstDtoParser(request, Constant.username);
		logger.info("PlanDtInsertCommand result1 : mstDto.planName ? " + mstDto.getPlanName());
		
		// request에서 넘어온 parameter를 planDtDto로 파싱하는 메서드 실행
		ArrayList<PlanDtDto> dtDtos = (ArrayList)Constant.planDtDtoParser(mstDto.getPlanNum(), Constant.username, request);
		logger.info("PlanDtInsertCommand result2 : dtDtos.isEmpty() ? " + dtDtos.isEmpty());		
		
		result = planDao.insertPlanDtDo(mstDto, dtDtos);
		logger.info("PlanDtInsertCommand result3 : PlanDtInsert " + result);		
	
		request.setAttribute("result", result);
		
	}

}
