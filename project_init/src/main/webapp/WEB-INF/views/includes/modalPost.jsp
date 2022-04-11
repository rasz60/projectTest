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
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/includes/modalPost.css" />
<style>
ul {
	list-style: none;	
}

.profile-img-s {
	height: 100%;
}

.img-s {
	width: 50px;
	height: 50px;
	border-radius: 50%;
}

.list-group-item:nth-child(3) {
	align-items: center;
}

input.comment,
input.recomment {
	width: 85%;
	border: none;
	border-bottom: 1px solid #dee2e6;
}

profile-img-xxs {
	height: 100%;
}

.img-xxs {
	width: 30px;
	height: 30px;
	border-radius: 50%;
}

.comments {
	height: 90%;
	overflow : auto;
}

.comments .comment-block {
	align-items: center;
}

span.comment-text {
	overflow-wrap: break-word;
}

.comment-block .form-group {
	display: none;
}

.location {
	width: auto;
	height: 30px;
	overflow-x: auto;
	display: none;
}
.comments::-webkit-scrollbar,
.location::-webkit-scrollbar{
    width: 2px;
    height: 4px;
}
.comments::-webkit-scrollbar-thumb,
.location::-webkit-scrollbar-thumb {
    background: #A0A0A0 ;
    border-radius: 5px;
}
.comments::-webkit-scrollbar-track,
.location::-webkit-scrollbar-track {
    background: #CCCCCC;
}


.location-item {
	height: 90%;
	font-size: 13px;
	font-style: italic;
	white-space: pre;
}

</style>
</head>
<body>
<!-- modal button -->
<input type="hidden" id="modalBtn" data-toggle="modal" data-target="#modal-reg" value="modal" />

<!-- modal 창 -->
<div class="modal fade" id="modal-reg" role="dialog">
	<div class="modal-dialog modal-dialog-centered modal-xl d-block">
		<button type="button" id="modalCloseBtn" class="btn btn-lg btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
		<div class="modal-content">
			<div class="modal-body bg-light d-flex justify-content-between">
				<div class="post-img border rounded mr-2">
					<div id="demo" class="carousel slide" data-ride="carousel">
                    	<!-- The slideshow -->
                        <div class="carousel-inner Citem">
                        	<!-- 이미지 등록 -->
                        </div>
                        <!-- Left and right controls -->
                        <a class="carousel-control-prev" href="#demo" data-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </a>
                        <a class="carousel-control-next" href="#demo" data-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </a>
                    </div>
				</div>
				<ul class="list-group d-block">
					<li class="list-group-item d-flex row mx-0 mb-1">
						<div class="profile-img-s col-2 px-0">
							<div class="img-s border"></div>
						</div>
						
						<div class="col-10">
							<div class="nickname">
								<b>nickname</b>
							</div>
							
							<div class="location"></div>
						</div>
					</li>
					
					
					<li class="list-group-item mb-1">
						<pre class="content"></pre>
					</li>
					
					<li class="list-group-item mb-1 d-flex row mx-0">
						<div class="col-4 likes">
							<c:choose>
								<c:when test ="${empty user}">
									<i class="modal-icon fa-solid fa-heart" data-num=""></i>
								</c:when>

								<c:otherwise>
									<i class="modal-icon fa-solid fa-heart modal-like" data-num=""></i>
								</c:otherwise>
							</c:choose>
							<span id="likeCount" class="ml-2"></span>
						</div>
						<div class="col-4 views">
							<i class="modal-icon fa-regular fa-circle-check"></i>
							<span class="ml-2"></span>	
						</div>
						<div class="col-4 comment_total">
							<i class="modal-icon fa-regular fa-comment-dots"></i>
							<span class="ml-2"></span>
						</div>
					</li>
					
					
					<li class="list-group-item">
                       	<input type="text" class="comment grpl" placeholder="comment" data-value="0">
                       	<button type="button" class="btn btn-sm btn-outline-success addcomment ml-1" data-num="" role="button">전송</button>
					
						<div class="comments">
						</div>
					</li>

					<li>

                    </li>
				</ul>
			</div>
		</div>
	</div>
</div>

</body>
</html>