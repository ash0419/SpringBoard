<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="centerCont">
	<div>
		<h1>비밀번호 변경</h1>
		
		<form id="findPwAuthFrm">
			<input type="hidden" name="user_id" value="${param.user_id}">
			<input type="hidden" name="cd" value="${param.cd}">
			<div>
				<input type="password" name="user_pw" placeholder="변경 비밀번호">
			</div>
			<div>
				<input type="password" name="chk_user_pw" placeholder="확인 비밀번호">
			</div>
			<input type="button" name="btnSend" value="변경">
			<div style="color: red;">${msg}</div>
		</form>
	</div>
</div>
