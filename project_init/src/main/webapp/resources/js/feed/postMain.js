$(document).ready(function() {
	
	let postNo = "";
	let email = $('#user').attr('value');
	
	$(".like").on('click',function () {
		postNo = $(this).attr('data-postno');
		
		$.ajax({
			url :'addLike.do',
			data : {
				postNo : postNo,
				email : email},
			type : 'post',
			beforeSend: function(xhr){
		 	   	var token = $("meta[name='_csrf']").attr('content');
		 		var header = $("meta[name='_csrf_header']").attr('content');
	 		    xhr.setRequestHeader(header, token);
	 		},
			success : function () {
				getPost();
				console.log('하트날리기 성공');	
			},
			error : function () {
				console.log('하트날리기 실패');
			}
		});
	});
	
	function getPost(){
		$.ajax({
			url :'getPost.do',
			type : 'get',
			beforeSend: function(xhr){
		 	   	var token = $("meta[name='_csrf']").attr('content');
		 		var header = $("meta[name='_csrf_header']").attr('content');
	 		    xhr.setRequestHeader(header, token);
	 		},
			success : function (data) {
				$('#main-body').html(data);
				console.log('JSP뿌리기 성공');	
			},
			error : function () {
				console.log('JSP뿌리기 실패');
			}
		});
	}
	
	
	
	$(".titleimg").click(function(){
        postNo = $(this).attr("data-value");
        
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
                       Citem+='<div class="carousel-item active"><img src="../images/'+images[i]+'"></div>';
                    }else{
                       Citem+='<div class="carousel-item"><img src="../images/'+images[i]+'"></div>';
                    }
                }
               	
            	userNickname ='<li>'+email+'</li>';
            	postContent ='<li>'+content+'</li>';
            	
				//내용 넣는 프로세스
                $(".Citem").html(Citem);
                $(".userNickname").html(userNickname);
                $(".postContent").html(postContent);
                
                $(".modify").attr('href','post/modify?postNo='+postNo);
                $(".delete").attr('href','post/delete.do?postNo='+postNo);
            },
            error:function(){
                console.log("ajax1 처리 실패");
            }
        });
        
        getComments();
        
    });

	
	$('.addcomment').click(function () {
		
		let content = $('.comment').val();
		let grpl = $('.grpl').attr('data-value');
		
		$.ajax({
			url : 'addcomments.do',
			type : 'post',
			data : {postNo : postNo,
					content : content,
					grpl : grpl},
			beforeSend: function(xhr){
		 	  	var token = $("meta[name='_csrf']").attr('content');
		 		var header = $("meta[name='_csrf_header']").attr('content');
	 		    xhr.setRequestHeader(header, token);
	 		},
	 		success : function (data) {
	 			console.log('success');
	 			getComments();
	 			$('.comment').val("");
			},
			error : function (data) {
				console.log('ERROR');
			}
			
		});
	});

	
	
	function getComments() {
		let comments ="";
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
		           		comments += '<div>';
						
		           		for(var y=0; y<data[i].grpl; y++){
		           		comments += '&nbsp;&nbsp;';
						}
		           		
		           		comments += '<span style="font-size:15px;">'+data[i].content+'</span>&nbsp;&nbsp;&nbsp;';
						comments += '<span class ="replyClick" style="font-size:5px; cursor : pointer;">답글달기</span>&nbsp;';
						comments += '<i class="fa-solid fa-heart" style="font-size:5px; color:red; cursor : pointer;"></i>&nbsp;';
						comments += '<span class ="addHeart" style="font-size:5px;">'+data[i].likes+'</span>&nbsp;';
						comments += '<i class="fa-solid fa-x deleteRe" style="font-size:5px; color:red; cursor : pointer;" data-no="'+data[i].commentNo+'" ></i><br/>';
						comments += '<div class="row">';
						comments += '<input type="hidden" class="col-xs-10 replyComment" data-grp="'+data[i].grp+'" data-grpl="'+data[i].grpl+'" " data-grps="'+data[i].grps+'">';
						comments += '<input type="hidden" class="btn btn-outline-success addreplyComment" role="button" value="전송"></input>';
						comments += '</div>';
						comments += '</div>';
		           	}
		           	
					$('.comments').html(comments);
					
					$('.replyClick').click(function () { //re댓글 작성
						
						$(this).siblings('.row').children('.replyComment').attr('type','text');
						$(this).siblings('.row').children('.addreplyComment').attr('type','button');
						
						
						$('.addreplyComment').click(function () {
							let content = $(this).siblings('.replyComment').val();
							let grp = $(this).siblings('.replyComment').attr('data-grp');
							let grpl = $(this).siblings('.replyComment').attr('data-grpl');
							let grps = $(this).siblings('.replyComment').attr('data-grps');
					

							$.ajax({
								url : 'addReplyComments.do',
								type : 'post',
								data : {postNo : postNo,
										content : content,
										grp : grp,
										grpl : grpl,
										grps : grps},
								beforeSend: function(xhr){
							 	  	var token = $("meta[name='_csrf']").attr('content');
							 		var header = $("meta[name='_csrf_header']").attr('content');
						 		    xhr.setRequestHeader(header, token);
						 		},
						 		success : function (data) {
						 			console.log('success');
						 			getComments();
								},
								error : function (data) {
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
	         error:function(data){
	             console.log("ajax 처리 실패");
	         }
	     });
	}
	
	
});



function deleteCheck(){
    if(confirm("삭제하시겠습니까?")){
        return true;
    } else {
    	$(".delete").attr('href','post/list');
        return false;
    }
}
