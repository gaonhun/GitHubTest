<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>주문 내역 상세</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub03_01_detail.css" />
<script src="./resources/js/oms_common.js"></script>
<script>
	$(document).ready(function() {
		const basic = "${orderDetail.MEM_PHONE}";
		const safty = "${orderDetail.SAFE_MEM_PHONE}";
		
		$('#memphone').append(phoneNumberFormat(basic));
		$('#safephone').append(phoneNumberFormat(safty));
		
		//오버레이 클릭
		$('.overlay').on('click', function () {
		    $('.overlay').fadeOut(100);
		    $('.hide_div').removeClass('show_div');
		    $('#STATUS_MSG').val('');
		});
		
		$('#cancel_btn').on('click', function () {
		    $('.overlay').fadeOut(100);
		    $('.hide_div').removeClass('show_div');
		    $('#STATUS_MSG').val('');
		});
	});
	
function order_cancel(){
    $('.overlay').fadeIn(100);
    $('.hide_div').addClass('show_div');
    $('#cancel_reason').val('');
    $('#cancel_reason').focus();
}

function cancel_submit(){
	const STO_CD = $('#STO_CD').text();
	const ORDER_CH_CD = $('#ORDER_CH_CD').val();
	const ORDER_CH_NO = $('#ORDER_CH_NO').text();
	const ORDER_RE_NO = $('#ORDER_RE_NO').text();
	const STATUS_MSG = $('#STATUS_MSG').val();
	const PAY_AMT = $('#PAY_AMT').text();
	
	$('#cancel_btn').click();
	
	if(isEmpty(STATUS_MSG)){
		swal({
 				text: "취소 사유를 입력해주세요",
 				icon: "warning"
 			});
		
		return;
	}else if(STATUS_MSG.length>120){
		swal({
 				text: "120자 이내로 입력해주세요",
 				icon: "warning"
 			});
		return;
	}
	
	swal({
		  icon: "warning",
		  title: "정말 취소하시겠습니까?",
		  text: "채널사주문번호 : " + ORDER_CH_NO + "\n	카드승인금액 : " + PAY_AMT + "원",
		  buttons: true,
		}).then((willDelete) => {
		  if (willDelete) {
			$.ajax ({
		  		url: 'cancel_submit',
		  	    type: 'POST',
				dataType: 'json',
			    async : false,
		  	    data: {
		  	    	STATUS_MSG,
		  			ORDER_RE_NO,
		  			ORDER_CH_NO,
		  			STO_CD,
		  			ORDER_CH_CD
				},
				success: function (data) {
		  	    	swal({
		  				title: "주문이 취소 되었습니다.",
		  				text:"정상 처리되었습니다.",
		  				icon: "success"
		  			}).then(function() {
		  				opener.parent.search_go();
			  	    	location.reload();
		  			});
		          }, 
		          error: function(request, status, error) { 
				     	swal({
								title: "주문 취소에 실패했습니다.",
								text: request.responseText,
								icon: "error"
						}).then(function() {
				  	    	location.reload();
		  				});
		          }
			});
		}
	});
}

$(document).ready(function() {
	$('.clicker').each(function() {

		const classRow = $(this).attr("id");
		const thisRow = '#' + $(this).attr("id");

		const tr = $(this);
		const td = tr.children();
		const ORDER_RE_NO = $('#ORDER_RE_NO').text();
		const MENU_CD = td.eq(1).text();
		const MENU_CD_SEQ = td.eq(0).text();

		$.ajax({
			url: 'getMenuItem_process',
			dataType: 'json',
			type: 'POST',
			data: { 
				ORDER_RE_NO,
				MENU_CD,
				MENU_CD_SEQ
				},
			success: function(data) {
				let rownum = Object.keys(data).length + 1;
				let obLength = Object.keys(data).length;
				if (isEmpty(data)) {
					$(thisRow).removeClass('row');
					$(thisRow).removeClass('clicker');
				}else{
					$(thisRow).children().eq(1).css('font-weight','bold');
					$(thisRow).children().eq(1).css('color','#099769');
					$(thisRow).after(
						'<tr class="itemTr ' + classRow + '" style="display:none;">' +
						'<td style="width:0.1%" class="inItem" rowspan=' + rownum + '></th>' +
						'<th>' + '아이템코드순번' + '</th>' +
						'<th>' + '아이템코드' + '</th>' +
						'<th>' + '아이템명' + '</th>' +
						'<th>' + '수량' + '</th>' +
						'<th>' + '단가' + '</th>' +
						'<th>' + '합계' + '</th>' +
						'<td colspan="5" rowspan=' + rownum + '></th>' +
						'</tr>'
					);
	
					$(data).each(function(i, e) {
						$(thisRow).next().after('<tr class="itemTd ' + classRow + '" style="display:none;">' +
							'<td style="text-align: center;">' + e.item_CD_SEQ + '</td>' +
							'<td>' + e.item_CD + '</td>' +
							'<td>' + e.item_NM + '</td>' +
							'<td style="text-align: right;">' + e.qty + '</td>' +
							'<td style="text-align: right;">' + moneyFormat(e.price) + '</td>' +
							'<td style="text-align: right;">' + moneyFormat(e.amount) + '</td>' +
							'</tr>'
						);
	
					});
				}
			},
			error: function(e) {
				alert("데이터를 가져오지 못했습니다.");
			}
		});
	});

	$('.clicker').on('click', function() {
		const thisRow = $(this).attr("id");
		//const td = $("#" + thisRow).children();
		//const opcd = td.eq(0);
		if ($("." + thisRow).is(":visible")) {
			//opcd.css('color', '#717171');

			$("." + thisRow).hide();

		} else {
			//opcd.css('color', '#099769');

			$("." + thisRow).show();
		}
	});

});
	
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="overlay"></div>
	<div class="page_div">
		<header>
			<div id="header_title">주문내역관리 > 주문내역조회 > 주문내역상세</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='btn_div'>
				<c:if test="${UAccount.USER_LV eq 'M' || UAccount.USER_LV eq 'A' || UAccount.USER_LV eq 'C'}">
					<button type="button" class='btn_cancel' onclick="order_cancel();">주문취소</button>
				</c:if>
				<button type="button" class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</section>
		<div class='main_div'>
			<table id="f_table">
				<tr>
					<th colspan='7' class='mtitle'>주문 상세 정보</th>
				</tr>
				<tr>
					<th>중계주문번호</th>
					<td id="ORDER_RE_NO">${orderDetail.ORDER_RE_NO}</td>
					<th>채널사주문번호</th>
					<td id="ORDER_CH_NO">${orderDetail.ORDER_CH_NO}</td>
					<th>주문타입</th>
					<td>${orderDetail.ORDER_TYPE}</td>
				</tr>
				<tr>
					<th>채널사명</th>
					<td>${orderDetail.ORDER_CH_NM}</td>
					<th>선결제여부</th>
					<td>${orderDetail.IS_PREPAID}</td>
					<th>주문상태</th>
					<td>${orderDetail.ORDER_STATE}</td>
				</tr>
				<tr>
					<th>매장명</th>
					<td>${orderDetail.STO_NM}</td>
					<th>매장코드</th>
					<td id="STO_CD">${orderDetail.STO_CD}</td>
					<th>고객전화번호</th>
					<td id="memphone"></td>
				</tr>
				<tr>
					<th>배달지주소</th>
					<td>${orderDetail.LAND_ADDR}</td>
					<th>상세주소</th>
					<td>${orderDetail.LAND_ADDR_DT}</td>
					<th>고객안심전화번호</th>
					<td id="safephone"></td>
				</tr>
				<tr>
					<th>카드승인금액(원)</th>
					<td id="PAY_AMT" style="text-align: right;"><fmt:formatNumber value="${orderDetail.PAY_AMT}" pattern='#,###' /></td>
					<th>할인금액(원)</th>
					<td style="text-align: right;"><fmt:formatNumber value="${orderDetail.DIS_AMT}" pattern='#,###' /></td>
					<th>할인타입명</th>
					<td>${orderDetail.DIS_TYPE_NM}</td>
				</tr>
				<tr>	
					<th>배달비(원)</th>
					<td style="text-align: right;"><fmt:formatNumber value="${orderDetail.DLV_AMT}" pattern='#,###' /></td>
					<th>순매출액(원)</th>
					<td style="text-align: right;"><fmt:formatNumber value="${orderDetail.NET_AMT}" pattern='#,###' /></td>
					<th>총매출액(원)</th>
					<td style="text-align: right;"><fmt:formatNumber value="${orderDetail.TOT_AMT}" pattern='#,###' /></td>
				</tr>
				<tr>
					<th>배달대행사명</th>
					<td>${orderDetail.DLV_CH_NM}</td>
					<th>배달상태</th>
					<td>${orderDetail.DLV_STATE}</td>
					<th>배달 최소 요청 시간(분)</th>
					<td>${orderDetail.DLV_TIME}</td>
				</tr>
				<tr>
					<th>고객요청사항</th>
					<td colspan="3">${orderDetail.REQ_MSG}</td>
					<td colspan="2"><input type="hidden" value="${orderDetail.ORDER_CH_CD}" id="ORDER_CH_CD" /></td>
				</tr>
				<tr>
					<th>채널사주문일시</th>
					<fmt:parseDate var="dateString" value="${orderDetail.ORDER_DATE}" pattern="yyyyMMddHHmmss" />
					<td><fmt:formatDate value="${dateString }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th>주문등록일시</th>
					<td><fmt:formatDate value="${orderDetail.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th>최종변경일시</th>
					<td><fmt:formatDate value="${orderDetail.MDF_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<th colspan="6" style="height: 68px;"></th>
				</tr>
			</table>
			
			<table id="defTb" style="margin-top: 25px;">
				<tr>
					<th colspan='8' class='mtitle'>주문 메뉴 정보</th>
				</tr>
				<tr>
					<th class='stitle' colspan='2' style="width: 10%;">메뉴코드순번</th>
					<th class='stitle' style="width: 20%;">메뉴코드</th>
					<th class='stitle' style="width: 20%;">메뉴명</th>
					<th class='stitle' style="width: 10%;">수량</th>
					<th colspan='2' class='stitle' style="width: 20%;">단가</th>
					<th colspan='2' class='stitle'>합계</th>
				</tr>
				<c:forEach var="orderMenu" items="${orderMenu }" varStatus="i">
					<tr class='clicker row' id="row${i.count }">
						<td colspan='2' id="clrTd">${orderMenu.MENU_CD_SEQ }</td>
						<td  id="MENU_CD">${orderMenu.MENU_CD }</td>
						<td>${orderMenu.MENU_NM }</td>
						<td style="text-align: right;">${orderMenu.QTY }</td>
						<td colspan='2' style="text-align: right;"><fmt:formatNumber value="${orderMenu.PRICE }" pattern="#,###" /></td>
						<td style="text-align: right;"><fmt:formatNumber value="${orderMenu.AMOUNT }" pattern="#,###" /></td>
					</tr>
				</c:forEach>
				<c:forEach begin="${fn:length(orderMenu)}" end="0">
					<tr>
						<td style="border: 0px"></td>
					</tr>
				</c:forEach>
			</table>
			
			<table id="s_table">
				<tr>
					<th colspan='7' class='mtitle'>주문 이력 정보</th>
				</tr>
				<tr>
					<th>주문이력타입</th>
					<th>주문상태</th>
					<th>배달상태</th>
					<th>처리상태</th>
					<th>처리 결과 메세지</th>
					<th>등록사용자</th>
					<th>등록일시</th>
	
				</tr>
				<c:forEach var="orderHistory" items="${orderHistory}" varStatus="status">
					<tr>
						<td>${orderHistory.HISTORY_TYPE}</td>
						<td>${orderHistory.ORDER_STATE}</td>
						<td>${orderHistory.DELIVERY_STATE}</td>
						<c:choose>
							<c:when test="${orderHistory.STATUS_CD eq '200'}">
								<td>정상</td>
							</c:when>
							<c:otherwise>
								<td>실패</td>
							</c:otherwise>
						</c:choose>
						<td>${orderHistory.STATUS_MSG}</td>
						<td>${orderHistory.REG_USER_NM}</td>
						<td><fmt:formatDate value="${orderHistory.REG_DATE}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
				<c:forEach begin="${fn:length(orderHistory)}" end="2">
					<tr>
						<td style="border: 0px"></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div class="hide_div">
			<form name="cancelForm">
				<p id ="p_cancel">취소사유</p>
				<textarea id="STATUS_MSG" class="cancel_reason" placeholder="120자 이내로 입력해주세요"></textarea>
				<div class="cancel_btn_div">
					<button id="confirm_btn" type="button" onclick="cancel_submit();">확인</button>
					<button id="cancel_btn" type="button">취소</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
