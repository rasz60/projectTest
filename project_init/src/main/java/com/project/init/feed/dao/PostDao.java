package com.project.init.feed.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.feed.dto.CommentsDto;
import com.project.init.feed.dto.PostDto;
import com.project.init.feed.dto.PostLikeDto;

@Component
public class PostDao implements PostIDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public PostDto write(PostDto dto) {
		sqlSession.insert("write", dto);
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> list(String email) {
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("list",email);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> getlist(String postNo) {
		ArrayList<PostDto> dto = (ArrayList)sqlSession.selectList("getlist",postNo);
		
		return dto;
	}

	@Override
	public void deleteBoard(String postNo) {
		sqlSession.delete("deleteBoard", postNo);
		sqlSession.delete("deleteComments", postNo);
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> modifyList(String postNo) {
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("modifyList",postNo);
		
		return list;
	}

	@Override
	public void modifyExcute(PostDto dto) {
		sqlSession.update("modifyExcute", dto);
	}

	@Override
	public void addcomments(CommentsDto dto) {
		sqlSession.insert("addcomments", dto);
	}
	
	
	@Override
	public void addReplyComments(CommentsDto dto){
		
		sqlSession.update("beforeAddReply", dto);
		sqlSession.insert("addReplyComments", dto);
	}

	@Override
	public ArrayList<CommentsDto> getcomments(String postNo) {
		ArrayList<CommentsDto> dto =(ArrayList)sqlSession.selectList("getcomments",postNo);
		
		return dto;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> search(String keyword, String searchVal) {
		ArrayList<PostDto> dto = new ArrayList<PostDto>();
		
		if(searchVal.equals("writer")) {
			dto = (ArrayList)sqlSession.selectList("searchWriter", keyword);
			return dto;
		}else if(searchVal.equals("content")) {
			dto = (ArrayList)sqlSession.selectList("searchContent", keyword);
			return dto;
		}
		else if(searchVal.equals("location")) {
			dto = (ArrayList)sqlSession.selectList("searchLocation", keyword);
			return dto;
		}else {
			dto = (ArrayList)sqlSession.selectList("searchHashtag", keyword);			
			return dto;
		}
		
		
	}

	@Override
	public void deleteReplyComments(String commentNo) {
		sqlSession.update("deleteReplyComments", commentNo);
	}

	@Override
	public void addLike(PostLikeDto dto) {
		int tmp =sqlSession.selectOne("like", dto);
		if(tmp ==0) {
			sqlSession.insert("addLike", dto);			
		}else {
			sqlSession.delete("deleteLike", dto);
		}
	}

	@Override
	public void deleteLike(PostLikeDto dto) {
		
		sqlSession.delete("deleteLike", dto);
	}
}
