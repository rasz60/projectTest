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
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/> <!-- 페이지위조요청을 방지를 위한 태그 -->
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
<title>로그인 창</title>
</head>
<body>

<div class="container mt-5">
	<h3 class="text-center text-info">로그인</h3>
	<div id="div1" class="text-success"></div> <!-- 로그인 관련 메세지를 서버에서 받아 작성 -->
	<form id="frm1" name="frm1" method="post" action="login"> 
		<!-- security에서 action은 login으로 기본 설정이고 login은 controller의 login처리부로 요청하는게 아니라
		스프링 security로 login처리 요청-->
		<!-- csrf를 방지하기 위한 헤더 부분을 숨겨서 추가 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-group">
			<label for="uId">아이디</label>
			<input type="email" class="form-control" name="pid" placeholder="E-Mail주소 입력" 
				id="uId" required /> 
		</div>
		<div class="form-group">
			<label for="uPw">비밀번호</label>
			<input type="password" class="form-control" name="ppw" placeholder="비밀번호 입력" 
				id="uPw" required /> 
		</div>
		<!-- 로그아웃이후 일정시간 로그인 없이 재접속 -->
		<div class="form-group form-check">
			<input type="checkbox" class="form-check-input" id="rememberMe"
				name="remember-me" checked>
			<label class="form-check-label" for="rememberMe"
				aria-describedby="rememberMeHelp">remember-me</label>
				<!-- aria-describedby는 스크린리더에서 설명을 할 내용 -->
		</div>
		<button type="submit" class="btn btn-success">로그인</button>&nbsp;&nbsp;
		<a href="join_view" class="btn btn-danger">회원가입</a> 
	</form>
</div>

<script>
//서버에서 컴파일 순서는 java - jstl - html - javascript
//js에서는 jstl을 사용 할 수 있다.
$(document).ready(function(){
	<c:choose>
		<c:when test="${not empty log}"> //model의 log속성이 null이 아니면 
			$("#div1").text("Welcome!"); 
		</c:when>
		<c:when test="${not empty logout}"> //logout성공시
			$("#div1").text("Logout 성공");
		</c:when>
		<c:when test="${not empty error}"> //로그인 실패시
			$("#div1").text("로그인 실패");
		</c:when>
		<c:otherwise>
			$("#div1").text("welcome"); //login성공시
		</c:otherwise>
	</c:choose>
});
</script>
</body>
</html>