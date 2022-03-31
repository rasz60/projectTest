package com.project.init.feed.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.project.init.feed.dto.PlanDto;
import com.project.init.feed.dto.PlanDto2;

@Component
public class PlanDAO implements IDao {

	private static final Logger logger = LoggerFactory.getLogger(PlanDAO.class);
	
	private final SqlSession sqlSession;
	
	//sqlSession 생성자 주입
	@Autowired
	public PlanDAO (SqlSession sqlSession) {
		logger.info("PlanDao Const in >>>");
		this.sqlSession = sqlSession;
		
		logger.info("PlanDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}

	// 모든 이벤트 가져오기
	@Override
	public ArrayList<PlanDto> selectAllPlan() {
		logger.info("getCalendarEvent() in >>>");
		
		ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("getCalendarEvent");

		logger.info("getCalendarEvent(" + ") result : dtos.isEmpty() ? " + dtos.isEmpty());
		return dtos;
	}
	
	// planNum으로 planMst 값 가져오기
	@Override
	public PlanDto selectPlanMst(String planNum) {
		logger.info("selectPlan (" + planNum + ") in >>>");
		
		PlanDto dto = sqlSession.selectOne("selectPlanMst", Integer.parseInt(planNum));
		
		logger.info("getCalendarEvent(" + ") result : dtos.isEmpty() ? " + dto.getPlanNum());
		
		return dto;
	}

	
	// planNum으로 planDt 값 가져오기
	@Override
	public ArrayList<PlanDto2> selectPlanDt(String planNum) {
		logger.info("selectPlanDt (" + planNum + ") in >>> ");
		
		ArrayList<PlanDto2> result = (ArrayList)sqlSession.selectList("selectPlanDt", Integer.parseInt(planNum));

		logger.info("selectPlanDt (" +planNum + ") result ? " + result.isEmpty());
		
		return result;
	}
	
	// modal창에서 수정한 내용 반영 /*비효율적*/
	@Override
	@Transactional
	public String modifyPlanMst(HttpServletRequest request) {
		logger.info("modifyPlanMst in >>> ");
		String result = null;

		//parameter parsing
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		String planName = request.getParameter("planName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String originDateCount = request.getParameter("originDateCount");
		String newDateCount = request.getParameter("newDateCount");
		String eventColor = request.getParameter("eventColor");

		
		// planMst update : [현재] 바뀐 내용이 없더라도 무조건 update 반영
		PlanDto mstDto = new PlanDto(planNum, 
				 					 planName, 
									 startDate, 
									 endDate, 
									 newDateCount, 
									 eventColor);

		int res = sqlSession.update("updatePlanMst", mstDto);
		result = res > 0 ? "success": "failed";
		logger.info("modifyPlanMst result 1 : " + result);

		
		// 수정되기 전 dateCount		
		int origin = Integer.parseInt(originDateCount);
		// 수정되기 후 dateCount
		int newly = Integer.parseInt(newDateCount);
		
		
		// startDate Date 객체로 만들어서 작업 ( startDate, planDate, endDate 모두 update )
		int y = Integer.parseInt(startDate.substring(0, 4));
		int m = Integer.parseInt(startDate.substring(5, 7)) - 1;;
		int d = Integer.parseInt(startDate.substring(8));
		
		Date date = new Date((y-1900), m, d);
		
		// dateCount가 작아졌으면 (원래 일정 수 - 새로운 일정 수)만큼 끝에서부터 지우고 나머지 날짜를 바꿔줌
		if ( origin > newly ) {
			// 기존 일정에서 newly+1 일차 일정부터 삭제
			// ex> origin 5일 , newly 2일 = 3일(newly+1)차부터 끝(origin)까지 planDt삭제
			for (int i = (newly+1); i <= origin; i++) {
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+i, "-");
	
				int resDt = sqlSession.delete("deletePlanDt1", dtDto);
				result = resDt > 0 ? "success": "failed";
			}
			
			// 새로 입력된 startDate부터 dateCount만큼 반복
			for ( int i = 0; i < newly; i++ ) {
				// Calendar 객체 이용 하루씩 늘려가면서 planDate 생성하여 update
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, i);
				String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
				int resDt = sqlSession.update("updatePlanDt1", dtDto);
				result = resDt > 0 ? "success": "failed";
			}
		}		
		
		// date count가 같으면 각 planDate의 날짜만 바꿔줌
		else if ( origin == newly ) {
			// 새로 입력된 startDate부터 dateCount만큼 반복
			for ( int i = 0; i < newly; i++ ) {
				// Calendar 객체 이용 하루씩 늘려가면서 planDate 생성하여 update
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.DATE, i);
				String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
				
				PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
				int resDt = sqlSession.update("updatePlanDt1", dtDto);
				result = resDt > 0 ? "success": "failed";
			}
			
			
		// dateCount가 더 커졌으면 원래의 일정 수 만큼만 바꿔줌
		} else if ( origin < newly ) {
			// 새로 입력된 startDate부터 dateCount만큼 반복
			for ( int i = 0; i < origin; i++ ) {
				// Calendar 객체 이용 하루씩 늘려가면서 planDate 생성하여 update
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

	// modal창에서 삭제한 내용 반영 /*비효율적*/
	@Override
	@Transactional
	public String deletePlan(String planNum) {
		logger.info("deletePlan(" + planNum + ") in >>>");

		String result = null;
		
		/* foreign key 연결하면 해결 [ON DELETE CASCADE ENABLE]*/
		
		// [PlanMst] - delete
		int res1 = sqlSession.delete("deletePlanMst", Integer.parseInt(planNum));
		result = res1 > 0 ? "success": "failed";
		logger.info("deletePlan(" + planNum + ") result1 : " + result);
		
		// [PlanDt] - delete
		int res2 = sqlSession.delete("deletePlanDt", Integer.parseInt(planNum));
		result = res2 > 0 ? "success": "failed";
		logger.info("deletePlan(" + planNum + ") result2 : " + result);
		
		
		return result;
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
		String[] placeCount = request.getParameterValues("placeCount");
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
		for ( int i = 0 ; i < planDay.length; i++ ) {
			PlanDto2 dtDto = new PlanDto2(Integer.parseInt(planDtNum[i]),
					  mstDto.getPlanNum(),
					  placeName[i],
					  placeCount[i],
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
	@Transactional
	public String detailModifyDo(HttpServletRequest request) {
		logger.info("detailModifyDo() in >>> ");
		Map<String, String> deleteDtMap = new HashMap<String, String>();
		
		List<Map<String, String>> deleteDtList = new ArrayList<Map<String, String>>();
		String[] deleteDtNum = request.getParameter("deleteDtNum").split("/");
		String result = null;
		
		if ( deleteDtNum != null ) {
			for ( int i = 0; i < deleteDtNum.length; i++ ) {
				if ( Integer.parseInt(deleteDtNum[i]) != 0 ) {
					deleteDtMap.put("value", "planDtNum");
					deleteDtMap.put("planDtNum", deleteDtNum[i]);
				}
				
				deleteDtList.add(deleteDtMap);
			}
			
			int res = sqlSession.delete("deleteDt", deleteDtList);
			result = res == 1 ? "success": "failed";
		}
		
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		
		//[planDt]
		String[] planDtNum = request.getParameterValues("planDtNum");
		String[] placeName = request.getParameterValues("placeName");
		String[] placeCount = request.getParameterValues("placeCount");
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
										  planNum,
										  placeName[i],
										  placeCount[i],
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
			
			if ( dtDto.getPlanDtNum() == 0 ) {
				int res1 = sqlSession.insert("insertDt", dtDto);
				result = res1== 1 ? "success": "failed";
			} else {
				int res2 = sqlSession.update("updatePlanDt2", dtDto);
				result = res2 == 1 ? "success": "failed";
			}
			

		};
		
		
		return result;
	}

}
