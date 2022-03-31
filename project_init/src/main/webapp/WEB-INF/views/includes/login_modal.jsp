<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<link rel="stylesheet" type="text/css" href="css/includes/login_modal.css" />
<title>login</title>
</head>
<body>
<input type="hidden" id="loginModalBtn" data-toggle="modal" data-target="#loginModal" value="modal" />

	<!-- modal 창 -->
	<div class="modal fade" id="loginModal" role="dialog">
		<div class="modal-dialog modal-dialog-centered modal-sm d-block">
			<button type="button" id="modalCloseBtn" class="btn btn-xl btn-default text-white text-weight-bold display-1 float-right" data-dismiss="modal">&times;</button>
			<div class="modal-content">
				<div class="modal-header bg-light d-flex justify-content-start">
					<h4 id="title" class="modal-title display-4">Login</h4>
				</div>
				
				<div class="modal-body bg-light d-flex justify-content-center">
					<form action="/init/login" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<div class="form-group">
							<label for="userId">ID</label>
							<input type="text" class="form-control" id="userId" name="uid" required/>
						</div>
						
						<div class="form-group">
							<label for="userPwd">PASSWORD</label>
							<input type="password" class="form-control"  id="userPwd" name="upw" required/>
						</div>
						<div class="form-group" style="visibility:hidden; color:red; font-size:12px;" id="loginError">아이디 혹은 비밀번호가 잘못 입력되었습니다.</div>
							<a href="user/join_view" class="ml-1 d-block mb-2" id="join">JOIN</a>
						<input type="submit" value="login" class="btn btn-sm btn-primary"/>
					</form>
				</div>
				<div class="modal-footer bg-light mb-2"></div>
			</div>
		</div>
	</div>

</body>
</html>