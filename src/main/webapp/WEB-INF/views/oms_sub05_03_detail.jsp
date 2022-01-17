<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>배달대행사 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub05_02_detail.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub05_02_detail.css" />
<script>
$(document).ready(function() {
	const cpn_tel = "${delivery.CPN_TEL }";
	const mng_hpno = "${delivery.MNG_HPNO }";
	const opn_dt = "${delivery.OPN_DT }";

	$('#cpn_tel').val(phoneNumberFormat(cpn_tel));
	$('#mng_hpno').val(phoneNumberFormat(mng_hpno));
	
	$('#opn_dt').val(dateFormat(opn_dt));

});
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			<div id="header_title">시스템관리 > 배달대행사관리 > 배달대행사상세</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class="btn_div">
				<p id="warn_p"><strong id="strong"></strong>&nbsp; 표시는 수정이 불가합니다. </p>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
				<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
				<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
				<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
			</div>
		</section>
		<div class='main_div'>
			<form id="comp_master">
				<table>
					<tr>
						<th colspan='11' class='mtitle'>배달대행사 기본 정보</th>
					</tr>
					<tr>
						<th class='stitle'>배달대행사코드</th>
						<td colspan='2' id="CPN_CD" class="defTd">${delivery.CPN_CD }</td>
						<th class='stitle'>배달대행사명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.CPN_NM }" name="CPN_NM" readonly/></td>
						<th class='stitle'>콜센터번호</th>
						<td colspan='2'><input type="text" class="tdInput" name="CPN_TEL" id="cpn_tel" onKeyup="inputTelNumber(this);" maxlength="13" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>사업자유형</th>
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.CPN_BIZ_TYPE}" name="CPN_BIZ_TYPE" readonly/></td>
						<th class='stitle'>사업자번호</th> 
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.CPN_BIZ_NO }" name="CPN_BIZ_NO" readonly/></td>
						<th class='stitle'>대표자명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.CEO_NM }" name="CEO_NM" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>담당자명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.MNG_NM }" name="MNG_NM" maxlength="13" readonly/></td>
						<th class='stitle'>휴대폰번호 (담당자)</th>
						<td colspan='2'><input type="text" class="tdInput" name="MNG_HPNO" id="mng_hpno" onKeyup="inputPhoneNumber(this);" maxlength="13" readonly/></td>
						<th class='stitle'>이메일</th>
						<td colspan='2'><input type="text" class="tdInput" value="${delivery.EMAIL}" name="EMAIL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>업체상태</th>
						<td colspan='2'>
							<select class='select_box' id="CPN_STATE" name='CPN_STATE' disabled>
								<option value='' disabled selected hidden>${delivery.CPN_STATE}</option>
								<c:forEach var="stcode" items="${stcode }">
									<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
						<th class='stitle'>상태변경일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${delivery.STATE_MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<th class='stitle'>오픈일자</th>
						<td colspan='2'><input type="text" class="tdInput" name="OPN_DT" id="opn_dt" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>업체 이미지 URL</th>
						<td colspan='7' class="defTd"><input type="text" class="tdInput" value="${delivery.CPN_IMAGE_URL}" name="CPN_IMAGE_URL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>주소</th>
						<td colspan='7'><input type="text" class="tdInput" value="${delivery.CPN_ADDR}" name="CPN_ADDR" readonly/></td>
					</tr>
					<tr>
						<th class='st_des'>업체설명</th>
						<td colspan='7'><textarea class="tdTextAr" name="CPN_DESC" maxlength="200" readonly>${delivery.CPN_DESC}</textarea></td>
					</tr>
					<tr>
						<th class='st_des'>비고</th>
						<td colspan='7'><textarea class="tdTextAr" name="MEMO" maxlength="200" readonly>${delivery.MEMO}</textarea></td>
					</tr>
					<tr>
						<th class='stitle'>등록사용자</th>
						<td class="defTd" colspan='2'>${delivery.REG_USER_NM}</td>
						<th class='stitle'>등록일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${delivery.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th class='stitle'>수정사용자</th>
						<td class="defTd" colspan='2'>${delivery.MDF_USER_NM}</td>
						<th class='stitle'>수정일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${delivery.MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>

</html>
