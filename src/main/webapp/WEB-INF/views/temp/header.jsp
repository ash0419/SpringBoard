<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:importAttribute name="menuList" />
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
		<!-- *Scope *(session, request, page, application) 객체에 접근 -->
		<li><a href="/board/home">Home</a></li>
		<c:forEach items="${pageScope.menuList}" var="item">
			<li class="${item.typ == param.typ ? 'selectedBoard' : '' }"><a href="/board/list?typ=${item.typ}">${item.nm}</a></li>
		</c:forEach>
		<c:if test="${loginUser != null}">
			<li><a href="/user/profile">프로필</a></li>
			<li><a href="/user/changePw">비밀번호 변경</a></li>
		</c:if>
	</ul>
</header>
