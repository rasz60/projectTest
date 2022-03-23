<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false"%>
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
<!-- KAKAO API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=94ef81dc370b9f961476a1859364f709&libraries=services"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />

<title>Insert title here</title>
</head>
<body>
<%@ include file="header.jsp" %>
 <br/><br/>
 <c:forEach items="${list}" var="list" >
 <section class="container mt-6 pt-1" style="margin: 200px, 0;">
	<form action="modifyExcute.do?postNo=${list.postNo}&${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
		<div class="form-group">
			<label for="email">EMAIL</label>
			<input name="email" type="text" class="email form-control" placeholder="${list.email}" value="${list.email}" required>
		</div>
		<div class="form-group">
			<label for="title">TITLE</label>
			<input name="title" type="text" class="title form-control" placeholder="${list.title}" value="${list.title}" required>
		</div>
		<div class="form-group">
			<label for="content">CONTENT</label>
			<input name="content" type="text" class="content form-control" placeholder="${list.content}" value="${list.content}" required>
		</div>
		<input name="location" type="hidden" class="location" placeholder="location" value="${list.location}" required>
		<div class="input-group mb-3">
			<div class="custom-file">
				<input name="img" type="file" class="img custom-file-input" placeholder="img" id="inputGroupFile01" multiple="multiple" required>
				<label class="custom-file-label" for="inputGroupFile01">Choose file</label>
			</div>
		</div>
		<div class="form-group">
			<label for="email">Address</label>
		<input type="text" id="sample5_address" class="form-control" placeholder="${list.location}" readonly>
		</div>
		<input type="button" class="btn btn-outline-primary  btn-block"  onclick="sample5_execDaumPostcode()" value="Search my location"/><br>
		<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div><br/>
        <input type="submit" id="sub" class="btn btn-outline-success" value="J O I N"/><hr/>
	</form>
</section>
</c:forEach>
<%@ include file="footer.jsp" %>


<script type="text/javascript">
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
mapOption = {
    center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
    level: 5 // 지도의 확대 레벨
};

//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption);

//주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder();
	
//마커를 미리 생성
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});


function sample5_execDaumPostcode() {
new daum.Postcode({
    oncomplete: function(data) {
        var addr = data.address; // 최종 주소 변수
        $('.location').val(data.sido+" "+data.sigungu);
        // 주소 정보를 해당 필드에 넣는다.
        document.getElementById("sample5_address").value = addr;
        // 주소로 상세 정보를 검색
        geocoder.addressSearch(data.address, function(results, status) {
            // 정상적으로 검색이 완료됐으면
            if (status === daum.maps.services.Status.OK) {

                var result = results[0]; //첫번째 결과의 값을 활용

                // 해당 주소에 대한 좌표를 받아서
                var coords = new daum.maps.LatLng(result.y, result.x);
                // 지도를 보여준다.
                mapContainer.style.display = "block";
                map.relayout();
                // 지도 중심을 변경한다.
                map.setCenter(coords);
                // 마커를 결과값으로 받은 위치로 옮긴다.
                marker.setPosition(coords)
            }
        });
    }
}).open({
	left : 500,
	top : 300	
});
}
</script>
</body>
</html>