var dataElem = document.querySelector('#data')
var listFrmElem = document.querySelector('#listFrm')
var typElem = listFrmElem.typ
var searchTypeElem = document.querySelector('#searchType')
var searchTextElem = document.querySelector('#searchText') 

function getBoardList(page) {
	
	var searchTextValue = searchTextElem.value
	if(searchTextValue !== '') {
		var searchTypeValue = searchTypeElem.value
		
		listFrmElem.searchType.value = searchTypeValue
		listFrmElem.searchText.value = searchTextValue
	}
	
	var {typ} = dataElem.dataset
	// var typ = dataElem.dataset.typ

	typElem.value = typ

	if(page) {
		listFrmElem.page.value = page
	}
	
	listFrmElem.submit()
	
}

