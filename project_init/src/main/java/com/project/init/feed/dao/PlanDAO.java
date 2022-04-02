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
	
	// sqlSession 생성자 주입
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
		
		logger.info("selectPlanMst(" + planNum + ") result : " + dto.getPlanNum());
		
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
	
	// modal창에서 수정한 내용 반영 /* 비효율적 = 1.수정 내용 없어도 update처리 */
	@Override
	@Transactional
	public String modifyPlanMst(HttpServletRequest request) {
		logger.info("modifyPlanMst in >>> ");
		String result = null;
		
		String originDateCount = request.getParameter("originDateCount");
		String newDateCount = request.getParameter("newDateCount");
		
		// planMst update : [현재] 바뀐 내용이 없더라도 무조건 update 반영
		PlanDto mstDto = planMstDtoParser(request);
		mstDto.setDateCount(newDateCount);
		int res = sqlSession.update("updatePlanMst", mstDto);
		result = res > 0 ? "success": "failed";
		logger.info("modifyPlanMst result 1 : " + result);

		
		// 수정되기 전 dateCount		
		int origin = Integer.parseInt(originDateCount);
		// 수정되기 후 dateCount
		int newly = Integer.parseInt(newDateCount);

		List<PlanDto2> updatePlanDt = new ArrayList<PlanDto2>();
		
		// dateCount가 작아졌으면 (원래 일정 수 - 새로운 일정 수)만큼 끝에서부터 지우고 나머지 날짜를 바꿔줌
		if ( origin > newly ) {
			// 기존 일정에서 newly+1 일차 일정부터 삭제 ex> origin 5일 , newly 2일 = 3일(newly+1)차부터 끝(origin)까지 planDt삭제
			for (int i = (newly+1); i <= origin; i++) {
				PlanDto2 dtDto = new PlanDto2(mstDto.getPlanNum(), "day"+i, "-");
				
				int resDt = sqlSession.delete("deletePlanDt1", dtDto);
				result = resDt > 0 ? "success": "failed";
			}
			updatePlanDt = getUpdateDtos(mstDto.getPlanNum(), mstDto.getStartDate(), newly);
		}		
		
		// date count가 같으면 각 planDate의 날짜만 바꿔줌
		else if ( origin == newly ) {
			updatePlanDt = getUpdateDtos(mstDto.getPlanNum(), mstDto.getStartDate(), newly);				
		}
			
		// dateCount가 더 커졌으면 원래의 일정 수 만큼은 바꿔주고 나머지 일자는 빈 일정을 생성해서 insert
		else if ( origin < newly ) {
			// 새로 생성한 dateCount만큼 생성한 배열을 가져옴
			updatePlanDt = getUpdateDtos(mstDto.getPlanNum(), mstDto.getStartDate(), newly);
			
			//나머지 일자에 넣을 빈 일정을 담을 배열
			ArrayList<PlanDto2> nullPlanDt = new ArrayList<PlanDto2>();
			
			// 원래 일정을 초과하는 만큼만 nullPlanDt에 담고 전체 배열에서 삭제함
			for(int i = (newly-1); i >= origin; i-- ) {
				updatePlanDt.get(i).setPlanDtNum(0);
				updatePlanDt.get(i).setTheme("방문");
				updatePlanDt.get(i).setPlaceCount("0");
				
				nullPlanDt.add(updatePlanDt.get(i));
				updatePlanDt.remove(i);
			}
			
			int resDtSub = sqlSession.insert("insertNullDt", nullPlanDt);
			
			logger.info("modifyPlanMst result 3 nullDto insert : " + (resDtSub == 0 ? "success": "failed"));
		}
		
		int resDt = sqlSession.update("updatePlanDt1", updatePlanDt);
		result = resDt == 0 ? "success": "failed";
		
		logger.info("modifyPlanMst result 2 : " + result);
	
		return result;
	}
	
	// modal창에서 삭제한 내용 반영 /* 비효율적 = foreign key 연결하면 해결 [ON DELETE CASCADE ENABLE] */
	@Override
	@Transactional
	public String deletePlan(String planNum) {
		logger.info("deletePlan(" + planNum + ") in >>>");

		String result = null;
		
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
	
	
	// planDt insert
	@Override
	@Transactional
	public String insertPlanDtDo(HttpServletRequest request, Model model) {
		logger.info("insertPlanDtDo >>> ");
		
		String result = null;
		
		// request에서 넘어온 parameter를 planMstDto로 파싱하는 메서드 실행
		PlanDto mstDto = planMstDtoParser(request);
		
		int res1 = sqlSession.insert("insertMst", mstDto);
		result = res1 > 0 ? "success": "failed";
		logger.info("insertPlanDtDo res1(Mst) : " + result);
		
		// request에서 넘어온 parameter를 planDtDto로 파싱하는 메서드 실행
		ArrayList<PlanDto2> dtDtos = (ArrayList)planDtDtoParser(mstDto.getPlanNum(), request);
		
		// 배열로 다중행 insert 실행
		int res2 = sqlSession.insert("insertDt", dtDtos);
		result = res2 > 0 ? "success": "failed";
		logger.info("insertPlanDtDo res2(Dt) : " + result);

		return result;
	}
	
	
	// planDt modify(update, delete, insert 동시 발생) /* 비효율적 1. 수정 내용 없어도 update처리하는 것  2. list 객체로 insert로 개선 */ 
	@Override
	@Transactional
	public String detailModifyDo(HttpServletRequest request) {
		logger.info("detailModifyDo() in >>> ");
	
		String result = null;
		
		// deleteDtNum : 삭제된 일정이 하나라도 있을 때
		if(! request.getParameter("deleteDtNum").equals("") ) {
			logger.info("detailModifyDo deleteNum is exist");
			// parameter로 넘어온 deleteDtNum을 '/'로 구분하여 배열로 생성
			String[] deleteDtNum = request.getParameter("deleteDtNum").split("/");
		
			List<Integer> deleteDtList = new ArrayList<Integer>();
		
			for ( int i = 0; i < deleteDtNum.length; i++ ) {
				deleteDtList.add(Integer.parseInt(deleteDtNum[i]));
			}
			// myBatis 구문 실행
			int res = sqlSession.delete("deleteDt", deleteDtList);
			result = res == 1 ? "success": "failed";
		} else {
			logger.info("detailModifyDo deleteNum is null");
		}
		
		
		// 수정 페이지에서 넘어온 parameter parsing해서 Dto객체 list 생성
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		ArrayList<PlanDto2> dtos = (ArrayList)planDtDtoParser(planNum, request);

		// 처리할 query로 구분해서 담을 list 생성
		ArrayList<PlanDto2> insertDtos = new ArrayList<PlanDto2>();
		ArrayList<PlanDto2> updateDtos = new ArrayList<PlanDto2>();
		
		// Dto로 만들어서 하나씩 update
		for ( int i = 0 ; i < dtos.size(); i++ ) {
	
			// planDtNum == 0 : 새로 추가된 상세 일정으로 insert
			if ( dtos.get(i).getPlanDtNum() == 0 ) {
				insertDtos.add(dtos.get(i));
				
			// planDtNum != 0 : 기존에 있던 상세 일정으로 update
			} else {
				updateDtos.add(dtos.get(i));
			}
		};
		
		if ( insertDtos.isEmpty() == false ) {	
			int res1 = sqlSession.insert("insertDt", insertDtos);
			result = res1== 1 ? "success": "failed";
		}
		if (updateDtos.isEmpty() == false ) {
			int res2 = sqlSession.update("updatePlanDt2", updateDtos);
			result = res2 == 1 ? "success": "failed";
		}
		
		return result;
	}
	
	//PlanMstDto를 생성하는 메서드
	public PlanDto planMstDtoParser(HttpServletRequest request) {
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		String planName = request.getParameter("planName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateCount = request.getParameter("dateCount");
		String eventColor = request.getParameter("eventColor");
		
		PlanDto mstDto = new PlanDto(planNum, planName, startDate, endDate, dateCount, eventColor);
		
		return mstDto;
	}
	
	//PlanDtDto를 리스트로 생성하는 메서드
	public List<PlanDto2> planDtDtoParser(int planNum, HttpServletRequest request) {
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

		List<PlanDto2> planDtDtos = new ArrayList<PlanDto2>();
		
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
			
			planDtDtos.add(dtDto);
		};
		
		return planDtDtos;
	}
	
	// modal창에서 수정한 plan을 update할 객체 배열 생성 메서드
	public List<PlanDto2> getUpdateDtos(int planNum, String startDate, int dateCount) {
		List<PlanDto2> dtos = new ArrayList<PlanDto2>();
		
		// startDate Calendar 객체로 만들어서 작업
		int y = Integer.parseInt(startDate.substring(0, 4));
		int m = Integer.parseInt(startDate.substring(5, 7)) - 1;;
		int d = Integer.parseInt(startDate.substring(8));
		
		Date date = new Date((y-1900), m, d);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		for ( int i = 0; i < dateCount; i++ ) {
			if ( i == 0 ) {
				// Calendar 객체로 된 startDate부터 하루씩 늘려가면서 planDate 생성
				cal.add(Calendar.DATE, i);
			}
			// Calendar 객체로 된 startDate부터 하루씩 늘려가면서 planDate 생성
			cal.add(Calendar.DATE, 1);
			
			// planDate를 db 포맷에 맞게 변경
			String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			// planNum, planDay, planDate 값만 가진 PlanDto2를 만들어서 배열에 저장
			PlanDto2 dtDto = new PlanDto2(planNum, "day"+(i+1), r);
			
			dtos.add(dtDto);
		}
		
		return dtos;
		
	}
	

}
