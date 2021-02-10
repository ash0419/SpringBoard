'use strict';

function chkEmptyEle(ele, eleNm) {
	if (ele.value == '') {
		alert(eleNm + '을(를) 입력해 주세요')
		ele.focus();
		return true;
	}
	return false;
}

// 뒤로가기
function back() {
	history.back()
}
