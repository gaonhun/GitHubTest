// 네비게이션 바
$(document).ready(function() {
	//버튼 포커스 해제
	$('.btn_search').focus(function() {
		$('.btn_search').blur();
	});
	$('.btn_excel').focus(function() {
		$('.btn_excel').blur();
	});

	//세션 없을때 로그인페이지로
	/*var sessionUAccount = '<%=session.getAttribute("UAccount")%>';
	if (sessionUAccount == '' || sessionUAccount == 'null') {
		var reLogin = confirm('세션이 만료되었습니다. 로그인 창으로 이동됩니다.');
		if (reLogin) { location.href = '/Gaon_OMS/oms_index'; }
	}*/

	//돋보기 클릭 시 검색창 띄우기

	let width = '1000';
	let height = '550';

	// 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
	let left = Math.ceil((window.screen.width - width) / 2);
	let top = Math.ceil((window.screen.height - height) / 4);

	let option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

	$("img").click(function() {
		if ($(this).attr("id") == "snm_img") {  //매장명 검색

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#snm').focus();

			let url = "oms_sub_search01";
			let name = "매장 상세 검색";
			let parent = window.open(url, name, option);

		} else if ($(this).attr("id") == "ocnm_img") { //체널사명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#ocnm').focus();

			let url = "oms_sub_search02";
			let name = "주문 상세 검색";
			window.open(url, name, option);

		} else if ($(this).attr("id") == "dcnm_img") {  //배송채널명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#dcnm').focus();

			let url = "oms_sub_search03";
			let name = "배송 상세 검색";
			window.open(url, name, option);

		} else if ($(this).attr("id") == "pnm_img") { //상품명

			const inptNm = $(this).attr("id").replace("_img", "");
			$('#inptNm').val(inptNm);
			$('#pnm').focus();

			let url = "oms_sub_search04";
			let name = "상품 상세 검색";
			window.open(url, name, option);
		}
	});

	$(".btn_reg").click(function() {
		width = '835';
		height = '365';

		top = Math.ceil((window.screen.height - height) / 4);
		option = "width =" + width + ", height = " + height + ", left = " + left + ", top = " + top + ", location = no";

		let url = "oms_sub05_01_insert";
		let name = "계정 등록";
		let parent = window.open(url, name);
	});

});

//엔터 키 입력 시 Search
function onEnterSearch() {
	let keyCode = window.event.keyCode;

	if (keyCode == 13) {
		search_go();
	}
}

//null 체크 함수
function isEmpty(value) {
	if (value == "" || value == " " || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return true
	} else {
		return false
	}
};

//not Null 체크 함수
function isNotEmpty(value) {
	const trimVal = value.trim();
	if (value == "" || value == " " || value == null || value == undefined || (value != null && typeof value == "object" && !Object.keys(value).length)) {
		return false
	} else {
		return true
	}
};

//numberformat 함수
function moneyFormat(num) {

	if (!num) return "0";

	let regexp = /\B(?=(\d{3})+(?!\d))/g;

	return num.toString().replace(regexp, ',');
}

function disMoneyFormat(num){
	let dis_amt = num;
	
	if (!dis_amt) return "0";

	let regexp = /\B(?=(\d{3})+(?!\d))/g;

	if(dis_amt == 0){
		dis_amt = dis_amt.toString().replace(regexp, ',');
	}else {
		dis_amt = "-"+dis_amt.toString().replace(regexp, ',');
	}

	return dis_amt;
}
//dateFormat 함수
function dateFormat(num) {

	if (!num) return "";

	let formatNum = '';

	// 공백제거
	num = num.replace(/\s/gi, "");

	try {
		if (num.length == 8) {
			formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
		}
	} catch (e) {
		formatNum = num;
		console.log(e);
	}
	return formatNum;
}

//unixTimeStamp 변환 함수
function unixTimeFormat(num) {

	if (!num) return "";

	const date = new Date(num);
	const year = date.getFullYear();
	const month = "0" + (date.getMonth() + 1);
	const day = "0" + date.getDate();
	const hour = "0" + date.getHours();
	const minute = "0" + date.getMinutes();
	const second = "0" + date.getSeconds();

	const formatDate = year + "-" + month.substr(-2) + "-" + day.substr(-2) + " " + hour.substr(-2) + ":" + minute.substr(-2) + ":" + second.substr(-2);

	return formatDate;
}

//phoneNumber 변환 함수
function phoneNumberFormat(num) {

	if (!num) return "";

	let formatNum = '';

	if (num.length == 12) {
		formatNum = num.replace(/(\d{4})(\d{4})(\d{4})/, "$1-$2-$3");

	} else if(num.length == 8){
		formatNum = num.replace(/(\d{4})(\d{4})/, "$1-$2");
	} else if(num.length == 10){
		if(num.substr(0,2) == "02"){
			formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, "$1-$2-$3");
		}else{
			formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
		}
	}else{
			formatNum = num.replace(/(\d{2,3})(\d{3,4})(\d{4})/, "$1-$2-$3");
		}

	return formatNum;
}

function dateStringFormat(num) {

	if (!num) return "";

	let formatNum = '';

	if (num.length == 8) {
		formatNum = num.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
	}

	return formatNum;
}

//javascript POST 전송
function post(path, params, method = 'post') {

	const form = document.createElement('form');
	form.method = method;
	form.action = path;

	for (const key in params) {
		if (params.hasOwnProperty(key)) {
			const hiddenField = document.createElement('input');
			hiddenField.type = 'hidden';
			hiddenField.name = key;
			hiddenField.value = params[key];

			form.appendChild(hiddenField);
		}
	}

	document.body.appendChild(form);
	form.submit();
}

//2차원 배열 중복 제거
function multiDimensionalUnique(arr) {
	var uniques = [];
	var itemsFound = {};
	for (var i = 0, l = arr.length; i < l; i++) {
		var stringified = JSON.stringify(arr[i]);
		if (itemsFound[stringified]) { continue; }
		uniques.push(arr[i]);
		itemsFound[stringified] = true;
	}
	return uniques;
}

//매장 번호 하이폰
function inputTelNumber(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let tel = "";
	if (number.length < 4) {
		return number;
	} else if (number.length < 9) {
		tel += number.substr(0, 4);
		tel += "-";
		tel += number.substr(4);
	} else if (number.length < 10) {
		tel += number.substr(0, 2);
		tel += "-";
		tel += number.substr(2, 3);
		tel += "-";
		tel += number.substr(5);
	} else if (number.length < 11){
		if(number.substr(0,2) == "02"){
			tel += number.substr(0, 2);
			tel += "-";
			tel += number.substr(2, 4);
			tel += "-";
			tel += number.substr(6, 4);
		}else{
			tel += number.substr(0, 3);
			tel += "-";
			tel += number.substr(3, 3);
			tel += "-";
			tel += number.substr(6, 4);
		}
	}else{
		tel += number.substr(0, 3);
		tel += "-";
		tel += number.substr(3, 4);
		tel += "-";
		tel += number.substr(7);
	}
	obj.value = tel;
}

//핸드폰 번호 하이폰
function inputPhoneNumber(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let phone = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 7) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3);
	} else if (number.length < 11) {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 3);
		phone += "-";
		phone += number.substr(6);
	} else {
		phone += number.substr(0, 3);
		phone += "-";
		phone += number.substr(3, 4);
		phone += "-";
		phone += number.substr(7);
	}
	obj.value = phone;
}

//시간 입력
function inputTime(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let time = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 5) {
		time += number.substr(0, 2);
		time += ":";
		time += number.substr(2);
	}
	obj.value = time;
}

//날짜 입력
function inputDate(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let date = "";

	if (number.length < 5) {
		return number;
	} else if (number.length < 7) {
		date += number.substr(0, 4);
		date += "-";
		date += number.substr(4);
	} else {
		date += number.substr(0, 4);
		date += "-";
		date += number.substr(4, 2);
		date += "-";
		date += number.substr(6);
	}
	obj.value = date;
}

//브레이크 타임 입력
function inputbreak(obj) {
	const number = obj.value.replace(/[^0-9]/g, "");
	let time = "";

	if (number.length < 4) {
		return number;
	} else if (number.length < 5) {
		time += number.substr(0, 2);
		time += "-";
		time += number.substr(2);
	}
	obj.value = time;
}
// 지번, 도로명 주소 하나만 return
/*function addrCheck(land, road){
	if(isNotEmpty(land)){
		return land;
	}else if(isNotEmpty(road)){
		return road;
	}
}

function addrDtCheck(landDt, roadDt){
	if(isNotEmpty(landDt)){
		return landDt;
	}else if(isNotEmpty(roadDt)){
		return roadDt;
	}
}*/

//핸드폰 번호 안심/기본 체크
function phoneCheck(basic, safe) {
	if (isNotEmpty(safe)) {
		return phoneNumberFormat(safe);
	}else if (isNotEmpty(basic)) {
		return phoneNumberFormat(basic);
	} else{
		return "";
	}
}

function errCodeCheck(errCd) {
	let errMsg = "";
	if (errCd == '1') {
		errMsg = "수정에 실패했습니다.";
	} else if (errCd == '2') {
		errMsg = "업로드에 실패했습니다.";
	} else if (errCd == '12899') {
		errMsg = "문자열의 값이 너무 큽니다.";
	} else if (errCd == '01722') {
		errMsg = "문자가 부적합합니다.";
	} else if (errCd == '00001') {
		errMsg = "중복된 데이터가 존재합니다.";
	} else if (errCd == '01407') {
		errMsg = "비어있는 값이 존재합니다.";
	} else if (errCd == '01403') {
		errMsg = "DB에 데이터가 존재하지 않습니다.";
	}  else {
		return false;
	}
	return errMsg;
}


jQuery.fn.serializeObject = function() {
	var obj = null;
	try {
		if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			var arr = this.serializeArray();
			if (arr) {
				obj = {};
				jQuery.each(arr, function() {
					obj[this.name] = this.value;
				});
			}//if ( arr ) {
		}
	} catch (e) {
		alert(e.message);
	} finally {
	}

	return obj;
};