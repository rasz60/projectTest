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
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/feed/postMain.js"></script>


<link rel="stylesheet" type="text/css" href="css/modalPost.css" />
<style>

#modalCloseBtn {
	font-size: 3rem;
}

.modal-dialog {
	height: 100%;	
}

.modal-content {
	height: 80%;
}

.modal-content .post-img {
	width: 60%;
}
.modal-content .post-img > .carousel > .Citem > .carousel-item {
	background-color: #fff;
	height: 800px;
	text-align: center;
	line-height:800px;
}


.modal-content .post-img > .carousel > .Citem > .carousel-item > img {
	max-width: 100%;
	max-height: 800px;
}


.modal-content #addForm {
	width: 40%;
}

.modal-content #addForm .form-group textarea {
	width: 100%;
	resize: none;
}

p#notice {
	font-size: 12px;
	font-style: italic;
}

input#sample5_address {
	border: none;
    border-radius: 0;
    border-bottom: 1px solid #ced4da;
}

#map {
	height: 80px;
}


</style>
</head>
<body>
<!-- modal button -->
<input type="hidden" id="modalBtn2" data-toggle="modal" data-target="#modal-reg2" value="modal" />

<!-- modal 창 -->
<div class="modal fade" id="modal-reg2" role="dialog">
	<div class="modal-dialog modal-dialog-centered modal-xl d-block">
		<button type="button" id="modalCloseBtn" class="btn btn-lg btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
		<div class="modal-content">
			<div class="modal-body bg-light d-flex justify-content-between">
				<div class="post-img border rounded mr-2">
					<div id="demo" class="carousel slide" data-ride="carousel">
                    	<!-- The slideshow -->
                        <div class="carousel-inner Citem">
                        	<!-- 이미지 등록 -->
                        </div>
                        <!-- Left and right controls -->
                        <a class="carousel-control-prev" href="#demo" data-slide="prev">
                            <span class="carousel-control-prev-icon"></span>
                        </a>
                        <a class="carousel-control-next" href="#demo" data-slide="next">
                            <span class="carousel-control-next-icon"></span>
                        </a>
                    </div>
				</div>
				
				<form id="addForm" action="uploadMulti?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data">
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
					
					
					
					<div class="list-group-item form-group mb-1">
						<input name="location" type="hidden" class="location" placeholder="location">
						<div class="form-group row mx-0 mb-0">
							<p id="notice" class="location_name col-10 mt-2">Post를 촬영한 장소를 입력해주세요.</p>
							<span class="input-group-btn col-2">
								<button type="button" id="searchPst" class="btn btn-sm btn-dark" onclick="sample5_execDaumPostcode()">
									<i class="fa-brands fa-sistrix"></i>
								</button>
							</span>
						</div>
						<div id="map" class="col-12" style="display: none;"></div>
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
		</div>
	</div>
</div>

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
    level: 8 // 지도의 확대 레벨
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
	        // 주소로 상세 정보를 검색
	        geocoder.addressSearch(data.address, function(results, status) {
	            // 정상적으로 검색이 완료됐으면
	            if (status === daum.maps.services.Status.OK) {
	            	
	            	$('.location_name').text(data.buildingName);
	            	
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