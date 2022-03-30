<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page session="false" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd&libraries=services"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/plan/plan_modify.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/plan/kakaomap/kakaomap.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/plan/plan_detail.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
<title>Insert title here</title>
<script>
var dateCount = '<c:out value="${plan1.dateCount}" />';
</script>
</head>

<body>
<%@ include file="../header.jsp" %>

<section class="container-fluid">
	<div class="planlist row mx-0">
		<!-- 맵 생성 -->
		<div id="kakaobox" class="map_wrap col-7">
			<div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
			<div id="menu_wrap" class="bg_white">
		    	<div class="option">
		        	<div>
		            	<form onsubmit="searchPlaces(); return false;">
		                	키워드 : <input type="text" value="이태원 맛집" id="keyword" size="15"> 
		                	<button type="submit">검색하기</button> 
		            	</form>
		        	</div>
		    	</div>
		    	<hr/>
		    	<ul id="placesList"></ul>
		    	<div id="pagination"></div>
			</div>
		</div>

		<div class="planbox col-5 d-block" id="tabdiv">
			<div>
				<ul class="nav nav-tabs">
					<c:forEach var="i" begin="1" end="${plan1.dateCount }">
						<c:choose>
							<c:when test="${i == 1}">
							<li class='nav-item' data-tab='tab-${i}' data-inputForm='frm${i}'>
								<p class="nav-link active">date${i}</p>
							</li>
							</c:when>
						
							<c:otherwise>
								<li class='nav-item' data-tab='tab-${i}' data-inputForm='frm${i}'>
									<p class="nav-link">date${i}</p>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
				<button type="button" id="submitAll" class="btn btn-sm btn-success float-right">저장</button>
			</div>
			
			<!-- [planMst] -->
			<form id="frm0" name="frm0" action="#" method="post">
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" readonly/>
				<!-- [pk] planNum -->
				<input type="hidden" class="form-control" name="planNum" value="${plan1.planNum}" value="0" readonly/>
				<!-- planName -->
				<input type="hidden" class="form-control" name="planName" value="${plan1.planName}" readonly/>
				<!-- startDate -->
				<input type="hidden" class="form-control" name="startDate" value="${plan1.startDate}" readonly/>
				<!-- endDate -->
				<input type="hidden" class="form-control" name="endDate" value="${plan1.endDate}" readonly/>
				<!-- dateCount -->
				<input type="hidden" class="form-control" name="dateCount" value="${plan1.dateCount}" readonly/>
				<!-- theme -->
				<input type="hidden" class="form-control" name="eventColor" value="${plan1.eventColor}" readonly/>
				
				<!-- deletePlanDtNum -->
				<input type="hidden" class="form-control" name="deleteDtNum" readonly/>
			</form>
			
			<c:forEach var="i" begin="1" end="${plan1.dateCount }">
				<c:choose>
					<c:when test="${i == 1}">
						<div id="tab-${i}" class="mt-2 tab-content current">
					</c:when>
					
					<c:otherwise>
						<div id="tab-${i}" class="mt-2 tab-content">
					</c:otherwise>
				</c:choose>
					<!-- User submit Input -->
					<h3 class="display-4 font-italic" id="date-title"></h3>
					<hr />
					<p class="mt-2">일정 : <span class="showIndex">0</span> / 10</p>
					<!-- input창 -->
					<div class="inputDiv">
						<!-- [planDt] -->
						<form id="frm${i}" name="frm${i}" action="#" method="post" data-count="0" data-day="Day${i}" data-date="">
							<c:set var="day" value="day${i}" /> 
							<c:forEach var="plan2" items="${plan2}">
								
								<c:if test="${plan2.planDay eq day}">
									<div class="detail0 mt-2 py-2 border bg-light rounded">
										<h3 class="font-italic ml-2 d-inline mt-2">Place</h3>
										<!-- placeName -->
										<input type="text" class="form-control col-8 d-inline ml-3" name="placeName" value="${plan2.placeName }" readonly/>
										<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2 mt-1"><i class="fa-solid fa-minus"></i></button>
										<button type="button" class="btn btn-sm btn-dark detailBtn float-right mr-2 mt-1" data-count="0"><i class="fa-solid fa-angles-down"></i></button>
										<hr />
			
										<div class="inputbox row mx-0 justify-content-between">
											<!-- [pk] planDtNum -->
											<input type="hidden" class="form-control" name="planDtNum" value="${plan2.planDtNum }" readonly/>
											<!-- day -->
											<input type="hidden" class="form-control" name="planDay" value="${plan2.planDay}" readonly/>
											<!-- placeCount -->
											<input type="hidden" class="form-control" name="placeCount" value="${plan2.placeCount}" readonly/>
											<!-- planDate -->
											<input type="hidden" class="form-control" name="planDate" value="${plan2.planDate}" readonly/>
											<!-- latitude -->
											<input type="hidden" class="form-control" name="latitude" value="${plan2.latitude}" readonly/>
											<!-- longitude -->
											<input type="hidden" class="form-control" name="longitude" value="${plan2.longitude}" readonly/>
											<!-- address -->
											<input type="hidden" class="form-control" name="address" value="${plan2.address}" readonly/>
											<!-- category -->
											<input type="hidden" class="form-control" name="category" value="${plan2.category}" readonly/>
											
											<!-- startTime -->
											<div class="form-group col-4">
												<label for="startTime">StartTime</label>
												<input type="time" class="form-control" name="startTime" value="${plan2.startTime}" />
											</div>
											
											<!-- endTime -->
											<div class="form-group col-4">
												<label for="endTime">EndTime</label>
												<input type="time" class="form-control" name="endTime" value="${plan2.endTime}" />
											</div>
											
											<!-- theme -->
											<div class="form-group col-4">
												<label for="theme">목적</label>
												<select class="custom-select my-1 mr-sm-2 " id="theme" name="theme">
													<option value="방문" selected>방문</option>
													<option value="데이트">데이트</option>
													<option value="가족여행">가족여행</option>
													<option value="친구들과">친구들과</option>
													<option value="맛집탐방">맛집탐방</option>
													<option value="비즈니스">비즈니스</option>
													<option value="소개팅">소개팅</option>
													<option value="미용">미용</option>
													<option value="운동">운동</option>
													<option value="문화생활">문화생활</option>
													<option value="여가생활">여가생활</option>
												</select>
											</div>
											
											<!-- transportation -->								
											<div class="form-group col-12 toggle none">
												<label for="transportation">교통수단</label>
												<input type="text" class="form-control" name="transportation" value="${plan2.transportation}"/>
											</div>
											
											<!-- details -->	
											<div class="form-group col-12 toggle none">
												<label for="details">상세 일정</label>
												<textarea rows="5" class="form-control" name="details" >${plan2.details}</textarea>
											</div>
										</div>
									</div>
								</c:if>
							</c:forEach>
						</form>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<!-- 저장 누를 시 생성되는 modal창 -->
<div class="container">
	<input id="modalBtn" type="hidden" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" value="modal"/>
	<!-- modal창 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-md text-center">
			<div class="modal-content">
				<div class="modal-header bg-light">
					<h3 class="modal-title font-italic">WAYG</h3>
				</div>
				<div class="modal-body bg-light">
					<h4></h4>
				</div>
				<div class="modal-footer bg-light row mx-0">
					<button id="trueBtn" type="button" class="btn btn-sm btn-primary col-6 mx-0 border-white">저장</button>
					<button id="falseBtn" type="button" class="btn btn-sm btn-danger col-6 mx-0 border-white" data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/plan/kakaomap/kakaomap.js"></script>

<%@ include file="../footer.jsp" %>

</body>
</html>