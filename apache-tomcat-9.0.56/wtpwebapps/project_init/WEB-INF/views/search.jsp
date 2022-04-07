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
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="../js/feed/postMain.js"></script>
<link rel="stylesheet" type="text/css" href="../css/header.css" />
<link rel="stylesheet" type="text/css" href="../css/search/main.css" />
<link rel="stylesheet" type="text/css" href="../css/footer.css" />
<title>Insert title here</title>
<style>
.like.active {
	color: red;
}

.profile-img-xs {
	display: flex;
	align-items: center;
	padding-top: 0.5px;
}

.post-nickname, bottom-likes, bottom-comments, bottom-views {
	height: 50%;
}

.img-xs {
	width: 100%;
	height: 90%;
	border-radius: 50%;
}

.userinfo, .status {
	height: 50%;
}

</style>
</head>

<body>
<%@ include file="header.jsp" %>
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
							<div class="img-xs border"></div>
						</div>
						
						<div class="info col-9 row mx-0 flex-wrap">
							<div class="post-nickname col-12 px-0 pt-2">${post.email}</div>
						
							<div class="bottom-likes col-4 px-0">
								<c:choose>
									<c:when test ="${empty user}">
										<i class="fa-solid fa-heart" data-postno="${post.postNo}"></i>
									</c:when>
		
									<c:when test="${post.heart == 1}">
										<i class="fa-solid fa-heart like active" data-postno="${post.postNo}"></i>
									</c:when>
									
									<c:otherwise>
										<i class="fa-solid fa-heart like" data-postno="${post.postNo}"></i>						
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

<nav class="container my-5" aria-label="Page navigation example">
  <ul class="pagination justify-content-center">
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
        <span class="sr-only">Previous</span>
      </a>
    </li>
    
    <li class="page-item active">
    	<a class="page-link" id="1" href="#">1</a>
    </li>
    
    <li class="page-item" id="2page">
    	<a class="page-link" id="2" href="#">2</a>
    </li>
    
    <li class="page-item">
    	<a class="page-link" id="3" href="#">3</a>
    </li>
    
    <li class="page-item">
      <a class="page-link" href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
        <span class="sr-only">Next</span>
      </a>
    </li>
  </ul>
</nav>

<%@ include file="modalPost.jsp" %>

<%@ include file="footer.jsp" %>


<script>
/*
$(document).ready(function() {
	$('.post').click(function() {
		console.log($(this).text());
		$('#modalBtn').trigger('click');
	})

	
	$('.page-link').click(function(e) {
		e.preventDefault();
		
		let currPage = Number($('.active').children('.page-link').text());
		
		if ( $(this).attr('aria-label') == "Previous" ) {
			movePage = currPage - 1;

			if ( movePage < 1 ) {
				movePage = 1;
			}
			
		} else if ( $(this).attr('aria-label') == "Next" ) {
			movePage = currPage + 1;
						
			if ( movePage > 20 ) {
				movePage = currPage;
			} 
		} else {
			movePage = $(this).text();
			
			if ( movePage < 1 ||  movePage > 20 ) {
				movePage = currPage;
			}
		}

		console.log("movepage = " + movePage);
		
		let currActive = $('.active');
		
		nPagePosts(currActive, movePage);
	});
	
	
	function nPagePosts(currActive, movePage) {	
		
		$.ajax({
			url : 'getSearchResult.do?page='+movePage,
			type: 'get',
			data: movePage,
			contentType: 'application/text; charset=UTF-8',
			beforeSend: function(xhr) {
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				currActive.removeClass('active');
							
				console.log(data);
			},
			error: function(data) {
				console.log(data);
			}
		})	
	};
	
	
});
*/
</script>
</body>
</html>