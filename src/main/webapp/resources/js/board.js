'use strict';

//글 제목 클릭
function clkArticle(i_board) {
	var url = `/board/detail?i_board=${i_board}`;
	location.href = url; //주소값 이동
}

// 삭제 버튼 클릭
function clkDel(i_board, typ) {
	if (confirm('삭제 하시겠습니까?')) {
		fetch(`/board/del/${i_board}`, {
			method: 'delete' // get방식이랑 비슷함, put이 post방식이랑 비슷하다.
		}).then((res) => {
			return res.json();
		}).then((myJson) => {
			console.log(myJson);
			if (myJson.result === 1) { // 삭제 완료
				location.href = `/board/list?typ=${typ}`;
			} else { // 삭제 실패
				alert('삭제 실패하였습니다.');
			}
		});
	}
}

function clkCmtDel(i_cmt, i_board) {
	if (confirm('삭제 하시겠습니까?')) {
		location.href = `cmt/del?i_cmt=${i_cmt}&i_board=${i_board}`;
	}
}

// 지금은 사용 X, 혹시나 나중에 욕이 있는지 체크하는 용도로 사용
function chk() {
	// var frm = document.querySelector('#frm'); 아이디만 써도 안써도 된다. 생략
	console.log('frm : ' + document.forms.frm.title.value);
	if (chkEmptyEle(frm.title, '제목') || chkEmptyEle(frm.ctnt, '내용')) {
		return false;
	}
}

// 댓글에서 수정버튼 클릭 > 수정FORM 나타나게 처리
function clkCmtMod(i_cmt) {
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.remove('cmd_mod_form'); // class element만 유일하게 접근할 때 classList를 사용
	console.log(trForm);
}

function clkCmtClose(i_cmt) {
	var trForm = document.querySelector('#mod_' + i_cmt);
	trForm.classList.add('cmd_mod_form');
}

function toggleFavorite(i_board) {
	var fc = document.querySelector('#favoriteContainer');
	var state = fc.getAttribute('is_favorite'); // 1: 좋아요, 0: 안좋아요
	var state = 1 - state;

	axios.get('/board/ajaxFavorite.korea', {
		params: {
			'state': state,
			i_board
		}
	}).then(function (res) {	// 통신 성공
		if (res.data.result == 1) { // res에 있는 data객체에 접근 후 result 값 가져오기
			//var iconClass = state == 1? 'fas' : 'far';
			fc.innerHTML = `<i class="${state ? 'fas' : 'far'} fa-heart"></i>`;
			fc.setAttribute('is_favorite', state)
		} else {
			alert('에러가 발생하였습니다.')
		}
	}).catch(function (err) {	// 통신 실패
		console.err('err 발생 : ' + err)
	});
}

var cmtFrmElem = document.querySelector('#cmtFrm');
if(cmtFrmElem) {
	var ctntElem = cmtFrmElem.ctnt
	var i_board = ctntElem.dataset.id
	var btnElem = cmtFrmElem.btn
	
	btnElem.addEventListener('click', ajax)
	
	
	//여기까지 한번만 실행
	function ajax () {
		console.log(`i_board : ${i_board}`)
		
		var param = {
			i_board,
			ctnt: ctntElem.value
		}
		
		fetch('/board/insCmt', {
			method:'POST',
			headers: {
			'Contetnt-type' : 'application/json'
		},
		body: JSON.stringify(param) // 객체를 문자열로 바꿔주는 함수
		}).then(function(res){
			return res.json()
		}).then (function(myJson) {
			console.log(myJson)
		})
	}
}

