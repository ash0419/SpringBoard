//글 제목 클릭
function clkArticle(i_board) {
	var url = `bDetail?i_board=${i_board}`;
	location.href = url; //주소값 이동
}

//삭제 버튼 클릭
function clkDel(i_board, typ) {
	if (confirm('삭제 하시겠습니까?')) {
		location.href = `bDel?i_board=${i_board}&typ=${typ}`;
	}
}

//지금은 사용 X, 혹시나 나중에 욕이 있는지 체크하는 용도로 사용
function chk() {
	var frm = document.querySelector('#frm');
	if (chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')) {
		return false;
	}
}