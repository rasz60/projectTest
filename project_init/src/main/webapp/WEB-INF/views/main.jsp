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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd&libraries=clusterer"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main/main.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
<title>Insert title here</title>

</head>

<body>

<%@ include file="header.jsp" %>

<section class="container">
	<div class="main-body-top d-flex justify-content-between mb-3">
		<div class="map border rounded p-2" id="map"></div> <!-- 맵 생성 -->
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
						
						<button type="button" class="logx-btn btn btn-primary btn-sm" id="loginBtn">login</button>
					</s:authorize>
					
					<s:authorize access="isAuthenticated()">				
						<div class="profile-img">
							<i class="user-info-icon fa-regular fa-circle-user"></i>
						</div>
					
						<div class="nickname">
							<p class="h5">Nickname</p>
						</div>
						
						<form method="post" action="logout">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<button type="submit" id="logoutBtn" class="logx-btn btn btn-danger btn-sm">logout</button>
						</form>
					</s:authorize>
					
				</div>
			</div>
			<div class="map-filter border rounded p-1"> <!-- 필터 생성 -->
				<form id="frm" name="frm" action="insertFilter" method="post"> <!-- 필터 form -->
					<div class="form-group">
						<div class="input-group-append">	
							<span class="input-group-text">날짜</span>		
						<input type="date" class="form-control" id="plandate" name="value2" value=""/> <!-- 날짜 선택 input 생성 -->
						</div>
					</div>
					<div class="form-group"> 
						<select id="selbox" class="main-filter custom-select my-1 mr-sm-2" name="value3"> <!-- 메인 필터 select창 생성 -->
							<option>Select Filter</option>
							<option value="1">모두보기</option>
							<option value="category">장소</option>
							<option value="address">지역</option>
							<option value="transportation">이동수단</option>
							<option value="theme">테마</option>
						</select><br/>		
						<select class="sub-filter custom-select my-1 mr-sm-2" name="value4"> <!-- 서브 필터 select창 생성, option은 script에서 생성 -->
							<option>Select Detail Filter</option>
						</select>			 
					</div>					
					<button type="submit" id="filterbtn" class="btn btn-success" style="float: right;">Filter</button> <!-- 필터 제출 버튼 -->
				</form>
			</div>
		</div>
	</div>
	
	<div class="main-body-bottom mb-3">
		<div class="recommand recommand-1 mb-2">
			<div class="recommand-icon text-danger d-flex justify-content-between">
				<i class="btn1 fa-solid fa-location-arrow"></i>
				<a href="post/postMain" class="text-danger">
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

<%@ include file="login_modal.jsp" %>
<%@ include file="modalPost.jsp" %>
<%@ include file="footer.jsp" %>


<script>

$(document).ready(function() {
	$('.post').click(function() {
		console.log($(this).text());
		$('#modalBtn').trigger('click');
	});
	
	
	$('#loginBtn').click(function() {
		console.log($(this).text());
		$('#loginModalBtn').trigger('click');
		//location.href="user/login_view";
	})
	
	<c:if test='${not empty error}'>
		console.log('error');
		$('#loginError').css('visibility','visible');
		$('#loginModalBtn').trigger('click');
	</c:if>
});


//메인 필터객체 생성
var mainFilter = document.querySelector('.main-filter');

//메인 필터 객체에 변화가 생겼을 때 이벤트가 실행될 수 있는 onchange이벤트 생성
mainFilter.onchange = function(){
	var subFilter = document.querySelector('.sub-filter');
	var mainOption = mainFilter.options[mainFilter.selectedIndex].innerText;

	//서브필터의 option 생성	
	var subOption = {
		allMarker : ['All Places'],
		place : ['관광명소', '숙박', '음식점', '카페', '병원', '약국', '대형마트', '편의점', '어린이집, 유치원', '학교', '학원', '주차장', '주유소, 충전소', '지하철역', '은행', '문화시설', '공공기관'],
		address : ['서울', '부산', '제주', '경기', '인천', '강원', '경상', '전라', '충청', '전남', '대전', '강원', '대구', '경북'],
		transportation : ['도보', '자가용', '고속/시외/시내버스', '지하철', '자전거', '택시', '전세/관광버스', '차량대여/렌트', '오토바이', '전동킥보드', '비행기', '선박', '기타'],
		theme : ['방문', '데이트', '가족여행', '친구들과', '맛집탐방', '비즈니스', '소개팅', '미용', '운동', '문화생활', '여가생활']
	}
	
	//메인옵션 선택에 따라 서브옵션 select
	switch(mainOption){
		case '모두보기' : 
			var subOption = subOption.allMarker;
			break;
		case '장소' : 
			var subOption = subOption.place;
			break; 
		case '지역' : 
			var subOption = subOption.address;
			break;
		case '이동수단' : 
			var subOption = subOption.transportation;
			break;
		case '테마' : 
			var subOption = subOption.theme;
			break;
	}
	
	subFilter.options.length = 0;
	
	for(var i=0; i < subOption.length; i++){ //생성된 서브옵션 수 만큼 option태그 생성
		var option = document.createElement('option');
		option.innerText = subOption[i]; // 생성된 option 태그에 서브옵션의 값 넣기
		subFilter.append(option); // 서브필터에 option태그 넣기
	}
}

var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
    center : new kakao.maps.LatLng(36.1372611294738, 128.09319902660602), // 지도의 중심좌표 
    level : 13 // 지도의 확대 레벨 
});

map.setMaxLevel(14); //지도 확대 최대 레벨

//검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 마커 클러스터러를 생성 
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
    minLevel: 10, // 클러스터 할 최소 지도 레벨
    disableClickZoom: true, // 클러스터 마커를 클릭했을 때 지도가 확대 되도록 설정한다
    calculator: [10, 20, 30], // 클러스터의 크기 구분 값(10개, 20개, 30개 마다 다르게), 각 사이값마다 설정된 text나 style이 적용된다
    styles: [{ // calculator 각 사이 값 마다 적용될 스타일을 지정한다
        width : '30px', height : '30px',
        background: 'rgba(51, 204, 255, .8)',
        borderRadius: '15px',
        color: '#000',
        textAlign: 'center',
        fontWeight: 'bold',
        lineHeight: '31px'
    },
    {
        width : '40px', height : '40px',
        background: 'rgba(255, 153, 0, .8)',
        borderRadius: '20px',
        color: '#000',
        textAlign: 'center',
        fontWeight: 'bold',
        lineHeight: '41px'
    },
    {
        width : '50px', height : '50px',
        background: 'rgba(255, 51, 204, .8)',
        borderRadius: '25px',
        color: '#000',
        textAlign: 'center',
        fontWeight: 'bold',
        lineHeight: '51px'
    },
    {
        width : '60px', height : '60px',
        background: 'rgba(255, 80, 80, .8)',
        borderRadius: '30px',
        color: '#000',
        textAlign: 'center',
        fontWeight: 'bold',
        lineHeight: '61px'
    }
	]
});

// 데이터를 가져오기 위해 ajax를 사용
// 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줌
$.ajax({
	url : "selectPlanList" ,
	type : "POST",
	beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);
	},
	success : function(data) {
		console.log(data);
		var markers =[]; // markers를 배열로 선언
		for (var i = 0; i < data.length; i++ ) {
			var marker = new kakao.maps.Marker({  //반복문에서 생성하는 marker 객체를 markers에 추가
	            map: map, // 마커를 표시할 지도
	            position: new kakao.maps.LatLng(data[i].latitude, data[i].longitude) // 마커를 표시할 위치
	        })											
			markers.push(marker);
			
			var placeName = [];
			placeName.push(data[i].placeName);
			var address=[];
			address.push(data[i].address);		
			var category = []; //카테고리
			
			switch(data[i].category){ // DB에는 카테고리의 code값이 들어가므로 code를 카테고리 명으로 변경
				case "MT1" : category.push("대형마트");
				break;
				case "CS2" : category.push("편의점");
				break;
				case "PS3" : category.push("어린이집, 유치원");
				break;
				case "SC4" : category.push("학교");
				break;
				case "AC5" : category.push("학원");
				break;
				case "PK6" : category.push("주차장");
				break;
				case "OL7" : category.push("주유소, 충전소");
				break;
				case "SW8" : category.push("지하철역");
				break;
				case "BK9" : category.push("은행");
				break;
				case "CT1" : category.push("문화시설");
				break;
				case "AG2" : category.push("중개업소");
				break;
				case "PO3" : category.push("공공기관");
				break;
				case "AT4" : category.push("관광명소");
				break;
				case "PO3" : category.push("숙박");
				break;
				case "FD6" : category.push("음식점");
				break;
				case "CE7" : category.push("카페");
				break;
				case "HP8" : category.push("병원");
				break;
				case "PM9" : category.push("약국");
				break;
			}
			
			(function(marker, placeName, address, category) { //이벤트 등록
				kakao.maps.event.addListener(marker, 'mouseover', function() { //마커에 마우스 올렸을 때
		            displayInfowindow(marker, placeName, address, category); // displayInfowindow()에서 처리
		        });
		
			        kakao.maps.event.addListener(marker, 'click', function() { // 마커에 마우스 치웠을 때 인포창 닫기
		            infowindow.close();
		        }); 	
			})(marker, placeName, address, category);
		 }		
		clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가						
	},
	error : function(data) {
		console.log(data);
		alert('selectPlanList error');
	}
});
 
//Filter 버튼 클릭 시
$('#filterbtn').click(function(e){
	e.preventDefault();
	
	//날짜 값
	var value1; //plandate 컬럼 
	var value2; //plandate의 값
	
  	if($("#plandate").val() == ""){ //날짜 선택 안할 시 전체 날짜 표시를 위해 컬럼 = 1, 값 = '1' 설정
  		value1 = "1";
		value2 = "1";
	}
  	else{ //날짜를 선택하면 컬럼에 plandate, 값에 선택한 날짜 입력
  		value1 = "plandate";
  		value2 = $("#plandate").val();
  	}
	
	//필터 값
	var value3; // 메인필터 값(컬럼이름)
	var subOption = $('.sub-filter').val();
	var value4; //서브필터 값(컬럼의 값)
	
  	if($('.main-filter').val() == "Select Filter"){ //날짜만 선택시 서브필터 컬럼 = 1, 값 = '1' 설정
  		value3 = "1";
		value4 = "1";
	}
  	else{ //날짜를 선택하면 컬럼에 서브필터 값, 컬럼의 값에 서브 필터 값 입력
  		value3 = $('.main-filter').val(); 		 
  		value4 = subOption; //서브필터 값(컬럼의 값)
  	}
	
	switch(subOption){ // DB에는 카테고리의 code값이 들어가므로 서브옵션의 값을 code로 변경
		case "대형마트" : value4 = "MT1";
		break;
		case "편의점" : value4 = "CS2";
		break;
		case "어린이집, 유치원" : value4 = "PS3";
		break;
		case "학교" : value4 = "SC4";
		break;
		case "학원" : value4 = "AC5";
		break;
		case "주차장" : value4 = "PK6";
		break;
		case "주유소, 충전소" : value4 = "OL7";
		break;
		case "지하철역" : value4 = "SW8";
		break;
		case "은행" : value4 = "BK9";
		break;
		case "문화시설" : value4 = "CT1";
		break;
		case "중개업소" : value4 = "AG2";
		break;
		case "공공기관" : value4 = "PO3";
		break;
		case "관광명소" : value4 = "AT4";
		break;
		case "숙박" : value4 = "PO3";
		break;
		case "음식점" : value4 = "FD6";
		break;
		case "카페" : value4 = "CE7";
		break;
		case "병원" : value4 = "HP8";
		break;
		case "약국" : value4 = "PM9";
		break;
		case "All Places" : value4 = "1";
		break;
	}

	clusterer.clear(); //이전에 생성된 마커들 제거
	console.log(value1, value2, value3, value4)
	$.ajax({
		url : 'filter',
		type: 'get',
		data: {"value1" : value1, "value2" : value2, "value3" : value3, "value4" : value4},
		beforeSend: function(xhr){
 		   	var token = $("meta[name='_csrf']").attr('content');
 			var header = $("meta[name='_csrf_header']").attr('content');
		        xhr.setRequestHeader(header, token);
		},
		success: function(data) {
			console.log(data);			
			var markers =[]; // markers를 배열로 선언
			for (var i = 0; i < data.length; i++ ) {
				var marker = new kakao.maps.Marker({  //반복문에서 생성하는 marker 객체를 markers에 추가
		            map: map, // 마커를 표시할 지도
		            position: new kakao.maps.LatLng(data[i].latitude, data[i].longitude) // 마커를 표시할 위치
		        })
				markers.push(marker);
				
				var placeName = [];
				placeName.push(data[i].placeName);
				var address=[];
				address.push(data[i].address);
				var category = []; //카테고리
				
				switch(data[i].category){ // DB에는 카테고리의 code값이 들어가므로 code를 카테고리 명으로 변경
					case "MT1" : category.push("대형마트");
					break;
					case "CS2" : category.push("편의점");
					break;
					case "PS3" : category.push("어린이집, 유치원");
					break;
					case "SC4" : category.push("학교");
					break;
					case "AC5" : category.push("학원");
					break;
					case "PK6" : category.push("주차장");
					break;
					case "OL7" : category.push("주유소, 충전소");
					break;
					case "SW8" : category.push("지하철역");
					break;
					case "BK9" : category.push("은행");
					break;
					case "CT1" : category.push("문화시설");
					break;
					case "AG2" : category.push("중개업소");
					break;
					case "PO3" : category.push("공공기관");
					break;
					case "AT4" : category.push("관광명소");
					break;
					case "PO3" : category.push("숙박");
					break;
					case "FD6" : category.push("음식점");
					break;
					case "CE7" : category.push("카페");
					break;
					case "HP8" : category.push("병원");
					break;
					case "PM9" : category.push("약국");
					break;
				}
				

			 }
			clusterer.addMarkers(markers); // 클러스터러에 마커들을 추가		
		},
		error: function(data) {
			alert("필터를 다시 선택해주세요.")
		}
	});	
});


function displayInfowindow(marker, placeName, address, category) { //인포윈도우 생성
		 var content = '<div class="wrap">' + 
			     	     '<div class="info">' + 
				             '<div class="title">' + 
				     				'<img src="images/marker.png" width="25px" height="25px" background-color="white">&nbsp;&nbsp;&nbsp;' + 
				     				placeName + 
				             '</div>' + 
				             '<div class="body">' + 
				                 '<div class="img">' +
				                     '<img src="images/dcfa90e9-aa6d-4faf-b3a7-02da8588dba0christmastree.png" width="65px" height="55px">' +
				                '</div>' + 
				                 '<div class="content">' + 
				                     '<div class="address">' + '주소 : ' + address + '</div>' +
				                     '<div class="category">' + '장소 : ' + category + '</div>' +
				                 '</div>' + 
				             '</div>' + 
				         '</div>' +    
				       '</div>'; 
     
	 infowindow.setContent(content);
	 infowindow.open(map, marker);
}

// 마커 클러스터러에 클릭이벤트를 등록합니다
// 마커 클러스터러를 생성할 때 disableClickZoom을 true로 설정하지 않은 경우
// 이벤트 헨들러로 cluster 객체가 넘어오지 않을 수도 있습니다
kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {
	
	// 현재 지도 레벨에서 1레벨 확대한 레벨
    var level = map.getLevel()-1;
	
 	// 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대합니다
    map.setLevel(level, {anchor: cluster.getCenter()});
});
</script>

</body>
</html>
