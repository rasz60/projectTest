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
	text-align: center;
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


<div id="feedPost">
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


<%@ include file="../modalPost.jsp" %>

<script>
$('#moreBtn').click(function() {
	var bodyHeight = $('#main-body').height();
	var postRow = '<c:forEach begin="1" end="2" var="i">'
				+ '<div class="posts d-flex justify-content-between mt-2">'			
				+ '<c:forEach begin="1" end="4" var="i">'
				+ '<div class="post mr-2">'
				+ '<div class="post-top border rounded"></div>'
				+ '</div>'
				+ '</c:forEach>'
				+ '</div>'
				+ '</c:forEach>';
	
	$('#postBox').append(postRow);
	$('#main-body').height(Number(bodyHeight)+660);
	
	
	console.log($('#main-body').innerHeight());
	
})


$('.post').click(function() {
	console.log($(this).text());
	$('#modalBtn').trigger('click');
});


</script>
</body>
</html>