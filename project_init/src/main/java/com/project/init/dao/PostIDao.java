package com.project.init.dao;

import com.project.init.dto.PostDto;

public interface PostIDao {
	
	public PostDto write(PostDto dto);
	
	/* unset

	public ArrayList<PostDto> list(String email);
	public ArrayList<PostDto> likeList(String email);
	public ArrayList<PostDto> viewList(String email);
	public ArrayList<PostDto> getlist(PostDto tmp);
	public void deleteBoard(String boardNum);
	public String addLike(PostLikeDto dto);
	public String addView(PostViewDto dto);
	public ArrayList<PostDto> modifyList(String boardNum);
	public void modifyExcute(PostDto dto);
	public void addcomments(CommentsDto dto);
	public void addReplyComments(CommentsDto dto);
	public void deleteReplyComments(String commentNo);
	public ArrayList<CommentsDto> getcomments(String postNo);
	public ArrayList<PostDto> search(String keyword,String searchVal);

	 */
}
