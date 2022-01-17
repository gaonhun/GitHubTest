<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>

<head>
    <title>상품 상세 검색</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/oms_sub_search.js"></script>
<script src="./resources/js/jquery.blockUI.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub_search.css" />
<script>
//document.ready()시 input 포커스
$(document).ready(function(){
	$('#keyword').focus();
});

// 상품검색 페이지 SELECT 선택시 INPUT 포커스
$(document).ready(function(){
    $('#type').change(function(){
        $('#keyword').focus();
    });
});

//조회 버튼 클릭 시 Search
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
	
	const type = $('#type').val();
	const keyword = $('#keyword').val();
	const pageNum = num;
	
	$.ajax ({
		url: 'oms_sub_search04_process',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    		type,
	    		keyword,
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
			
	    	$(data.tpmView).each(function(i,e){

	    		$('tbody').append('<tr class="row ajaxTr">' +
	    				 			       '<td>' + e.rn + '</td>' +
			    						   '<td>' + e.menu_CD + '</td>' + 
			    						   '<td>' + e.menu_NM + '</td>' +
			    						   '<td>' + e.ctgr_NM + '</td>' +
			    						   '<td>' + e.definition_NM + '</td>' +
			    						   '<td>' + unixTimeFormat(e.reg_DATE) + '</td>' +
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
        }, 
        error: function(e) { 
        	alert("데이터를 가져오지 못했습니다."); 
        	$.unblockUI();
        } 
	});
}

//더블클릭시 데이터 전송 후 종료
$(document).on("dblclick", "td", function() {
	
	var tdArr = new Array();
	var checkBtn = $(this);
	
	var tr = checkBtn.parent();
	var td = tr.children();
	
	//반복문 이용 값 저장
	td.each(function(i) {
	    tdArr.push(td.eq(i).text());
	});
	
	const pcd = tdArr[1];
	const pnm = tdArr[2];
	
	opener.$("#pcd").val(pcd);
	opener.$("#pnm").val(pnm);

	window.close();
});
</script>
<body>
<section class='div_header'>
    <div class='search_div' onkeyup='onEnterSearch()'>
        <select class='select_box' id="type">
            <option value='pnm'>메뉴명</option>
            <option value='pcd'>메뉴코드</option>
        </select>
        <input class="search_input" id="keyword" type=text autocomplete="off"/>
    </div>
    <div class="btn_div">
        <button type="button" class='btn_search' onclick='search_go();'>조회</button>
    </div>
</section>
<div class='main_div'>
    <table>
    	<thead>
	        <tr>
	            <th colspan='7' class="mtitle">메뉴리스트</th>
	        </tr>
	        <tr id="searchTr">
	        	<th>순번</th>
	            <th>메뉴코드</th>
	            <th>메뉴명</th>
	            <th>카테고리명</th>
	            <th>메뉴상태</th>
	            <th>등록일자</th>
	        </tr>
    	</thead>
    	<tbody>
    	</tbody>
    </table>
</div>
<div class="page_nav">
	<ul class="paging"></ul>
</div>
</body>
</html>
