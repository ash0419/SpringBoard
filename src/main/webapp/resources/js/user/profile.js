// 기본 이미지 사용 (프로필 이미지 삭제)
function delProfileImg() {
	axios.get('/user/delProfileImg.korea').then(function(res) {
		var basicProfileImg = '/res/img/basic_profile.jpg';

		if (res != null && res.status == 200) {
			if (res.data.result == 1) { // 프로필 이미지 삭제가 완료됨
				var img = document.querySelector('#profileImg');
				var container = document.querySelector('#delProfileBtnContainer');

				img.src = basicProfileImg;
				container.remove();
			}
		}
	}).catch(function(err) {
		console.error('err 발생 : ' + err);
	})
}

var inputImgElem = document.querySelector('#inputImg')
function upload() {
	if (inputImgElem.files.length === 0) {
		alert('이미지를 선택해 주세요')
		return
	}

	ajax()

	function ajax() {
		var formData = new FormData()
		for (var i = 0; i < inputImgElem.files.length; i++) {
			formData.append('imgs', inputImgElem.files[i])
		}
		fetch('/user/profileUpload', {
			method: 'post',
			body: formData
		}).then(res => res.json())
		.then(myJson => {
			if(myJson === 1) {
				getData()
			}else {
				alert('이미지 업로드 실패')
			}
		})
	}
}

var splide = null
var centerContElem = document.querySelector('.centerCont')
function getData() {
	fetch('/user/profileData')
		.then(res => res.json())
		.then(myJson => {
			proc(myJson)
		})

	function proc(myJson) {
		const div = document.createElement('div')
		div.classList.add('profileBox')
		let imgOption = ''
		let delProfileHTML = ''
		const imgBasicSrc = `/res/img/basic_profile.jpg`
		
		if (myJson.profile_img) {
			imgSrc = `/res/img/user/${myJson.i_user}/${myJson.profile_img}`
			imgOption = ` onclick="clkProfile()" class="pointer" `
			delProfileHTML = `
				<div id="delProfileBtnContainer">
					<button onclick="delProfileImg();">기본이미지 사용</button>
				</div>
			`
		}

		let gender = '여'
		if (myJson.gender === 1) {
			gender = '남'
		}

		div.innerHTML = `
			<div class="circular--landscape circular--size200">
				<img id="profileImg" src="${imgSrc}" ${imgOption} alt="프로필 이미지" onerror ="this.onerror = null; this.src='${imgBasicSrc}'">
			</div>
			<div>
				<div>아이디 : ${myJson.user_id}</div>
				<div>이름 :${myJson.nm}</div>
				<div>성별 :${gender}</div>
				<div>전화번호 :${myJson.ph}</div>
			</div>
			${delProfileHTML}
		`
		centerContElem.innerHTML = null
		centerContElem.append(div)
	}
}

getData()

var modalContainerElem = document.querySelector('.modalContainer')
function clkProfile() {
	openModal()
	getProfileImgList()
}

//프로필 이미지 리스트 가져오기
function getProfileImgList() {
	fetch('/user/profileImgList')
		.then(res => res.json())
		.then(myJson => {
			profileImgCarouselProc(myJson)
		})
}

// 프로필 이미지 삭제
function delProfileImg({ i_img, img }) {
	return new Promise(resolve => {
		fetch(`/user/profileImg?i_img=${i_img}&img=${img}`, {
			method: 'delete'
		})
			.then(res => res.json())
			.then(myJson => {
				resolve(myJson)
			})
	})
}

function profileImgCarouselProc(myJson) {
	console.log(myJson)
	var splideList = document.querySelector('.splide__list')
	splideList.innerHTML = null
	myJson.forEach(function(item) {
		const div = document.createElement('div')
		div.classList.add('splide__slide')
		const img = document.createElement('img')

		const span = document.createElement('span')
		//innerText innerHtml은 기존에 있던걸 날림 append는 기존에 있는거에 덫붙임
		span.classList.add('pointer')
		span.append('X')
		span.addEventListener('click', () => {
			delProfileImg(item).then(myJson => {
				if (myJson ===1) {
					div.remove()
					splide.refresh()
				} else {
					alert('삭제를 실패하였습니다.')
				}
			})
		})

		img.src = `/res/img/user/${item.i_user}/${item.img}`
		div.append(img)
		div.append(span)
		splideList.append(div)
	})
	if(splide != null) {
		splide.destroy(completely = true)
	}
	splide = new Splide('.splide').mount()
}

function openModal() {
	modalContainerElem.classList.remove('hide')
}

function closeModal() {
	modalContainerElem.classList.add('hide')
}



