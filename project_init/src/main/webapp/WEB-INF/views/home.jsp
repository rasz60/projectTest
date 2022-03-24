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
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/main/main.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />
<title>Insert title here</title>
</head>



<body>

<div class="container bg-secondary">
<h3 class="display-4 font-italic">Post</h3>
</div>

<div class="container" id="commentary">
	<form action="home/comment.do" method="post" name="frm">
		<div class="form-group row mx-0">
			<label for="comment-input" class="col-1 mt-2">댓글 : </label>
			<input type="text" class="form-control col-10" id="comment-input" name="comments"/>

			<button type="button" id="m-comment" class="btn btn-sm btn-primary col-1">댓글</button>	
		</div>
	</form>
	
	<!-- 댓글이 뿌려지는 곳 -->
	<div class="form-group row mx-0">
		<label for="comment-input" class="col-1 mt-2">댓글 : </label>
		<input type="hidden" class="mnum" value="${comments.no }"/>
		<input type="hidden" class="inum" value="${comments.mnum }"/>
		<input type="hidden" class="inum" value="${comments.gnum }"/>
		<input type="hidden" class="mnum" value="${comments.inum }"/>
		<input type="text" class="form-control col-10" id="comment-input" name="comments"/>
		<button type="button" id="c-comment" class="btn btn-sm btn-primary col-1" 
		data-no=${comments.no } data-mnum=${comments.mnum } data-gnum=${comments.gnum } data-inum=${comments.inum } >댓글</button>
	</div>
	
	
	<div class="form-group row mx-0">
		<label for="comment-input" class="col-1 mt-2">댓글 : </label>
		<input type="text" class="form-control col-10" id="comment-input" name="comments"/>
		<button type="button" id="m-comment" class="btn btn-sm btn-primary col-1" data-indent=0 >댓글</button>
	</div>
	
</div>

</body>


<script>

$(document).ready(function() {
	
	let m_index = 0;
	$('#m-comment').click(function(e) {
		e.preventDefault();

		let indentNum = $(this).attr("data-value");
		let comment = $('#comment-input').val();
		let createBox = $('.form-group').html();
		
		
		let data = 'iNum=' + indentNum + "&" + $('form').serialize();
		
		console.log(data);
		
		$.ajax({
			url: $('form').attr('action'),
			type: $('form').attr('method'),
			data: data,
 		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
			success: function(data) {
				console.log(data);
			},
			error: function(data) {
				console.log(data);
			}
		});
		
		m_index++;		
	})
	
})



</script>
</html>
