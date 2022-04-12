package com.project.init.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.project.init.dto.CommentsDto;
import com.project.init.dto.PlanDtDto;
import com.project.init.dto.PostDto;
import com.project.init.dto.PostLikeDto;
import com.project.init.dto.PostViewDto;
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
	public void write(PostDto dto) {
		logger.info("write(" + dto.getEmail() + ") in >>>");
		
		sqlSession.insert("write", dto);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> list(String email) {
		logger.info("list(" + email + ") in >>>");
				
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("list",email);
		
		logger.info("PostDao list result : list.isEmpty() ? " + list.isEmpty());
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ArrayList<PostDto> mylist(String email, Model model) {
		logger.info("mylist(" + email + ") in >>>");
		
		ArrayList<PostDto> list =  (ArrayList)sqlSession.selectList("mylist", email);

		logger.info("PostDao mylist result : list.isEmpty() ? " + list.isEmpty());
		return list;
	}
	
	@Override
	public String addView(PostViewDto dto) {
		logger.info("addView(" + dto.getEmail() + ") in >>>");
		
		String result = null;
		
		int tmp = sqlSession.selectOne("view", dto);	
		
		if(tmp == 0) {
			sqlSession.insert("addView", dto);
			
			logger.info("addView() result 1 : addview");
		
		}else {			
			logger.info("addView() result 1 : not-work");
		}
		
		return "success";
	}
	
	@Override
	public ArrayList<CommentsDto> getcomments(String postNo) {
		logger.info("getcomments(" + postNo + ") in >>>");
		
		ArrayList<CommentsDto> dto =(ArrayList)sqlSession.selectList("getcomments",postNo);

		return dto;
	}
	
	@Override
	public void addReplyComments(CommentsDto dto){
		logger.info("addReplyComments(" + dto.getEmail() + ") in >>>");
		
		int res1 = sqlSession.update("beforeAddReply", dto);
		
		logger.info("addReplyComments(" + dto.getEmail() + ") result 1 : " + (res1 > 0 ? "update-success": "update-failed"));
		
		int res2 = sqlSession.insert("addReplyComments", dto);
		
		logger.info("addReplyComments(" + dto.getEmail() + ") result 1 : " + (res2 > 0 ? "insert-success": "insert-failed"));
	}
	
	@Override
	public void deleteReplyComments(String commentNo) {
		logger.info("deleteReplyComments(" + commentNo + ") in >>>");
		
		int res = sqlSession.update("deleteReplyComments", commentNo);
		
		logger.info("deleteReplyComments(" + commentNo + ") result : " + (res > 0 ? "update-success": "update-failed"));
	}

	@Override
	public void addcomments(CommentsDto dto) {
		logger.info("addcomments(" + dto.getEmail() + ") in >>>");
		
		int res = sqlSession.insert("addcomments", dto);
		
		logger.info("addcomments(" + dto.getEmail() + ") result : " + (res > 0 ? "update-success": "update-failed"));
	}
	
	@Override
	public String addLike(PostLikeDto dto) {
		logger.info("addLike(" + dto.getPostNo() + ") in >>>");
		
		int tmp =sqlSession.selectOne("like", dto);
		String result ="";
		
		if(tmp ==0) {
			sqlSession.insert("addLike", dto);	
			result = "add";
			
		}else {
			sqlSession.delete("deleteLike", dto);
			result = "delete";
		}
		
		logger.info("addLike(" + dto.getPostNo() + ") result : " + result);
		
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PostDto getlist(PostDto tmp) {
		logger.info("getlist(" + tmp.getPostNo() + ") in >>>");
		
		PostDto dto = sqlSession.selectOne("getlist",tmp);
		
		if (dto.getPlan() != null ) {
			String[] arr = dto.getPlan().split("/");
			ArrayList<PlanDtDto> dto2s = new ArrayList<PlanDtDto>();
				
			for ( int j = 0; j < arr.length; j++ ) {
				PlanDtDto dto2 = new PlanDtDto();
				dto2.setPlanDtNum(Integer.parseInt(arr[j]));
				dto2s.add(dto2);
			}
			ArrayList<PlanDtDto> location = (ArrayList)sqlSession.selectList("locationList", dto2s);
				
			dto.setLocation(location);
			
			logger.info("getlist(" + tmp.getPostNo() + ") sub-result : dto.getLocation().size() ? " + dto.getLocation().size());
		}
		
		logger.info("getlist(" + tmp.getPostNo() + ") result : dto.getViews() ? " + dto.getViews());
		
		return dto;
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
	public void deletePost(String postNo) {
		sqlSession.delete("deletePost", postNo);
		sqlSession.delete("deleteComments", postNo);
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
	
	
	
	/* unset
	



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

*/

}
