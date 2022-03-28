package com.project.init.feed.dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.project.init.feed.dto.CommentDto;
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
	public PlanDto selectPlan(int planNum) {
		logger.info("selectPlan (" + planNum + ") in >>>");
		
		PlanDto dto = sqlSession.selectOne("selectPlan", planNum);
		
		return dto;
	}
	
	@Override
	public ArrayList<CommentDto> selectComments() {
		
		ArrayList<CommentDto> dtos = (ArrayList)sqlSession.selectList("selectAllComments");
		
		return dtos;
	}
	
	
	
	@Override
	public String insertMcomment(CommentDto dto) {
		logger.info("insertComment >>> ");
		int res = sqlSession.insert("McommentC", dto);

		logger.info("insertComment result : " + (res == 1 ? "success": "failed") );
		
		return res == 1 ? "success": "failed";
	}

	@Override
	@Transactional
	public String insertPlanDtDo(HttpServletRequest request, Model model) {
		logger.info("insertPlanDtDo >>> ");
		
		String result = null;
		
		
		//[planMst]
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		String planName = request.getParameter("planName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateCount = request.getParameter("dateCount");
		String eventColor = request.getParameter("eventColor");
		
		//Make mstDto
		PlanDto mstDto = new PlanDto(planNum, planName, startDate, endDate, dateCount, eventColor);
		logger.info(mstDto.getDateCount());
		int res1 = sqlSession.insert("insertMst", mstDto);
		result = res1 > 0 ? "success": "failed";
		
		logger.info("insertPlanDtDo res1 : " + result);

		//[planDt]
		String[] planDtNum = request.getParameterValues("planDtNum");
		String[] placeName = request.getParameterValues("placeName");
		String[] planDay = request.getParameterValues("planDay");
		String[] planDate = request.getParameterValues("planDate");
		String[] startTime = request.getParameterValues("startTime");
		String[] endTime = request.getParameterValues("endTime");
		String[] theme = request.getParameterValues("theme");
		String[] latitude = request.getParameterValues("latitude");
		String[] longitude = request.getParameterValues("longitude");
		String[] address = request.getParameterValues("address");
		String[] category = request.getParameterValues("category");
		String[] transportation = request.getParameterValues("transportation");
		String[] details = request.getParameterValues("details");
		
		//Make dtDto[]
		for ( int i = 0 ; i < planDtNum.length; i++ ) {
			PlanDto2 dtDto = new PlanDto2(Integer.parseInt(planDtNum[i]),
										  mstDto.getPlanNum(),
										  placeName[i],
										  planDay[i],
										  planDate[i],
										  startTime[i],
										  endTime[i],
										  theme[i],
										  latitude[i],
										  longitude[i],
										  address[i],
										  category[i],
										  transportation[i],
										  details[i]);
			
			int res2 = sqlSession.insert("insertDt", dtDto);
			result = res2 > 0 ? "success": "failed";
		};

		
		logger.info("insertPlanDtDo res2 : " + result);

		return result;
	}

	@Override
	public String insertMap(Model model, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}
