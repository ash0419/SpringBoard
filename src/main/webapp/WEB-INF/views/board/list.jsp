<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="data" data-typ="${param.typ == null ? 1 : param.typ}">
	<c:if test="${sessionScope.loginUser != null}">
		<div>
			<a href="/board/reg?typ=${param.typ == null ? 1 : param.typ}"><button>글쓰기</button></a>
		</div>
	</c:if>
	<c:choose>
		<c:when test="${fn:length(requestScope.data.list) == 0}">
			<div>글이 없습니다.</div>
		</c:when>
		<c:otherwise>
			<div>
				<form id="listFrm" action="/board/list" method="get">
				<input type="hidden" name="typ">
				<input type="hidden" name="page" value="1">
					<select name="recordCntPerPage" onchange="getBoardList()">
					<c:forEach begin="5" end="20" step="5" var="p">
						<option value="${p}" ${requestScope.data.recordCntPerPage == pageScope.p ? 'selected' : '' }>${p}개</option>
					</c:forEach>
					</select>
				</form>
			</div>
			<table>
				<tr>
					<td>번호</td>
					<td>제목</td>
					<td>조회수</td>
					<td>좋아요</td>
					<td>작성일</td>
					<td>작성자</td>
				</tr>
				<c:forEach items="${requestScope.data.list}" var="item">
					<tr class="pointer" onclick="clkArticle(${item.i_board})">
						<td>${item.seq}</td>
						<td>${item.title}</td>
						<td>${item.hits}</td>
						<td>${item.favorite_cnt}</td>
						<td>${item.r_dt}</td>
						<td class="profile-td"><c:if test="${item.profile_img == null}">
								<div class="circular--landscape circular--size50">
									<img id="profileImg" src="/res/img/basic_profile.jpg">
								</div>
							</c:if> <c:if test="${item.profile_img != null}">
								<div class="circular--landscape circular--size50">
									<img id="profileImg" src="/res/img/${item.i_user}/${item.profile_img}">
								</div>
							</c:if> 
							<span class="profile-td-nm">${item.writer_nm}</span></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>


	<div class="pageContainer">
		<c:forEach begin="1" end="${requestScope.data.maxPageNum}" var="i">
			<span class="page" onclick="getBoardList(${i})">${i}</span>
		</c:forEach>
	</div>
</div>

<script src="/res/js/board/list.js"></script>
