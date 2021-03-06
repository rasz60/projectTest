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

<link rel="stylesheet" type="text/css" href="../css/header.css" />
<link rel="stylesheet" type="text/css" href="../css/search/main.css" />
<link rel="stylesheet" type="text/css" href="../css/footer.css" />
<title>List</title>
</head>
<body>
<%@ include file="../header.jsp" %>
 <br/><br/>
 <section class="container mt-6 pt-1" style="margin: 200px, 0;">
	<form id="addForm" action="uploadMulti?${_csrf.parameterName}=${_csrf.token }" method="post" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
		<input type="hidden" name="email" value="${user}"/>		
		<div class="form-group">
			<h2>CONTENT</h2>
			<textarea class="form-control col-sm-5 content" name="content" rows="10" cols="20" placeholder="content" required></textarea>
		</div>
		
		<div class="form-group">
			<label for="hashtag">#HASHTAG</label>
			<input name="hashtag" type="text" class="title form-control" placeholder="#HASHTAG" required>
		</div>
		
		<input name="location" type="hidden" class="location" placeholder="location" required>
		<div class="input-group mb-3">
			<div class="custom-file">
				<input name="img" type="file" class="img custom-file-input" placeholder="img" id="inputGroupFile01" multiple="multiple" required>
				<label class="custom-file-label" for="inputGroupFile01">Choose file</label>
			</div>
		</div>

		<div class="form-group">
			<label for="email">Address</label>
		<input type="text" id="sample5_address" class="form-control" placeholder="Address" readonly>
		</div>
		<input type="button" class="btn btn-outline-primary  btn-block"  onclick="sample5_execDaumPostcode()" value="Search my location"/><br>
		<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div><br/>
        <input type="button" class="btn btn-outline-success" onclick="checkfrm()" value="??????"/><hr/>
	</form>
</section>
<%@ include file="../footer.jsp" %>

<script type="text/javascript">


function checkfrm() { 
	if($('.location').val()==""){
		alert('????????? ????????????????????? ??????');
		return false;
	}else{
	$('#addForm').submit();
	}
}
    
var mapContainer = document.getElementById('map'), // ????????? ????????? div
mapOption = {
    center: new daum.maps.LatLng(37.537187, 127.005476), // ????????? ????????????
    level: 5 // ????????? ?????? ??????
};

//????????? ?????? ??????
var map = new daum.maps.Map(mapContainer, mapOption);

//??????-?????? ?????? ????????? ??????
var geocoder = new daum.maps.services.Geocoder();
	
//????????? ?????? ??????
var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
});


function sample5_execDaumPostcode() {
new daum.Postcode({
    oncomplete: function(data) {
        var addr = data.address; // ?????? ?????? ??????
        $('.location').val(data.sido+" "+data.sigungu);
        // ?????? ????????? ?????? ????????? ?????????.
        document.getElementById("sample5_address").value = addr;
        // ????????? ?????? ????????? ??????
        geocoder.addressSearch(data.address, function(results, status) {
            // ??????????????? ????????? ???????????????
            if (status === daum.maps.services.Status.OK) {
                var result = results[0]; //????????? ????????? ?????? ??????
                // ?????? ????????? ?????? ????????? ?????????
                var coords = new daum.maps.LatLng(result.y, result.x);
                // ????????? ????????????.
                mapContainer.style.display = "block";
                map.relayout();
                // ?????? ????????? ????????????.
                map.setCenter(coords);
                // ????????? ??????????????? ?????? ????????? ?????????.
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