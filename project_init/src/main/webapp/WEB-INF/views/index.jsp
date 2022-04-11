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
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/includes/footer.css" />
<title>WAYG</title>
<script>
<c:if test='${not empty error}'>
	$('#loginError').css('visibility','visible');
	$('#loginModalBtn').trigger('click');
</c:if>

</script>
</head>

<body>
<%@ include file="includes/header.jsp" %>

<section class="container">
	<div class="main-body-top d-flex justify-content-between mb-3">
		<div id="map" class="map border rounded p-2"></div>
		<div class="right ml-2">
			<div class="user-info border rounded mb-2 p-1">
				<div class="user-info-top d-flex row mx-0">
					<s:authorize access="isAnonymous()">				
						<div class="profile-img col-3">
							<i class="user-info-icon fa-solid fa-masks-theater"></i>
						</div>
						
						<div class="d-block col-9">
							<div class="nickname">
								<p class="h5 font-italic ml-2">Anonymous</p>
							</div>
							<div class="row mx-0">
								<a href="#" class="text-secondary font-italic col-10 px-1">Find your info <i class="fa-regular fa-circle-question"></i></a>
								<button type="button" id="loginBtn" class="logx-btn btn btn-primary btn-sm col-2">
									<i class="fa-solid fa-lock"></i>
								</button>
							</div>
						</div>
						
					</s:authorize>
					
					<s:authorize access="isAuthenticated()">				
						<div class="profile-img">
							<i class="user-info-icon fa-regular fa-circle-user"></i>
						</div>
					
						<div class="nickname">
							<p class="h5 font-italic ml-2"></p>
						</div>
						
						<form method="post" action="logout">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<button type="submit" id="logoutBtn" class="logx-btn btn btn-danger btn-sm"><i class="fa-solid fa-lock-open"></i></button>
						</form>
					</s:authorize>
					
				</div>
			</div>
			<div class="map-filter border rounded p-2 bg-light">
			
				<h6 id="filter-title" class="display-4">&nbsp;WAYG Filter</h6>
				<hr />
			
				<form id="frm" name="frm" action="insertFilter" method="post">
					<div class="form-group">
						<label for="selbox" class="labels">&nbsp;Filter 1 <span class="required">*</span></label>
						<select id="selbox" class="main-filter custom-select my-1 mr-sm-2 labels" name="value3"> <!-- 메인 필터 select창 생성 -->
							<option>Select Filter</option>
							<option value="1">모두보기</option>
							<option value="category">장소</option>
							<option value="address">지역</option>
							<option value="transportation">이동수단</option>
							<option value="theme">테마</option>
						</select>
					</div>
					
					<div class="form-group">
						<label for="subSelBox" class="mt-2 labels">&nbsp;Filter 2 <span class="required">*</span></label>
						<select id="subSelBox" class="sub-filter custom-select my-1 mr-sm-2 labels" name="value4"> <!-- 서브 필터 select창 생성, option은 script에서 생성 -->
							<option>Select Detail Filter</option>
						</select>			 
					</div>	
							
					<div class="form-group">
						<!-- <label for="plandate">날짜</label> -->
						<label for="plandate" class="labels">&nbsp;Date</label>
						<input type="date" class="form-control labels" id="plandate" name="value2" value=""/> <!-- 날짜 선택 input 생성 -->
					</div>
					
					<div class="form-group d-flex justify-content-end row mx-0">
						<button type="submit" id="filterbtn" class="btn btn-success col-2 mr-2">
							<i class="fa-solid fa-magnifying-glass"></i>
						</button> <!-- 필터 제출 버튼 -->
						
						<button type="reset" id="filterResetbtn" class="btn btn-danger col-2" >
							<i class="fa-solid fa-delete-left"></i>
						</button> <!-- 필터 초기화 버튼 -->
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<div class="main-body-bottom mb-3">
		<div class="recommand recommand-1 mb-2">
			<div class="recommand-icon text-danger d-flex justify-content-between">
				<i class="btn1 fa-solid fa-location-arrow"></i>
				<a href="search" class="text-danger">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-between mt-2">
				<c:forEach begin="1" end="4" var="i">
					<div class="post mr-2">
						<div class="post-top border rounded"></div>
						<div class="post-bottom border"></div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<div class="recommand recommand-2 mb-2">
			<div class="recommand-icon text-primary d-flex justify-content-between">
				<i class="btn1 fa-solid fa-users"></i>
				<a href="search" class="text-primary">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-between mt-2">
				<c:forEach begin="1" end="4" var="i">
					<div class="post mr-2">
						<div class="post-top border rounded"></div>
						<div class="post-bottom border"></div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<div class="recommand recommand-3 mb-2">
			<div class="recommand-icon text-success d-flex justify-content-between">
				<i class="btn1 fa-solid fa-font-awesome"></i>
				<a href="search" class="text-success">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-between mt-2">			
				<c:forEach begin="1" end="4" var="i">
					<div class="post mr-2">
						<div class="post-top border rounded"></div>
						<div class="post-bottom border"></div>
					</div>
				</c:forEach>
			</div>
		</div>
		
		<div class="recommand recommand-4 mb-2">
			<div class="recommand-icon text-warning d-flex justify-content-between">
				<i class="btn1 fa-solid fa-trophy"></i>
				<a href="search" class="text-warning">
					<i class="btn2 fa-regular fa-circle-right"></i>
				</a>
			</div>
			
			<div class="posts d-flex justify-content-between mt-2">			
				<c:forEach begin="1" end="4" var="i">
					<div class="post mr-2">
						<div class="post-top border rounded"></div>
						<div class="post-bottom border"></div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>	
</section>

<%@ include file="includes/login_modal.jsp" %>
<%@ include file="includes/modalPost.jsp" %>
<%@ include file="includes/footer.jsp" %>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd&libraries=clusterer"></script>
<script src="js/index.js"></script>
</body>
</html>