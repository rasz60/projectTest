package com.project.init.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.dto.PlanDtDto;
import com.project.init.dto.UserDto;

@Component
public class AdminDao implements AdminIDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);
	
	@Autowired
	private final SqlSession sqlSession;
	
	@Autowired
	public AdminDao (SqlSession sqlSession) {
		logger.info("DashBoardDao Const in >>>");
		this.sqlSession = sqlSession;
		
		logger.info("DashBoardDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	// 장소 별 통계
	@Override
	public ArrayList<PlanDtDto> selectDashBoardPlaces(Map<String, String> map) {
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("selectDashBoardPlaces", map);
		
		return result;
	}
	
	// 년도, 월, 일 별 회원수 통계
	@Override
	public ArrayList<UserDto> selectDashBoardUser(Map<String, String> map) {

		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUser", map);
		
		return result;
	}
	
	// 회원 성별 통계
	@Override
	public ArrayList<UserDto> selectDashBoardUserGender() {
		
		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUserGender");
		
		return result;
	}
	
	// 회원 연령별 통계
	@Override
	public ArrayList<UserDto> selectDashBoardUserAge() {
		
		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUserAge");
		
		return result;
	}
}