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
		//logger.info("selectAllPlan() in >>>");
		//ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("selectAllPlan");
		
		logger.info("getCalendarEvent() in >>>");
		ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("getCalendarEvent");
		logger.info("getCalendarEvent(" + ") result : dtos.isEmpty() ? " + dtos.isEmpty());
		
		//logger.info("selectAllPlan(" + ") result : dtos.isEmpty() ? " + dtos.isEmpty());
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
		
		String index = request.getParameter("placecount"); //mappage���� ������ ��� ��(placecount) ���ؼ� index ��ü�� ���
		int j = Integer.parseInt(index); // String���� ���� �� Integer�� �� ��ȯ �ؼ� ��ü j�� ���
		System.out.println("j");
		for(int i = 0; i < j; i++) { //��ü j�� ����ŭ �ݺ��ؼ� result�� ����
			PlanDto2 pdto2 = new PlanDto2(null,
					  request.getParameter("plan-name"),
					  request.getParameter("start-date"),
					  request.getParameter("end-date"),
					  request.getParameter("theme"),
					  request.getParameter("latitude" + i),
					  request.getParameter("longitude" + i), 
					  request.getParameter("placeName" + i), 
					  request.getParameter("placecount"),
					  request.getParameter("category" + i),
					  request.getParameter("address" + i));
			int res = sqlSession.insert("insertMap", pdto2);
		}
		
		return "success";
	}
	
}
