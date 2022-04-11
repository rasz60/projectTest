package com.project.init.feed.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.feed.dto.PlanDto2;
import com.project.init.feed.dto.UserDto;

@Component
public class AdminDao implements AdminIDao {
	
	private static final Logger logger = LoggerFactory.getLogger(PlanDAO.class);
	
	@Autowired
	private final SqlSession sqlSession;
	
	@Autowired
	public AdminDao (SqlSession sqlSession) {
		logger.info("DashBoardDao Const in >>>");
		this.sqlSession = sqlSession;
		
		logger.info("DashBoardDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}

	@Override
	public ArrayList<PlanDto2> selectDashBoardPlaces(Map<String, String> map) {
		ArrayList<PlanDto2> result = (ArrayList)sqlSession.selectList("selectDashBoardPlaces", map);
		
		return result;
	}

	@Override
	public ArrayList<UserDto> selectDashBoardUser(Map<String, String> map) {

		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUser", map);
		
		return result;
	}

}
