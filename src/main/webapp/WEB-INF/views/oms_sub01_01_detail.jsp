<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
    <title>매장 리스트 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sub01_01_detail.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub01_01_detail.css" />
<script>
$(document).ready(function() {
	const startDt = "${sodView.STOP_START_DAY }";
	const endDt = "${sodView.STOP_END_DAY }";
	const hpno = "${sdView.HPNO }";
	const cpn_tel = "${sdView.CPN_TEL }";
	const mng_hpno = "${sdView.MNG_HPNO }";
	const opn_dt = "${sdView.OPN_DT }";

	$('#startDt').val(dateStringFormat(startDt));
	$('#endDt').val(dateStringFormat(endDt));
	
	$('#hpno').val(phoneNumberFormat(hpno));
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
			<div id="header_title">매장관리 > 매장리스트조회 > 매장리스트상세</div>
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
			<form id="comp_master">
				<table>
					<tr>
						<th colspan='11' class='mtitle'>매장 기본 정보</th>
					</tr>
					<tr>
						<th class='stitle'>매장코드</th>
						<td colspan='2' id="CPN_CD" class="defTd">${sdView.CPN_CD }</td>
						<th class='stitle'>매장명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.CPN_NM }" name="CPN_NM" readonly/></td>
						<th class='stitle'>매장번호</th>
						<td colspan='2'><input type="text" class="tdInput" name="CPN_TEL" id="cpn_tel" onKeyup="inputTelNumber(this);" maxlength="13" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>사업자유형</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.CPN_BIZ_TYPE}" name="CPN_BIZ_TYPE" readonly/></td>
						<th class='stitle'>사업자번호</th> 
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.CPN_BIZ_NO }" name="CPN_BIZ_NO" readonly/></td>
						<th class='stitle'>인앱여부</th>
						<td colspan='2'>
							<select class='select_box' id="IN_APP" name='IN_APP' disabled>
								<option value='${sdView.IN_APP}' selected hidden>${sdView.IN_APP}</option>
								<option value='Y'>Y</option>
								<option value='N'>N</option> 
							</select>
						</td>
					</tr>
					<tr>
						<th class='stitle'>대표자명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.CEO_NM }" name="CEO_NM" readonly/></td>
						<th class='stitle'>점장명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.MNG_NM }" name="MNG_NM" maxlength="13" readonly/></td>
						<th class='stitle'>휴대폰번호 (점장)</th>
						<td colspan='2'><input type="text" class="tdInput" name="MNG_HPNO" id="mng_hpno" onKeyup="inputPhoneNumber(this);" maxlength="13" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>담당FC명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.FC_NM}" name="FC_NM" readonly/></td>
						<th class='stitle'>휴대폰번호 (FC)</th>
						<td colspan='2'><input type="text" class="tdInput" name="HPNO" id="hpno" onKeyup="inputPhoneNumber(this);" maxlength="13" readonly/></td>
						<th class='stitle'>이메일</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sdView.EMAIL}" name="EMAIL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>매장상태</th>
						<td colspan='2'>
							<select class='select_box' id="CPN_STATE" name='CPN_STATE' disabled>
								<option value='' selected hidden>${sdView.CPN_STATE_NM}</option>
								<c:forEach var="stcode" items="${stcode }">
									<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
								</c:forEach>
							</select>
						</td>
						<th class='stitle'>오픈일자</th>
						<td colspan='2'><input type="text" class="tdInput" name="OPN_DT" id="opn_dt" readonly/></td>
						<th class='stitle'>상태변경일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${sdView.STATE_MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th class='stitle'>업체 이미지 URL</th>
						<td colspan='7'><input type="text" class="tdInput" value="${sdView.CPN_IMAGE_URL}" name="CPN_IMAGE_URL" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>주소</th>
						<td colspan='7'><input type="text" class="tdInput" value="${sdView.CPN_ADDR}" name="CPN_ADDR" readonly/></td>
					</tr>
					<tr>
						<th class='st_des'>업체설명</th>
						<td colspan='7'><textarea class="tdTextAr" name="CPN_DESC" maxlength="200" readonly>${sdView.CPN_DESC}</textarea></td>
					</tr>
					<tr>
						<th class='st_des'>비고</th>
						<td colspan='7'><textarea class="tdTextAr" name="MEMO" maxlength="200" readonly>${sdView.MEMO}</textarea></td>
					</tr>
					<tr>
						<th class='stitle'>등록사용자</th>
						<td class="defTd" colspan='2'>${sdView.REG_USER_NM}</td>
						<th class='stitle'>등록일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${sdView.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<th class='stitle'>수정사용자</th>
						<td class="defTd" colspan='2'>${sdView.MDF_USER_NM}</td>
						<th class='stitle'>수정일시</th>
						<td class="defTd" colspan='2'><fmt:formatDate value="${sdView.MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</table>
			</form>
			<form id="sto_oper">
				<table style="margin-top: 35px;">
					<tr>
						<th colspan='11' class='mtitle'>매장 운영 정보</th>
					</tr>
					<tr>
						<th class='stitle'>주중 영업 시작</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.DAY_START_TIME }" name="DAY_START_TIME" onKeyup="inputTime(this);" maxlength="5" readonly/></td>
						<th class='stitle'>주말 영업 시작</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.WEEKEND_START_TIME }" onKeyup="inputTime(this);" maxlength="5" name="WEEKEND_START_TIME" readonly/></td>
						<th class='stitle'>공휴일 영업 시작</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.HOLIDAY_START_TIME }" onKeyup="inputTime(this);" maxlength="5" name="HOLIDAY_START_TIME" readonly/></td>
						</td>
					</tr>
					<tr>
						<th class='stitle'>주중 영업 종료</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.DAY_END_TIME }" name="DAY_END_TIME" onKeyup="inputTime(this);" maxlength="5" readonly/></td>
						<th class='stitle'>주말 영업 종료</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.WEEKEND_END_TIME }" onKeyup="inputTime(this);" maxlength="5" name="WEEKEND_END_TIME" readonly/></td>
						<th class='stitle'>공휴일 영업 종료</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.HOLIDAY_END_TIME }" onKeyup="inputTime(this);" maxlength="5" name="HOLIDAY_END_TIME" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>배달비(원)</th>
						<td colspan='2'><input type="text" class="tdInput" style="text-align: right;" value="<fmt:formatNumber value="${sodView.DELIVERY_PRICE}" pattern='#,###' />" name="DELIVERY_PRICE" maxlength="10" readonly/>
						<th class='stitle'>최대주문 가능금액(원)</th>
						<td colspan='2'><input type="text" style="text-align: right;" class="tdInput" value="<fmt:formatNumber value="${sodView.MAXIMUM_ORDER_PRICE}" pattern='#,###' />" name="MAXIMUM_ORDER_PRICE" maxlength="10" readonly/></td>
						<th class='stitle'>매장 위도</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.LOC_LAT }" name="LOC_LAT"  readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>배달시간(분)</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.ESTIMATED_DELIVERY_TIME }" name="ESTIMATED_DELIVERY_TIME"  maxlength="5" readonly/></td>
						<th class='stitle'>최소주문 가능금액(원)</th>
						<td colspan='2'><input type="text" style="text-align: right;" class="tdInput" value="<fmt:formatNumber value="${sodView.MINIMUM_ORDER_PRICE}" pattern='#,###' />" name="MINIMUM_ORDER_PRICE"  maxlength="10" readonly/></td>
						<th class='stitle'>매장 경도</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.LOC_LONG }" name="LOC_LONG" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>예금주</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.ACC_HOLDER }" name="ACC_HOLDER" readonly/></td>
						<th class='stitle'>은행명</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.BANK_NM }" name="BANK_NM" readonly/></td>
						<th class='stitle'>계좌번호</th>
						<td colspan='2'><input type="text" class="tdInput" value="${sodView.ACC_NUM }" name="ACC_NUM" readonly/></td>
					</tr>
					<tr>
						<th class='stitle'>배달대행사</th>
						<td colspan='2'>
							<select class='select_box' id="DELIVERY_CH_CD" name='DELIVERY_CH_CD' disabled>
								<option value='${sodView.DELIVERY_CH_CD}' selected hidden>${sodView.DELIVERY_CH_NM}</option>
								<c:forEach var="dvlList" items="${dvlList }">
									<option value='${dvlList.CPN_CD }'>${dvlList.CPN_NM }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
			<form id="chst_op">
				<table id="th_table" style="margin-top: 35px;">
					<tr>
						<th colspan='7' class="mtitle">주문채널사 정보</th>
					</tr>
					<tr>
						<th class='stitle'>채널사명</th>
						<th class='stitle'>채널사 매장상태</th>
						<th class='stitle'>채널사 전송상태</th>
						<th class='stitle'>등록사용자</th>
						<th class='stitle'>등록일자</th>
						<th class='stitle'>수정사용자</th>
						<th class='stitle'>수정일자</th>
					</tr>
					<c:forEach var="csoView" items="${csoView }">
						<tr>
							<td class="defTd" id="CH_NM">${csoView.CH_NM}
								<input type="hidden" value="${csoView.CH_CD}" name="CH_CD" readonly/>
							</td>
							<td>
								<select class='select_box chList'id="CH_STATE" name='CH_STATE' disabled>
									<option value='${csoView.CH_STATE}' selected hidden>${csoView.CH_STATE_NM}</option>
									<c:forEach var="stcode" items="${stcode }">
										<option value='${stcode.DEFINITION_CD }'>${stcode.DEFINITION_NM }</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<select class='select_box chList'id="CH_SEND_FLAG" name='CH_SEND_FLAG' disabled>
									<option value='${csoView.CH_SEND_FLAG}' selected hidden>${csoView.CH_SEND_FLAG_NM}</option>
									<c:forEach var="chflag" items="${chflag }">
										<option value='${chflag.DEFINITION_CD }'>${chflag.DEFINITION_NM }</option>
									</c:forEach>
								</select>
							</td>
							<td class="defTd">${csoView.REG_USER_NM}</td>
							<td class="defTd"><fmt:formatDate value="${csoView.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td class="defTd">${csoView.MDF_USER_NM}</td>
							<td class="defTd"><fmt:formatDate value="${csoView.MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
					<c:forEach begin="${fn:length(csoView)}" end="5">
					<tr>
						<td style="border: 0px"></td>
					</tr>
				</c:forEach>
				</table>
			</form>
		</div>
	</div>
</body>

</html>
