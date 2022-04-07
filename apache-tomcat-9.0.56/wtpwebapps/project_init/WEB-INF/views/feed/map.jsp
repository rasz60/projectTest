<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>
<%-- kakaomap javascript CDN --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=92b6b7355eb56122be94594a5e40e5fd"></script>

<%-- kakaomap css file --%>
<link rel="stylesheet" type="text/css" href="css/feed/kakaomap.css" />
<style>
#map {
	width: 100%;
}

</style>
</head>

<body>
	<div id="map" class="border rounded p-3"></div>

<script>

// 문서가 불러와지면 맵을 생성
$(document).ready(function() {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
	    center: new kakao.maps.LatLng(37, 127), // 지도의 중심좌표
	    level: 13 // 지도의 확대 레벨
	};
	var map = new kakao.maps.Map(mapContainer, mapOption);
});
</script>

</body>
</html>