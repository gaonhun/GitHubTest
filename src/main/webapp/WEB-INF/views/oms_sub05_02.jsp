  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>채널사 관리</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub05_02.css" />
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
let mdfForm = new Array();

$(function(){
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
	});
	
});

$(document).on("change", ".reg_input", function() {
	mdfForm.push($(this).closest('form').attr('id'));

	mdfForm = multiDimensionalUnique(mdfForm);
});

//검색 쿼리
function search_go(num) {
	if (isEmpty(num)) {
		$.blockUI({
			message: '<img src="./resources/img/loading_spiner.png"/>',
			css: {
				backgroundColor: 'rgba(0,0,0,0.0)',
				color: '#000000',
				border: '0px solid #a00'
			}
		});
	}

	const cpncd = $('#occd').val();
	const sst = $('#sst').val();
	const pageNum = num;

	$.ajax({
		url: 'oms_sub05_02_process',
		dataType: 'json',
		type: 'POST',
		async: false,
		data: {
			cpncd,
			sst,
			pageNum
		},
		success: function(data) {

			$('.ajaxTr').remove();
			$('.paging').children().remove();
			$.unblockUI();

			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.ordChList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {

				$(data.ordChList).each(function(i, e) {

					$('#ajax_tbody').append('<tr class="row ajaxTr">' +
						'<td style="padding-right:2px">' + e.rn + '</td>' +
						'<td>' + e.cpn_CD + '</td>' +
						'<td>' + e.cpn_NM + '</td>' +
						'<td>' + e.cpn_STATE + '</td>' +
						'<td>' + e.cpn_BIZ_NO + '</td>' +
						'<td>' + e.ceo_NM + '</td>' +
						'<td>' + e.cpn_ADDR +' ' + e.cpn_ADDR_DT + '</td>' +
						'<td>' + phoneNumberFormat(e.cpn_TEL) + '</td>' +
						'<td>' + e.mng_NM + '</td>' +
						'<td>' + phoneNumberFormat(e.mng_HPNO) + '</td>' +
						'<td>' + e.email + '</td>' +
						'<td>' + e.k7_LINK_CD + '</td>' +
						/* '<td>' + e.in_APP + '</td>' + */
						'<td>' + unixTimeFormat(e.reg_DATE) + '</td>' +
						'</tr>'
					);
				});
				if (prev) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + 1 + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
				}
				for (let num = startPage; num <= endPage; num++) {
					if (num == page) {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn active">' + num + '</a></li>');
					} else {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn">' + num + '</a></li>');
					}
				}
				if (next) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + (endPage + 1) + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + totalPage + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
				}
				$("#excel_download").attr("disabled", false);
			}
		},
		error: function(e) {
			alert("데이터를 가져오지 못했습니다.");
			$("#excel_download").attr("disabled", true);
			$.unblockUI();
		}
	});
}

function refresh() {
	const userLv = "${UAccount.USER_LV}";
	if(userLv != 'H'){
		$('#ocnm').val('');
		$('#occd').val('');
	}
	$('#sst').val('');
}

function reg_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
}

function reg_submit(){
	const CPN_CD = $('#CPN_CD').val();
	const CPN_NM = $('#CPN_NM').val();
	const CPN_TEL = $('#CPN_TEL').val();
	
	if(isEmpty(CPN_CD) || isEmpty(CPN_NM) || isEmpty(CPN_TEL)){
		swal({
			text: "필수값을 입력해주세요.",
			icon: "warning"
		});
		
		return
	}
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			const regVal = $('#channel_reg').serializeObject();
			const CPN_TYPE = "Order";
			if(isEmpty(mdfForm)){
				swal({
					text: "입력 된 데이터가 없습니다.",
					icon: "warning"
				}).then(function() {
					reg_cancel();
					return
				});
		
			} else{
				$.ajax({
					url: 'channel_register',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					async: false,
					traditional: true,
					data: JSON.stringify({
						regVal,
						CPN_TYPE
					}),
					success: function(data) {
						swal({
							text: "저장되었습니다.",
							icon: "success"
						}).then(function() {
							mdfForm.length = 0;
							search_go();
							reg_cancel();
						});
					},
					error: function(request, status, error) {
						const errCd = request.responseText;
						let errMsg = errCodeCheck(errCd);
						if (errMsg == false) {
							swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
							location.reload();
							return;
						}
						swal({
							text: errMsg,
							icon: "error"
						}).then(function() {
							cancel();
						});
					}
				});
				
			}
		}
	});
}

function reg_cancel() {
	$('.overlay').fadeOut(100);
	$('.hide_div').removeClass('show_div');
	$('.reg_input').val('');
}

//상세정보 페이지 이동
$(document).on("dblclick", "td", function() {
	const checkBtn = $(this);

	const tr = checkBtn.parent();
	const td = tr.children();
	const cpncd = td.eq(1).text();

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "oms_sub05_02_detail";
	form.style.display = "none";

	let Inputcpncd = document.createElement("input");
	Inputcpncd.type = "text";
	Inputcpncd.name = "cpncd";
	Inputcpncd.value = cpncd;

	form.appendChild(Inputcpncd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);

});
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title"><p>시스템관리 > 채널사관리</p></div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch();'>
				<p id='ocnm_p'>채널사명</p>
				<div class="search_form">
					<input class='search_box' type="search" id='ocnm' name='ocnm' readonly /><img src="./resources/img/icon_search.png"
						id='ocnm_img' /> <input type='text' id='occd' name='occd' hidden />
				</div>
				<input type='text' id='occd' name='occd' hidden />
				<select class='select_box' id="sst" name='sst'>
					<option value='' selected >--업체상태--</option>
					<c:forEach var="stcode" items="${stcode }">
						<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
					</c:forEach>
				</select>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class="btn_div">
				<button type="button" class='btn_search' onclick='reg_click();'>채널사 등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<section class='main_div'>
			<form name="editForm">
				<table id="ajaxTable">
					<thead>
						<tr>
							<th class='mtitle'>순번</th>
							<th class='mtitle'>채널사코드</th>
							<th class='mtitle'>채널사명</th>
							<th class='mtitle'>업체상태</th>
							<th class='mtitle'>사업자번호</th>
							<th class='mtitle'>대표자명</th>
							<th class='mtitle'>주소</th>
							<th class='mtitle'>콜센터전화</th>
							<th class='mtitle'>담당자명</th>
							<th class='mtitle'>담당자휴대번호</th>
							<th class='mtitle'>이메일</th>
							<th class='mtitle'>채널사연동코드</th>
							<!-- <th class='mtitle'>인앱여부</th> -->
							<th class='mtitle'>등록일자</th>
						</tr>
					</thead>
					<tbody id="ajax_tbody">
					</tbody>
				</table>
			</form>
		</section>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
	<div class="hide_div">
		<form id="channel_reg">
			<p id="reg_title">채널사 등록</p>
			<div class="reg_box">
				<table>
					<tr>
						<th class='mtitle'>채널사코드<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_CD" name="CPN_CD" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>채널사명<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_NM" name="CPN_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>업체상태<p id="emp">*</p></th>
						<td>
							<select class="reg_select" name='CPN_STATE'>
								<c:forEach var="stcode" items="${stcode }">
									<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th class='mtitle'>대표자명</th>
						<td><input type="text" class="reg_input" name="CEO_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>사업자번호</th>
						<td><input type="text" class="reg_input" name="CPN_BIZ_NO" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>주소</th>
						<td><input type="text" class="reg_input" name="CPN_ADDR" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>콜센터전화<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" id="CPN_TEL" name="CPN_TEL" onKeyup="inputTelNumber(this);" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>담당자명</th>
						<td><input type="text" class="reg_input" name="MNG_NM" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>담당자휴대번호</th>
						<td><input type="text" class="reg_input" name="MNG_HPNO" onKeyup="inputPhoneNumber(this);" autocomplete='off'/></td>
					</tr>
					<tr>
						<th class='mtitle'>이메일</th>
						<td><input type="text" class="reg_input" name="EMAIL" autocomplete='off'/></td>  
					</tr>
					<!-- <tr>
						<th class='mtitle'>인앱여부<p id="emp">*</p></th>
						<td>
							<select class="reg_select" name='IN_APP'>
								<option value='Y' selected>Y</option>
								<option value='N'>N</option>
							</select>
						</td>  
					</tr> -->
					<tr>
						<th class='mtitle'>채널사연동코드<p id="emp">*</p></th>
						<td><input type="text" class="reg_input" name="K7_LINK_CD" autocomplete='off'/></td>  
					</tr>
				</table> 
			</div>
			<div class="upload_div2">    
			    <button type="button" class='btn_search' onclick='reg_submit();'>등록</button>
				<button type="button" class='btn_search' onclick='reg_cancel();'>취소</button>
			</div>
		</form>
	</div>
</body>
</html>