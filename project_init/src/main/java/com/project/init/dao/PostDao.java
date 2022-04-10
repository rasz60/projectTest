package com.project.init.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.init.dto.PostDto;
import com.project.init.util.Constant;

@Component
public class PostDao implements PostIDao {

	private static final Logger logger = LoggerFactory.getLogger(PostDao.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	public PostDao (SqlSession sqlSession) {
		logger.info("PostDao Const in >>>");
		this.sqlSession = sqlSession;
		Constant.postDao = this;
		
		logger.info("PostDao Const result : sqlSession getConn success ? " + sqlSession.toString());
	}
	
	@Override
	public PostDto write(PostDto dto) {
		sqlSession.insert("write", dto);
		return null;
	}

	/* unset

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> list(String email) {
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("list",email);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> likeList(String email) {
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("likeList",email);
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> viewList(String email) {
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("viewList",email);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> getlist(PostDto tmp) {
		ArrayList<PostDto> dto = (ArrayList)sqlSession.selectList("getlist",tmp);
		
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
	public String addLike(PostLikeDto dto) {
		int tmp =sqlSession.selectOne("like", dto);
		String result ="";
		
		if(tmp ==0) {
			sqlSession.insert("addLike", dto);	
			result = "add";
			
		}else {
			sqlSession.delete("deleteLike", dto);
			result = "delete";
		}
		return result;
	}

	@Override
	public String addView(PostViewDto dto) {
		int tmp =sqlSession.selectOne("view", dto);	
		
		if(tmp ==0) {
			sqlSession.insert("addView", dto);	
			return "success";
		}else {
			return "success";
		}
	}

	*/
}
