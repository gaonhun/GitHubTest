$(document).ready(function() {
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
	});

	var fileTarget = $('.filebox .upload-hidden');
	fileTarget.on('change', function() {
		// 값이 변경되면 
		if (window.FileReader) {
			// modern browser 
			var filename = $(this)[0].files[0].name;
		} else {
			// old IE
			var filename = $(this).val().split('/').pop().split('\\').pop();
			// 파일명만 추출 
		} // 추출한 파일명 삽입
		$(this).siblings('.upload-name').val(filename);
	});
});


function upload_click() {
	$('.overlay').fadeIn(100);
	$('.hide_div').addClass('show_div');
}

function sampleDownload() {
	const path = 'SampleDown';
	const params = {
		stat: 'Store'
	}
	post(path, params);
}

//엑셀 다운
function ExcelDown() {
	const scd = $('#scd').val();
	const occd = $('#occd').val();
	const dccd = $('#dccd').val();
	const sst = $('#sst').val();

	const path = 'oms_sub01_01excel';
	const params = {
		scd,
		occd,
		dccd,
		sst
	}

	post(path, params);
}

//검색 쿼리
function search_go(num) {
	if (isEmpty(num)) {
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
	const dccd = $('#dccd').val();
	const sst = $('#sst').val();
	const pageNum = num;

	$.ajax({
		url: 'oms_sub01_01_process',
		dataType: 'json',
		type: 'POST',
		data: {
			scd,
			occd,
			dccd,
			sst,
			pageNum
		},
		success: function(data) {

			$('.ajaxTr').remove();
			$('.paging').children().remove();
			$.unblockUI();

			const page = data.pageMaker.cri.pageNum;
			var startPage = data.pageMaker.startPage;
			var endPage = data.pageMaker.endPage;
			const prev = data.pageMaker.prev;
			const next = data.pageMaker.next;
			const totalPage = data.pageMaker.totalPage;

			let obLength = Object.keys(data.storeList).length;

			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {

				$(data.storeList).each(function(i, e) {

					$('tbody').append('<tr class="row ajaxTr">' +
						'<td style="padding-right:2px">' + e.rn + '</td>' +
						'<td>' + e.cpn_CD + '</td>' +
						'<td>' + e.cpn_NM + '</td>' +
						'<td>' + e.cpn_ADDR + '</td>' +
						'<td>' + e.cpn_STATE + '</td>' +
						'<td>' + phoneNumberFormat(e.cpn_TEL) + '</td>' +
						'<td>' + phoneNumberFormat(e.hpno) + '</td>' +
						'<td>' + e.day_START_TIME + '</td>' +
						'<td>' + e.day_END_TIME + '</td>' +
						'<td>' + e.ord_CH_NM + '</td>' +
						'<td>' + e.dlv_CH_NM + '</td>' +
						'</tr>'
					);
				});
				if (prev) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + 1 + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left_fin.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + (startPage - 1) + '); return false;" class="page-prev"><img src="./resources/img/list_arrow_left.png"/></a></li>');
				}
				for (let num = startPage; num <= endPage; num++) {
					if (num == page) {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn active">' + num + '</a></li>');
					} else {
						$('.paging').append('<li><a href="#" onclick="search_go(' + num + '); return false;" class="page-btn">' + num + '</a></li>');
					}
				}
				if (next) {
					$('.paging').append('<li><a href="#" onclick="search_go(' + (endPage + 1) + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right.png"/></a></li>');
					$('.paging').append('<li><a href="#" onclick="search_go(' + totalPage + '); return false;" class="page-next"><img src="./resources/img/list_arrow_right_fin.png"/></a></li>');
				}
				$("#excel_download").attr("disabled", false);
			}
		},
		error: function(e) {
			alert("데이터를 가져오지 못했습니다.");
			$("#excel_download").attr("disabled", true);
			$.unblockUI();
		}
	});
}


//엑셀 파일 체크
function checkFileType(filePath) {
	var fileFormat = filePath.split(".");
	if (fileFormat.indexOf("xlsx") > -1 || fileFormat.indexOf("xls") > -1) {
		return true;
	} else {
		return false;
	}

}

function excelupload() {
	var file = $("#excelFile").val();
	var form = $('#excelUploadForm')[0];
	var size = document.getElementById("excelFile").files[0].size;
	var upTime = Math.round(size/60000);

	// FormData 객체 생성
	var formData = new FormData(form);

	if (file == "" || file == null) {
		swal("파일을 선택해주세요.");

		return false;
	} else if (!checkFileType(file)) {
		swal({
			text: "엑셀 파일만 업로드 가능합니다.",
			icon: "warning"
		});
		$("#upload_name").val('');
		$("#excelFile").val('');

		return false;
	}
	swal({
		icon: "warning",
		title: "정말 업로드 하시겠습니까?",
		text: "매장을 추가하거나 업데이트합니다.",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {
			$.blockUI({
				message: '<img src="./resources/img/loading_spiner.png"/><br><br><p id="uiMsg">예상 최대 대기시간은 '+upTime+'분 입니다.',
				css: {
					backgroundColor: 'rgba(0,0,0,0.0)',
					color: '#000000',
					border: '0px solid #a00'
				}
			});
			$.ajax({
				url: 'storeUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('파일선택');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						search_go();
					});
				},
				error: function(request, status, error) {
					$.unblockUI();
					const errCd = request.responseText;
					let errMsg = errCodeCheck(errCd);
					if (errMsg == false) {
						swal(
							"code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error
						).then(function() {
							location.reload();
							return;
						});
					}
					swal({
						title: "업로드 실패",
						text: errMsg,
						icon: "error"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
					});
				}
			});
		}
	});
	$("#upload_name").val('');
	$("#excelFile").val('');
}

//상세정보 페이지 이동
$(document).on("dblclick", "td", function() {
	const checkBtn = $(this);

	const tr = checkBtn.parent();
	const td = tr.children();
	const stocd = td.eq(1).text();

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "oms_sub01_01_detail";
	form.style.display = "none";

	let Inputscd = document.createElement("input");
	Inputscd.type = "text";
	Inputscd.name = "stocd";
	Inputscd.value = stocd;

	form.appendChild(Inputscd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);

});