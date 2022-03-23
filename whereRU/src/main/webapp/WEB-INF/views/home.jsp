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
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />
<title>Insert title here</title>

</head>

<body>

<%@ include file="header.jsp" %>

<section class="container">
	<h3><a href="form">폼</a></h3>
	<h3><a href="list">릿</a></h3>
	<h3><a href="join">죤</a></h3>
	
	<div class="main-body-top d-flex justify-content-between mb-3">
		<div class="map border rounded p-2">
			<i class="kakaomap fa-solid fa-location-dot"></i>
		</div>
		<div class="right ml-2">
			<div class="user-info border rounded mb-2 p-1">
				<div class="user-info-top d-flex">
					<s:authorize access="isAnonymous()">				
						<div class="profile-img">
							<i class="user-info-icon fa-regular fa-circle-user"></i>
						</div>
					
						<div class="nickname">
							<p class="h5">Nickname</p>
						</div>
						
						<button type="button" class="logx-btn btn btn-primary btn-sm">login</button>
					</s:authorize>
					
					<s:authorize access="isAuthenticated()">				
						<div class="profile-img">
							<i class="user-info-icon fa-regular fa-circle-user"></i>
						</div>
					
						<div class="nickname">
							<p class="h5">Nickname</p>
						</div>
						
						<button type="button" class="logx-btn btn btn-danger btn-sm">logout</button>
					</s:authorize>
					
				</div>
			</div>
			<div class="map-filter border rounded p-1">
				<i class="map-filter-icon fa-solid fa-list-check"></i>
			</div>
		</div>
	</div>
	
	<div class="main-body-bottom mb-3">
		<div class="recommand recommand-1 mb-2">
			<div class="recommand-icon text-danger d-flex justify-content-between">
				<i class="btn1 fa-solid fa-location-arrow"></i>
				<a href="#" class="text-danger">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-around mt-2">
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
			</div>
		</div>
		
		<div class="recommand recommand-2 mb-2">
			<div class="recommand-icon text-primary d-flex justify-content-between">
				<i class="btn1 fa-solid fa-users"></i>
				<a href="#" class="text-primary">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-around mt-2">
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
			</div>
		</div>
		
		<div class="recommand recommand-3 mb-2">
			<div class="recommand-icon text-success d-flex justify-content-between">
				<i class="btn1 fa-solid fa-font-awesome"></i>
				<a href="#" class="text-success">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-around mt-2">			
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
			</div>
		</div>
		
		<div class="recommand recommand-4 mb-2">
			<div class="recommand-icon text-warning d-flex justify-content-between">
				<i class="btn1 fa-solid fa-trophy"></i>
				<a href="#" class="text-warning">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-around mt-2">			
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post mr-2">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
				
				<div class="post">
					<div class="post-top border rounded"></div>
					<div class="post-bottom border"></div>
				</div>
			</div>
		</div>
	</div>	
</section>

<%@ include file="footer.jsp" %>
</body>
</html>