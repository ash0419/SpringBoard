<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="centerCont">
	
</div>

<div>
	<input type="file" id="inputImg" multiple accept="image/*">
	<!-- multiple은 여러 이미지 선택 -->
	<input type="button" value="업로드" onclick="upload()">
</div>

<script src="/res/js/user/profile.js"></script>