<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
	<a href="/board/list?typ=${data.typ}">돌아가기</a>

	<c:if test="${data.i_user == loginUser.i_user}">
		<button onclick="clkDel(${data.i_board}, ${data.typ});">삭제</button>
		<a href="/board/mod?typ=${data.typ}&i_board=${data.i_board}">
			<button>수정</button>
		</a>
	</c:if>
	<div>
		<div>번호 : ${data.seq}</div>
		<div>조회수 : ${data.hits}</div>
		<div>
			이름 : ${data.writer_nm}
			<c:if test="${data.profile_img == null}">
				<div class="circular--landscape circular--size50">
					<img id="profileImg" src="/res/img/basic_profile.jpg">
				</div>
			</c:if>
			<c:if test="${data.profile_img != null}">
				<div class="circular--landscape circular--size50">
					<img id="profileImg" src="/res/img/${data.i_user}/${data.profile_img}">
				</div>
			</c:if>
		</div>
		<div>제목 : ${data.title}</div>
		<div>작성일 : ${data.r_dt}</div>
		<div>${data.ctnt}</div>
	</div>
	<div style="margin-top: 20px;">
	<span id="i_board" data-id="${requestScope.data.i_board}"></span>
		<c:if test="${loginUser != null}">
			<div>
				<form id="cmtFrm">
					댓글:
					<!-- html은 배열이 없고 무조건 string이라고 생각하면 편하다. -->
					<input type="text" name="ctnt">
					<input type="button" name="btn" value="댓글쓰기">
				</form>
			</div>
		</c:if>
		
		<div id="cmtList"></div>
		
	</div>

	<c:if test="${loginUser != null}">
		<div id="favoriteContainer" is_favorite="${data.is_favorite}" onclick="toggleFavorite(${data.i_board});">
			<c:choose>
				<c:when test="${data.is_favorite == 1}">
					<i class="fas fa-heart"></i>
				</c:when>
				<c:otherwise>
					<i class="far fa-heart"></i>
				</c:otherwise>
			</c:choose>
		</div>

		<div class="black_bg"></div>
		<div class="modal_wrap">
			<div class="modal_close">
				<a href="#">close</a>
			</div>
			<div>
			<h3>댓글 수정</h3>
			<input type="text" id="cmtCtnt">
			<input type="button" value="수정" id="cmtModBtn">
			</div>
		</div>
		
	</c:if>
</div>

