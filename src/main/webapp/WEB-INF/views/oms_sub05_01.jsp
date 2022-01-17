<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>계정 관리</title>
</head>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub05_01.css">
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script>
//검색 쿼리
function search_go(num) {
	
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
	
	const userNm = $("#userNm").val();
	const pageNum = num;
	
	$.ajax ({
		url: 'oms_sub05_01_process',
		dataType: 'json',
	    type: 'POST',
	    data: {
	    		userNm,
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
			
			let obLength = Object.keys(data.tuaccount).length;
			
			if(obLength == 0){
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
			}else if(obLength > 0){
			
		    	$(data.tuaccount).each(function(i,e){
		    		
		    		$('tbody').append('<tr class="row ajaxTr">' +
				   		    				   '<td>' + e.rn + '</td>' +
				    						   '<td>' + e.account + '</td>' + 
				    						   '<td>' + e.user_NM + '</td>' +
				    						   '<td>' + e.user_LV + '</td>' +
				    						   '<td>' + unixTimeFormat(e.reg_DATE) + '</td>' +
				    						   '<td>' + unixTimeFormat(e.mdf_DATE) + '</td>' +
				    						   '<input type="hidden" value="'+ e.user_CD + '"/>' +
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
		    }
        }, 
        error: function(e) { 
        	swal({
				text: "데이터를 가져올 수 없습니다.",
				icon: "error"
			});
        	
        	$.unblockUI();
        } 
	});
}

$(document).on("dblclick", "td", function() {
	const checkBtn = $(this);
	const tr = checkBtn.parent();
	const td = tr.children();
	const user_cd = td.last().val();

	let form = document.createElement("form");
    form.target = "_blank";
    form.method = "POST";
    form.action = "oms_sub05_01_detail";
	form.style.display = "none";

	let Inputcd = document.createElement("input");
	Inputcd.type = "text";
	Inputcd.name = "user_cd";
	Inputcd.value = user_cd;

    form.appendChild(Inputcd);

    document.body.appendChild(form);
	
	form.submit();
	
	document.body.removeChild(form);
	      
});
</script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
	<div class="page_div">
		<header>
			
			<div id="header_title">시스템관리 > 계정관리</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<section class='div_header'>
			<div class='search_div' onkeyup='onEnterSearch()'>
				<p id='user_nm_p'>사용자명</p>
				<input class='search_box' type="search" id='userNm' name='userNm' autocomplete="off" />
			</div>
			<div class="btn_div">
				<button type="button" class='btn_reg'>등록</button>
				<button type="button" class='btn_search' onclick="search_go();">조회</button>
			</div>
		</section>
		<div class='main_div'>
			<table id="ajaxTable">
				<thead>
					<tr>
						<th width="5%">순번</th>
						<th width="15%">계정</th>
						<th width="15%">사용자명</th>
						<th width="10%">사용자레벨</th>
						<th width="15%">등록일시</th>
						<th width="15%">수정일시</th>
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