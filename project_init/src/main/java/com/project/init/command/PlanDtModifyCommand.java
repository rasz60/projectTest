package com.project.init.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String uId = user.getUsername();
		
		// ó���� query�� �����ؼ� ���� list ����
		ArrayList<PlanDtDto> deleteDtDtos = new ArrayList<PlanDtDto>();
		ArrayList<PlanDtDto> insertDtDtos = new ArrayList<PlanDtDto>();
		ArrayList<PlanDtDto> updateDtDtos = new ArrayList<PlanDtDto>();
		
		// deleteDtNum : ������ ������ �ϳ��� ���� ��
		if(! request.getParameter("deleteDtNum").equals("") ) {
			logger.info("detailModifyDo deleteNum is exist");
			
			// parameter�� �Ѿ�� deleteDtNum�� '/'�� �����Ͽ� �迭�� ����
			String[] deleteDtNums = request.getParameter("deleteDtNum").split("/");
			
			// deleteDtNums �迭�� ��� planDtNum���� dto�� ���� list�� add
			for ( int i = 0; i < deleteDtNums.length; i++ ) {
				
				PlanDtDto dto = new PlanDtDto();
				dto.setUserId(uId);
				dto.setPlanDtNum(Integer.parseInt(deleteDtNums[i]));
				
				deleteDtDtos.add(dto);
			}
			logger.info("detailModifyDo result1: deleteDtDtos.size() ? " + deleteDtDtos.size());
			
		} else {
			logger.info("detailModifyDo deleteNum is null");
		}
		
		// ���� ���������� �Ѿ�� parameter parsing�ؼ� Dto��ü list ����
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		ArrayList<PlanDtDto> dtos = (ArrayList)Constant.planDtDtoParser(planNum, uId, request);

		// Dto�� ���� �ϳ��� add
		for ( int i = 0 ; i < dtos.size(); i++ ) {
			// planDtNum == 0 : ���� �߰��� �� �������� insert
			if ( dtos.get(i).getPlanDtNum() == 0 ) {
				insertDtDtos.add(dtos.get(i));
				
			// planDtNum != 0 : ������ �ִ� �� �������� update
			} else {
				updateDtDtos.add(dtos.get(i));
			}
		};
		
		result = planDao.detailModifyDo(deleteDtDtos, insertDtDtos, updateDtDtos);
		
		request.setAttribute("result", result);
	}

}
