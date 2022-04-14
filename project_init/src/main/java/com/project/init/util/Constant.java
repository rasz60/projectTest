package com.project.init.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.init.dao.BoardDao;
import com.project.init.dao.ChatDao;
import com.project.init.dao.PlanDao;
import com.project.init.dao.PostDao;
import com.project.init.dao.SearchDao;
import com.project.init.dao.UserDao;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PlanMstDto;
import com.project.init.dto.UserDto;

public class Constant {
	
	public static PlanDao pdao;
	public static PostDao postDao;
	public static UserDao udao;
	public static SearchDao searchDao;
	public static BCryptPasswordEncoder passwordEncoder;
	public static String username;
	public static BoardDao bdao;
	public static ChatDao cdao;
	
	public static UserDto getUserInfo(Authentication authentication) {
		
		String uId = authentication.getPrincipal().toString();
		UserDto dto = null;
		if ( uId == "" || uId == null || uId != "anonymousUser" ) {
			User user = (User)authentication.getPrincipal();
			uId = user.getUsername();
			dto = udao.login(uId);
		}
		
		return dto;
	}
	
	
	
	//PlanMstDto占쏙옙 占쏙옙占쏙옙占싹댐옙 占쌨쇽옙占쏙옙
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
	
	//PlanDtDto占쏙옙 占쏙옙占쏙옙트占쏙옙 占쏙옙占쏙옙占싹댐옙 占쌨쇽옙占쏙옙
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
	
	// modal창占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 plan占쏙옙 update占쏙옙 占쏙옙체 占썼열 占쏙옙占쏙옙 占쌨쇽옙占쏙옙
	public static List<PlanDtDto> getUpdateDtos(int planNum, String userId, String startDate, int dateCount) {
		List<PlanDtDto> dtos = new ArrayList<PlanDtDto>();
		
		// startDate Calendar 占쏙옙체占쏙옙 占쏙옙占쏙옙底� 占쌜억옙
		int y = Integer.parseInt(startDate.substring(0, 4));
		int m = Integer.parseInt(startDate.substring(5, 7)) - 1;;
		int d = Integer.parseInt(startDate.substring(8));
		
		Date date = new Date((y-1900), m, d);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		for ( int i = 0; i < dateCount; i++ ) {
			if ( i == 0 ) {
				// Calendar 占쏙옙체占쏙옙 占쏙옙 startDate占쏙옙占쏙옙 占싹루씩 占시뤄옙占쏙옙占썽서 planDate 占쏙옙占쏙옙
				cal.add(Calendar.DATE, i);
			}
			// Calendar 占쏙옙체占쏙옙 占쏙옙 startDate占쏙옙占쏙옙 占싹루씩 占시뤄옙占쏙옙占썽서 planDate 占쏙옙占쏙옙
			cal.add(Calendar.DATE, 1);
			
			// planDate占쏙옙 db 占쏙옙占싯울옙 占승곤옙 占쏙옙占쏙옙
			String r = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			
			// planNum, planDay, planDate 占쏙옙占쏙옙 占쏙옙占쏙옙 PlanDtDto占쏙옙 占쏙옙占쏙옙底� 占썼열占쏙옙 占쏙옙占쏙옙
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