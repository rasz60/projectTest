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
<link rel="stylesheet" type="text/css" href="css/header.css" />
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/footer.css" />
<link rel="stylesheet" type="text/css" href="css/search/main.css" />
<link rel="stylesheet" type="text/css" href="css/search/modalPost.css" />
<title>List</title>
<style>
ul{
   list-style:none;
   }
</style>


</head>
<body>
<%@ include file="header.jsp" %>

<section class="container mb-4">
	<div class="result_posts">
		<div class="posts d-flex flex-wrap justify-content-start mt-2">
			<c:forEach items="${list}" var="list" >
				<div class="post mr-2">
					<div class="post-top border rounded">
						<img class="titleimg" width="280px" src="images/${list.titleImage}" data-value="${list.postNo}" data-toggle="modal" data-target="#modal-reg">
					</div>
					<div class="post-bottom border text-center">
						<h5>${list.location}</h5>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>

<!--  modal And Caouosel -->
<div class="modal fade" id="modal-reg">
    <div class="modal-dialog modal-dialog-centered modal-xl">
        <div class="modal-content">
            <div class="modal-body bg-light d-flex justify-content-between">
                    <div class="post-img border rounded mr-2"><i class="modal-icon fa-regular fa-images" /></i>
                        <div id="demo" class="carousel slide" data-ride="carousel">
                            <!-- The slideshow -->
                            <div class="carousel-inner Citem">
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
                    <ul class="list-group d-block">
                        <li class="list-group-item mb-1"><i class="modal-icon fa-regular fa-circle-user"></i>
                        <ul class="userNickname">
                        	
                        </ul>
                        </li>
                        <li class="list-group-item mb-1"><i class="modal-icon fa-regular fa-rectangle-list"></i>
						<ul class="postContent">
                        	
                        </ul>
						</li>
                        <li class="list-group-item mb-1 d-flex row mx-0">
                            <div class="col-6"><i class="modal-icon fa-regular fa-heart"></i>fa-heart</div>
                            <div class="col-3"><i class="modal-icon fa-regular fa-bookmark"></i>fa-bookmark</div>
                            <div class="col-3"><i class="modal-icon fa-regular fa-comment-dots"></i>fa-comment-dots</div>
                        </li>
                        <li class="list-group-item comments"><i class="modal-icon fa-regular fa-comment-dots"></i>comment
								<p>첫 번째 댓글입니다.<p>
                        	<ul class="reply">
                        		<li><p >첫 번째 대 댓글입니다.</p></li>
                        		<li><p >두 번째 대 댓글입니다.</p></li>
                        		<li><p >세 번째 대 댓글입니다.</p></li>
                        	</ul>
                        	<p>두 번째 댓글입니다.</p>
                        </li>
                        <li>
                        <div class="row">
                        <input type="text" class="col-sm-10 comment">
                        <button type="button" class="btn btn-outline-success addcomment" role="button">전송</button>
                        </div>
                        </li>
                        
                    </ul>
                </div>
            <!-- Modal footer -->
        	<div class="modal-footer">
        		<a class="btn btn-outline-success modify" href="#" role="button">Modify</a>
                <a class="btn btn-outline-danger delete" href="#" role="button" onclick="deleteCheck()">Delete</a>
        	</div>
        </div>
    </div>
</div>


<%@ include file="footer.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	let postNo = "";
	$(".titleimg").click(function(){
        postNo = $(this).attr("data-value");
        let comments ="";
    
        $.ajax({
            url:"getlist.do",
            data:{postNo:postNo},
            type:"post",
 		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
            success:function(data){
	           	var Cslide = "";
                var Citem = "";
                var userNickname="";
                var postContent="";

            	let email = data[0].email;
            	let title = data[0].title;
            	let content = data[0].content;
            	let location = data[0].location;
            	let titleImage = data[0].titleImage;
            	let images = data[0].images.split('/');
            	let postNo = data[0].postNo;
            	
            	for(var i=0; i<images.length-1; i++){
                    if(i==0){
                       Citem+='<div class="carousel-item active"><img src="images/'+images[i]+'"></div>';
                    }else{
                       Citem+='<div class="carousel-item"><img src="images/'+images[i]+'"></div>';
                    }
                }
               	
            	userNickname ='<li>'+email+'</li>';
            	postContent ='<li>'+content+'</li>';
            	
				//내용 넣는 프로세스
                $(".Citem").html(Citem);
                $(".userNickname").html(userNickname);
                $(".postContent").html(postContent);
                
                $(".modify").attr('href','modify?postNo='+postNo);
                $(".delete").attr('href','delete.do?postNo='+postNo);
            },
            error:function(data){
                console.log("ajax 처리 실패");
            }
        });
        
        $.ajax({
            url:"getcomments.do",
            data:{postNo:postNo},
            type:"post",
 		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
            success:function(data){
	           	console.log(data);
	           	for(var i=0; i<data.length; i++){
					comments += '<span>'+data[i].content+'</span>'+'&nbsp<a href="#">댓글달기</a><br/>';
				}
				$('.comments').html(comments);
            },
            error:function(data){
                console.log("ajax 처리 실패");
            }
        });
        
    });

	$('.addcomment').click(function () {
		
		let content = $('.comment').val();
		let comments ="";
		$.ajax({
			url : 'addcomments.do',
			type : 'post',
			data : {postNo : postNo,
					content : content},
			beforeSend: function(xhr){
		 	  	var token = $("meta[name='_csrf']").attr('content');
		 		var header = $("meta[name='_csrf_header']").attr('content');
	 		    xhr.setRequestHeader(header, token);
	 		},
	 		success : function (data) {
	 			console.log('success');
	 	        $.ajax({
	 	            url:"getcomments.do",
	 	            data:{postNo:postNo},
	 	            type:"post",
	 	 		    beforeSend: function(xhr){
	 		 		   	var token = $("meta[name='_csrf']").attr('content');
	 		 			var header = $("meta[name='_csrf_header']").attr('content');
	 	 		        xhr.setRequestHeader(header, token);
	 	 		    },
	 	            success:function(data){
	 		           	console.log(data);
	 		           	for(var i=0; i<data.length; i++){
	 						comments += '<p>'+data[i].content+'<p>';
	 					}
	 					$('.comments').html(comments);
	 					$('.comment').val('');
	 	            },
	 	            error:function(data){
	 	                console.log("ajax 처리 실패");
	 	            }
	 	        });
			},
			error : function (data) {
				console.log('ERROR');
			}
			
		});
	});



});



function deleteCheck(){
    if(confirm("삭제하시겠습니까?")){
        return true;
    } else {
    	$(".delete").attr('href','list');
        return false;
    }
}
</script>
</body>
</html>
