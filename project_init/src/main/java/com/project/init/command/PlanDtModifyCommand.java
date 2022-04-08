package com.project.init.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.project.init.dao.PlanIDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.util.Constant;

public class PlanDtModifyCommand implements ICommand {

	private static final Logger logger = LoggerFactory.getLogger(PlanDtModifyCommand.class);
	
	@Autowired
	private PlanIDao planDao = Constant.pdao;
	
	@Override
	public void execute(HttpServletRequest request, Model model) {
		logger.info("detailModifyDo() in >>> ");
		
		String result = null;
		String userId = Constant.username;
		
		// 처리할 query로 구분해서 담을 list 생성
		ArrayList<PlanDtDto> deleteDtDtos = new ArrayList<PlanDtDto>();
		ArrayList<PlanDtDto> insertDtDtos = new ArrayList<PlanDtDto>();
		ArrayList<PlanDtDto> updateDtDtos = new ArrayList<PlanDtDto>();
		
		// deleteDtNum : 삭제된 일정이 하나라도 있을 때
		if(! request.getParameter("deleteDtNum").equals("") ) {
			logger.info("detailModifyDo deleteNum is exist");
			
			// parameter로 넘어온 deleteDtNum을 '/'로 구분하여 배열로 생성
			String[] deleteDtNums = request.getParameter("deleteDtNum").split("/");
			
			// deleteDtNums 배열에 담긴 planDtNum으로 dto를 만들어서 list에 add
			for ( int i = 0; i < deleteDtNums.length; i++ ) {
				
				PlanDtDto dto = new PlanDtDto();
				dto.setUserId(userId);
				dto.setPlanDtNum(Integer.parseInt(deleteDtNums[i]));
				
				deleteDtDtos.add(dto);
			}
			logger.info("detailModifyDo result1: deleteDtDtos.size() ? " + deleteDtDtos.size());
			
		} else {
			logger.info("detailModifyDo deleteNum is null");
		}
		
		// 수정 페이지에서 넘어온 parameter parsing해서 Dto객체 list 생성
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		ArrayList<PlanDtDto> dtos = (ArrayList)Constant.planDtDtoParser(planNum, userId, request);

		// Dto로 만들어서 하나씩 add
		for ( int i = 0 ; i < dtos.size(); i++ ) {
			// planDtNum == 0 : 새로 추가된 상세 일정으로 insert
			if ( dtos.get(i).getPlanDtNum() == 0 ) {
				insertDtDtos.add(dtos.get(i));
				
			// planDtNum != 0 : 기존에 있던 상세 일정으로 update
			} else {
				updateDtDtos.add(dtos.get(i));
			}
		};
		
		result = planDao.detailModifyDo(deleteDtDtos, insertDtDtos, updateDtDtos);
		
		request.setAttribute("result", result);
	}

}
