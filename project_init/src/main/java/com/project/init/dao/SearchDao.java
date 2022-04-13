package com.project.init.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.dto.PostDto;
import com.project.init.dto.SearchDto;
import com.project.init.util.Constant;

@Component
public class SearchDao implements SearchIDao {

	private static final Logger logger = LoggerFactory.getLogger(SearchDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	public SearchDao (SqlSession sqlSession) {
		logger.info("PostDao Const in >>>");
		this.sqlSession = sqlSession;
		Constant.searchDao = this;
		
		logger.info("PostDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> search(SearchDto dto) {
		
		
		String keyword =dto.getKeyword();
		
		if(keyword.equals("Hashtag")) {
			ArrayList<PostDto> dtos =(ArrayList)sqlSession.selectList("searchHashtag", dto);			
			return dtos;
		}else if(keyword.equals("NickName")) {			
			ArrayList<String> arr =(ArrayList) sqlSession.selectList("checkNickName", dto);
			ArrayList<PostDto> dtos =(ArrayList)sqlSession.selectList("searchNickName", arr);			
			return dtos;	
			
		}else{
			ArrayList<String> arr =(ArrayList) sqlSession.selectList("checkLocation", dto);
			ArrayList<PostDto> dtos =(ArrayList)sqlSession.selectList("searchLocation", arr);			
			return dtos;	
			
		}
	}
}
