package com.project.init.command;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
		String userId = Constant.username;
		List<PlanDtDto> updatePlanDt = new ArrayList<PlanDtDto>();
		List<PlanDtDto> deletePlanDt = new ArrayList<PlanDtDto>();
		List<PlanDtDto> insertPlanDt = new ArrayList<PlanDtDto>();
		
			
		String originDateCount = request.getParameter("originDateCount");
		String newDateCount = request.getParameter("newDateCount");
		
		// planMst update : [현재] 바뀐 내용이 없더라도 무조건 update 반영
		PlanMstDto mstDto = Constant.planMstDtoParser(request, userId);
		mstDto.setDateCount(newDateCount);
		
		// 수정되기 전 dateCount		
		int origin = Integer.parseInt(originDateCount);
		// 수정되기 후 dateCount
		int newly = Integer.parseInt(newDateCount);

		// dateCount가 작아졌으면 (원래 일정 수 - 새로운 일정 수)만큼 끝에서부터 지우고 나머지 날짜를 바꿔줌
		if ( origin > newly ) {
			// 기존 일정에서 newly+1 일차 일정부터 삭제 ex> origin 5일 , newly 2일 = 3일(newly+1)차부터 끝(origin)까지 planDt삭제
			for (int i = (newly+1); i <= origin; i++) {
				PlanDtDto dtDto = new PlanDtDto();
				dtDto.setPlanNum(mstDto.getPlanNum());
				dtDto.setUserId(userId);
				dtDto.setPlanDay("day"+i);			

				deletePlanDt.add(dtDto);
			}
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);
		}		
		
		// date count가 같으면 각 planDate의 날짜만 바꿔줌
		else if ( origin == newly ) {
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);				
		}
			
		// dateCount가 더 커졌으면 원래의 일정 수 만큼은 바꿔주고 나머지 일자는 빈 일정을 생성해서 insert
		else if ( origin < newly ) {
			// 새로 생성한 dateCount만큼 생성한 배열을 가져옴
			updatePlanDt = Constant.getUpdateDtos(mstDto.getPlanNum(), userId, mstDto.getStartDate(), newly);
			
			// 원래 일정을 초과하는 만큼만 nullPlanDt에 담고 전체 배열에서 삭제함
			for(int i = (newly-1); i >= origin; i-- ) {
				updatePlanDt.get(i).setPlanDtNum(0);
				updatePlanDt.get(i).setTheme("방문");
				updatePlanDt.get(i).setPlaceCount("0");
				
				insertPlanDt.add(updatePlanDt.get(i));
				updatePlanDt.remove(i);
			}
		}	
		String result = planDao.modifyPlanMst(mstDto, updatePlanDt, deletePlanDt, insertPlanDt, userId);
		
		request.setAttribute("result", result);
	}

}
