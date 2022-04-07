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
	height: 100%;
	margin-bottom: 2%;
}

#feedPost #postBox .posts .post-bottom {
	height: 18%;
}

img {
	object-fit: cover;
}

#moreBtn {
	width: 100%;
	border: none;
}

</style>
</head>

<div id="feedPost" class="col-12">
	<div id="postBox">
		<c:forEach begin="1" end="3" var="i">
			<div class="posts d-flex justify-content-between mt-2">			
				<c:forEach begin="1" end="4" var="i">
					<div class="post mr-2">
						<div class="post-top border rounded"></div>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	<button type="button" class="btn btn-outline-secondary mt-3" id="moreBtn">더보기 ()</button>
</div>


<div class="quickmenu">
	<button type="button" class="btn btn-sm btn-dark" id="new-post">New</button>
</div>

<%@ include file="modalPost.jsp" %>
<%@ include file="modalPost2.jsp" %>
<script>

// 더보기 버튼을 눌렀을 때,
$('#moreBtn').click(function() {
	// 현재 #main-body의 높이를 구함
	var bodyHeight = $('#main-body').height();
	
	// 추가될 포스트 박스 html[임시]
	var postRow = '<c:forEach begin="1" end="2" var="i">'
				+ '<div class="posts d-flex justify-content-between mt-2">'			
				+ '<c:forEach begin="1" end="4" var="i">'
				+ '<div class="post mr-2">'
				+ '<div class="post-top border rounded"></div>'
				+ '</div>'
				+ '</c:forEach>'
				+ '</div>'
				+ '</c:forEach>';
	
	// postBox에 html append 시킴
	$('#postBox').append(postRow);
	
	// main-body의 높이를 +660px해서 늘림
	$('#main-body').height(Number(bodyHeight)+660);
	
})

// post 클릭하면 modal창 열림 (더보기 버튼 눌러서 동적으로 생긴 post도 클릭 이벤트 실행되게끔 document.on 사용)
$(document).on('click', '.post', function() {
	$('#modalBtn').trigger('click');
});


$('#new-post').click(function() {
	$('#modalBtn2').trigger('click');
});



/*
$(document).ready(function() { 
	var currentPosition = parseInt($(".quickmenu").css("top")); 
	
	console.log(currentPosition);
	
	$(window).scroll(function() { 
		var position = $(window).scrollTop(); 
		$(".quickmenu").stop().animate({"top":position+currentPosition+"px"},1000);
	});
});
*/

</script>
</body>
</html>