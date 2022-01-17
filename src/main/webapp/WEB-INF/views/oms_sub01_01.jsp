<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>매장 리스트 조회</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub01_01.css" />
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub01_01.js"></script>
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
	$('#dcnm').val('');
	$('#dccd').val('');
	
	$('#sst').val('');
}
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title"><p>매장관리 > 매장리스트조회</p></div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch();'>
				<c:if test="${UAccount.USER_LV ne 'S'}">
					<p id='snm_p'>매장명</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm'  readonly /><img src="./resources/img/icon_search.png" id='snm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UAccount.USER_LV eq 'S'}">
						<input type='hidden' id='scd' name='scd' value='${UAccount.CPN_CD }' />
					</c:when>
					<c:otherwise>
						<input type='hidden' id='scd' name='scd'/>
					</c:otherwise>
				</c:choose>
				<c:if test="${UAccount.USER_LV ne 'H'}">
					<p id='ocnm_p'>채널사명</p>
					<div class="search_form">
						<input class='search_box' type="search" id='ocnm' name='ocnm' readonly /><img src="./resources/img/icon_search.png"
							id='ocnm_img' /> <input type='hidden' id='occd' name='occd'/>
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UAccount.USER_LV eq 'H'}">
						<input type='hidden' id='occd' name='occd' value='${UAccount.CPN_CD }'/>
					</c:when>
					<c:otherwise>
						<input type='hidden' id='occd' name='occd'/>
					</c:otherwise>
				</c:choose>
				<p id='dcnm_p'>배달대행사명</p>
				<div class="search_form">
					<input class='search_box' type="search" id='dcnm' name='dcnm' readonly /><img src="./resources/img/icon_search.png"
						id='dcnm_img' />
				</div>
				<input type='hidden' id='dccd' name='dccd'/>
				<select class='select_box' id="sst" name='sst'>
					<option value='' selected>--업체상태--</option>
					<c:forEach var="stcode" items="${stcode }">
						<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
					</c:forEach>
				</select>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class="btn_div">
				<c:if test="${UAccount.USER_LV eq 'A' || UAccount.USER_LV eq 'M'}">
					<button type="button" class='btn_excel' id="excel_upload" onclick='upload_click();'>업로드</button>
				</c:if>
				<button type="button" class='btn_excel' id="excel_download" onclick="ExcelDown();" disabled>엑셀 다운로드</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<section class='main_div'>
			<table id="ajaxTable">
				<thead>
					<tr>
						<th class='mtitle'>순번</th>
						<th class='mtitle'>매장코드</th>
						<th class='mtitle'>매장명</th>
						<th class='mtitle'>주소</th>
						<th class='mtitle'>매장상태</th>
						<th class='mtitle'>매장전화</th>
						<th class='mtitle'>휴대폰번호(FC)</th>
						<th class='mtitle'>영업시작시간</th>
						<th class='mtitle'>영업종료시간</th>
						<th class='mtitle'>채널사명</th>
						<th class='mtitle'>배달대행사명</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</section>
		<div class="page_nav">
			<ul class="paging"></ul>
		</div>
		
		<div class="hide_div">
			<form name="excelUploadForm" id="excelUploadForm">
				<div class="filebox"> 
					<label for="excelFile">파일선택</label> 
					<input type="file" id="excelFile" name="excelFile" class="upload-hidden"> 
					<input class="upload-name" id="upload_name" disabled="disabled"> 
				</div>
				<!-- <input type="file" id="excelFile" name="excelFile" value="엑셀 업로드" /> -->
				<div class="upload_div2">    
				    <button type="button" class='btn_excel' id="btn_excel1" onclick='excelupload();'>파일 업로드</button>
					<button type="button" class='btn_excel' id="btn_excel2" onclick='sampleDownload();'>양식 다운로드</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>