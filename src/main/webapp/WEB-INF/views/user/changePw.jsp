<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="centerCont">
	<div>
		<h1>비밀번호 변경</h1>
		
		<form id="frm" action="/user/changePwProc.korea" method="post" onsubmit="return chkPw();">
			<div>
				<input type="password" name="current_pw" placeholder="현재 비밀번호">
			</div>
			<div>
				<input type="password" name="user_pw" placeholder="변경 비밀번호">
			</div>
			<div>
				<input type="password" name="chk_user_pw" placeholder="확인 비밀번호">
			</div>
			<input type="submit" value="변경">
			<div style="color: red;">${msg}</div>
		</form>
	</div>
</div>
