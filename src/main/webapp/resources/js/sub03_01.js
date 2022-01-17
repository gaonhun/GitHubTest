//오늘, 일주일, 1개월 css
$(document).ready(function(){
    $('#dateType1').focus(function() {
        $(this).addClass('active_button');
		$('#dateType2').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
    });    
    $('#dateType2').focus(function() {
        $(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType3').removeClass('active_button');
    });
    
    $('#dateType3').focus(function() {
        $(this).addClass('active_button');
		$('#dateType1').removeClass('active_button');
		$('#dateType2').removeClass('active_button');
    });
});

// datepicker 선언, 날짜 저장 or 새로 불러오기
$(document).ready(function() {
    $("#sdate").datepicker();
    $("#edate").datepicker();
    
    const sdate = "${sdate}";
    const edate = "${edate}";
    
    if(isNotEmpty(sdate) && isNotEmpty(edate)){
	    $("#sdate").val(sdate);
	    $("#edate").val(edate);
    }
    
    const sval = $("#sdate").val();
    const eval = $("#edate").val();
    
    if(isEmpty(sval) && isEmpty(eval) || sval == "${sdate}" && eval == "${edate}"){
	    $("#sdate").datepicker("setDate", new Date());
    	$("#edate").datepicker("setDate", new Date());
	}
});

// 오늘, 일주일, 1개월 기능
function setSearchDate(start){
    const edate = $('#edate').val();
    const rpldate = edate.split('-');

    const edt = new Date(rpldate[0],rpldate[1]-1,rpldate[2]); //edate 날짜

    d = new Date();

    if(start == '1d') {
        d = $.datepicker.formatDate('yy-mm-dd', d)
        $('#sdate').val(d);
        $('#edate').val(d); 
    } else if(start == '7d') {
        d = new Date(edt.getFullYear(), edt.getMonth(), edt.getDate() - 7);
    } else if(start == '1m') {
        d = new Date(edt.getFullYear(), edt.getMonth() - 1, edt.getDate());
    }
    
    const endDate = $.datepicker.formatDate('yy-mm-dd', d);
    $('#sdate').val(endDate);
}

//엑셀 다운
function ExcelDown() {
	
	const sdate = $("#sdate").val().replaceAll('-', '');
    const edate = $("#edate").val().replaceAll('-', '');
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	
	const path = 'oms_sub03_01excel';
	const params = {
			scd,
    		ono,
    		occd,
    		chono,
    		sdate,
    		edate
	}
	post(path, params);
}

//검색 쿼리
function search_go(num) {
	const sdate = $("#sdate").val().replaceAll('-', '');
    const edate = $("#edate").val().replaceAll('-', '');
	const snm = $("#snm").val();
	
	if(sdate > edate){
		swal(
			"시작일이 종료일보다 클 수 없습니다."
			).then(function() {
				$('#sdate').focus();
			});
		
		return
	}
	
	/*if(isEmpty(snm)){
		swal(
			"매장명은 필수 입력값입니다."
			).then(function() {
				$('#snm_img').click();
			});
		
		return
	}*/
	
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
	
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const ono = $('#ono').val();
	const chono = $('#chono').val();
	const pageNum = num;
	
	$.ajax ({
		url: 'oms_sub03_01_process',
		dataType: 'json',
	    type: 'POST',
	    async : false,
	    data: {
	    		scd,
	    		ono,
	    		chono,
	    		occd,
	    		sdate,
	    		edate,
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
			
			let obLength = Object.keys(data.orderList).length;
			
			if(obLength == 0){
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$(".btn_excel").attr("disabled", true);
			}else if(obLength > 0){
				
		    	$(data.orderList).each(function(i,e){
	
		    		$('tbody').append('<tr class="row ajaxTr">' +
		    								   '<td>' + e.rn + '</td>' + 
				    						   '<td>' + e.order_RE_NO + '</td>' + 
				    						   '<td>' + e.order_CH_NO + '</td>' + 
				    						   '<td>' + e.sto_CD + '</td>' +
				    						   '<td>' + e.sto_NM + '</td>' +
				    						   '<td>' + e.order_CH_NM + '</td>' +
				    						   '<td>' + e.order_STATE + '</td>' +
				    						   '<td>' + e.order_TYPE + '</td>' +
				    						   '<td>' + moneyFormat(e.pay_AMT) + '</td>' +
				    						   '<td>' + moneyFormat(e.dlv_AMT) + '</td>' +
				    						   '<td>' + moneyFormat(e.dis_AMT) + '</td>' +
				    						   '<td>' + moneyFormat(e.tot_AMT) + '</td>' +
				    						   '<td>' + moneyFormat(e.net_AMT) + '</td>' +
				    						   '<td>' + e.land_ADDR +' '+ e.land_ADDR_DT + '</td>' +
				    						   '<td>' + phoneCheck(e.mem_PHONE, e.safe_MEM_PHONE) + '</td>' +
				    						   '<td>' + e.dlv_CH_NM + '</td>' +
				    						   '<td>' + e.dlv_STATE + '</td>' +
				    						   '<td>' + unixTimeFormat(e.reg_DATE) + '</td>' +
				    						   '<input type="hidden" value="'+ e.order_CH_CD + '"/>' +
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
		    	$(".btn_excel").attr("disabled", false);
			}
        }, 
        error: function(e) { 
        	swal("데이터를 가져오지 못했습니다."); 
        	$(".btn_excel").attr("disabled", true);
        	$.unblockUI();
        } 
	});
}

$(document).on("dblclick", "td", function() {
	const checkBtn = $(this);
	
	const tr = checkBtn.parent();
	const td = tr.children();
	const oid = td.eq(1).text();
	const scd = td.eq(3).text();
	const occd = td.last().val();
	
	if(isNotEmpty(oid) && isNotEmpty(scd) && isNotEmpty(occd)){
		
		let form = document.createElement("form");
	    form.target = "_blank";
	    form.method = "POST";
	    form.action = "oms_sub03_01_detail";
		form.style.display = "none";
	
		let Inputscd = document.createElement("input");
	    Inputscd.type = "text";
	    Inputscd.name = "scd";
	    Inputscd.value = scd;
	
		let Inputoid = document.createElement("input");
	    Inputoid.type = "text";
	    Inputoid.name = "oid";
	    Inputoid.value = oid;
	
		let Inputoccd = document.createElement("input");
	    Inputoccd.type = "text";
	    Inputoccd.name = "occd";
	    Inputoccd.value = occd;
	
	    form.appendChild(Inputscd);
		form.appendChild(Inputoid);
		form.appendChild(Inputoccd);
	
	    document.body.appendChild(form);
		
		form.submit();
		
		document.body.removeChild(form);
	}
});