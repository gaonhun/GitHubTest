<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주문채널사 정산</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub04_01.css" />
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
//수정모드 true/false
let editStat = false;
//수정된 값 Array
let modiArr = new Array();
//수정된 Index Array
let rowIdx = new Array(); // 해당 행 저장

//오늘, 일주일, 1개월 css
$(document).ready(function(){
    $('#dateType1').focus(function() {
        $(this).addClass('active_button');
		$('#dateType2').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
    });    
    $('#dateType2').focus(function() {
        $(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
    });
    
    $('#dateType3').focus(function() {
        $(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType2').removeClass('active_button');
    });
});

// datepicker 선언, 날짜 저장 or 새로 불러오기
$(document).ready(function() {
    $("#sdate").datepicker();
    $("#edate").datepicker();
    
    const sdate = "${sdate}";
    const edate = "${edate}";
    
    if(isNotEmpty(sdate) && isNotEmpty(edate)){
	    $("#sdate").val(sdate);
	    $("#edate").val(edate);
    }
    
    const sval = $("#sdate").val();
    const eval = $("#edate").val();
    
    if(isEmpty(sval) && isEmpty(eval)){
	    $("#sdate").datepicker("setDate", new Date());
    	$("#edate").datepicker("setDate", new Date());
	}
});

// 오늘, 일주일, 1개월 기능
function setSearchDate(start){
    const edate = $('#edate').val();
    const rpldate = edate.split('-');

    const edt = new Date(rpldate[0],rpldate[1]-1,rpldate[2]); //edate 날짜

    d = new Date();

    if(start == '1d') {
        d = $.datepicker.formatDate('yy-mm-dd', d)
        $('#sdate').val(d);
        $('#edate').val(d); 
    } else if(start == '7d') {
        d = new Date(edt.getFullYear(), edt.getMonth(), edt.getDate() - 7);
    } else if(start == '1m') {
        d = new Date(edt.getFullYear(), edt.getMonth() - 1, edt.getDate());
    }
    
    const endDate = $.datepicker.formatDate('yy-mm-dd', d);
    $('#sdate').val(endDate);
}

//수정 버튼 클릭시
function edit(){
	$('#edit_button').css('display', 'none');
	$('#cancel_button').css('display', 'inline');
	$('#save_button').css('display', 'inline');
	$(".ajaxTr").addClass("row");
	//수정모드 true
	editStat = true;
	
	swal("수정 모드가 켜졌습니다.");
}
//취소 버튼 클릭시
function cancel(){
	$('#cancel_button').css('display', 'none');
	$('#save_button').css('display', 'none');
	$('#edit_button').css('display', 'inline');  
	$(".ajaxTr").removeClass("row");
	$(".editCompleted").removeClass("editCompleted");
	//editForm 리셋
	document.editForm.reset();
	//Array 리셋
	rowIdx.length = 0;
	modiArr.length = 0;
	//수정모드 false
	editStat = false;
	//수정 Array 초기화
	//editArr.fill();
}

//마우스 over 시 input줄 회색
$(document).on("mouseover", "tr", function() {
	if(editStat){
	    const tr = $(this);
	    const td = tr.children();
	    const input = td.children();
	    
	    //해당 input이 readonly일 경우만 addClass
	    if($(input).is('[readonly]')){
	    	$(input).addClass('input_hover');
	    }
	}
});
//마우스 leave시 원래대로
$(document).on("mouseleave", "tr", function() {
	if(editStat){
	    const tr = $(this);
	    const td = tr.children();
	    const input = td.children();
	   	
	    $(input).removeClass('input_hover');
	}
});

//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
$(document).on("click", "td", function() {	
	if(editStat){
	    const tdOne = $(this);
		const inputOne = tdOne.children();
	    
		const tr = tdOne.parent();
		const td = tr.children();
	    const input = td.children();
	    
	    $(inputOne).attr("readonly", false);
	    $(inputOne).addClass("writeonly");
	    $(tr).css("pointer-events", "none");
	    
	    $(inputOne).focus();
	    
	    //console.log(input.eq(0).val());
	    
    	/* input.each(function(i) {
	    	befoArr.push(input.eq(i).val());
		}); */
		
		$(inputOne).on("change", function() {
	    	let editArr = new Array();
	    	
	    	$(tr).css("pointer-events", "auto");
	    	$(input).removeClass("writeonly");
	    	$(input).attr("readonly", true);

	    	/* input.each(function(i) {
	    		editArr.push(input.eq(i).val());
			});
	    	
		    $(inputOne).focusout(function(){
		    	modiArr.push(editArr);
			}); */
		    
		    $(input).addClass("editCompleted");
		    $(td).addClass("editCompleted");

			rowIdx.push($(this).closest('tr').index()+1); // th 때문에 tr index +1
			
			rowIdx = multiDimensionalUnique(rowIdx);
		});
	}
});

//input에서 focusout시 원래대로
$(document).on("focusout", "input", function(){
	const inputOne = $(this);
    const tdOne = inputOne.parent();
    
	const tr = tdOne.parent();
	const td = tr.children();
    const input = td.children();

	$(tr).css("pointer-events", "auto");
	$(input).removeClass("writeonly");
	$(input).attr("readonly", true);

});

//저장시 수정된 배열 전송
function save(){
	
	if(confirm("저장하시겠습니까?")){
		
	}else{
		return
	}

	const temp = ["temp"];
	
	for(let i = 0; i < rowIdx.length; i++){ // 수정된 row 가져오기
		const tempArr = new Array();
	
		const input = $('tr').eq(rowIdx[i]).children().children(); // 수정된 row의 input 가져오기
		
		input.each(function(i) { // input 값 tempArr배열에 저장
			tempArr.push(input.eq(i).val());
		});
		
		//console.log("tempArr" + tempArr);
		
		modiArr.push(tempArr);
		
	}
		if(modiArr.length == 1){
			modiArr.push(temp);
		}
	
	
	let modifyArr = multiDimensionalUnique(modiArr);
	
	console.log(modifyArr);
	
	if(isEmpty(modifyArr)){
		swal({
				text: "수정 된 데이터가 없습니다.",
				icon: "warning"
		});
		
		cancel();
		
		return
	}else {
		$.ajax ({
			url: 'oms_sub04_01edit',
			dataType: 'json',
		    type: 'POST',
		    traditional: true,
		    async : false,
		    data: {
		    	modifyArr
		    },
		    success: function (data) {
		    	swal({
					text: "저장되었습니다.",
					icon: "success"
				});
		    	
		    	$('#cancel_button').css('display', 'none');
		    	$('#save_button').css('display', 'none');
		    	$('#edit_button').css('display', 'inline');  
		    	$(".ajaxTr").removeClass("row");
		    	$(".editCompleted").removeClass("editCompleted");
		    	//editForm 리셋
		    	document.editForm.reset();
		    	//Array 리셋
		    	rowIdx.length = 0;
		    	modiArr.length = 0;
		    	//수정모드 false
		    	editStat = false;
		    	
		    	search_go();
		    	
	        },
	        error: function(e) { 
	        	swal({
	   				text: "수정 실패했습니다.",
	   				icon: "error"
	    		});
	        	modiArr.length = 0;
		    	modifyArr.length = 0;
		    	rowIdx.length = 0;
		    	
		    	cancel();
	        }
		});
	}
}


//엑셀 파일 체크
function checkFileType(filePath) {
    var fileFormat = filePath.split(".");
    if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1) {
        return true;
    } else {
        return false;
    }

}

function excelupload() {
    var file = $("#excelFile").val();
    var form = $('#excelUploadForm')[0];

    // FormData 객체 생성
    var formData = new FormData(form);
 
    if (file == "" || file == null) {
    	swal("파일을 선택해주세요.");
        
        return false;
    } else if (!checkFileType(file)) {
    	swal({
				text: "엑셀 파일만 업로드 가능합니다.",
				icon: "warning"
		});
        $("#excelFile").val('');
        
        return false;
    }

    if (confirm("업로드 하시겠습니까?")) {
    	
    	$.ajax ({
    		url: 'excelUploadAjax',
    		enctype:"multipart/form-data",
    	    type: 'POST',
    	    data: formData,
    	    processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
    	    success: function (data) {
    	    	swal({
    				text: "\"" + data.fileName + "\" 파일이 정상 업로드 되었습니다.",
    				icon: "success"
    			});
            }, 
            error: function(e) { 
            	swal({
	    				text: "업로드에 실패했습니다.",
	    				icon: "error"
	    		});
            }
    	});
    }
    $("#excelFile").val('');
}


//엑셀 다운
function exceldown() {
	const form = document.excelform;
	const mainDiv = $(".main_div").html();
	
	$("#excelHtml").val(mainDiv);
    
    form.method = "POST";
	form.action = "oms_sub01_01excel";
    form.submit();
}

//검색 쿼리
function search_go(num) {
	/* if(editStat) {
		swal({
			text: "수정 모드에서 조회할 수 없습니다.",
			icon: "warning"
		});
		return
	}
	
	if(isEmpty(num)){
		$.blockUI({ 
			message: '<img src="./resources/img/loading_spiner.png"/>',
			css: {
				backgroundColor: 'rgba(0,0,0,0.0)',
				color: '#000000', 
				border: '0px solid #a00'
			} 
		});
	}
	
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const dccd = $('#dccd').val();
	const sst = $('#sst').val();
	const pageNum = num;
	
	$.ajax ({
		url: 'oms_sub04_01_process',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    		scd,
	    		occd,
	    		dccd,
	    		sst,
	    		pageNum
	    		},
	    success: function (data) {
	    	
			$('.ajaxTr').remove();		
			$('.paging').children().remove();
			$.unblockUI();
			
			const a = '';
			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;
			
			let obLength = Object.keys(data.storeList).length;
			
			if(obLength == 0){
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$(".btn_excel").attr("disabled", true);
			}else if(obLength > 0){
				
		    	$(data.storeList).each(function(i,e){
		    		
		    		$('tbody').append('<tr class="ajaxTr">' +
				    						   '<td><input type="text" value="' + e.sto_CD + '" name="STO_CD" class="tdInput" style="width:50px" readonly/></td>' + 
				    						   '<td><input type="text" value="' + e.sto_NM + '" name="STO_NM" class="tdInput" style="width:100px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.biz_ADDR + '" name="BIZ_ADDR" class="tdInput" style="width:300px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.store_STATE + '" class="tdInput" style="width:50px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.ceo_NM + '" class="tdInput" style="width:100px" readonly/></td>' +
				    						   '<td><input type="text" value="' + phoneNumberFormat(e.biz_TEL) + '" class="tdInput" style="width:120px" readonly/></td>' +
				    						   '<td><input type="text" value="' + phoneNumberFormat(e.biz_HPNO) + '" class="tdInput" style="width:120px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.day_START_TIME + '" class="tdInput" style="width:120px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.day_END_TIME + '" class="tdInput" style="width:120px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.franchise_NAME + '" class="tdInput" style="width:250px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.delivery_VENDOR_NAME + '" class="tdInput" style="width:200px" readonly/></td>' +
				    						   '<td><input type="text" value="' + e.reg_DT + '" class="tdInput" style="width:120px" readonly/></td>' +
				    						'</tr>'
		    		);
		    	});
		    	if(prev){
	    			$('.paging').append('<li><a href="#" onclick="search_go(' + 1 +'); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
	    			$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) +'); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
	    		}
		    	for (let num = startPage; num <= endPage; num++) {
	                if (num == page) {
	                	$('.paging').append('<li><a href="#" onclick="search_go('+ num +'); return false;" class="page-btn active">' + num + '</a></li>');
	                } else {
	                	$('.paging').append('<li><a href="#" onclick="search_go('+ num +'); return false;" class="page-btn">' + num + '</a></li>');
	                }
	             }
		    	if(next){
		    		$('.paging').append('<li><a href="#" onclick="search_go('+ (endPage + 1) +'); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
		    		$('.paging').append('<li><a href="#" onclick="search_go('+ totalPage +'); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
	    		}
				$(".btn_excel").attr("disabled", false);
			}
        }, 
        error: function(e) { 
        	alert("데이터를 가져오지 못했습니다."); 
        	$(".btn_excel").attr("disabled", true);
        	$.unblockUI();
        } 
	}); */
}

</script>
<body>
	<div id="nav_bar"></div>
	<div class="overlay"></div>
	<header>
		
		<div id="header_title"><p>매장관리&nbsp; &nbsp;>&nbsp; &nbsp;주문채널사 정산</p></div>
		<img src="./resources/img/top_logo.png" id="top_logo" />
	</header>
	<section class='div_fheader'>
		<div class='search_div'>
			<p>주문일자</p>
			<input type="button" name="day" id="dateType1" class="dateType1 active_button" onclick="setSearchDate('1d')" value="오늘" /><input
				type="button" name="week" id="dateType2" class="dateType2" onclick="setSearchDate('7d')" value="일주일" /><input
				type="button" name="month" id="dateType3" class="dateType3" onclick="setSearchDate('1m')" value="1개월" /> <input
				type='text' class='input_date' id='sdate' name='sdate' autocomplete='off' /> ~ <input type='text'
				class='input_date' id='edate' name='edate' autocomplete='off' />
		</div>
		<div class="btn_div">
			<button type="button" class='btn_excel' onclick='ExcelDown();' disabled>엑셀 다운로드</button>
		</div>
	</section>
	<section class='div_header'>
		<div class='search_div' onkeyup='onEnterSearch();'>
			<p id='ocnm_p'>주문채널명</p>
			<div class="search_form" id="ocnm_form">
				<input class='search_box' type="search" id='ocnm' name='ocnm' readonly /><img src="./resources/img/icon_search_hover.png"
					id='ocnm_img' />
			</div>
			<input type='text' id='occd' name='occd' hidden />
			<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
			<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
			<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
		</div>
		<div class="btn_div">
			<button type="button" class='btn_search' onclick="search_go();">조회</button>
		</div>
		<div class="upload_div">
		<form name="excelUploadForm" id="excelUploadForm">
		    <input type="file" id="excelFile" name="excelFile" value="엑셀 업로드" />
			<button type="button" class='btn_excel' id="btn_excel" onclick='excelupload();'>업로드</button>
		</form>
		</div>
	</section>
	<section class='main_div'>
		<form name="editForm">
			<table id="ajaxTable">
				<thead>
					<tr>
						<th class='mtitle'>주문채널명</th>
						<th class='mtitle'>건수 합계 (정산 - 취소)</th>
						<th class='mtitle'>금액 합계 (정산 - 취소)</th>
						<th class='mtitle'>정상건수</th>
						<th class='mtitle'>정상금액(원)</th>
						<th class='mtitle'>취소건수</th>
						<th class='mtitle'>취소금액(원)</th>
						<th class='mtitle'>주문채널사 수수료</th>
					</tr>
				</thead>

				<tbody>
				</tbody>
			
			</table>
		</form>
	</section>
	<div class="page_nav">
		<ul class="paging"></ul>
	</div>
	<form name="excelform">
		<input type="hidden" id="excelHtml" name="excelHtml">
	</form>
</body>
</html>