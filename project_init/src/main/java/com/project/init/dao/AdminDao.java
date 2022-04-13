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
	
	// ��� �� ���
	@Override
	public ArrayList<PlanDtDto> selectDashBoardPlaces(Map<String, String> map) {
		
		ArrayList<PlanDtDto> result = (ArrayList)sqlSession.selectList("selectDashBoardPlaces", map);
		
		return result;
	}
	
	// �⵵, ��, �� �� ȸ���� ���
	@Override
	public ArrayList<UserDto> selectDashBoardUser(Map<String, String> map) {

		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUser", map);
		
		return result;
	}
	
	// ȸ�� ���� ���
	@Override
	public ArrayList<UserDto> selectDashBoardUserGender() {
		
		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUserGender");
		
		return result;
	}
	
	// ȸ�� ���ɺ� ���
	@Override
	public ArrayList<UserDto> selectDashBoardUserAge() {
		
		ArrayList<UserDto> result = (ArrayList)sqlSession.selectList("selectDashBoardUserAge");
		
		return result;
	}
	
	// ȸ�� �˻�
	@Override
	public UserDto searchNick(String nick) {
		UserDto udto = sqlSession.selectOne("searchNick",nick);
		return udto;
	}
	
	// ȸ�� Ż��
	@Override
	public UserDto deleteUser(String nick) {
		UserDto udto = sqlSession.selectOne("deleteUser",nick);
		return udto;
	}
	
	// ȸ�� ���� Ȱ��ȭ
	@Override
	public UserDto banUser(String nick) {
		
		UserDto udto = sqlSession.selectOne("disabledUser",nick);
		
		return udto;
	}

	// ȸ�� ���� ��Ȱ��ȭ
	@Override
	public UserDto activateUser(String nick) {
		
		UserDto udto = sqlSession.selectOne("activateUser",nick);
		
		return udto;
	}





}