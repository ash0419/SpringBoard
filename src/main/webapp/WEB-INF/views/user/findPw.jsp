<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="centerContainer">
	<div>
		<div>
			<input type="text" name="user_id" placeholder="id" id="findPwUserId">
		</div>
		<div>
			<input type="button" value="인증메일 받기" onclick="clkFindPwBtn()">
		</div>
		<div style="color: red;" id="findPwMsg"></div>
		<a href="/user/login">Go to Login</a>
	</div>
</div>
