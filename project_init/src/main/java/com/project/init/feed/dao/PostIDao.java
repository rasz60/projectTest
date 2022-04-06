package com.project.init.feed.dao;

import java.util.ArrayList;

import com.project.init.feed.dto.CommentsDto;
import com.project.init.feed.dto.PostDto;
import com.project.init.feed.dto.PostLikeDto;


public interface PostIDao {

	public PostDto write(PostDto dto);
	public ArrayList<PostDto> list(String email);
	public ArrayList<PostDto> getlist(String boardNum);
	public void deleteBoard(String boardNum);
	public void addLike(PostLikeDto dto);
	public void deleteLike(PostLikeDto dto);
	public ArrayList<PostDto> modifyList(String boardNum);
	public void modifyExcute(PostDto dto);
	public void addcomments(CommentsDto dto);
	public void addReplyComments(CommentsDto dto);
	public void deleteReplyComments(String commentNo);
	public ArrayList<CommentsDto> getcomments(String postNo);
	public ArrayList<PostDto> search(String keyword,String searchVal);
	
}
