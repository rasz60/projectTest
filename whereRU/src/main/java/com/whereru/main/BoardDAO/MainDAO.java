package com.whereru.main.BoardDAO;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.whereru.main.BoardDTO.MainDTO;

public class MainDAO implements InterfaceBoardDAO{
	
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public MainDTO write(MainDTO dto) {
		sqlSession.insert("write", dto);
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<MainDTO> list() {
		ArrayList<MainDTO> list =  (ArrayList)sqlSession.selectList("list");
		
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<MainDTO> getlist(String boardNum) {
		ArrayList<MainDTO> dto = (ArrayList)sqlSession.selectList("getlist",boardNum);
		
		return dto;
	}

	@Override
	public void deleteBoard(String boardNum) {
		sqlSession.delete("deleteBoard", boardNum);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<MainDTO> modifyList(String boardNum) {
		ArrayList<MainDTO> list =  (ArrayList)sqlSession.selectList("modifyList",boardNum);
		
		return list;
	}

	@Override
	public void modifyExcute(MainDTO dto) {
		sqlSession.update("modifyExcute", dto);
	}

	
}
