<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
</head>
<body>

	<!-- modal 창 -->
	<div class="modal fade" id="myModal2" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-xl d-block">
			<button type="button" id="modalCloseBtn" class="btn btn-sm btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
			<div class="modal-content">
				<div class="modal-header bg-light d-flex justify-content-start">
					<h4 id="plan-name" class="modal-title display-4 font-italic"></h4>
				</div>
				
				<div class="modal-body bg-light">
					<form action="feed/modify_plan.do" class="row" method="post" id="modify_form">
						<input type="hidden" name="planNum" id="planNum" />
						<div class="form-group col-4">
							<label for="planName">일정 이름</label>
							<input type="text" id="planName" name="planName" class="form-control"/>
						</div>
						
						<div class="form-group col-4">
							<label for="startDate">시작 일자</label>
							<input type="date" id="startDate" name="startDate" class="form-control"/>
						</div>
						
						<div class="form-group col-4">
							<label for="endDate">종료 일자</label>
							<input type="date" id="endDate" name="endDate" class="form-control"/>
						</div>
					</form>
					<div class="button-group d-flex justify-content-end mt-2">
						<button type="button" id="btn-modify" class="btn btn-sm btn-success mx-1">일정 수정</button>
						<button type="button" id="btn-delete" class="btn btn-sm btn-danger mx-1">일정 삭제</button>
						<button type="button" id="btn-detail" class="btn btn-sm btn-dark mx-1">상세 수정</button>
					</div>
				
					<div class="detail-days d-flex" data-index="1">
						<button type="button" class="btn btn-outline-light text-dark col-1">
							<i class="fa-solid fa-angle-left"></i>
						</button>
						
						<h4 id="plan-day" class="display-4 font-italic" style="font-size: 30px; font-weight: 600;">day 1</h4>
						
						<button type="button" class="btn btn-outline-light text-dark col-1">
							<i class="fa-solid fa-angle-right"></i>
						</button>
					</div>
					<div class="plan-details mt-2 d-flex row mx-0" style="height:500px;">
						<div id="map" class="col-8 border"></div>
						<div id="details show" class="col-4 border">
						
						
						
						</div>
					</div>
				</div>
				<div class="modal-footer bg-light mb-2"></div>
			</div>
		</div>
	</div>
	
	
</body>
</html>