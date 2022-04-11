package com.project.init.dao;

import java.util.ArrayList;

import org.springframework.ui.Model;

import com.project.init.dto.CommentsDto;
import com.project.init.dto.PostDto;
import com.project.init.dto.PostLikeDto;
import com.project.init.dto.PostViewDto;

public interface PostIDao {

	public PostDto write(PostDto dto);
	
	public ArrayList<PostDto> mylist(String email, Model model);
	
	public ArrayList<PostDto> list(String email);

	public String addView(PostViewDto dto);
	
	public ArrayList<CommentsDto> getcomments(String postNo);
	
	public void addReplyComments(CommentsDto dto);
	
	public void deleteReplyComments(String commentNo);
	
	public void addcomments(CommentsDto dto);
	
	public String addLike(PostLikeDto dto);
	
	public PostDto getlist(PostDto tmp);
	
	/* unset
	public ArrayList<PostDto> likeList(String email);
	public ArrayList<PostDto> viewList(String email);
	
	public void deleteBoard(String boardNum);
	
	public ArrayList<PostDto> modifyList(String boardNum);
	public void modifyExcute(PostDto dto);
	public ArrayList<PostDto> search(String keyword,String searchVal);
	 */
}
