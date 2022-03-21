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
</head>
<body>
<%@ include file="header.jsp" %>

<section class="container mb-4">
	<div class="result_posts">
		<div class="posts d-flex flex-wrap justify-content-start mt-2">
			<c:forEach items="${list}" var="list" >
				<div class="post mr-2">
					<div class="post-top border rounded">
						<img class="titleimg" width="280px" src="images/${list.titleImg}" data-value="${list.boardNum}" data-toggle="modal" data-target="#modal-reg">
					</div>
					<div class="post-bottom border text-center">
						<h5>${list.location}</h4>
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
                        <li class="list-group-item mb-1"><i class="modal-icon fa-regular fa-circle-user"></i></li>
                        <li class="list-group-item mb-1"><i class="modal-icon fa-regular fa-rectangle-list"></i></li>
                        <li class="list-group-item mb-1 d-flex row mx-0">
                            <div class="col-6"><i class="modal-icon fa-regular fa-heart"></i></div>
                            <div class="col-3"><i class="modal-icon fa-regular fa-bookmark"></i></div>
                            <div class="col-3"><i class="modal-icon fa-regular fa-comment-dots"></i></div>
                        </li>
                        <li class="list-group-item"><i class="modal-icon fa-regular fa-comment-dots"></i></li>
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
    $(".titleimg").click(function(){
        let boardNum = $(this).attr("data-value");
        
        $.ajax({
            url:"getlist.do",
            data:{boardNum:boardNum},
            type:"post",
 		    beforeSend: function(xhr){
	 		   	var token = $("meta[name='_csrf']").attr('content');
	 			var header = $("meta[name='_csrf_header']").attr('content');
 		        xhr.setRequestHeader(header, token);
 		    },
            success:function(data){
            	
	           	var Cslide = "";
                var Citem = "";

            	let nickname = data[0].nickname;
            	let title = data[0].title;
            	let content = data[0].content;
            	let location = data[0].location;
            	let titleImg = data[0].titleImg;
            	let filenames = data[0].filenames.split('/');
            	let boardNum = data[0].boardNum;
            	
            	for(var i=0; i<filenames.length-1; i++){
                    if(i==0){
                       Citem+='<div class="carousel-item active"><img src="images/'+filenames[i]+'"></div>';
                    }else{
                       Citem+='<div class="carousel-item"><img src="images/'+filenames[i]+'"></div>';
                    }
                }
               
                $(".Citem").html(Citem);
               
                $(".modify").alert('rreturn?');
                $(".modify").attr('href','modify?boardNum='+boardNum);
                $(".delete").attr('href','delete.do?boardNum='+boardNum);
            },
            error:function(data){
                console.log("ajax 처리 실패");
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
