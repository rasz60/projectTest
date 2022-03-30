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
					<form action="#" class="row" method="post" id="modify_form">
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
						<input type="hidden" name="planNum" id="modal-planNum" />
						<input type="hidden" name="originDateCount" id="modal-originDateCount" value="" />
						<input type="hidden" name="newDateCount" id="modal-newDateCount" value="" />
						<div class="form-group col-8">
							<label for="planName">일정 이름</label>
							<input type="text" id="modal-planName" name="planName" class="form-control"/>
						</div>

						<div class="form-group col-4">
							<label for="modal-eventColor">블럭 색상</label>
							<select class="custom-select my-1 mr-sm-2 " id="modal-eventColor" name="eventColor">
								<option value="#007bff" selected>Blue</option>
								<option value="#6610f2">Indigo</option>
								<option value="#6f42c1">Purple</option>
								<option value="#e83e8c">Pink</option>
								<option value="#dc3545">Red</option>
								<option value="#fd7e14">Orange</option>
								<option value="#ffc107">Yellow</option>
								<option value="#28a745">Green</option>
								<option value="#20c997">Teal</option>
								<option value="#17a2b8">Cyan</option>
								<option value="#6c757d">Gray</option>
								<option value="gray-dark">Dark Gray</option>
							</select>
						</div>
						
						<div class="form-group col-6">
							<label for="modal-startDate">시작 일자</label>
							<input type="date" id="modal-startDate" name="startDate" class="form-control"/>
						</div>
						
						<div class="form-group col-6">
							<label for="modal-endDate">종료 일자</label>
							<input type="date" id="modal-endDate" name="endDate" class="form-control"/>
						</div>
				</form>
				<div class="button-group d-flex justify-content-end mt-2">
					<button type="submit" id="btn-modify" class="btn btn-sm btn-success mx-1">일정 수정</button>
					<button type="button" id="btn-delete" class="btn btn-sm btn-danger mx-1">일정 삭제</button>
					<a href="${pageContext.request.contextPath}/plan/detail_modify?planNum=" id="btn-detail" class="btn btn-sm btn-dark mx-1">상세 수정</a>
				</div>
			
				<div class="detail-days d-flex" data-count="">
					<button type="button" class="btn btn-outline-default text-dark col-1" id="prev-btn" data-index="0">
						<i class="fa-solid fa-angle-left"></i>
					</button>
					
					<h4 id="plan-day" class="display-4 font-italic"></h4>
					
					<button type="button" class="btn btn-outline-default text-dark col-1" id="next-btn" data-index="2">
						<i class="fa-solid fa-angle-right"></i>
					</button>
				</div>
				
				<div class="plan-details mt-2 d-flex row mx-0">
					<div id="map" class="col-8"></div>
					<div id="details1" class="col-4 px-0">
						<c:forEach begin="1" end="10" var="i">
							<div class="list-group-item planDt${i} flex-row mx-0 px-1 pt-2">
								<h4 class="placeName col-12 font-italic"></h4>
								<h6 class="font-italic col-6 startTime"></h6>
								<h6 class="font-italic col-6 endTime"></h6>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="modal-footer bg-light mb-2"></div>
		</div>
	</div>
</div>
	
</body>
</html>