package com.whereru.main.BoardDAO;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.whereru.main.BoardDTO.CommentsDTO;
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
	public ArrayList<MainDTO> getlist(String postNo) {
		ArrayList<MainDTO> dto = (ArrayList)sqlSession.selectList("getlist",postNo);
		
		return dto;
	}

	@Override
	public void deleteBoard(String postNo) {
		sqlSession.delete("deleteBoard", postNo);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<MainDTO> modifyList(String postNo) {
		ArrayList<MainDTO> list =  (ArrayList)sqlSession.selectList("modifyList",postNo);
		
		return list;
	}

	@Override
	public void modifyExcute(MainDTO dto) {
		sqlSession.update("modifyExcute", dto);
	}

	@Override
	public void addcomments(CommentsDTO dto) {
		sqlSession.insert("addcomments", dto);
	}

	@Override
	public ArrayList<CommentsDTO> getcomments(String postNo) {
		ArrayList<CommentsDTO> dto =(ArrayList)sqlSession.selectList("getcomments",postNo);
		
		return dto;
	}

	
}
