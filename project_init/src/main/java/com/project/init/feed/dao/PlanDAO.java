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
		
		logger.info("insertPlan(" + dto + ") result1 : " + result);
		logger.info("insertPlan(" + dto + ") result2 : " + dto.getPlanNum());
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
		
		String index = request.getParameter("placecount"); //mappage占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쏙옙占� 占쏙옙(placecount) 占쏙옙占쌔쇽옙 index 占쏙옙체占쏙옙 占쏙옙占�
		int j = Integer.parseInt(index); // String占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙 Integer占쏙옙 占쏙옙 占쏙옙환 占쌔쇽옙 占쏙옙체 j占쏙옙 占쏙옙占�
		System.out.println("j");
		for(int i = 0; i < j; i++) { //占쏙옙체 j占쏙옙 占쏙옙占쏙옙큼 占쌥븝옙占쌔쇽옙 result占쏙옙 占쏙옙占쏙옙
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





	@Override
	public PlanDto selectPlan(int planNum) {
		logger.info("selectPlan (" + planNum + ") in >>>");
		
		PlanDto dto = sqlSession.selectOne("selectPlan", planNum);
		
		return dto;
	}
	
}
