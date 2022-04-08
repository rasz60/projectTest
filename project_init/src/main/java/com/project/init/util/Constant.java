package com.project.init.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dao.BoardDao;
import com.project.init.dao.PlanDao;

public class Constant {
	
	public static PlanDao pdao;
	public static BCryptPasswordEncoder passwordEncoder;
	public static UserDao udao;
	public static String username;
	public static BoardDao bdao;
	
	
	
	
	//PlanMstDto를 생성하는 메서드
	public static PlanMstDto planMstDtoParser(HttpServletRequest request, String userId) {
		int planNum = Integer.parseInt(request.getParameter("planNum"));
		String planName = request.getParameter("planName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String dateCount = request.getParameter("dateCount");
		String eventColor = request.getParameter("eventColor");
		
		PlanMstDto mstDto = new PlanMstDto(planNum, userId, planName, startDate, endDate, dateCount, eventColor);
		
		return mstDto;
	}
	
	//PlanDtDto를 리스트로 생성하는 메서드
	public static List<PlanDtDto> planDtDtoParser(int planNum, String userId, HttpServletRequest request) {
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

		List<PlanDtDto> planDtDtos = new ArrayList<PlanDtDto>();
		
		for ( int i = 0 ; i < planDtNum.length; i++ ) {
			PlanDtDto dtDto = new PlanDtDto(Integer.parseInt(planDtNum[i]),
										  planNum,
										  userId,
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
	public static List<PlanDtDto> getUpdateDtos(int planNum, String userId, String startDate, int dateCount) {
		List<PlanDtDto> dtos = new ArrayList<PlanDtDto>();
		
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
			
			// planNum, planDay, planDate 값만 가진 PlanDtDto를 만들어서 배열에 저장
			PlanDtDto dtDto = new PlanDtDto();
			dtDto.setUserId(userId);
			dtDto.setPlanNum(planNum);
			dtDto.setPlanDay("day"+(i+1));
			dtDto.setPlanDate(r);
			
			dtos.add(dtDto);
		}
		
		return dtos;
		
	}
	
}