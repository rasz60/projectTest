<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<!-- KAKAO API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- css -->
<link rel="stylesheet" type="text/css" href="../css/header.css" />
<link rel="stylesheet" type="text/css" href="../css/footer.css" />
<title>Insert title here</title>
<style>
#form-title {
	font-size: 40px;
	font-weight: 400;
}


.validation {
	height:8px;
	font-size:14px; 
}

.conditions {
	height: 300px;
	overflow: auto;
}

span.required {
	color: orange;
	font-weight: 400;
}

label#male.active, label#male:hover, label#male:focus {
	background-color: #4E77FF;
	color: white;

}

label#female.active, label#female:hover, label#female:focus {
	background-color: #FF5C5C;
	color: white
}

.form-group label {
	font-weight: 500;
}

.form-group .form-control {
	border: none;
	border-radius: 0;
	border-bottom: 1px solid #ced4da;
}

input[id^=userAddr] {
	border: none;
	border-radius: 0;
	width: 80%;
	border-bottom: 1px solid #ced4da;
}

.form-group .form-control[readonly=readonly] {
	background-color: #F4F4F4;
}

#userinfo {
	display: none;
}

</style>

</head>
<body>
<%@ include file="../header.jsp" %>

<div class="container" style="margin-top:90px; margin-bottom:50px;">
	<!-- 회원가입 성공시 모달 -->
	<!-- modal button -->
	<input id="modalBtn" type="hidden" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" value="modal">
	<!-- modal창 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm text-center">
			<div class="modal-content">
				<div class="modal-header bg-light">
					<h4 class="modal-title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;JOIN</h4>
				</div>
				<div class="modal-body bg-light">
					<h4>WELCOME!</h4>
				</div>
				<div class="modal-footer bg-light">
					<button id="closeBtn" type="button" class="btn btn-default btn-success" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 회원 가입 창 -->	
	<h1 class="text-center mb-4 display-4">WAYG</h1>
	
	
	<form id="joinForm" action="join" method="post" name="joinForm">
		<div id="agreements" class="mb-5">
			<div class="conditions border px-2">
				<h5><strong>WAYG 개인정보 수집 동의 요청서</strong></h5><br/>
				&nbsp;1. 위치를 기반으로 한 서비스 사용시 현재 위치를 설정하여 원하는 데이터를 제공하기 위한 목적으로 회원님의 현재 실시간 위치 정보를 요청할 수 있으며,
				  이는 위에 언급된 기능 이외에 다른 목적으로는 사용되지 않습니다.<br/><br/>
				&nbsp;2. 타 사이트 계정 사용한 소셜 로그인시 WAYG 서비스 이용을 위한 추가 정보를 별도로 기입하여야 합니다.<br/><br/>
				&nbsp;3. 
			</div>
			
			<div class="checkBox d-flex justify-content-end">
				<p class="font-italic mt-3 mr-5">WAYG에서 요청하는 개인정보 수집에 동의하시겠습니까?</p>
				<div class="form-check-inline">
					<input type="radio" name="agreements" class="form-check-input" value="agree" id="agree" />
					<label class="form-check-label" for="flexCheckChecked">
						동의
					</label>
				</div>
				
				<div class="form-check-inline">
					<input type="radio" name="agreements" class="form-check-input" value="disagree" id="disagree" checked/>
					<label class="form-check-label" for="flexCheckChecked">
						비동의
					</label>
				</div>
			</div>
		</div>
		<div id="userinfo">
		    <input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		
			<h1 class="mb-4 d-inline display-4" id="form-title">USER INFO</h1><p class="ml-2 d-inline font-italic text-secondary"><span class="required">*</span>로 표시된 부분은 필수 입력 사항입니다.</p>
			
			<hr />
		
			<div class="form-group row mx-0">
				<label for="userEmail" class="mt-2 col-2 border-right">E-mail <span class="required">*</span></label>
				<div class="col-10">
					<input type="text" class="userEmail form-control" id="userEmail" name="uEmail" placeholder="EMAIL" onkeyup="checkEmail()" autocomplete="off" maxlength="30">
					<div class="userEmail-validation validation mt-1">
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="form-group row mx-0">
				<label for="userPw1" class="mt-2 col-2 border-right">Password <span class="required">*</span></label>
				<div class="col-10">
					<input type="password" class="userPw1 form-control" id="userPw1" name="uPw1" placeholder="PASSWORD" maxlength="16">
					<div class="userPw1-validation validation">
					</div>
				</div>
			</div>	
			
			<div class="form-group row mx-0">
				<label for="userPw2" class="mt-2 col-2"></label>
				<div class="col-10">
					<input type="password" class="userPw2 form-control" id="userPw2" name="uPw2" placeholder="CONFIRRM PASSWORD" maxlength="16" readonly="readonly">
					<div class="userPw2-validation validation">
					</div>
				</div>
			</div>	
			
			<hr />
			
			<div class="form-group row mx-0">
				<label for="userNickName" class="mt-2 col-2 border-right">Nickname <span class="required">*</span></label>
				<div class="col-10">
					<input type="text" class="userNickName form-control" id="userNickName" name="uNickName" placeholder="NICKNAME" onkeyup="checkNickName()" autocomplete="off" maxlength="20">
	  				<div class="userNickName-validation validation">
		  			</div>		
		  		</div>
			</div>	
			
			<hr />
			
			<div class="form-group row mx-0">
				<label for="userBirth" class="mt-2 col-2 border-right">Birth <span class="required">*</span></label>
				<div class="col-10">
					<input type="text" class="userBirth form-control" id="userBirth" name="uBirth" placeholder="EX:19970501" oninput="this.value = this.value.replace(/[^0-9]/g, '').replace(/(\..*)\./g, '$1');" autocomplete="off" maxlength="8">
					<div class="userBirth-validation validation">
					</div>
				</div>
			</div>
			
			<hr />
			
			<div class="form-group row mx-0">
				<label for="btn-group" class="mt-2 col-2 border-right">Gender <span class="required">*</span></label>
				
				<div class="btn-group btn-group-toggle col-3 row mx-0" data-toggle="buttons">
					<label id="male" class="btn btn-outline-defalut border-white active">
						<input type="radio" name="uGender" autocomplete="off" value="MALE" checked><i class="fa-solid fa-mars"></i>
					</label>
					<label id="female" class="btn btn-outline-defalut border-white ">
						<input type="radio" name="uGender" autocomplete="off" value="FEMALE"><i class="fa-solid fa-venus"></i>
					</label>
				</div>
			</div>	
			
			<hr />
			
			<div class="form-group row mx-0">
				<label for="userAddr1" class="mt-2 col-2 border-right">Post-code <span class="required">*</span></label>
					<div class="form-inline mb-2 ml-2">
						<div class="input-group">
							<input type="text" class="form-control bg-white mr-2" id="userPst" name="uPst" placeholder="POSTCODE" onfocus="clickSerachPst()" readonly>
							<span class="input-group-btn">
								<button type="button" id="searchPst" class="btn btn-sm btn-dark" onclick="serchPostCode()"><i class="fa-brands fa-sistrix"></i></button>
							</span>
						</div>
					</div>
				<span id="guide" style="color:#999;display:none"></span>
			</div>
			
			<div class="form-group row mx-0">
				<label for="userAddr1" class="mt-2 col-2 border-right">Address <span class="required">*</span></label>
				<input type="text" class="form-control bg-white ml-2" id="userAddr1" name="uAddr1" placeholder="ADDRESS1" onfocus="clickSerachPst()" readonly>
			</div>
				
			<div class="form-group row mx-0 pb-3">
				<label for="userAddr2" class="mt-2 col-2 border-right">ADDRESS2</label>
				<input type="text" class="form-control ml-2" id="userAddr2" name="uAddr2" placeholder="ADDRESS2" maxlength="50">
			</div>		
		
			<hr />
		
			<div class="d-flex justify-content-end">
				<button type="submit" class="btn btn-primary border-white">가입</button>
				<button type="button" id="goback" class="btn btn-danger border-white">취소</button>
			</div>
		</div>
	</form>
</div>
    
<%@ include file="../footer.jsp" %>

<script type="text/javascript">

$('input[name=agreements]').change(function() {
	if( $('input[name=agreements]:checked').val() == "disagree" ) {
		$('#userinfo').css('display', 'none');
	} else {
		$('#userinfo').css('display', 'block');
		// scroll을 부드럽게 이동시키는 메서드
		window.scrollTo({top: 450, left: 0, behavior:'smooth'});
		$('#userEmail').focus();
	}
});




//유효성 검사 통과유무 변수
var chkEmail = false;
var chkPw1 = false;
var chkPw2 = false;
var chkNick = false;
var chkBir = false;
var chkPst = false;

$(document).ready(function () {
 	//1차 비밀번호 유효성검사
    $('.userPw1').keyup(function () {
    	$('#userPw2').val(''); //1차 pw input keyup시 2차 pw 입력값 초기화
    	$('.userPw2-validation').html(''); //1차 pw input keyup시 2차 pw validation 메세지 초기화
        var val = $('.userPw1').val();
        const regExp_pw = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&^])[A-Za-z\d$@$!%*#?&^]{8,}$/;

        if (regExp_pw.test(val) == false) {          
            $('.userPw1-validation').css('color', 'blue');
            $('.userPw1-validation').html('영문,숫자,특수문자를 혼합하여 8자 이상 16자 이하로 입력해주세요.');
            $('#userPw2').attr('readonly',true); //1차 pw 형식 안맞을시 2차 pw readonly true(입력불가)
            chkPw1 = false;
        } else {
            $('.userPw1-validation').css('color', 'green');
            $('.userPw1-validation').html('사용가능한 비밀번호 입니다.');
            $('#userPw2').attr('readonly',false); //1차 pw 형식 맞으면 2차 pw readonly false(입력가능)
            chkPw1 = true;
        }
    });
    
    //2차 비밀번호 유효성검사
    $('.userPw2').keyup(function () {
        var val = $('.userPw1').val();
        var checkVal = $('.userPw2').val();
        if (checkVal!=''){ //2차 pw 입력값 없을땐 validation 메세지 아무것도 안뜸
			if (val != checkVal) {
            	$('.userPw2-validation').css('color', 'blue');
            	$('.userPw2-validation').html('동일한 비밀번호를 입력해주세요.');
            	chkPw2 = false;
        	} else {
            	$('.userPw2-validation').css('color', 'green');
            	$('.userPw2-validation').html('비밀번호가 일치합니다.');
            	chkPw2 = true;
       		}
        }
    });
    
    //생년월일 유효성검사
    $('.userBirth').keyup(function() {
    	var val = $('.userBirth').val();
    	const regExp_birth = /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/;
    	
    	if(regExp_birth.test(val) == false) {
    		$('.userBirth-validation').css('color','blue');
    		$('.userBirth-validation').html('정확한 생년월일을 입력해주세요. EX:19970501');
    		chkBir = false;
    	} else {
    		$('.userBirth-validation').css('color','green');
    		$('.userBirth-validation').html(val.substr(0,4)+'년 '+val.substr(4,2)+'월 '+val.substr(6)+'일');
    		chkBir = true;
    	}
    });
     
    //회원가입
    $("#joinForm").submit(function(event){
    	event.preventDefault();
    	//유효성검사 통과해야만 submit되게 함
    	if(!chkEmail) { 
    		$('#userEmail').keyup().focus();
    	} else if(!chkPw1) {
    		$('#userPw1').keyup().focus();
    	} else if(!chkPw2) {
    		$('#userPw2').keyup().focus();
    	} else if(!chkNick) {
    		$('#userNickName').keyup().focus();
    	} else if(!chkBir) {
    		$('#userBirth').keyup().focus();
    	} else if(!chkPst) {
    		$('.userPst-validation').css('color', 'blue');
    		$('.userPst-validation').html('주소를 입력해주세요.');
    	} else {
    	$.ajax({
    		type : $("#joinForm").attr("method"),
    		url : $("#joinForm").attr("action"),
    		data : $("#joinForm").serialize(),
    		success : function(data) {
    			if(data.search("join-success") > -1) {
    			$("#modalBtn").trigger("click");
    			$("#closeBtn").click(function(event) {
    				event.preventDefault();
    				location.href="../";
    			});
    			}
    			else {
    				$(".modal-body").text("다시 시도해 주세요."); //insert실패?
    				$("#modalBtn").trigger("click");
    			}
    		},
    		error : function(data) {
    			$(".modal-body").text("전송 실패입니다. 다시 시도해 주세요");
    			$("#modalBtn").trigger("click");
    		}
    	});
    	}
    });
    
    $('#goback').click(function() {
    	$("#modalBtn").trigger("click");
    	$(".modal-body").text("메인 페이지로 돌아갈까요?");
    	
    });

    
});

//email 유효성검사 및 중복 체크
function checkEmail() {
	var val = $('.userEmail').val();
    const regExp_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{3,6}(?:\.[a-z]{2})?)$/; //이메일 확인 정규식
	
    
    if (regExp_email.test(val) == false) {
        $('.userEmail-validation').css('color', 'blue');
        $('.userEmail-validation').html('본인의 이메일을 입력해주세요.');
        chkEmail = false;

    } else {
        
    	$.ajax({
			type : 'get',
			url : 'emailCheck',
			data : {id:val},
			success:function(res){
				console.log("emailCheck");
				if(res != 1) {
					$('.userEmail-validation').css('color', 'green');
					$('.userEmail-validation').html("사용 가능한 이메일 입니다.");
					chkEmail = true;
				} else{
					$('.userEmail-validation').css('color', 'red');
					$('.userEmail-validation').html("누군가 사용중인 이메일 입니다.");
					chkEmail = false;
				}
			},
			error:function(){
				alert("에러입니다.");
			}
		
		});
	}
}

//nickname 유효성검사 및 중복 체크
function checkNickName() {
	var val = $('.userNickName').val();
	const regExp_nick = /^[가-힣|a-z|A-Z|0-9|]{2,20}$/; //닉네임 한글,영문,숫자 2자이상 20자이하 정규식
	const regExp_onlynum = /^[0-9]*$/; //숫자만 입력하는 정규식
	
	if(regExp_onlynum.test(val) == false) { //숫자만으로 이루어지지 않았을때
		if(regExp_nick.test(val) == false) {
        	$('.userNickName-validation').css('color', 'blue');
        	$('.userNickName-validation').html('2자이상 20자이하로 입력해주세요.(영문,한글,숫자 사용가능. 숫자만X)');
			chkNick = false;
		} else {
			$.ajax({
				type : 'get',
				url : 'nickCheck',
				data : {nick:val},
				success : function(res){
					console.log('nickCheck');
					if(res != 1) {
			    		$('.userNickName-validation').css('color', 'green');
			    		$('.userNickName-validation').html('사용가능한 닉네임 입니다.');
			    		chkNick = true;
					} else {
						$('.userNickName-validation').css('color', 'red');
			    		$('.userNickName-validation').html('누군가 사용중인 닉네임 입니다.');
						chkNick = false;
					}
				},
				error:function(){
					alert("에러입니다.");
				}
			});
		}
		} else { //숫자만으로 이루어진 경우
        	$('.userNickName-validation').css('color', 'blue');
        	$('.userNickName-validation').html('2자이상 20자이하로 입력해주세요.(영문,한글,숫자 사용가능. 숫자만X)');
        	chkNick = false;
		}
	}

//====== 주소 입력 ======
function clickSerachPst() { //POSTCODE input, ADDRESS1 input 포커스시 serchPst 클릭
	document.getElementById("searchPst").click();
	document.getElementById("userPst").blur();
	document.getElementById("userAddr1").blur();
}

//카카오 api
function serchPostCode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('userPst').value = data.zonecode;
            //document.getElementById("userAddr1").value = roadAddr; // roadAddr + extraRoadAddr(ex:(공항동))로 표시하기 위해 밑으로 내림
            //document.getElementById("sample4_jibunAddress").value = data.jibunAddress; //지번주소 입력 안받음
            
            // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
            if(roadAddr !== ''){
                //document.getElementById("sample4_extraAddress").value = extraRoadAddr;
                document.getElementById('userAddr1').value = roadAddr + extraRoadAddr;
            } else {
                //document.getElementById("sample4_extraAddress").value = '';
                document.getElementById('userAddr1').value = roadAddr + '';
            }
            
			var guideTextBox = document.getElementById("guide");
            // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
            if(data.autoRoadAddress) {
                var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                guideTextBox.style.display = 'block';

           /*  } else if(data.autoJibunAddress) {
                var expJibunAddr = data.autoJibunAddress;
                guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                guideTextBox.style.display = 'block'; */
                //지번주소 안받음
            } else {
                guideTextBox.innerHTML = '';
                guideTextBox.style.display = 'none';
            }
            document.getElementById("userAddr2").focus(); // userAddr1입력 후 포커스
            $('.userPst-validation').html('');
            chkPst = true;
        }
    }).open();
}

</script>
</body>
</html>