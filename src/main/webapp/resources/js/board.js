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
	}).then(function(res) {	// 통신 성공
		if (res.data.result == 1) { // res에 있는 data객체에 접근 후 result 값 가져오기
			//var iconClass = state == 1? 'fas' : 'far';
			fc.innerHTML = `<i class="${state ? 'fas' : 'far'} fa-heart"></i>`;
			fc.setAttribute('is_favorite', state)
		} else {
			alert('에러가 발생하였습니다.')
		}
	}).catch(function(err) {	// 통신 실패
		console.err('err 발생 : ' + err)
	});
}

// modal창 열기 닫기
function openCloseCmtModal(state) {
	var modalWrapElem = document.querySelector('.modal_wrap')
	var blackBgElem = document.querySelector('.black_bg')

	modalWrapElem.style.display = state
	blackBgElem.style.display = state
}

// 댓글 수정
function modCmt(i_cmt, ctnt) {
	openCloseCmtModal('block')

	var cmtCtntElem = document.querySelector('.modal_wrap #cmtCtnt')
	cmtCtntElem.value = ctnt
	var cmtModBtn = document.querySelector('.modal_wrap #cmtModBtn')

	cmtModBtn.onclick = ajax

	function ajax() {
		var param = {
			i_cmt,
			ctnt: cmtCtntElem.value
		}
		fetch('/board/updCmt', {
			method: 'put',
			headers: {
				'Content-type': 'application/json',
			},
			body: JSON.stringify(param) // 객체를 문자열로 바꿔주는 함수
		}).then(res => res.json()) // 인자값이 1개일때는 괄호 안해줘도 댐 리턴할 떈 {}을 넣으면 안댄다.
		.then((myJson) => {
			openCloseCmtModal('none')
			
			switch (myJson.result) {
			case 1:
				cmtObj.getCmtList()
				return
			case 0:
				alert('댓글 수정 실패')
				}
		})
	}
}
// 댓글 삭제
function delCmt(i_cmt, i_board) {
	if (!confirm('삭제하시겠습니까?')) {
		return
	}
	fetch(`/board/delCmt?i_cmt=${i_cmt}`, {
		method: 'delete'
	}).then((res) => {
		return res.json()
	}).then((myJson) => {
		switch (myJson.result) {
			case 1:
				cmtObj.getCmtList()
				return
			case 0:
				alert('댓글 삭제 실패')
		}
	})
}
var cmtObj = {
	i_board: 0,
	createCmtTable: function() {
		var tableElem = document.createElement('table')
		tableElem.innerHTML =
			`<tr>
			<th>내용</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>비고</th>
		</tr>`
		return tableElem
	},

	getCmtList: function() {
		if (this.i_board === 0) {
			return
		}
		fetch(`/board/cmtList?i_board=${this.i_board}`)
			.then(function(res) {
				return res.json()
			})
			.then((list) => {
				cmtListElem.innerHTML = ''
				this.proc(list)
			})
	},

	createRecode: function(item) {
		var etc = ''
		if (item.is_mycmt === 1) {
			etc = `<button onclick="modCmt(${item.i_cmt}, '${item.ctnt}')">수정</button><button onclick="delCmt(${item.i_cmt})">삭제</button>`
		}
		var tr = document.createElement('tr')
		tr.innerHTML = `
		<td>${item.ctnt}</td>
		<td>${item.user_nm}</td>
		<td>${item.r_dt}</td>
		<td>${etc}</td>
		`
		return tr
	},
	proc: function(list) {
		var table = this.createCmtTable()
		if (list.length == 0) {
			return
		}
		for (var i = 0; i < list.length; i++) { // for가 끝난다음에 다음 코드 수행 foreach문을 쓰면 뿌리는 도중에 다른 동작까지 작동
			var recode = this.createRecode(list[i])
			table.append(recode)
		}

		cmtListElem.append(table)
		console.log(list)
	}
}

// 댓글 리스트
var cmtListElem = document.querySelector('#cmtList')
if (cmtListElem) {
	var modalCloseElem = document.querySelector('.modal_close')

	modalCloseElem.addEventListener('click', () => {
		openCloseCmtModal('none')
	})
	var i_board = document.querySelector('#i_board').dataset.id
	cmtObj.i_board = i_board
	cmtObj.getCmtList()
}

//댓글 달기
var cmtFrmElem = document.querySelector('#cmtFrm');
if (cmtFrmElem) {

	// enter를 눌렀을 때 submit이 안되게 하는 방법
	/*
	cmtFrmElem.onsubmit = function() {
		return false;
	}*/

	cmtFrmElem.onsubmit = (e) => {
		e.preventDefault()
	}

	var ctntElem = cmtFrmElem.ctnt
	var i_board = document.querySelector('#i_board').dataset.id
	var btnElem = cmtFrmElem.btn
	cmtObj.i_board = i_board

	// enter 눌렀을 때 submit 버튼을 누른거랑 똑같은 효과
	ctntElem.onkeyup = function(e) {
		if (e.keyCode === 13) {
			ajax()
		}
	}
	btnElem.addEventListener('click', ajax)


	//여기까지 한번만 실행
	function ajax() {
		console.log(`i_board : ${i_board}`)


		if (ctntElem.value === '') {
			return
		}
		var param = {
			i_board, // 키값과 변수명이 똑같을 때만 사용 가능 => i_board: i_board,
			ctnt: ctntElem.value
		}
		console.log(param)
		fetch('/board/insCmt', { // explorer에서 돌아가지 않는다.
			method: 'POST',
			headers: {
				'Content-type': 'application/json',
			},
			body: JSON.stringify(param) // 객체를 문자열로 바꿔주는 함수
		}).then(function(res) {
			console.log(res)
			return res.json()
		}).then(function(data) {
			proc(data)
		})
	}

	function proc(data) {
		switch (data.result) {
			case 0:
				alert('댓글 작성 실패하였습니다')
				return
			case 1:
				ctntElem.value = ''
				cmtObj.getCmtList()
				return
		}
	}
}



