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
<script>
$(document).ready(function() {
	function allComments() {
		$.ajax({
			url: 'home/allComments.do',
			type: 'post',
		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
			        xhr.setRequestHeader(header, token);
			},
			success: function(data) {
				for ( var i = 0; i < data.length; i++ ) {
					let box = '<input type="text" class="form-control comment" value="' + data[i].comments + '" readonly />'
							+ '<form action="home/recomment.do" method="post" name="refrm" class="refrm">'
							+ '<input type="text" class="form-control col-10" name="commentNum" value="' + data[i].commentNum + '"/>'
							+ '<input type="text" class="form-control col-10" name="mNum" value="' + data[i].mNum + '"/>'
							+ '<input type="text" class="form-control col-10" name="gNum" value="' + data[i].gNum + '"/>'
							+ '<input type="text" class="form-control col-10" name="inum" value="' + data[i].iNum + '"/>'
							+ '<div class="form-group row mx-0">'
							+ '<input type="text" class="form-control col-10" id="recomment-input" name="comments"/>'
							+ '<button type="submit" class="btn btn-sm btn-primary" >댓글</button>'
							+ '</div>'
							+ '</form>';
					$('#commentary').append(box);
					
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	};
	
	allComments();
	
})



</script>

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

</div>










<script>
$(function() {
	
	$('#m-comment').click(function(e) {
			e.preventDefault();
			
			let comment = $('#comment-input').val();
			
			if ( comment == null ) {
				alert('null exception');
				return false;
			};
			
			let data = {
					commentNum : 0,
					comments : comment,
					mNum : 0,
					gNum : 0,
					iNum : 0
			};
		
			$.ajax({
				url: $('form').attr('action'),
				type: $('form').attr('method'),
				data: JSON.stringify(data),
				contentType: 'application/json; charset-utf-8',
	 		    beforeSend: function(xhr){
		 		   	var token = $("meta[name='_csrf']").attr('content');
		 			var header = $("meta[name='_csrf_header']").attr('content');
	 		        xhr.setRequestHeader(header, token);
	 		    },
				success: function(data) {
					if( data == 'success' ) {
						allComments();
					}
				},
				error: function(data) {
					console.log(data);
				}
			});
			
		});
		
	});
	
	
	$('.refrm').submit(function(e){
		e.preventDefault();
		$.ajax({
			url: $(this).attr('action'),
			type: $(this).attr('method'),
			success: function(data) {
				if( data == 'success' ) {
					allComments();
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	});
</script>



</body>
</html>
