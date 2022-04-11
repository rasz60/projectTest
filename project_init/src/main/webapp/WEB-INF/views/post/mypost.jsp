<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<link rel="stylesheet" href="css/post/mypost.css" />
<title>Insert title here</title>
<script>
var email = '<c:out value="${user}" />';
</script>
<script src="js/post/mypost.js"></script>
</head>

<body>
<c:set var="totalCount" value="${list.size()}" />
<c:set var="rowCount" value="${totalCount / 4}" />
<c:set var="lastRow" value="${Math.floor(rowCount) }" />
<div id="feedPost" class="col-12">
	<div id="postBox">
	<c:choose>
		<c:when test="${rowCount <= 1}">
			<div class="posts mt-2">
				<c:forEach items="${list}" var="post">
					<div class="post mr-2" data-value="${post.postNo}">
						<div class="post-top border rounded">
							<img class="titleimg" src="images/${post.titleImage}"/>
						</div>
					</div>
				</c:forEach>
			</div>		
		</c:when>
		
		
		<c:when test="${rowCount > 1}">
			<c:forEach begin="0" end="${lastRow}" var="row">
					<c:choose>
						<c:when test="${row < lastRow}">
							<div class="posts mt-2">
								<c:forEach begin="${(row*4) }" end="${((row+1)*4) - 1}" var="index">
									<div class="post mr-2"  data-value="${list.get(index).postNo}">
										<div class="post-top border rounded">
											<img class="titleimg" src="images/${list[index].titleImage}"/>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:when>
						
						<c:otherwise>
							<c:if test="${rowCount > lastRow}">
								<div class="posts mt-2">
								<c:forEach begin="${row*4 }" end="${totalCount - 1}" var="index">
									<div class="post mr-2"  data-value="${list.get(index).postNo}">
										<div class="post-top border rounded">
											<img class="titleimg" src="images/${list.get(index).titleImage}"/>
										</div>
									</div>
								</c:forEach>
								</div>
							</c:if>
						</c:otherwise>
					</c:choose>
			</c:forEach>
		</c:when>
		
		<c:when test="${lastRow < 1 }">
			<div class="mt-5">
				<h1 class="display-4 text-center">
					<i class="fa-regular fa-face-dizzy text-warning"></i>
				</h1>

				<p class="display-4 text-center font-italic" style="font-size: 35px; font-weight: 500;">아직 작성된 포스트가 없습니다.</p>
			</div>
			<br />
			<hr />
		</c:when>
	</c:choose>
	
	</div>

	<c:if test="${ lastRow >= 3 }">
		<button type="button" class="btn btn-dark mt-3" data-currIndex="0" data-maxindex="${Math.floor(rowCount/3)}" id="moreBtn">더보기</button>
	</c:if>
	
</div>

<%@ include file="../includes/modalPost.jsp" %>

<script>
</script>

</body>
</html>