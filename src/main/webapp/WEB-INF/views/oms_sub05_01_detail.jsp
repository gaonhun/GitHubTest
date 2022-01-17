<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>계정 관리</title>
</head>
<!-- <link rel="stylesheet" type="text/css" href="./resources/css/confirm_modal.css"> -->
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub05_01_detail.css">
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/sub05_01_detail.js"></script>
<script src="./resources/js/oms_common.js"></script>
<body>
	<c:if test="${UAccount.USER_LV ne 'M'}">
		<script>
			$(document).ready(function(){
				swal({
					text : "접근 권한이 없는 페이지입니다.",
					icon : "warning"
				}).then(function() {
					location.replace("logoutCustomer");
				});
			});
		</script>
	</c:if>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
		<div class="page_div">
		<header>
			
			<div id="header_title">시스템관리 > 계정관리 > 계정상세</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<div class='div_header'>
			<div class="btn_div">
				<button type="button" class='btn_search' id="edit_button" onclick="edit();">수정</button>
				<button type="button" class='btn_search' id="save_button" onclick="save();">저장</button>
				<button type="button" class='btn_search' id="cancel_button" onclick="cancel();">취소</button>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</div>
		<div class='main_div'>
			<form>
				<input type="hidden" id="user_cd" value="${tuaccount.USER_CD}"/>
				<table id="f_table">
					<tr>
						<th colspan='8' class='mtitle'>계정정보 상세</th>
					</tr>
					<tr>
						<th class='stitle' colspan='2' width='20%'>계정</th>
						<td colspan='6'><input type="text" value="${tuaccount.ACCOUNT}" id='user_id' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
		
					<tr>
						<th class='stitle' colspan='2'>비밀번호</th>
						<td colspan='6'><input type="text" value="${tuaccount.PASSWORD}" id='pwd' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>사용자명</th>
						<td colspan='6'><input type="text" value="${tuaccount.USER_NM}" id='user_nm' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>핸드폰번호</th>
						<td colspan='6'><input type="text" value="${tuaccount.PHONE}" id='phone' class="tdInput" onKeyup="inputPhoneNumber(this);" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>이메일</th>
						<td colspan='6'><input type="text" value="${tuaccount.USER_EMAIL}" id='email' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>주소</th>
						<td colspan='6'><input type="text" value="${tuaccount.USER_ADDR}" id='addr' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>상세주소</th>
						<td colspan='6'><input type="text" value="${tuaccount.USER_ADDR_DETAIL}" id='addr_dt' class="tdInput" readonly autocomplete="off"/></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>권한</th>
						<td colspan='6' >
							<select class='select_box' id="userLv" disabled>
								<option value='${tuaccount.USER_LV}' disabled selected hidden>${tuaccount.LEVEL_EXP}</option>
								<c:forEach var="userLv" items="${userLv }">
									<option value='${userLv.USER_LV }'>${userLv.LEVEL_EXP }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="ifLv">
						<th class='stitle' colspan='2'>업체코드</th>
						<td colspan='6' >
							<input type="text" id="cpncd" value="${tuaccount.CPN_CD}" class="tdInput" readonly autocomplete="off"/> 
						</td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>등록자명</th>
						<td colspan='6' id='defTd'>${tuaccount.REG_USER_NM}</td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>등록일시</th>
						<td colspan='6' id='defTd'><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tuaccount.REG_DATE}" /></td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>수정자명</th>
						<td colspan='6' id='defTd'>${tuaccount.MDF_USER_NM}</td>
					</tr>
					<tr>
						<th class='stitle' colspan='2'>수정일시</th>
						<td colspan='6' id='defTd'><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${tuaccount.MDF_DATE}" /></td>
					</tr>
				</table>
				
				<table id="s_table">
					<tr>
						<td/>
					</tr>
					<tr>
						<td class="check_font" id="id_check" />
					</tr>
					<tr>
						<td class="check_font" id="pwd_check" />
					</tr>
					<tr>
						<td class="check_font" id="nm_check" />
					</tr>
					<tr>
						<td class="check_font" id="ph_check" />
					</tr>
					<tr>
						<td class="check_font"/>
					</tr>
					<tr>
						<td class="check_font"/>
					</tr>
					<tr>
						<td class="check_font"/>
					</tr>
					<tr>
						<td class="check_font"/>
					</tr>
					<tr>
						<td class="check_font" id="cd_check"/>
					</tr>
					<tr>
						<td class="check_font" id="lv_check" />
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>