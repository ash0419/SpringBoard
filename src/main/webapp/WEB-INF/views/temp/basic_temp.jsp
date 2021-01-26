<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<c:forEach items="${jsList}" var="item">
	<script asysn src="/res/js/${item}.js"></script>
	<!-- defer는 맨 밑에 놔둔 효과, async는 화면을 안 느려지게 하는 효과 -->
</c:forEach>
<script asysn src="/res/js/common.js"></script>
<link rel="stylesheet" href="/res/css/board.css">
<link rel="stylesheet" href="/res/css/common.css">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>
<body>
	<div id="container" class="themed">
		<header>
			<ul>
				<c:if test="${loginUser == null}">
					<li><a href="/user/login">로그인</a></li>
				</c:if>
				<c:if test="${loginUser != null}">
					<li>${loginUser.nm}님환영합니다.</li>
					<li><a href="/user/logout">Logout</a></li>
				</c:if>
				<!-- TODO: 메뉴 뿌리기 -->
				<c:forEach items="${menus}" var="item">
					<li class="${item.typ == param.typ ? 'selectedBoard' : '' }"><a href="/board/list?typ=${item.typ}">${item.nm}</a></li>
				</c:forEach>
				<c:if test="${loginUser != null}">
					<li><a href="/user/profile">프로필</a></li>
					<li><a href="/user/changePw">비밀번호 변경</a></li>
				</c:if>
			</ul>
		</header>
		<section>
			<jsp:include page="${page}" />
		</section>
		<footer></footer>
	</div>
</body>
</html>