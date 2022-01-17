
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>메뉴 리스트 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub02_01_detail.css">
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub02_01_detail.js"></script>
<script>

</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">메뉴관리 > 메뉴리스트조회 > 메뉴리스트상세</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class="btn_div">
			<p id="warn_p"><strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다. </p>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
				<c:if test="${UAccount.USER_LV eq 'M' || UAccount.USER_LV eq 'A'}">
					<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
					<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
					<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
				</c:if>
			</div>
		</section>
		<div class='main_div'>
			<form id="menu_master">
				<table>
					<tr>
						<th colspan='11' class='mtitle'>메뉴 기본 정보</th>
					</tr>
					<tr>
						<th class='stitle'>메뉴코드</th>
						<td colspan='2' style="width: 15%" id="MENU_CD" class="defTd">${pmdView.MENU_CD }</td>
						<th class='stitle'>메뉴명</th>
						<td colspan='2' style="width: 15%"><input type="text" class="tdInput" value="${pmdView.MENU_NM }" name="MENU_NM" readonly/></td>
						<th class='stitle'>매장 내 카테고리명</th>
						<td colspan='2' style="width: 15%">
							<select class='select_box' id='CTGR_CD' name="CTGR_CD" style="width: 100%;" disabled>
								<option value="${pmdView.CTGR_CD }" selected hidden>${pmdView.CTGR_NM }</option>
								<c:forEach var='category' items="${category }">
									<option value='${category.CTGR_CD }'>${category.CTGR_NM }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th class='stitle'>단가(원)</th>
						<td colspan='2'><input type="text" class="tdInput" value="<fmt:formatNumber value="${pmdView.PRICE }" pattern="#,###" />" name="PRICE" maxlength="10" readonly/></td>
						<th class='stitle' style="width: 6%">메뉴상태</th>
						<td colspan='2'>
							<select class='select_box' id="MENU_STATE" name="MENU_STATE" disabled>
								<option value="" selected hidden>${pmdView.MENU_STATE_NM }</option>
								<c:forEach var="plcode" items="${plcode }">
									<option value='${plcode.DEFINITION_CD }'>${plcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
						<th class='stitle' style="width: 6%">상태수정일시</th>
						<td colspan='2' class="defTd"><fmt:formatDate value="${pmdView.MENU_STATE_MDF_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th style="width: 7%;" class='stitle'>메뉴 이미지 주소</th>
						<td colspan='5'><input type="text" class="tdInput" value="${pmdView.IMAGE_URL }" name="IMAGE_URL" readonly/></td>
						<th class='stitle' style="width: 6%">정렬순서</th>
						<td colspan='2'><input type="text" class="tdInput" value="${pmdView.SEQUENCE }" name="SEQUENCE" maxlength="10" readonly/></td>
					</tr>
					<tr>
						<th style="width: 7%;" class='stitle'>영양정보 URL 주소</th>
						<td colspan='7'><input type="text" class="tdInput" value="${pmdView.NUTRITION_INFO_URL }" name="NUTRITION_INFO_URL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>연동 행사 코드</th>
						<td colspan='7' class="defTd">${pmdView.LINK_MENU_CD }</td>
					</tr>
					<tr>
						<th class='pd_des' style="padding: 25px">상품설명</th>
						<td colspan='7'><textarea class="tdTextAr" name="MENU_DESC" maxlength="200"  readonly>${pmdView.MENU_DESC }</textarea></td>
					</tr>
					<tr>
						<th class='stitle'>등록사용자</th>
						<td class="defTd" colspan='2'>${pmdView.REG_USER_NM}</td>
						<th class='stitle'>등록일시</th>
						<td colspan='2' class="defTd"><fmt:formatDate value="${pmdView.REG_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th class='stitle'>수정사용자</th>
						<td class="defTd" colspan='2'>${pmdView.MDF_USER_NM}</td>
						<th class='stitle'>수정일시</th>
						<td colspan='2' class="defTd"><fmt:formatDate value="${pmdView.MDF_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
			</form>
			<form id="menu_opt">
				<table id="defTb" style="margin-top: 25px;">
					<tr>
						<th colspan='10' class='mtitle'>행사 메뉴 옵션 정보</th>
					</tr>
					<tr>
						<th class='stitle' colspan='2' style="width: 13%;">옵션코드</th>
						<th class='stitle' style="width: 12%;">옵션명</th>
						<th class='stitle' style="width: 8%;">필수여부</th>
						<th class='stitle' style="width: 8%;">정렬순서</th>
						<th class='stitle' colspan='2'>다수 아이템 선택 가능 여부</th>
						<th class='stitle' style="width: 10%;">최소 선택 개수</th>
						<th class='stitle' style="width: 10%;">최대 선택 개수</th>
						<th class='stitle' style="width: 20%;">주문시 아이템에 수량을 입력 받을지 여부</th>
					</tr>
					<c:forEach var="podView" items="${podView }" varStatus="i">
						<tr class='clicker row' id="row${i.count }">
							<td colspan='2' id="OPTION_CD">${podView.OPTION_CD }</td>
							<td>${podView.OPTION_NM }</td>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${podView.IS_REQUIRED eq 'Y'.charAt(0)}">
										<img src="./resources/img/box_select.png" />
									</c:when>
									<c:otherwise>
										<img src="./resources/img/box_no_select.png" />
									</c:otherwise>
								</c:choose>
							</td>
							<td style="text-align: center;">${podView.SEQUENCE }</td>
							<td colspan='2' style="text-align: center;">
								<c:choose>
									<c:when test="${podView.IS_MULTI_SELECTABLE eq 'Y'.charAt(0)}">
										<img src="./resources/img/box_select.png" />
									</c:when>
									<c:otherwise>
										<img src="./resources/img/box_no_select.png" />
									</c:otherwise>
								</c:choose>
							</td>
							<td>${podView.MIN_SELECT_COUNT }</td>
							<td>${podView.MAX_SELECT_COUNT }</td>
							<td style="text-align: center;">
							<c:choose>
									<c:when test="${podView.IS_COUNTABLE eq 'Y'.charAt(0)}">
										<img src="./resources/img/box_select.png" />
									</c:when>
									<c:otherwise>
										<img src="./resources/img/box_no_select.png" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					<c:forEach begin="${fn:length(podView)}" end="0">
						<tr>
							<td style="border: 0px"></td>
						</tr>
					</c:forEach>
				</table>
			</form>
			<form id="sto_menu">
				<table style="margin-top: 25px;">
					<tr>
						<th colspan='6' class='mtitle'>매장 재고 정보</th>
					</tr>
					<tr>
						<th class='stitle' style="width: 13%;">매장재고</th>
						<td style="width: 20%; text-align: right;" class="defTd">${tspView.STO_STOCK_QTY }</td>
						<th class='stitle' style="width: 14%;">재고변경일시</th>
						<td style="width: 21%;" class="defTd"><fmt:formatDate value="${tspView.STOCK_MDF_DATE }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
				<input type="hidden" value="${tspView.STO_CD }" name="STO_CD"/>
			</form>
		</div>
		<form name="itemSearch">
			<input type="hidden" name="opcd" id="opcd" />
		</form>
	</div>
</body>
</html>
