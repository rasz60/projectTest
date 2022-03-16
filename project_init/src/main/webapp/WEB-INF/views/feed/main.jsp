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
<script src="js/feed/fc/main.js"></script>
<script src="js/feed/fc/locales/ko.js"></script>
<script src="js/feed/feed_calendar.js"></script>
<link href="css/feed/fc/main.css" rel="stylesheet"></link>
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/feed/main_custom.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />
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
						<input type="reset" class="btn btn-sm btn-danger px-2" id="reset" value="Clear" />
					</div>
				</form>
			</div>
			<!-- fullcalendar div  -->
			<div id="calendar" class="container"></div>
		</div>
	</div>
	<!-- modal button -->
	<input type="hidden" id="modalBtn2" data-toggle="modal" data-target="#myModal2" value="modal" />
	
	<!-- modal 창 -->
	<div class="modal fade" id="myModal2" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm d-block">
			<button type="button" id="modalCloseBtn" class="btn btn-xl btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
			<div class="modal-content">
				<div class="modal-header bg-light d-flex justify-content-start">
					<h4 id="plan-name" class="modal-title display-4 font-italic">update</h4>
				</div>
				
				<div class="modal-body bg-light d-flex justify-content-center">
					<form action="feed/modify_plan.do" method="post" id="modify_form">
						<input type="hidden" name="planNum" id="planNum" />
						<div class="form-group">
							<label for="planName">일정 이름</label>
							<input type="text" id="planName" name="planName" class="form-control"/>
						</div>
						
						<div class="form-group">
							<label for="startDate">시작 일자</label>
							<input type="date" id="startDate" name="startDate" class="form-control"/>
						</div>
						
						<div class="form-group">
							<label for="endDate">종료 일자</label>
							<input type="date" id="endDate" name="endDate"" class="form-control"/>
						</div>
						<div class="button-group text-right">
							<button type="button" id="btn-modify" class="btn btn-sm btn-success">수정</button>
							<button type="button" id="btn-delete" class="btn btn-sm btn-danger">삭제</button>
						</div>
					</form>
				</div>
				<div class="modal-footer bg-light mb-2"></div>
			</div>
		</div>
	</div>

</section>
<%@ include file="/WEB-INF/views/footer.jsp" %>
</body>
</html>