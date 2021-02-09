<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div id="data" data-typ="${param.typ == null ? 1 : param.typ}">
	<c:if test="${sessionScope.loginUser != null}">
		<div>
			<a href="/board/reg?typ=${param.typ == null ? 1 : param.typ}"><button>글쓰기</button></a>
		</div>
	</c:if>
	<div>
		<span id=""> <select id="searchType">
				<option value="1" ${param.searchType == 1? 'selected' : ''}>제목</option>
				<option value="2" ${param.searchType == 2? 'selected' : ''}>내용</option>
				<option value="3" ${param.searchType == 3? 'selected' : ''}>제목+내용</option>
				<option value="4" ${param.searchType == 4? 'selected' : ''}>작성자</option>
		</select> 
			<input type="search" id="searchText" value="${param.searchText}"> 
			<input type="button" value="검색" onclick="getBoardList()">
		</span>
		<form id="listFrm" action="/board/list" method="get">
			<input type="hidden" name="typ">
			<input type="hidden" name="searchType" value="0">
			<input type="hidden" name="searchText">
			<input type="hidden" name="page" value="1">
			<select name="recordCntPerPage" onchange="getBoardList()">
				<c:forEach begin="5" end="20" step="5" var="p">
					<option value="${p}" ${requestScope.data.recordCntPerPage == pageScope.p ? 'selected' : '' }>${p}개</option>
				</c:forEach>
			</select>
		</form>
	</div>
	<c:choose>
		<c:when test="${fn:length(requestScope.data.list) == 0}">
			<div>글이 없습니다.</div>
		</c:when>
		<c:otherwise>
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
		<c:if test="${requestScope.data.sPage > 1}">
			<span class="page" onclick="getBoardList(1)">1</span>
			<span>...</span>
		</c:if>	
		
		<c:forEach begin="${requestScope.data.sPage}" end="${requestScope.data.ePage}" var="i">			
			<span class="page ${requestScope.data.page == i ? 'selected' : ''}" onclick="getBoardList(${i})">${i}</span>
		</c:forEach>
		
		<c:if test="${requestScope.data.ePage < requestScope.data.maxPageNum}">
			<span>...</span>
			<span class="page" onclick="getBoardList(${requestScope.data.maxPageNum})">${requestScope.data.maxPageNum}</span>
		</c:if>	
	</div>
</div>

<script src="/res/js/board/list.js"></script>
