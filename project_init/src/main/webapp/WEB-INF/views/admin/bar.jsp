<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<!--
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.js"></script>
<title>Bar그래프</title>
</head>
<body>

<canvas id="canvas" class="container"></canvas>

<script>
//line처럼 서버에서 데이터를 가져와서 그리기만 bar그래프로 그려줌
$(document).ready(function(){
	//변수 선언부는 페이지 로딩시 변수 선언
	let chartLabels = []; //장소 표시 배열 초기화
	let chartData1 = []; // top.5 장소
	
	//그래프를 그릴 수 있도록 가공해둔 변수
	let lineChartData = {
		labels : chartLabels, //그래프의 기본축인 x에 들어 가는 값
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
				data : chartData1,
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
		var ctx = document.getElementById("canvas").getContext("2d");
		new Chart(ctx,{
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
									fontSize : 8
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
	
	$.ajax({
		type : 'POST',
		url : 'placesDashBoard', //서버에서 그래프용 데이터 처리 요청
		beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);
		},
		success : function(data) { //result는 서버에서 오는 json형태 값
			//.each함수는 자바의 enhanced for문을 생각 할 것
			//result.datas는 배열형 객체, index는 색인번호,obj는 현재 객체
			console.log(data);
			for(i=0; i<data.length; i++) {
				chartLabels.push(data[i].placeName);
				chartData1.push(data[i].place_count);
			};
			createChart();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('There is an error : method(group)에 에러가 있습니다.');
		}
	});
});
</script>
</body>
</html>