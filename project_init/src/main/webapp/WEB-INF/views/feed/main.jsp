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

<%-- csrf beforesend 이용을 위한 header setting --%>
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<%-- fullcalendar javascript files --%>
<script src="js/feed/fc/main.js"></script>
<script src="js/feed/fc/locales/ko.js"></script>

<%-- custom javascript files --%>
<script src="js/feed/feed_calendar.js"></script>

<!-- KAKAO API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=94ef81dc370b9f961476a1859364f709&libraries=services"></script>


<%-- fullcalendar css files --%>
<link href="css/feed/fc/main.css" rel="stylesheet"></link>

<%-- page custom css files --%>
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/feed/main_custom.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />

<title>Insert title here</title>

</head>

<body>
<%@ include file="../header.jsp" %>

<section class="container mb-5">
	<%-- section-header --%>
	<div class="body-container">
		<%-- 1. 유저 프로필 (프로필 이미지, 정보)--%>
		<div id="feed-header" class="d-flex justify-content-start border-bottom row mx-0 flex-nowrap">
			<%-- 1- 유저정보1 =  --%>
			<div id="profile-left" class="p-3 mb-5 bg-body col-5"></div>
			
			<%-- 2- 프로필 이미지 --%>
			<div id="profile-img" class="p-3 mb-5 bg-body col-2 text-center">
				<i class="profile-img fa-regular fa-circle-user"></i>
			</div>
			
			<%-- 3- 유저정보2 =  --%>
			<div id="profile-right" class="p-3 mb-5 bg-body col-5"></div>
		</div>

		<%-- 2. 피드 탭 메뉴 --%>		
		<ul class="feed-tabs row mx-0 flex-nowrap">
			<%-- 1- 캘린더 피드 버튼 --%>
			<li id="feed-calendar" class='nav-item active col-3' data-tab=''>
				<a href="feed" class="nav-link">
					<i class="fa-regular fa-calendar-check"></i>
				</a>
			</li>
			
			<%-- 2- 맵 피드 버튼 --%>
			<li id="feed-map" class='nav-item col-3' data-tab=''>
				<a href="feed/feedMap" class="nav-link">
					<i class="fa-solid fa-map-location-dot"></i>
				</a>
			</li>
		
			<%-- 3- 포스트 피드 버튼 --%>			
			<li id="feed-post" class='nav-item col-3' data-tab=''>
				<a href="feed/feedPost" class="nav-link">
					<i class="fa-solid fa-images"></i>
				</a>
			</li>
			
			<%-- 4- 유저 정보 피드 버튼 --%>			
			<li id="feed-info" class='nav-item col-3' data-tab=''>
				<a href="feed/feedInfo" class="nav-link">
					<i class="fa-solid fa-gear"></i>
				</a>
			</li>
		</ul>

		<%-- section-body --%>
		<div class="d-flex justify-content-between mt-5" id="main-body">
			<%-- 1. PlanMst 생성 inputBox --%>
			<div class="border rounded p-3">
				<form action="plan" id="frm" method="post">
					<%-- 1- _csrf input --%>			
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

					<%-- 2- planName --%>	
					<div class="form-group">
						<label for="planName">일정 이름</label>
						<input type="text" name="planName" id="planName" class="form-control" required/>
					</div>
					
					<%-- 3- startDate --%>	
					<div class="form-group">
						<label for="startDate">시작 일자</label>
						<input type="text" name="startDate" id="startDate" class="form-control bg-light" required readonly/>
					</div>
					
					<%-- 4- endDate --%>	
					<div class="form-group">
						<label for="endDate">종료 일자</label>
						<input type="text" name="endDate" id="endDate" class="form-control bg-light" required readonly/>
					</div>
					
					<%-- 5- eventColor : 캘린더에 생성되는 이벤트 블럭 색상 default = blue --%>	
					<div class="form-group">
						<label for="eventColor">블럭 색상</label>
						<select class="custom-select my-1 mr-sm-2 " id="eventColor" name="eventColor">
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
					
					<%-- 6- dateCount[hidden] : submit 일어나면 몇 일짜리 일정인지 계산하여 value 추가 --%>	
					<input type="hidden" name="dateCount" id="dateCount" class="form-control"/>
					
					<%-- 7- buttonBox : submit / form 전체 reset button --%>
					<div class="d-flex justify-content-end mp_btn">
						<button type="submit" class="btn btn-sm btn-primary mr-1" id="submit" >Create</button>
						<input type="reset" class="btn btn-sm btn-danger px-2" id="reset" value="Clear" />
					</div>
				</form>
			</div>
			<%-- fullcalendar div  --%>
			<div id="calendar"></div>
			<%-- modal button : 이미 생성한 이벤트 블럭을 클릭하면 열리는 모달창 버튼 --%>
			<input type="hidden" id="modalBtn" data-toggle="modal" data-target="#detailModal" value="modal" />
			<%@ include file="../feed/modal2.jsp" %>
		</div>
	</div>
	
</section>

<%@ include file="../footer.jsp" %>

<script>

// feed tab 메뉴 각각의 요소 클릭했을 때,
$('.nav-link').click(function(e) {
	// feed일 때는 feed 페이지로 재진입, 아닐때는 get방식 ajax호출해서 #main-body에 html 뿌림
	if ( $(this).attr('href') != 'feed' ) {
		e.preventDefault();
		
		// 현재 active로 되어있는 tab menu의 active 클래스를 삭제
		$(this).parent().siblings('.active').removeClass('active');
		
		// 클릭된 요소에 active 클래스 부여
		$(this).parent().addClass('active');
		
		
		// 해당되는 페이지 jsp 파일을 #main-body에 뿌려주는 ajax
		$.ajax({
			url: $(this).attr('href'),
			type: 'get',
			success: function(data) {
				// post에서 더보기 버튼으로 늘어났을 때, main-body의 height를 초기화
				var height = $('#main-body').height(1000);
				
				if ( Number(height) > 1000 ) {
					$('#main-body').height(1000);
				};
				
				$('#main-body').html(data);
				
			},
			error: function(data) {
				console.log(data);
				alert('ajax 실패');
			}
		})
	}
});

</script>

</body>
</html>