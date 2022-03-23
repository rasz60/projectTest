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
 <section class="container mt-6 pt-1" style="margin: 200px, 0;">
        <form id="addForm" action="uploadMulti?${_csrf.parameterName}=${_csrf.token }" method="post"
            enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
            
            <div class="form-group ">
                <label for="userEmail">EMAIL</label>
                <div class="row justify-content-center">
                    <input name="userEmail" type="text" class="userEmail form-control col-sm-8" placeholder="ENTER YOUR EMAIL" required>
                    <button type="button" class="btn btn-outline-primary col-sm-2">CHECK EMAIL</button>
                </div>
                <div class="userEmail-validation text-center">
                </div>
            </div>

            <div class="form-group">
                <label for="pw">PASSWORD</label>
                <input name="pw" type="password" class="pw form-control" placeholder="ENTER YOUR PASSWORD" maxlength="20" required>
                <div class="pw-validation">
                </div>
            </div>

            <div class="form-group">
                <label for="pw2">PASSWORD CHECK</label>
                <input name="pw2" type="password" class="pw2 form-control" placeholder="CHECK YOUR PASSWORD" maxlength="20" required>
                <div class="pw2-validation">
                </div>
            </div>

            <div class="form-group">
                <label for="userNickname ">NICKNAME</label>
                <div class="row justify-content-center">
                    <input name="userNickname" type="text" class="userNickname form-control col-sm-3" placeholder="ENTER YOUR NICKNAME" maxlength="12" required>
                    <button type="button" class="btn btn-outline-primary">CHECK NICKNAME</button>
                </div>
                <div class="userNickname-validation text-center">
                </div>
            </div>


            <div class="form-group">
                <label for="userBirth ">BIRTH</label>
                <div class="row justify-content-center">
                    <input name="userBirth" type="text" class="userBirth form-control col-sm-2" placeholder="BIRTH EX)991212" maxlength="6" required>
                </div>
                <div class="userBirth-validation text-center">
                </div>
            </div>

            <input name="location" type="hidden" class="location" placeholder="location" required>
            <div class="form-group">
                <label for="email">Address</label>
                <input type="text" id="sample5_address" class="form-control" placeholder="Address" readonly>
            </div>
            <input type="button" class="btn btn-outline-primary  btn-block" onclick="sample5_execDaumPostcode()"
                value="Search my location" /><br>
            <div id="map" style="width:300px;height:300px;margin-top:10px;display:block"></div><br />
            <input type="button" class="btn btn-outline-success" onclick="checkfrm()" value="등록" />
            <hr />
        </form>
    </section>
<%@ include file="footer.jsp" %>


<script type="text/javascript">
$(document).ready(function () {
	$('.userEmail').keyup(function () {//아이디 폼검사
        var val = $('.userEmail').val();
        var valvalidationMsg = "";
        const regExp_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{3,6}(?:\.[a-z]{2})?)$/; //이메일 확인 정규식


        if (regExp_email.test(val) == false) {
            valvalidationMsg = "이메일형식이 아닙니다.";
            $('.userEmail-validation').css('color', 'red');
            $('.userEmail-validation').html(valvalidationMsg);

        } else {
            valvalidationMsg = "올바른 형태입니다.";
            $('.userEmail-validation').css('color', 'green');
            $('.userEmail-validation').html(valvalidationMsg);
        }
    });

    $('.pw').keyup(function () { //1차 비밀번호 폼검사
        var val = $('.pw').val();
        var valvalidationMsg = "";
        const regExp_special_number = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g;  //특수문자 체크
        const regExp_number = /[0-9]/g;


        if (val.length < 10 || val.length > 21) { //비밀번호 10자~21자 체크          
            valvalidationMsg = "영문자,숫자,특수문자를 혼합하여 10자 이상 20자 이하로 해주세요";
            $('.pw-validation').css('color', 'red');
            $('.pw-validation').html(valvalidationMsg);

        } else if (regExp_number.test(val) == false) {//숫자 포함 체크                                    
            valvalidationMsg = "숫자가 포함되어야합니다.";
            $('.pw-validation').css('color', 'red');
            $('.pw-validation').html(valvalidationMsg);

        } else if (regExp_special_number.test(val) == false) { //특수문자 체크
            valvalidationMsg = "특수문자가 포함되어야합니다.";
            $('.pw-validation').css('color', 'red');
            $('.pw-validation').html(valvalidationMsg);
        } else {
            valvalidationMsg = "올바른 형태입니다.";
            $('.pw-validation').css('color', 'green');
            $('.pw-validation').html(valvalidationMsg);
        }
    });


    $('.pw2').keyup(function () {//2차 비밀번호 폼검사
        var val = $('.pw').val();
        var checkVal = $('.pw2').val();

        if (val != checkVal) {
            valvalidationMsg = "비밀번호가 일치하지 않습니다.";
            $('.pw2-validation').css('color', 'red');
            $('.pw2-validation').html(valvalidationMsg);
        } else {
            valvalidationMsg = "비밀번호가 일치합니다.";
            $('.pw2-validation').css('color', 'green');
            $('.pw2-validation').html(valvalidationMsg);
        }
    });

    $('.userNickname').keyup(function () {//2차 비밀번호 폼검사
        var val = $('.userNickname').val();

        if (val.length < 4 || 13 < val.length) {
            valvalidationMsg = "Nickname은 4자이상 12자이하로 해주세요.";
            $('.userNickname-validation').css('color', 'red');
            $('.userNickname-validation').html(valvalidationMsg);
        } else {
            valvalidationMsg = "올바른 Nickname입니다.";
            $('.userNickname-validation').css('color', 'green');
            $('.userNickname-validation').html(valvalidationMsg);
        }
    });

    $('.userBirth').keyup(function () {//2차 비밀번호 폼검사
        var val = $('.userBirth').val();
        const regExp_num = /^[0-9]+$/;
        var year = val.substr(0, 2);
        var month = val.substr(2, 2);
        var day = val.substr(4, 2);

        if(val.length<6){
            valvalidationMsg = "6자 이상 작성해주세요. EX)960430";
            $('.userBirth-validation').css('color', 'red');
            $('.userBirth-validation').html(valvalidationMsg);
        }else if (regExp_num.test(val)==false) {
            valvalidationMsg = "숫자만 입력가능합니다. 1999년 04월 30일생 EX)960430";
            $('.userBirth-validation').css('color', 'red');
            $('.userBirth-validation').html(valvalidationMsg);
        } else if(month>12){
            valvalidationMsg = "12월까지 밖에 없어요";
            $('.userBirth-validation').css('color', 'red');
            $('.userBirth-validation').html(valvalidationMsg);
        } else if(day>31){
            valvalidationMsg = "31일까지 밖에 없어요";
            $('.userBirth-validation').css('color', 'red');
            $('.userBirth-validation').html(valvalidationMsg);
        }else {
            if(22<=year &&year<=99){
                year='19'+year;
            }else{
                year='20'+year;
            }
            valvalidationMsg = year+'년 '+month+'월 '+day+'일입니다.';
            $('.userBirth-validation').css('color', 'green');
            $('.userBirth-validation').html(valvalidationMsg);
        }
    });
});

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
    center: new daum.maps.LatLng(37.575869, 126.976859), // 지도의 중심좌표
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
        $('.location').val(data.address);
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