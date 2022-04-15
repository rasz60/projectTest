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
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<script src="https://kit.fontawesome.com/b4e02812b5.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" type="text/css" href="/init/css/includes/header.css" />
<link rel="stylesheet" type="text/css" href="/init/css/main.css" />
<link rel="stylesheet" type="text/css" href="/init/css/includes/footer.css" />
<title>WAYG</title>
<style>
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#main {
	max-width: 100%;
	height: 100%;
}


</style>

</head>

<body>
<%@ include file="includes/roader.jsp" %>
<%@ include file="includes/header.jsp" %>

<section class="container" id="main">
	
</section>
<%@ include file="includes/login_modal.jsp" %>
<%@ include file="includes/modalPost.jsp" %>
<%@ include file="includes/footer.jsp" %>
<script src="js/pdfobject.js"></script>
<script>
/* 
 * pdfobject.js 스크립트에 정의된 메서드
 * PDFObject.embed("pdf파일 경로[문자열]", "pdf 내용을 넣을 엘리먼트 선택자[문자열]");
 * 
 */
PDFObject.embed("/init/resources/pdf/WAYG.pdf", "#main");
</script>

</body>
</html>