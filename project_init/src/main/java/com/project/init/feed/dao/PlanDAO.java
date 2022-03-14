package com.project.init.feed.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.feed.dto.PlanDto;

@Component
public class PlanDAO implements IDao {

	private final SqlSession sqlSession;
	
	@Autowired
	public PlanDAO (SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	@Override
	public void insertPlan(PlanDto dto) {
		sqlSession.insert("insertPlan", dto);
	}

	@Override
	public ArrayList<PlanDto> selectAllPlan() {
		ArrayList<PlanDto> dtos = (ArrayList)sqlSession.selectList("selectAllPlan");
		
		return dtos;
	}

}
