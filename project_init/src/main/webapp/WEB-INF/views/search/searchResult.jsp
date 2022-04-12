<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="css/search/searchResult.css" />
<link rel="stylesheet" type="text/css" href="css/includes/footer.css" />
<title>Search Result</title>
</head>

<body>
<%@ include file="../includes/header.jsp" %>
<input type="hidden" value = "${user}" id="user">
<section class="container mb-4">
	<input type="hidden" id="modalBtn" data-toggle="modal" data-target="#myModal" value="modal" />

	<div class="result_posts">
		<div class="posts d-flex flex-wrap justify-content-start mt-2">
			<c:forEach items="${list}" var="post" >
				<div class="post mr-2">
					<div class="post-top border rounded">
						<img class="titleimg" style="cursor : pointer;" width="280px" src="../images/${post.titleImage}" data-value="${post.postNo}" data-toggle="modal" data-target="#modal-reg">
					</div>
					<div class="post-bottom border row mx-0">
						<div class="profile-img-xs col-3 px-0">
							<div class="img-xs border">
								<!-- <img src="../images/${post.userProfileImg}"> -->
							</div>
						</div>
						
						<div class="info col-9 row mx-0 flex-wrap">
							<div class="post-nickname col-12 px-0 pt-2">
							${post.userNick}
							</div>
						
							<div class="bottom-likes col-4 px-0">
								<c:choose>
									<c:when test ="${empty user}">
										<i class="fa-solid fa-heart" data-postno="${post.postNo}"></i>
									</c:when>
		
									<c:when test="${post.heart == 0}">
										<i class="fa-solid fa-heart like" data-postno="${post.postNo}"></i>						
									</c:when>
									
									<c:otherwise>
										<i class="fa-solid fa-heart like active" data-postno="${post.postNo}"></i>
									</c:otherwise>
								</c:choose>
									<span id="likeCount">${post.likes}</span>
							</div>
								
							<div class="bottom-comments col-4 px-0">		  
								<i class="fa-solid fa-comment-dots"></i>
								<span id="commentCount">${post.comments}</span>
							</div>
							<div class="bottom-views col-4 px-0">
								<i class="fa-solid fa-eye"></i>
								<span id="viewCount">${post.views}</span>
							</div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>





<section class="container mb-4">
	<input type="hidden" id="modalBtn" data-toggle="modal" data-target="#myModal" value="modal" />

	<div class="result_posts">
		<div class="posts d-flex flex-wrap justify-content-start mt-2">
			<c:forEach begin="1" end="20" var="i">
				<div class="post mr-2">
					<div class="post-top border rounded">
						<img src="images/5.jpg" alt="test"/>
					</div>
					<div class="post-bottom border">${ i}</div>
				</div>
			</c:forEach>
		</div>
	</div>

</section>

<%@ include file="../includes/modalPost.jsp" %>
<%@ include file="../includes/footer.jsp" %>

<script>

$(document).ready(function() {
	// 포스트 이미지 클릭시 모달창 생성
	$('.post').click(function() {
		console.log($(this).text());
		$('#modalBtn').trigger('click');
	})
});

</script>
</body>
</html>