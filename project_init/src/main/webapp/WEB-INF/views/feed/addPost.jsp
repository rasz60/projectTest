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
<meta id="_csrf" name="_csrf" content="${_csrf.token}" />
<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<!-- KAKAO API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=94ef81dc370b9f961476a1859364f709&libraries=services"></script>

<link rel="stylesheet" type="text/css" href="../css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="../css/includes/footer.css" />
<title>List</title>
<style>
section.container-fluid {
	padding-top: 4rem;
	height: 900px;
}

.detail-days, .plan-details {
	width: 100%;
}

.plan-details div[id^=details] div.list-group-item {
	height: 75px;
}


.postImg {
	max-width: 100%;
	height: 800px;
	max-height: 800px;
}

#addForm .form-group textarea {
	width: 100%;
	resize: none;
}

p#notice {
	font-size: 12px;
	font-style: italic;
}

</style>

</head>
<body>
<%@ include file="../includes/header.jsp" %>
 <br/><br/>
 <section class="container-fluid bg-light">
 
 	<div class="posting-box row mx-0">
 		<div class="col-2">
			<div class="detail-days row mx-0 d-flex" data-count="">
				<%-- 3-1- 이전 planDay 이동 버튼 --%>
				<button type="button" class="btn btn-outline-default text-dark col-1" id="prev-btn" data-index="0">
					<i class="fa-solid fa-angle-left"></i>
				</button>
				
				<%-- 3-2- 현재 보이는 planDay --%>
				<h4 id="plan-day" class="display-4 col-9 font-italic"></h4>
				
				<%-- 3-3- 다음 planDay 이동 버튼 --%>
				<button type="button" class="btn btn-outline-default text-dark col-1" id="next-btn" data-index="2">
					<i class="fa-solid fa-angle-right"></i>
				</button>
			</div>
			
			<%-- 4- 상세 일정 출력 박스 --%>
			<div class="plan-details mt-2">
				<%-- 4-2- 상세 일정의 장소 이름, 시작/종료 시간을 출력 --%>
				<div id="details1" class="px-0">
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
 		
 		<div class="col-5">
 			<div class="postImg border"></div>
 		</div>
 
	 
		<form id="addForm" action="uploadMulti?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data" class="col-5">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
			<input type="hidden" name="email" value="${user}"/>		

			<div class="list-group-item d-flex row mx-0 mb-1">
				<div class="profile-img-s col-2 px-0">
					<div class="img-s border"></div>
				</div>
				
				<div class="col-10">
					<b>nickname</b>
				</div>
			</div>
			
			<div class="list-group-item d-flex row mx-0 mb-1">
				<div class="profile-img-s col-2 px-0">
					<div class="img-s border"></div>
				</div>
				
				<div class="col-10">
					<b>Location</b>
				</div>
			</div>
			

			<div class="list-group-item form-group mb-1">
				<h4>Content</h4>
				<p id="notice">해시태그는 최대 10개 이상을 초과할 수 없습니다.</p>
				<hr />
				<textarea class="form-control content" name="content" rows="10" cols="40" placeholder="content" required></textarea>
				<input name="hashtag" type="text" class="title form-control mt-2" placeholder="#HASHTAG" required>
			</div>
			
			<div class="list-group-item mb-1">
				<h4>Image</h4>
				<p id="notice">최대 10장, 전체 용량 20MB를 초과할 수 없습니다.</p>
				<hr />
				<div class="input-group">
					<div class="custom-file">
						<input name="img" type="file" class="img custom-file-input" placeholder="img" id="inputGroupFile01" multiple="multiple" required>
						<label class="custom-file-label" for="inputGroupFile01">Choose file</label>
					</div>
				</div>
			</div>
	
			<div class="list-group-item button-group row mx-0 d-flex justify-content-around">					
		        <input type="reset" class="btn btn-outline-danger col-3" value="초기화"/>
		        <input type="button" class="btn btn-outline-success col-3" onclick="checkfrm()" value="등록"/>
		        <input type="button" class="btn btn-outline-info col-3" data-dismiss="modal" value="취소"/>
			</div>
		</form>
	
	</div>
</section>
<%@ include file="../includes/footer.jsp" %>

<script type="text/javascript">


function checkfrm() { 
	if($('.location').val()==""){
		alert('장소는 필수입력입니다 ㅜㅜ');
		return false;
	}else{
	$('#addForm').submit();
	}
}
    
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