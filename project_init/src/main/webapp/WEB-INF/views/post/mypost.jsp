<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>Insert title here</title>

<style>
#feedPost {
	width: 100%;
}

#feedPost #postBox .posts {
	height: 320px;
}

#feedPost #postBox .posts .post {
	width : 24%;
	height: 100%;
}

#feedPost #postBox .posts .post-top {
	height: 320px;
	margin-bottom: 2%;
	cursor: pointer;
	line-height: 100%;
}

#feedPost #postBox .posts .post-top img {
	max-width: 100%;
	max-height: 100%;
	object-fit: cover;
}

#moreBtn {
	width: 100%;
	border: none;
}

ul{
   list-style:none;
}

.like.active {
	color: red;
}
.modal-like.active {
	color: red;
}
.profile-img-xs {
	display: flex;
	align-items: center;
	padding-top: 0.5px;
}
.post-nickname, bottom-likes, bottom-comments, bottom-views {
	height: 50%;
}
.img-xs {
	width: 100%;
	height: 90%;
	border-radius: 50%;
}
.userinfo, .status {
	height: 50%;
}
ul {
	list-style: none;	
}
.profile-img-s {
	height: 100%;
}
.img-s {
	width: 50px;
	height: 50px;
	border-radius: 50%;
}
.list-group-item:nth-child(3) {
	align-items: center;
}
input.comment,
input.recomment {
	width: 85%;
	border: none;
	border-bottom: 1px solid #dee2e6;
}
profile-img-xxs {
	height: 100%;
}
.img-xxs {
	width: 30px;
	height: 30px;
	border-radius: 50%;
}
.comments {
	height: 90%;
	overflow : auto;
}
.comments .coment-block {
	align-items: center;
}
span.comment-text {
	overflow-wrap: break-word;
}
.coment-block .form-group {
	display: none;
}


</style>


</head>

<body>
<c:set var="totalCount" value="${list.size()}" />
<c:set var="rowCount" value="${totalCount / 4}" />
<c:set var="lastRow" value="${Math.floor(rowCount) }" />

<div id="feedPost" class="col-12">
	<div id="postBox">
	<c:choose>
		<c:when test="${rowCount <= 1}">
			<div class="posts d-flex justify-content-start mt-2">
				<c:forEach items="${list}" var="post">
					<div class="post mr-2" data-value="${post.postNo}">
						<div class="post-top border rounded">
							<img class="titleimg" src="images/${post.titleImage}"/>
						</div>
					</div>
				</c:forEach>
			</div>		
		</c:when>
		
		
		<c:otherwise>
			<c:forEach begin="0" end="${lastRow}" var="row">
				<div class="posts d-flex justify-content-start mt-2">
						<c:choose>
							<c:when test="${row < lastRow}">
								<c:forEach begin="${(row*4) + 1 }" end="${(row+1)*4}" var="index">
									<div class="post mr-2"  data-value="${list[index].postNo}">
										<div class="post-top border rounded">
											<img class="titleimg" src="images/${list[index].titleImage}"/>
										</div>
									</div>
								</c:forEach>
							</c:when>
							
							<c:when test="${row == lastRow }">
								<c:forEach begin="${(row*4) + 1 }" end="${totalCount}" var="index">
									<div class="post mr-2"  data-value="${list[index].postNo}">
										<div class="post-top border rounded">
											<img class="titleimg" src="images/${list[index].titleImage}"/>
										</div>
									</div>
								</c:forEach>
							</c:when>
						</c:choose>
					</div>
				</c:forEach>
		</c:otherwise>
	</c:choose>	
	
	</div>
	<c:if test="${ rowCount > 3 }">
		<button type="button" class="btn btn-outline-secondary mt-3" data-maxindex="${Math.floor(rowCount/3)}" id="moreBtn">더보기 ()</button>
	</c:if>
	
</div>

<%@ include file="../includes/modalPost.jsp" %>


<script>
var email = '<c:out value="${user}" />';



$(document).ready(function() {
	let postNo = "";
	$(".post").click(function(){
		postNo = $(this).attr("data-value");
		
		$('#modalBtn').trigger('click');
		
		$.ajax({
            url:"post/getlist.do",
            type:"post",
            data:{
            	postNo : postNo,
				email : email
			},
			
			beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
	 		success:function(data){
	 			$('#modalBtn').trigger('click');
                
	 			// data parsing
	 			var userNick=data.userNick;
                var userProfileImg = data.userProfileImg;
                var likes = data.likes;
                var content = data.content;
                var comment_total = data.comments;
                var location = data.location;
                var views = data.views;
                var images = data.images.split('/');
                var postNo = data.postNo;
                var heartCheck =data.heartCheck;
                
                
                // image carousel setting
            	for( var i = 0; i < images.length-1; i++ ){
                    if ( i == 0 ) {
                        $('.Citem').append('<div class="carousel-item active"><img src="images/'+images[i]+'"></div>');
                    } else {
                    	$('.Citem').append('<div class="carousel-item"><img src="images/'+images[i]+'"></div>');
                    }
                }
                
                if ( location.length != 0 ) {
                	for ( var i = 0; i < location.length; i++ ) {
            			var item = '<div class="mr-1 px-1 location-item border bg-light rounded">'
       					 + '<i class="fa-solid fa-location-dot text-primary"></i>&nbsp;'
       					 + location[i].placeName
       					 + '&nbsp</div>';
			       		
			   			$('.location').css('height', '28px');
			   			$('.location').css('display', 'flex');
			   			$('.location').css('flex-wrap', 'nowrap');	
                		if( i == 0 ) {
	            			$('.location').html(item);
                		} else {
                			$('.location').append(item);
                		}
                	}
                }
                
                // heart 확인해서 좋아요 누른 게시물은 active 부여
            	if( heartCheck == 1 ) {
            		$('i.modal-like').addClass('active');
            	}
            	
                $(".nickname>b").html(userNick);
                $(".content").html(content);
                $(".comment_total span").html(comment_total);
                $('.likes span').html(likes);
                $('.likes i.modal-like').attr('data-num', postNo);
                $('button.addcomment ').attr('data-num', postNo);
                $(".views span").html(views);
            	
	 		},
	 		error: function(data) {
	 			console.log("ajax1 처리 실패");
	 		}
		});
		getComments(postNo);
	});
	modalLike();
	
});

function modalLike() {
	$(document).on('click', '.modal-like', function(){
		var element = $(this);
		postNo = $(this).attr('data-num');
		
		$.ajax({
	    	url :'post/addLike.do',
	     	data : {
	        	postNo : postNo,
	            email : email
	        },
	     	type : 'post',
	     	beforeSend: function(xhr){
	        	var token = $("meta[name='_csrf']").attr('content');
	        	var header = $("meta[name='_csrf_header']").attr('content');
	        	xhr.setRequestHeader(header, token);
	     	},
	    	success : function(info) {
	        	if ( info == 'add' ) {
	           		element.addClass('active');
	           		element.siblings('#likeCount').text(Number(element.siblings('#likeCount').text())+1);
	        	} else {
	           		element.removeClass('active');
	           		element.siblings('#likeCount').text(Number(element.siblings('#likeCount').text())-1);
	   		    }
	        	console.log('하트날리기 성공');   
	    	},
	    	
	     	error : function () {
	        	console.log('하트날리기 실패');
	    	}
		});
	});
}



$('.addcomment').click(function () {
	postNo = $(this).attr('data-num');
	let content = $('.comment').val();
	let grpl = $('.grpl').attr('data-value');
	
	$.ajax({
		url : 'post/addcomments.do',
		type : 'post',
		data : {postNo : postNo,
				content : content,
				grpl : grpl,
				email : email},
		beforeSend: function(xhr){
	 	  	var token = $("meta[name='_csrf']").attr('content');
	 		var header = $("meta[name='_csrf_header']").attr('content');
 		    xhr.setRequestHeader(header, token);
 		},
 		success : function () {
 			console.log('success');
 			getComments(postNo);
			
 			$('.comment').val('');
 			
 			console.log($('.comment-block').length);
 			
 			$('div.comment_total>span').text(Number($('.comment-block').length)+1);
		},
		error : function () {
			console.log('ERROR');
		}
		
	});
});

function getComments(postNo) {
	let comments ="";
	$.ajax({
        url:"post/getcomments.do",
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
				comments += '<div class="comment-block row mx-0 my-1 d-flex">';
	      		for(var y=0; y < data[i].grpl; y++){
	       			comments += '<span>&nbsp;</span>';
				}
				comments +=	'<div class="profile-img-xxs col-1 px-0">';
				comments +=	'<div class="img-xxs border"></div>';
				comments +=	'</div>';
				comments +=	'<span class="col-3 pl-1" style="font-size: 14px; font-weight: 600;">' + data[i].userNick + '</span>';
	           	comments += '<span class="col-6 px-0 comment-text" style="font-size: 13px;">'+data[i].content+'</span>';
					
				if(email!=="" && email!==null && email!=="null"){
					comments += '<span class="replyClick col-1 px-0" data-count="0" style="font-size: 5px; cursor : pointer;">답글</span>';
				}
				if(email===data[i].email){
					comments += '<i class="fa-solid fa-x deleteRe" style="font-size:5px; color:red; cursor : pointer;" data-no="'+data[i].commentNo+'"></i><br/>';
				}

				comments += '<div class="form-group col-12 row mx-0">';
				comments += '<input type="text" class="col-10 recomment" data-grp="'+data[i].grp+'" data-grpl="'+data[i].grpl+'" data-grps="'+data[i].grps+'">';
				comments += '<input type="button" class="btn btn-sm btn-outline-success addreplyComment ml-1" role="button" value="전송">';
				comments += '</div>';
				comments += '</div>';
				comments += '</div>';
	        }
	           	
			$('.comments').html(comments);
	
			$('.replyClick').click(function () { //re댓글 작성
				var count = $(this).attr('data-count');
				if ( count == 0 ) {
					$(this).siblings('.form-group').css('display', 'flex');
					$(this).attr('data-count', Number(count)+1);
				} else {
					$(this).siblings('.form-group').css('display', 'none');
					$(this).attr('data-count', 0);
				}
				
				
				$('.addreplyComment').click(function () {
					let content = $(this).siblings('.recomment').val();
					let grp = $(this).siblings('.recomment').attr('data-grp');
					let grpl = $(this).siblings('.recomment').attr('data-grpl');
					let grps = $(this).siblings('.recomment').attr('data-grps');
					console.log(email);

					$.ajax({
						url : 'post/addReplyComments.do',
						type : 'post',
						data : {postNo : postNo,
								content : content,
								grp : grp,
								grpl : grpl,
								grps : grps,
								email : email},
						beforeSend: function(xhr){
					 	  	var token = $("meta[name='_csrf']").attr('content');
					 		var header = $("meta[name='_csrf_header']").attr('content');
				 		    xhr.setRequestHeader(header, token);
				 		},
				 		success : function () {
				 			console.log('success');
				 			getComments();
						},
						error : function () {
							console.log('ERROR');
						}
					});
				});
			});
			
			$('.deleteRe').click(function () { //re댓글 삭제
				

				let commentNo = $(this).attr('data-no');
			
				$.ajax({
					url : 'deleteReplyComments.do',
					type : 'post',
					data : {commentNo : commentNo},
					beforeSend: function(xhr){
				 	  	var token = $("meta[name='_csrf']").attr('content');
				 		var header = $("meta[name='_csrf_header']").attr('content');
			 		    xhr.setRequestHeader(header, token);
			 		},
			 		success : function () {
			 			console.log('success');
			 			getComments();
					},
					error : function () {
						console.log('ERROR');
					}
					
				});
			});
     	},
     	error:function(){
        	console.log("ajax 처리 실패");
     	}
	});
}

$('#modal-reg').on('hidden.bs.modal', function() {
    $(".nickname>b").html('nickname');
    $(".content").html('');
    $(".comment_total span").html('');
    $('.likes span').html('');
    $('.likes i.modal-like').attr('data-num', '');
    $('button.addcomment ').attr('data-num', '');
    $(".views span").html('');
    $('i.modal-like').removeClass('active');
    $('.Citem').children('div.carousel-item').remove();
    $('div.location').html('Location');
    $('.comments').childern('div.comment-block').remove();
});


function deleteCheck(){
    if(confirm("삭제하시겠습니까?")){
        return true;
    } else {
    	$(".delete").attr('href','post/list');
        return false;
    }
}

</script>

</body>
</html>