<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>주문 내역 관리</title>
</head>
<!-- JQUERY DATE PICKER -->
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script src="./resources/datepicker/jquery-ui-1.12.1/datepicker-ko.js"></script>
<link rel="stylesheet" href="./resources/datepicker/jquery-ui-1.12.1/jquery-ui.min.css"/>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub03_01.css" />
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub03_01.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script>
function refresh() {
	const userLv = "${UAccount.USER_LV}";
	if(userLv != 'S'){
		$('#snm').val('');
		$('#scd').val('');
	}
	if(userLv != 'H'){
		$('#ocnm').val('');
		$('#occd').val('');
	}
	$('#ono').val('');
	$('#chono').val('');
}
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			
			<div id="header_title">주문내역관리 > 주문내역조회</div>
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
			<div class='search_div' onkeyup='onEnterSearch()'>
				<c:if test="${UAccount.USER_LV ne 'S'}">
					<p id='snm_p'>매장명</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search.png" id='snm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UAccount.USER_LV eq 'S'}">
						<input type='text' id='scd' value='${UAccount.CPN_CD }' hidden />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='scd' />
					</c:otherwise>
				</c:choose>
				<c:if test="${UAccount.USER_LV ne 'H'}">
					<p id='ocnm_p'>채널사명</p>
					<div class="search_form">
						<input class='search_box' type="search" id='ocnm' readonly /><img src="./resources/img/icon_search.png"
							id='ocnm_img' /> <input type='text' id='occd' hidden />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UAccount.USER_LV eq 'H'}">
						<input type='hidden' id='occd' value='${UAccount.CPN_CD }' />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='occd'/>
					</c:otherwise>
				</c:choose>
				<p id='ono_p'>중계주문번호</p>
				<div class="search_form2">
					<input type="search" class='search_box' type="search" id='ono' autocomplete="off" />
				</div>
				<p id='ono_p'>채널사주문번호</p>
				<div class="search_form2">
					<input class='search_box' type="search" id='chono' autocomplete="off" />
				</div>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class="btn_div">
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
			<table id="ajaxTable">
				<thead>
					<tr>
						<th class="mtitle">순번</th>
						<th class="mtitle">중계주문번호</th>
						<th class="mtitle">채널사주문번호</th>
						<th class="mtitle">매장코드</th>
						<th class="mtitle">매장명</th>
						<th class="mtitle">채널사명</th>
						<th class="mtitle">주문상태</th>
						<th class="mtitle">주문타입</th>
						<th class="mtitle">카드승인금액(A)</th>
						<th class="mtitle">배달비(B)</th>
						<th class="mtitle">할인금액(C)</th>
						<th class="mtitle">총매출액(A+B)</th>
						<th class="mtitle">순매출액(A+B-C)</th>
						<th class="mtitle">배달주소지</th>
						<th class="mtitle">고객전화번호</th>
						<th class="mtitle">배달대행사명</th>
						<th class="mtitle">배달상태</th>
						<th class="mtitle">등록일시</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
	</div>
</body>
</html>