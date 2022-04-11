<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" 
 integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" 
 crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/footer.css" />
<!-- Cart.js API 라이브러리 추가 -->
<title>Insert title here</title>
<style>
html,body {
	height: 100%;
	margin: 0; 
	padding : 0;
}

body{
	padding-top: 80px;
}

.container{
	width : 100%;
}

#admin_ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 15%;
  background-color: #f1f1f1;
  overflow: auto;
  border: solid;
}

#admin-li a {
  display: block;
  color: #000;
  padding: 8px 16px;
  text-decoration: none;
}

#admin-li a.active {
  background-color: #555;
  color: white;
}

#admin-li a:hover:not(.active) {
  background-color: #555;
  color: white;
}

.DashBoard-filter{
	width : 100%;
	height : auto;
}

#btn{
	border: solid;
}

#canvas{
	width : 100%;
	/* border: solid; */
}

</style>
</head>
<body>

<%@ include file="../header.jsp" %>

	<nav class="navbar navbar-default bg-white">
		<ul id="admin_ul">
		  <li id="admin-li"><a class="active" href="#">DashBoard</a></li>
		  <li id="admin-li"><a href="#">게시물 관리</a></li>
		  <li id="admin-li"><a href="#">유저 관리</a></li>
		</ul>
	</nav>	
<div class="container">
	<div class="DashBoard-filter d-flex"> <!-- 필터 생성 -->
		<form id="frm" name="frm" action="insertFilter" method="post"> <!-- 필터 form -->
			<div class="pSelBox_title">인기 장소 통계 : </div>
			<div class="form-group"> 
				<select id="pSelBox" class="main-filter custom-select my-1 mr-sm-2" name="value1"> <!-- 메인 필터 select창 생성 -->
					<option value="AllPlaces Top 5">AllPlaces Top 5</option>
					<option value="Restaurant Top 5">Restaurant Top 5</option>
					<option value="Cafe Top 5">Cafe Top 5</option>
					<option value="Attractions Top 5">Attractions Top 5</option>
				</select><br/>		
				<select id="pSelSubBox" class="sub-filter custom-select my-1 mr-sm-2" name="value2" style="display:none"> <!-- 서브 필터 select창 생성, option은 script에서 생성 --></select>			 
			</div>					
			<button type="submit" id="pfilterbtn" class="btn btn-success" style="float: left; margin-top:32px;">Filter</button> <!-- 필터 제출 버튼 -->
		</form>
		
		<form id="frm" name="frm" action="insertFilter" method="post" style="margin-left:20px;"> <!-- 필터 form -->
			<div class="form-group">
				<div class="uSelBox_title" >회원 통계 : </div> 
				<select id="uSelBox" class="main-filter custom-select my-1 mr-sm-2" name="value1" style="width:45%;" > <!-- 메인 필터 select창 생성 -->
					<option value="년도 별 가입자 수">년도 별 가입자 수</option>
					<option value="월 별 가입자 수">월 별 가입자 수</option>
					<option value="일 별 가입자 수">일 별 가입자 수</option>
				</select>
				<div class="date_value1">		
					<input id="uvalue3" type="text" name="value3" placeholder="ex)2018"/>&nbsp;&nbsp;~&nbsp;
					<input id="uvalue4" type="text" name="value4" placeholder="ex)2022"/>
				</div>			 
			</div>					
			<button type="submit" id="ufilterbtn" class="btn btn-primary" style="float: left;">Filter</button> <!-- 필터 제출 버튼 -->
		</form>
	</div>
	<hr/>
	<!-- 그래픽표시 공간 -->
	<div id="main" class="container">
		<div id="title" style="text-align:center; font-size:30px;"></div>
		<canvas id="canvas" class="container"></canvas>
	</div>
</div>

<%@ include file="../footer.jsp" %>

<script>
$(document).ready(function(){
	//메인 필터객체 생성
	var mainFilter = document.querySelector('.main-filter');
	
	//메인 필터 객체에 변화가 생겼을 때 이벤트가 실행될 수 있는 onchange이벤트 생성
	mainFilter.onchange = function(){
		var subFilter = document.querySelector('.sub-filter');
		var mainOption = mainFilter.options[mainFilter.selectedIndex].innerText;
	
		//서브필터의 option 생성	
		var subOption = {
			allPlaces : ['all'],	
			restaurant : ['FD6'],
			cafe : ['CE7'],
			attractions : ['AT4']	
		}
		
		
		
		//메인옵션 선택에 따라 서브옵션 select
		switch(mainOption){
			case 'AllPlaces Top 5' : 
				var subOption = subOption.allPlaces;
				break; 
			case 'Restaurant Top 5' : 
				var subOption = subOption.restaurant;
				break; 
			case 'Cafe Top 5' : 
				var subOption = subOption.cafe;
				break;
			case 'Attractions Top 5' : 
				var subOption = subOption.attractions;
				break;
		}
		
		subFilter.options.length = 0;
		
		for(var i=0; i < subOption.length; i++){ //생성된 서브옵션 수 만큼 option태그 생성
			var option = document.createElement('option');
			option.innerText = subOption[i]; // 생성된 option 태그에 서브옵션의 값 넣기
			subFilter.append(option); // 서브필터에 option태그 넣기
		}
	}
	
	$('#uSelBox').change(function(){
		console.log($('#uSelBox option:selected').val());
		if($('#uSelBox option:selected').val() == '년도 별 가입자 수'){
			$('#uvalue3').attr('placeholder', 'ex)2018');
			$('#uvalue4').attr('placeholder', 'ex)2022');
		}
		else if($('#uSelBox option:selected').val() == '월 별 가입자 수'){
			$('#uvalue3').attr('placeholder', 'ex)202201');
			$('#uvalue4').attr('placeholder', 'ex)202212');
		}
		else{
			$('#uvalue3').attr('placeholder', 'ex)20220401');
			$('#uvalue4').attr('placeholder', 'ex)20220430');
		}
	});
	
	$("#pfilterbtn").click(function(event){
		event.preventDefault();
		
	    document.getElementById('title').innerText //그래프에 맞는 title 생성
	    = $("#pSelBox").val();
		
		let value1; //컬럼
		let value2; //값
		
	  	if($("#pSelBox").val() == "AllPlaces Top 5"){ //모든 장소는 컬럼 = 1, 값 = '1' 설정
	  		value1 = "1";
			value2 = "1";
		}
	  	else{ 
	  		value1 = "category"; //컬럼
	  		value2 = $("#pSelSubBox").val(); //값
	  	}
		
		let pChartLabels = []; //장소 표시 배열 초기화
		let pChartData = []; // top.5 장소
		
		//그래프를 그릴 수 있도록 가공해둔 변수
		let lineChartData = {
			labels : pChartLabels, //그래프의 기본축인 x에 들어 가는 값
			fill: false,
			datasets : [ //그래프에 표시할 데이터 값 charData1
				{
					label: "Top 1", //데이터 종류 이름
					fillColor : "rgba(220,220,220,0.2)", //채울색
					strokeColor : "rgba(220,220,220,1)", //선색
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : pChartData,
					backgroundColor: [
						//색상
	                   'rgba(255, 99, 132, 0.2)',
	                   'rgba(54, 162, 235, 0.2)',
	                   'rgba(255, 206, 86, 0.2)',
	                   'rgba(75, 192, 192, 0.2)',
	                   'rgba(153, 102, 255, 0.2)',
					],
	                borderColor: [
	                    //경계선 색상
	                    'rgba(255, 99, 132, 1)',
	                    'rgba(54, 162, 235, 1)',
	                    'rgba(255, 206, 86, 1)',
	                    'rgba(75, 192, 192, 1)',
	                    'rgba(153, 102, 255, 1)',
	                ],
	                borderWidth: 1 //경계선 굵기
				}
			]
		};
		
		
		
		function createChart() {
			if(window.placeChart != undefined){ //이전 차트를 지워서 차트 중복 제거
				window.placeChart.destroy();
			}
			window.placeChart = new Chart(document.getElementById("canvas").getContext("2d"),{
				type: 'bar', //수평막대그래프
				data: lineChartData,
				options: {
					title:{
						display : true,
						text : "가장 많이 방문한 장소 Top 5"
					},
					legend:{
						display : false
					},
					scales: { //눈금표시
						xAxes: [
									{
									ticks:{
										fontColor : 'rgba(12, 13, 13, 1)',
										fontSize : 15
									}
								}
							],
						yAxes: [
									{
										ticks: {
											beginAtZero:true, //0부터 시작
											max : 100 //y 최대값
										}
									}
								]
					}
				}
			});
		}
		
		console.log(value1, value2);
		
		$.ajax({
			type : 'POST',
			url : 'placesDashBoard', //서버에서 그래프용 데이터 처리 요청
			data: {"value1" : value1, "value2" : value2},
			beforeSend: function(xhr){
			   	var token = $("meta[name='_csrf']").attr('content');
				var header = $("meta[name='_csrf_header']").attr('content');
		        xhr.setRequestHeader(header, token);
			},
			success : function(data) { //result는 서버에서 오는 json형태 값
				console.log(data);
			
				for(i=0; i<data.length; i++) {
					pChartLabels.push(data[i].placeName);
					pChartData.push(data[i].count);
				};
				createChart();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('There is an error : method(group)에 에러가 있습니다.');
			}
		});
	});
	
	$("#ufilterbtn").click(function(event){
		event.preventDefault();
		
	    document.getElementById('title').innerText //그래프에 맞는 title 생성
	    = $("#uSelBox").val();
		
		let value1 = $("#uSelBox").val();
		
		switch(value1){ // DB에는 카테고리의 code값이 들어가므로 code를 카테고리 명으로 변경
			case "년도 별 가입자 수" : value1 = "'YYYY'";
			break;
			case "월 별 가입자 수" : value1 = "'MM'";
			break;
			case "일 별 가입자 수" : value1 = "'DD'";
			break;
		}
		
		let value2;
			
		switch(value1){
			case "'YYYY'" : value2 = "YYYY";
			break;
			case "'MM'" : value2 = "YYYYMM";
			break;
			case "'DD'" : value2 = "YYYYMMDD";
			break;
		}
		
		let value3 = $('#uvalue3').val();
		let value4 = $('#uvalue4').val();
		
		let uChartLabels = []; //장소 표시 배열 초기화
		let uChartData = []; // top.5 장소
		
		//그래프를 그릴 수 있도록 가공해둔 변수
		let lineChartData = {
			labels : uChartLabels, //그래프의 기본축인 x에 들어 가는 값
			datasets : [ //그래프에 표시할 데이터 값 uChartData
				{
					label: "유저 현황", //데이터 종류 이름
					fillColor : "rgba(220,220,220,0.2)", //채울색
					strokeColor : "rgba(220,220,220,1)", //선색
					pointColor : "rgba(220,220,220,1)",
					pointStrokeColor : "#fff",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(220,220,220,1)",
					data : uChartData,
					backgroundColor: [
						//색상
	                   'rgba(255, 99, 132, 0.2)',
	                   'rgba(54, 162, 235, 0.2)',
	                   'rgba(255, 206, 86, 0.2)',
	                   'rgba(75, 192, 192, 0.2)',
	                   'rgba(153, 102, 255, 0.2)',
					],
	                borderColor: [
	                    //경계선 색상
	                    'rgba(255, 99, 132, 1)',
	                    'rgba(54, 162, 235, 1)',
	                    'rgba(255, 206, 86, 1)',
	                    'rgba(75, 192, 192, 1)',
	                    'rgba(153, 102, 255, 1)',
	                ],
	                borderWidth: 1 //경계선 굵기
				}
			]
		};
		
		
		
		function createChart() {
			if(window.placeChart != undefined){ //이전 차트를 지워서 차트 중복 제거
				window.placeChart.destroy();
			}
			window.placeChart = new Chart(document.getElementById("canvas").getContext("2d"),{
				type: 'line', //수평막대그래프
				data: lineChartData,
				options: {
					title:{
						display : true,
						text : "가입자 현황"
					},
					legend:{
						display : false
					},
					scales: { //눈금표시
						xAxes: [
									{
									ticks:{
										fontColor : 'rgba(12, 13, 13, 1)',
										fontSize : 10
									}
								}
							],
						yAxes: [
									{
										ticks: {
											beginAtZero:true, //0부터 시작
										}
									}
								]
					}
				}
			});
		}
		
		console.log(value1, value2 ,value3, value4);
		
		$.ajax({
			type : 'POST',
			url : 'userDashBoard', //서버에서 그래프용 데이터 처리 요청
			data: {"value1" : value1, "value2" : value2, "value3" : value3, "value4" : value4},
			beforeSend: function(xhr){
			   	var token = $("meta[name='_csrf']").attr('content');
				var header = $("meta[name='_csrf_header']").attr('content');
		        xhr.setRequestHeader(header, token);
			},
			success : function(data) { //result는 서버에서 오는 json형태 값
				console.log(data);
			
				for(i=0; i<data.length; i++) {
					uChartLabels.push(data[i].userDate);
					uChartData.push(data[i].count);
				};
				createChart();
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert('There is an error : method(group)에 에러가 있습니다.');
			}
		});
	});
	
	
	$("#pie").click(function(event){
		event.preventDefault();
		$.ajax({
			url : "pie",
			type : "get",
			success : function(data) { //data는 jsp가 변환된 html
				$("#main").html(data);
			},
			error : function() {
				alert("bar그래프 에러 발생");
			}
		});
	});
	
	$('#pfilterbtn').trigger('click');
});
</script>
</body>
</html>