<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>    
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
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="css/includes/footer.css" />
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

#main{
	height : 80%;
}

#user_ul{
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 15%;
  background-color: #E6E6FA;
  overflow: auto;
  border: solid;
}

#user_ul li{
  display: block;
  color: #000;
  text-decoration: none;
  border-top: solid 1px black;
}

#user_ul li a{
  display: block;
  color: #000;
  text-align : center;
  font-weight : bold;
  text-decoration: none;
}

#user_ul li a:hover{
  background-color: #555;
  color: white;	
}

</style>
</head>
<body>

<%@ include file="../includes/header.jsp" %>

<div id="main" class="container">
	<nav class="navbar navbar-default bg-white">
		<ul id="admin_ul">
		  <li id="admin-li"><a href="admin">DashBoard</a></li>
		  <li id="admin-li"><a class="active" href="u_admin">유저 관리</a></li>
		</ul>
	</nav>	
	
	<!-- 유저 검색 창 -->
	<div id="search-div" class="container px-4 d-none d-md-block mt-5">
		<div class="d-flex align-items-center">
			<div class="flex-grow-1">
				<input id="searchNick" type="text" class="form-control my-3" placeholder="회원 검색">
			</div>
		</div>
		
		<!-- 유저 검색 결과 창 -->
		<a href="#" id="foundUserInfo" class="list-group-item list-group-item-action border-0" style="display:none;">
			<button id="createUser" style="all:unset;" class="float-right"><i class="fa-solid fa-user"></i></button>
			<div class="d-flex align-items-start">	
				<img id="foundUserImg" class="rounded-circle mr-1" width="40" height="40">
				<div id="foundUserNick" class="flex-grow-1 ml-3">
				</div>
			</div>
		</a>
		<!-- 유저 관리 창 -->
		<nav class="navbar navbar-default bg-white" id="user_nav" style="display:none;">
			<ul id="user_ul" style="display">
			  <li id="user-li"><a href="#">회원정보 관리</a></li>
			  <li id="user-li-ban"><a href="#">회원 정지</a></li>
			  <li id="user-li-delete"><a href="#">회원 탈퇴</a></li>  
			</ul>
		</nav>	
	</div>
	
	<!-- 유저 mypage 보일 위치 -->
	<div class="container" id="myPage"></div>
</div>

<!-- 회원탈퇴 모달 창 -->
<div class="container">
	<input id="modalBtn" type="hidden" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal" value="modal">
	<!-- modal창 -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm text-center">
			<div class="modal-content">
				<div class="modal-header bg-light">
					<h4 class="modal-title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;회원탈퇴</h4>
				</div>
				<div class="modal-body bg-light">
					<h4>회원 탈퇴를 진행하시겠습니까?</h4>
				</div> 
				<div class="modal-footer bg-light">
					<button id="yesBtn" type="submit" class="btn btn-danger">Yes</button>
					<button id="noBtn" type="button" class="btn btn-default btn-success" data-dismiss="modal">No</button>
				</div> 
			</div>
		</div>
	</div>
</div>

<!-- 회원 정지 모달 창 -->
<div class="container">
	<input id="modalBtn2" type="hidden" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal2" value="modal2">
	<!-- modal창 -->
	<div class="modal fade" id="myModal2" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm text-center">
			<div class="modal-content">
				<div class="modal-header bg-light">
					<h4 class="modal-title">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;회원정지</h4>
				</div>
				<div class="modal-body bg-light">
					<h4>회원 정지를 진행하시겠습니까?</h4>
				</div> 
				<div class="modal-footer bg-light">
					<button  id="activateBtn" type="submit" class="btn btn-success">활성화</button>
					<button id="disabledBtn" type="submit" class="btn btn-default btn-warning">비활성화</button>
					<button id="closeBtn" type="button" class="btn btn-default btn-danger" data-dismiss="modal">Close</button>
				</div> 
			</div>
		</div>
	</div>
</div>

<script>

// 회원 검색
$("#searchNick").keyup(function(){
	var val = $("#searchNick").val();
	
	$.ajax({
		type : "get",
		url : "searchNick",
		data : {nick:val},
		success : function(data) {
			console.log(data)
			console.log(data);
			if(data.userNick != null) {
				$("#foundUserInfo").css("display","block");
				$("#foundUserImg").attr("src","/init/resources/profileImg/"+data.userProfileImg);
				$("#foundUserNick").html(data.userNick);
				$("#uemail").attr("value", data.userEmail);
			} else {
				$("#foundUserInfo").css("display","none");
			}
		},error : function() {
			alert("에러입니다. 다시 시도해주세요.");
		}
	});
});

//찾은 회원 눌렀을 때 회원관리 창 보이게
$("#foundUserInfo").click(function(){
	$("#user_nav").toggle();
});

//회원 탈퇴 누르면 모달 창 생성
$("#user-li-delete").click(function(){
	$("#modalBtn").trigger("click");
});

//회원 정지 누르면 모달 창 생성
$("#user-li-ban").click(function(){
	$("#modalBtn2").trigger("click");
});

//회원 탈퇴 확인 버튼 클릭 시 
$("#yesBtn").click(function(){
	
	let nick = $("#searchNick").val();
	
	$.ajax({
		type : 'post',
		url : 'deleteUser',
		data : {"nick" : nick},
		beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);    
		},
		success : function(data){
			console.log(data);
			alert('회원 탈퇴에 성공했습니다.');
			$("#noBtn").trigger("click");
		},
		error : function(){
			alert('회원 탈퇴에 실패했습니다.');
		}		
	});
});

//회원 비활성화 버튼 클릭 시
$("#disabledBtn").click(function(){
	
	let nick = $("#searchNick").val();
	
	$.ajax({
		type : 'post',
		url : 'banUser',
		data : {"nick" : nick},
		beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);    
		},
		success : function(data){
			console.log(data);
			alert('회원 계정 비활성화에 성공했습니다.');
			$("#closeBtn").trigger("click");
		},
		error : function(){
			alert('회원 계정 비활성화에 실패했습니다.');
		}		
	});
});

//회원 활성화 버튼 클릭 시
$("#activateBtn").click(function(){

	let nick = $("#searchNick").val();
	
	$.ajax({
		type : 'post',
		url : 'activateUser',
		data : {"nick" : nick},
		beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);    
		},
		success : function(data){
			console.log(data);
			alert('회원 계정 활성화에 성공했습니다.');
			$("#closeBtn").trigger("click");
		},
		error : function(){
			alert('회원 계정 활성화에 실패했습니다.');
		}		
	});
});

//회원 정보관리 클릭 시 mypage 생성
$("#user-li").click(function(){

	let uId = $("#uemail").val();
	
	$.ajax({
		type : 'post',
		url : 'adminMyPage',
		data : {"uId" : uId},
		beforeSend: function(xhr){
		   	var token = $("meta[name='_csrf']").attr('content');
			var header = $("meta[name='_csrf_header']").attr('content');
	        xhr.setRequestHeader(header, token);    
		},
		success : function(data){
			console.log(data);
			$("#myPage").html(data);
		},
		error : function(){
			console.log("error");
		}		
	});
});

</script>		
		
</body>
</html>