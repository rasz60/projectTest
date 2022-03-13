package com.whereru.main.BoardDAO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class MainDAO implements InterfaceBoardDAO{
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void test() {
		sqlSession.insert("test","test222");
	}
	
}
