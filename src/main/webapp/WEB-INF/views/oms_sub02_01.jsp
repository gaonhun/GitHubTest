<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>메뉴 리스트 조회</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub02_01.css">
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub02_01.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>

<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">메뉴관리 > 메뉴리스트조회</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>
				<c:if test="${UAccount.USER_LV ne 'S'}">
					<p id='snm_p'>매장명</p>
					<div class="search_form" id="snm_form">
						<input class='search_box' type="search" id='snm' name='snm' readonly /><img src="./resources/img/icon_search_hover.png" id='snm_img' />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${UAccount.USER_LV eq 'S'}">
						<input type='text' id='scd' name='scd' value='${UAccount.CPN_CD }' hidden />
					</c:when>
					<c:otherwise>
						<input type='text' id='scd' name='scd' hidden />
					</c:otherwise>
				</c:choose>
				<p id='pnm_p'>메뉴명</p>
				<div class="search_form">
					<input class='search_box' type="search" id='pnm' name="pnm" readonly /><img src="./resources/img/icon_search.png"
						id='pnm_img' /> <input type='text' id='pcd' name='pcd' hidden />
				</div>
				<select class='select_box' id="etyp" name="etyp">
					<option value='' id="etyp_p" selected>--메뉴상태--</option>
					<c:forEach var="plcode" items="${plcode }">
						<option value='${plcode.DEFINITION_CD }'>${plcode.DEFINITION_NM }</option>
					</c:forEach>
				</select>
	 			<select class='select_box' id='sinp' name="sinp">
					<option value='' id="sinp_p" selected>--매장 내 카테고리--</option>
					<c:forEach var='category' items="${category }">
						<option value='${category.CTGR_CD }'>${category.CTGR_NM }</option>
					</c:forEach>
				</select>
				<button type="button" class='btn_refresh' onclick = "refresh();" title="검색 초기화"></button>
			</div>
			<div class='btn_div'>
				<c:if test="${UAccount.USER_LV eq 'A' || UAccount.USER_LV eq 'M'}">
					<button type="button" class='btn_excel' id="excel_upload" onclick='upload_click();'>업로드</button>
				</c:if>
				<button type="button" class='btn_excel' id="excel_download" onclick='ExcelDown();' disabled>엑셀 다운로드</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
		<table id="ajaxTable">
			<thead>
				<tr>
					<th class='mtitle'>순번</th>
					<th class='mtitle'>메뉴코드</th>
					<th class='mtitle'>메뉴명</th>
					<th class='mtitle'>단가(원)</th>
					<th class='mtitle'>메뉴상태</th>
					<th class='mtitle'>상태수정일시</th>
					<th class='mtitle'>매장 내 카테고리명</th>
					<th class='mtitle'>등록자명</th>
					<th class='mtitle'>등록일시</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
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
			    <button type="button" class='btn_excel' id="btn_excel1" onclick='excelupload();'>전체 업로드</button>
			    <button type="button" class='btn_excel' id="btn_excel2" onclick='excelpartupload();'>일부 업로드</button>
				<button type="button" class='btn_excel' id="btn_excel2" onclick='sampleDownload();'>양식 다운로드</button>
			</div>
			</form>
		</div>
	</div>
</body>
</html>
