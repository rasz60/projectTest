package com.project.init.feed.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	
	@Override
	@Transactional
	public String modifyPlanMst(HttpServletRequest request) {
		logger.info("modifyPlanMst in >>> ");
		String result = null;
		
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		String planName = request.getParameter("planName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String originDateCount = request.getParameter("originDateCount");
		String newDateCount = request.getParameter("newDateCount");
		String eventColor = request.getParameter("eventColor");
		
		PlanDto mstDto = new PlanDto(planNum, 
				 					 planName, 
									 startDate, 
									 endDate, 
									 newDateCount, 
									 eventColor);
		
		
		
		int res = sqlSession.update("updatePlanMst", mstDto);

		result = res > 0 ? "success": "failed";
		
		logger.info("modifyPlanMst result 1 : " + result);
		
		int origin = Integer.parseInt(originDateCount);
		int newly = Integer.parseInt(newDateCount);
		
		
		int y = Integer.parseInt(startDate.substring(0, 4));
		int m = Integer.parseInt(startDate.substring(5, 7)) - 1;;
		int d = Integer.parseInt(startDate.substring(8));
		
		Date date = new Date((y-1900), m, d);
		
		// date count가 같으면 각 date의 날짜만 바꿔줌
		if ( origin == newly ) {
			
			for ( int i = 0; i < newly; i++ ) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, i);
				
				String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
				
				int resDt = sqlSession.update("updatePlanDt1", dtDto);
				
				result = resDt > 0 ? "success": "failed";
			}
			
			
		// date count가 더 커졌으면 원래의 일정 수 만큼만 바꿔줌
		} else if ( origin < newly ) {
			
			for ( int i = 0; i < origin; i++ ) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, i);
				
				String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
				
				int resDt = sqlSession.update("updatePlanDt1", dtDto);
				
				result = resDt > 0 ? "success": "failed";
			}
			
			// date count가 작아졌으면 (원래 일정 수 - 새로운 일정 수) 만큼 지우고 나머지 날짜를 바꿈		
		} else if ( origin > newly ) {
			
			for (int i = (newly+1); i <= origin; i++) {
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+i, "-");

				int resDt = sqlSession.delete("deletePlanDt1", dtDto);
				result = resDt > 0 ? "success": "failed";
				
				result = resDt > 0 ? "success": "failed";
			}
			
			
			for ( int i = 0; i < newly; i++ ) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, i);
				
				String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
				
				int resDt = sqlSession.update("updatePlanDt1", dtDto);
				
				result = resDt > 0 ? "success": "failed";
			}
			
		}
		
		logger.info("modifyPlanMst result 2 : " + result);
	
		return result;
	}

	@Override
	public ArrayList<PlanDto2> selectPlanDt(int planNum) {
		logger.info("selectPlanDt (" + planNum + ") in >>> ");
		
		ArrayList<PlanDto2> result = (ArrayList)sqlSession.selectList("selectPlanDt", planNum);
		
		
		logger.info("selectPlanDt (" + planNum + ") result ? " + result.isEmpty());
		
		return result;
	}

}
