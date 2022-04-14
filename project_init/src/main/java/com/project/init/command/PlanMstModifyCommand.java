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

import com.project.init.dao.PlanDao;
import com.project.init.dao.PlanIDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.util.Constant;

public class PlanMstModifyCommand implements ICommand {

	private static final Logger logger = LoggerFactory.getLogger(PlanMstModifyCommand.class);
	
	@Autowired
	private PlanIDao planDao = Constant.pdao;
	
	@Override
	public void execute(HttpServletRequest request, Model model) {
		// pdao Parameter Setting
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		String userId = user.getUsername();

		List<PlanDtDto> updatePlanDt = new ArrayList<PlanDtDto>();
		List<PlanDtDto> deletePlanDt = new ArrayList<PlanDtDto>();
		List<PlanDtDto> insertPlanDt = new ArrayList<PlanDtDto>();
		
			
		String originDateCount = request.getParameter("originDateCount");
		String newDateCount = request.getParameter("newDateCount");
		
		// planMst update : [����] �ٲ� ������ ������ ������ update �ݿ�
		PlanMstDto mstDto = Constant.planMstDtoParser(request, userId);
		mstDto.setDateCount(newDateCount);
		
		// �����Ǳ� �� dateCount		
		int origin = Integer.parseInt(originDateCount);
		// �����Ǳ� �� dateCount
		int newly = Integer.parseInt(newDateCount);

		// dateCount�� �۾������� (���� ���� �� - ���ο� ���� ��)��ŭ ���������� ����� ������ ��¥�� �ٲ���
		if ( origin > newly ) {
			// ���� �������� newly+1 ���� �������� ���� ex> origin 5�� , newly 2�� = 3��(newly+1)������ ��(origin)���� planDt����
			for (int i = (newly+1); i <= origin; i++) {
				PlanDtDto dtDto = new PlanDtDto();
				dtDto.setPlanNum(mstDto.getPlanNum());
				dtDto.setUserId(userId);
				dtDto.setPlanDay("day"+i);			

				deletePlanDt.add(dtDto);
			}
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);
		}		
		
		// date count�� ������ �� planDate�� ��¥�� �ٲ���
		else if ( origin == newly ) {
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);				
		}
			
		// dateCount�� �� Ŀ������ ������ ���� �� ��ŭ�� �ٲ��ְ� ������ ���ڴ� �� ������ �����ؼ� insert
		else if ( origin < newly ) {
			System.out.println(origin + " - " + newly );
			
			// ���� ������ dateCount��ŭ ������ �迭�� ������
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);
			
			System.out.println("updateDt.size : " + updatePlanDt.size());
			
			// ���� ������ �ʰ��ϴ� ��ŭ�� nullPlanDt�� ��� ��ü �迭���� ������
			for(int i = (newly-1); i >= origin; i-- ) {
				updatePlanDt.get(i).setPlanDtNum(0);
				updatePlanDt.get(i).setTheme("방문");
				updatePlanDt.get(i).setTransportation("도보");
				updatePlanDt.get(i).setPlaceCount("0");
				System.out.println("updateDt.size : " + updatePlanDt.size());
				insertPlanDt.add(updatePlanDt.get(i));
				System.out.println("insertPlanDt.size : " + updatePlanDt.size());
				updatePlanDt.remove(i);
				System.out.println("updateDt.size : " + updatePlanDt.size());
			}
		}	
		String result = planDao.modifyPlanMst(mstDto, updatePlanDt, deletePlanDt, insertPlanDt, userId);
		
		request.setAttribute("result", result);
	}

}
