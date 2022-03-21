package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

@Component
public class PlanDAO implements IDao {

	private static final Logger logger = LoggerFactory.getLogger(PlanDAO.class);
	
	private final SqlSession sqlSession;
	
	@Autowired
	public PlanDAO (SqlSession sqlSession) {
		logger.info("PlanDao Const in >>>");
		this.sqlSession = sqlSession;
		
		logger.info("PlanDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	
	@Override
	public void insertPlan(PlanDto dto) {
		logger.info("insertPlan(" + dto + ") in >>>");
		int result = sqlSession.insert("insertPlan", dto);
		
		logger.info("insertPlan(" + dto + ") result : " + result);
	}

	@Override
	public ArrayList<PlanDto> selectAllPlan() {
		logger.info("selectAllPlan() in >>>");
		
		ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("selectAllPlan");
		
		logger.info("selectAllPlan(" + ") result : dtos.isEmpty() ? " + dtos.isEmpty());
		return dtos;
	}


	@Override
	public String updatePlan(PlanDto dto) {
		logger.info("PlanDto(" + dto + ") in >>>");
		int result = sqlSession.update("updatePlan", dto);
		
		logger.info("PlanDto(" + dto + ") result : " + result);
		
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

		logger.info("deletePlan(" + planNum + ") result : " + result);
		
		if ( result > 0 ) {
			return "success";
		} else {
			return "failed";
		}
	}


	@Override
	public String insertMap(Model model, HttpServletRequest request) {
		logger.info("insertMap in >>>> ");
		
		String index = request.getParameter("placecount"); //mappage에서 선택한 장소 수(placecount) 구해서 index 객체에 담기
		int j = Integer.parseInt(index); // String으로 받은 값 Integer로 형 변환 해서 객체 j에 담기
		System.out.println("j");
		for(int i = 0; i < j; i++) { //객체 j의 수만큼 반복해서 result에 저장
			PlanDto2 pdto2 = new PlanDto2(null,
					  request.getParameter("plan-name"),
					  request.getParameter("start-date"),
					  request.getParameter("end-date"),
					  request.getParameter("latitude" + i),
					  request.getParameter("longitude" + i), 
					  request.getParameter("placeName" + i), 
					  request.getParameter("category" + i),
					  request.getParameter("placecount"),
					  request.getParameter("address" + i));
			int res = sqlSession.insert("insertMap", pdto2);
		}
		
		return "success";
	}
	
}
