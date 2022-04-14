package com.project.init.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.util.Constant;

@Component
public class PlanDao implements PlanIDao {
	private static final Logger logger = LoggerFactory.getLogger(PlanDao.class);
	
	private final SqlSession sqlSession;

	// sqlSession 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
	@Autowired
	public PlanDao (SqlSession sqlSession) {
		logger.info("PlanDao Const in >>>");
		this.sqlSession = sqlSession;
		Constant.pdao = this;
		
		logger.info("PlanDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	
	// 占쏙옙占� PlanDt占쏙옙 占쌀뤄옙占쏙옙
	@Override
	public ArrayList<PlanDtDto> selectPlanList() {
		logger.info("selectPlanList() in >>>");
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("selectPlanList");
		
		logger.info("getCalendarEvent() result : dtos.isEmpty() ? " + result.isEmpty());
		return result;
	}
	
	
	// 占쏙옙占� PlanDt占쏙옙 占쌀뤄옙占쏙옙
	@Override
	public ArrayList<PlanDtDto> filter(Map<String, String> map) {
		logger.info("filter() in >>> ");
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("filter", map);
		
		logger.info("filter() result : result.isEmpty() ? " + result.isEmpty());
		
		return result;
	}
	
	
	
	// 占쏙옙占� 占싱븝옙트 占쏙옙占쏙옙占쏙옙占쏙옙
	@Override
	public ArrayList<PlanMstDto> selectAllPlan(String userId) {
		logger.info("getCalendarEvent(" + userId + ") in >>>");

		ArrayList<PlanMstDto> dtos = (ArrayList)sqlSession.selectList("getCalendarEvent", userId);

		logger.info("getCalendarEvent(" + ") result : dtos.isEmpty() ? " + dtos.isEmpty());
		return dtos;
	}
	
	// planNum占쏙옙占쏙옙 planMst 占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
	@Override
	public PlanMstDto selectPlanMst(String planNum, String userId) {
		logger.info("selectPlan (" + planNum + ") in >>>");
		
		PlanMstDto dto = new PlanMstDto();
		dto.setPlanNum(Integer.parseInt(planNum));
		dto.setUserId(userId);
		
		dto = sqlSession.selectOne("selectPlanMst", dto);
		
		logger.info("selectPlanMst(" + planNum + ") result : " + dto.getPlanNum());
		
		return dto;
	}

	
	// planNum占쏙옙占쏙옙 planDt 占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
	@Override
	public ArrayList<PlanDtDto> selectPlanDt(String planNum, String userId) {
		logger.info("selectPlanDt (" + planNum + ") in >>> ");
		
		PlanDtDto dto = new PlanDtDto();
		dto.setPlanNum(Integer.parseInt(planNum));
		dto.setUserId(userId);
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("selectPlanDt", dto);
		
		logger.info("selectPlanDt (" +planNum + ") result ? " + result.isEmpty());
		
		return result;
	}
	
	
	
	// modal창占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쌥울옙
	@Override
	@Transactional
	public String modifyPlanMst(PlanMstDto mstDto, List<PlanDtDto> updatePlanDt, List<PlanDtDto> deletePlanDt, List<PlanDtDto> insertPlanDt, String userId) {
		logger.info("modifyPlanMst in >>> ");
		String result = null;
		
		if( mstDto != null ) {
		
			int resMst = sqlSession.update("updatePlanMst", mstDto);
			result = resMst > 0 ? "success": "failed";
			logger.info("modifyPlanMst result 1 : " + result);
			
		} 
		
		if (deletePlanDt.isEmpty() == false ) {
			
			int resDt1 = sqlSession.delete("deletePlanDt1", deletePlanDt);
			result = resDt1 > 0 ? "success": "failed";
			logger.info("modifyPlanMst result 2 : " + result);
			
		}
		
		if (insertPlanDt.isEmpty() == false ) {			
		
			int resDt2 = sqlSession.insert("insertNullDt", insertPlanDt);
			result = resDt2 > 0 ? "success": "failed";
			logger.info("modifyPlanMst result 3 : " + result);		
		
		}

		if ( updatePlanDt.isEmpty() == false ) {
		
			int resDt3 = sqlSession.update("updatePlanDt1", updatePlanDt);
			result = resDt3 == 0 ? "success": "failed";
			logger.info("modifyPlanMst result 4 : " + result);	
		
		}
		
		logger.trace("modifyPlanMst result : " + result);
	
		return result;
	}
	
	// modal창占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙 占쌥울옙
	@Override
	@Transactional
	public String deletePlanMst(String planNum, String userId) {
		logger.info("deletePlan(" + planNum + ") in >>>");

		String result = null;
		
		PlanMstDto dto = new PlanMstDto();
		dto.setPlanNum(Integer.parseInt(planNum));
		dto.setUserId(userId);
		
		// [PlanMst] - delete
		int res1 = sqlSession.delete("deletePlanMst", dto);
		result = res1 > 0 ? "success": "failed";
		logger.trace("deletePlan(" + planNum + ") result1 : " + result);
		
		// [PlanDt] - delete
		int res2 = sqlSession.delete("deletePlanDt", dto);
		result = res2 > 0 ? "success": "failed";
		logger.trace("deletePlan(" + planNum + ") result2 : " + result);

		return result;
	}
	
	
	// planDt insert
	@Override
	@Transactional
	public String insertPlanDtDo(PlanMstDto mstDto, ArrayList<PlanDtDto> dtDtos) {
		logger.info("insertPlanDtDo >>> ");
		
		String result = null;

		int res1 = sqlSession.insert("insertMst", mstDto);
		result = res1 > 0 ? "success": "failed";
		logger.trace("insertPlanDtDo res1(Mst) : " + result);
		
		for ( int i = 0; i < dtDtos.size(); i++ ) {
			dtDtos.get(i).setPlanNum(mstDto.getPlanNum());
		}
		
		// 占썼열占쏙옙 占쏙옙占쏙옙占쏙옙 insert 占쏙옙占쏙옙
		int res2 = sqlSession.insert("insertDt", dtDtos);
		result = res2 > 0 ? "success": "failed";
		logger.trace("insertPlanDtDo res2(Dt) : " + result);

		return result;
	}
	
	
	// planDt modify(update, delete, insert 占쏙옙占쏙옙 占쌩삼옙)
	@Override
	@Transactional
	public String detailModifyDo(ArrayList<PlanDtDto> deleteDtDtos, ArrayList<PlanDtDto> insertDtDtos, ArrayList<PlanDtDto> updateDtDtos) {
		logger.info("detailModifyDo() in >>> ");
	
		String result = null;
		
		if ( deleteDtDtos.isEmpty() == false ) {
			int res1 = sqlSession.delete("deleteDt", deleteDtDtos);
			result = res1 > 0 ? "success": "failed";
			logger.info("detailModifyDo result 1 : deletePlanDt ? " + result);	
		} 
		
		if ( insertDtDtos.isEmpty() == false ) {	
			int res2 = sqlSession.insert("insertNullDt", insertDtDtos);
			result = res2 > 0 ? "success": "failed";
			logger.info("detailModifyDo result 2 : insertPlanDt ? " + result);	
		}
		
		if (updateDtDtos.isEmpty() == false ) {
			int res3 = sqlSession.update("updatePlanDt2", updateDtDtos);
			result = res3 > 0 ? "success": "failed";
			logger.info("detailModifyDo result 3 : updatePlanDt ? " + result);	
		}
		
		return result;
	}
	
	@Override
	public ArrayList<PlanDtDto> selectPlanDtMap(Map<String, String> map) {
		logger.info("selectPlanDtMap() in >>>");
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("selectPlanDtMap", map);
		
		logger.info("selectPlanDtMap() result : result.isEmpty() ? " + result.isEmpty());
		return result;
	}
	
	@Override
	public int countPlanMst(String email) {
		int res = sqlSession.selectOne("countPlanMst", email);
		
		System.out.println(res);
		
		return res;
	}
	
}
