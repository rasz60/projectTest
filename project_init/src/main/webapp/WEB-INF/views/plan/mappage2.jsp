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
<script type="text/javascript" src="js/plan/plan_detail.js"></script>
<link rel="stylesheet" type="text/css" href="css/plan/kakaomap/kakaomap.css" />
<link rel="stylesheet" type="text/css" href="css/plan/plan_detail.css" />
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />
<title>Insert title here</title>
<script>
function strToDate(str) {
	let y = str.slice(0, 4);
	let m = Number(str.slice(5, 7)) - 1;
	let d = str.slice(8);
	
	return new Date(y, m, d);
}

function dateToStr(date) {
	let y = date.getFullYear();
	let m = date.getMonth() + 1;
	let d = date.getDate();
	
	if ( m <= 9 ) {
		m = "0" + m;
	}
	
	if ( d <= 9 ) {
		d = "0" + d;
	}
	
	return y+"-"+m+"-"+d;
}

function getPlanDate(start, end) {
	var dates = [];
	dates.push(dateToStr(start));

	var date = new Date(start);
	
	while ( date < end ) {
		date.setDate(date.getDate() + 1);
		
		dates.push(dateToStr(date));
	}
	
	return dates;
}

var sDate = strToDate('<c:out value="${plan.startDate}" />');
var eDate = strToDate('<c:out value="${plan.endDate}" />');
var dateCount = '<c:out value="${plan.dateCount}" />';
var dates = getPlanDate(sDate, eDate);

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
					<li class='nav-item' data-tab='tab-1' data-inputForm='frm1'>
						<p class="nav-link active">date1</p>
					</li>
				</ul>
				<button type="button" id="submitAll" class="btn btn-sm btn-success float-right">저장</button>
			</div>
			
			<!-- [planMst] -->
			<form id="frm0" name="frm0" action="#" method="post">
				<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" readonly/>
				<!-- [pk] planNum -->
				<input type="hidden" class="form-control" name="planNum" value="${plan.planNum}" value="0" readonly/>
				<!-- planName -->
				<input type="hidden" class="form-control" name="planName" value="${plan.planName}" readonly/>
				<!-- startDate -->
				<input type="hidden" class="form-control" name="startDate" value="${plan.startDate}" readonly/>
				<!-- endDate -->
				<input type="hidden" class="form-control" name="endDate" value="${plan.endDate}" readonly/>
				<!-- dateCount -->
				<input type="hidden" class="form-control" name="dateCount" value="${plan.dateCount}" readonly/>
				<!-- theme -->
				<input type="hidden" class="form-control" name="theme" value="${plan.theme}" readonly/>
			</form>
			
			
			<div id="tab-1" class="mt-2 tab-content current">
			
				<!-- User submit Input -->
				<h3 class="display-4 font-italic" id="date-title">DAY 1 : ${plan.startDate }</h3>
				<hr />
				<p class="mt-2">일정 : <span class="showIndex">0</span> / 10</p>
				<!-- input창 -->
				<div class="inputDiv">
					<!-- [planDt] -->
					<form id="frm1" name="frm1" action="#" method="post" data-count="0" data-day="day1" data-date="${plan.startDate}">
						<div class="detail0 mt-2 py-2 border bg-light rounded">
							<h3 class="font-italic ml-2 d-inline mt-2">Place</h3>
							<!-- placeName -->
							<input type="text" class="form-control col-8 d-inline ml-3" name="placeName" readonly/>
							<button type="button" class="btn btn-sm btn-danger deleteBtn float-right mr-2 mt-1"><i class="fa-solid fa-minus"></i></button>
							<button type="button" class="btn btn-sm btn-dark detailBtn float-right mr-2 mt-1" data-count="0"><i class="fa-solid fa-angles-down"></i></button>
							<hr />

							<div class="inputbox row mx-0 justify-content-between">
								<!-- [pk] planDtNum -->
								<input type="hidden" class="form-control" name="planDtNum" value="0" readonly/>
								<!-- day -->
								<input type="hidden" class="form-control" name="planDay" value="day1"readonly/>
								<!-- placeCount -->
								<input type="hidden" class="form-control" name="placeIndex" readonly/>
								<!-- planDate -->
								<input type="hidden" class="form-control" name="planDate" value="${plan.startDate}" readonly/>
								<!-- latitude -->
								<input type="hidden" class="form-control" name="latitude" readonly/>
								<!-- longitude -->
								<input type="hidden" class="form-control" name="longitude" readonly/>
								<!-- address -->
								<input type="hidden" class="form-control" name="address" readonly/>
								<!-- category -->
								<input type="hidden" class="form-control" name="category" readonly/>			
								
								<!-- startTime -->
								<div class="form-group col-6">
									<label for="startTime">StartTime</label>
									<input type="time" class="form-control" name="startTime" />
								</div>
								
								<!-- endTime -->
								<div class="form-group col-6">
									<label for="endTime">EndTime</label>
									<input type="time" class="form-control" name="endTime" />
								</div>
								
								<!-- transportation -->								
								<div class="form-group col-12 toggle none">
									<label for="transportation">교통수단</label>
									<input type="text" class="form-control" name="transportation" />
								</div>
								
								<!-- details -->	
								<div class="form-group col-12 toggle none">
									<label for="details">상세 일정</label>
									<textarea rows="5" class="form-control" name="details" ></textarea>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
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

<script type="text/javascript" src="js/plan/kakaomap/kakaomap.js"></script>

<%@ include file="../footer.jsp" %>

</body>
</html>