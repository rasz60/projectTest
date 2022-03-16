package com.project.init.feed.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.feed.dto.PlanDto;

@Component
public class PlanDAO implements IDao {

	private static final Logger logger = LoggerFactory.getLogger(PlanDAO.class);
	
	private final SqlSession sqlSession;
	
	@Autowired
	public PlanDAO (SqlSession sqlSession) {
		logger.info("PlanDao Const in >>>");
		this.sqlSession = sqlSession;
		
		logger.info("PlanDao Const out >>> sqlSession getConn success ? " + sqlSession.toString());
	}
	
	
	@Override
	public void insertPlan(PlanDto dto) {
		logger.info("insertPlan(" + dto + ") in >>>");
		int result = sqlSession.insert("insertPlan", dto);
		
		logger.info("insertPlan(" + dto + ") out >>> result : " + result);
	}

	@Override
	public ArrayList<PlanDto> selectAllPlan() {
		logger.info("selectAllPlan() in >>>");
		
		ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("selectAllPlan");
		
		logger.info("selectAllPlan(" + ") out >>> dtos.isEmpty() ? " + dtos.isEmpty());
		return dtos;
	}


	@Override
	public String updatePlan(PlanDto dto) {
		logger.info("PlanDto(" + dto + ") in >>>");
		int result = sqlSession.update("updatePlan", dto);
		
		logger.info("PlanDto(" + dto + ") out >>> result : " + result);
		
		if ( result > 0 ) {
			return "success";
		} else {
			return "failed";
		}
	}
	
	@Override
	public String deletePlan(String planNum) {
		logger.info("deletePlan(" + planNum + ") in >>>");
		
		
		int result = sqlSession.delete("deletePlan", planNum);
		
		
		logger.info("deletePlan(" + planNum + ") out >>> result : " + result);
		
		if ( result > 0 ) {
			return "success";
		} else {
			return "failed";
		}
	}
	

}
