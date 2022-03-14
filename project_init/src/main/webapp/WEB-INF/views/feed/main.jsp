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
<script src="/js/feed/fc/main.js"></script>
<script src="/js/feed/fc/locales/ko.js"></script>
<script src="/js/feed/calendar.js"></script>
<link href="/css/feed/fc/main.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css" href="/css/header.css" />
<link rel="stylesheet" type="text/css" href="/css/feed/main_custom.css" />
<link rel="stylesheet" type="text/css" href="/css/footer.css" />
<title>Insert title here</title>

</head>

<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<section class="container mb-5">
	<div class="body-container">
		<div id="feed-header" class="d-flex justify-content-around">
			<div id="profile-left" class="p-3 mb-5 bg-body"></div>
			<div id="profile-img" class="p-3 mb-5 bg-body">
				<i class="profile-img fa-regular fa-circle-user"></i>
			</div>
			<div id="profile-right" class="p-3 mb-5 bg-body"></div>
		</div>
		
		<hr />
	
		<div class="d-flex justify-content-between mt-4">
			<!-- create plan form -->
			<div class="border rounded p-3">
				<form action="planner" id="frm" method="post">
					<div class="form-group">
						<label for="planName">일정 이름</label>
						<input type="text" name="planName" id="planName" class="form-control" size="20"/>
					</div>
					<div class="form-group">
						<label for="startDate">시작 일자</label>
						<input type="text" name="startDate" id="startDate" class="form-control bg-light" size="20" readonly="readonly"/>
					</div>
					<div class="form-group">
						<label for="endDate">종료 일자</label>
						<input type="text" name="endDate" id="endDate" class="form-control bg-light" size="20" readonly="readonly"/>
					</div>
		
					<div class="d-flex justify-content-end mp_btn">
						<input type="submit" class="btn btn-sm btn-primary mr-1" id="submit" value="Create"/>
						<input type="reset" class="btn btn-sm btn-danger px-2" value="Clear" />
					</div>
				</form>
			</div>
			<!-- fullcalendar div  -->
			<div id="calendar" class="container"></div>
		</div>
	</div>
	
	<!-- modal button -->
	<input type="hidden" id="modalBtn1" data-toggle="modal" data-target="#myModal" value="modal" />
	
	<!-- modal 창 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-xl d-block">
			<button type="button" id="modalCloseBtn" class="btn btn-xl btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
			<div class="modal-content">
				<div class="modal-header bg-light d-flex justify-content-start">
					<h4 id="plan-name" class="modal-title display-4"></h4>
				</div>
				
				<div class="modal-body bg-light d-flex justify-content-center">
					<div id="map-container" class="d-block">
						<div id="map" class="border rounded bg-warning mb-2 text-white text-weight-bold">
							KAKAO MAP
						</div>
						<div id="locations" class="border rounded bg-light overflow-auto">
							<ul id="locations-list" class="list-group">
								
								<c:forEach begin="0" end="5">								
									<li class="list-group-item row d-flex mx-0 px-1 text-center ">
										<div class="location-name border col-7 overflow-hidden ml-1 mr-1 px-1 pt-1">location name</div>
										<div class="location-likes col-2 mr-1 px-1 pt-1"><i class="fa-solid fa-heart text-danger"></i> 425</div>
										
										<button type="button" class="btn btn-default">										
											<i class="fa-solid fa-circle-plus text-success"></i>
										</button>
	
										<button type="button" class="btn btn-default">
											<i class="fa-solid fa-circle-minus text-danger"></i>
										</button>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>

					<div id="plan_container" class="ml-2 px-1 overflow-auto">
						<c:forEach begin="0" end="5">
							<div class="time_box border rounded d-flex justify-content-around pt-1 my-1">
								<div class="form-group">
									<label for="start">장소</label>
									<input type="text" class="form-control" name="location" readonly />
								</div>
								
								<div class="form-group">
									<label for="start">시작시간</label>
									<input type="time" class="form-control" name="start" step="1800"/>
								</div>
														
								<div class="form-group">
									<label for="end">종료시간</label>
									<input type="time" class="form-control" name="end" />
								</div>
								<div class="d-block">
									<button type="button" class="del-btn btn btn-default"><i class="fa-solid fa-rectangle-xmark text-danger"></i></button>
								</div>
							</div>
						</c:forEach>
					</div>
					
					
					
				</div>
				<div class="modal-footer bg-light mb-2"></div>
			</div>
		</div>
	</div>
</section>
<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>