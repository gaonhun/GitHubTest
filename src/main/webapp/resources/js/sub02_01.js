//수정모드 true/false
$(document).ready(function() {
	//오버레이 클릭
	$('.overlay').on('click', function() {
		$('.overlay').fadeOut(100);
		$('.hide_div').removeClass('show_div');
		$('#cancel_reason').val('');
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
	$('#cancel_reason').val('');
	$('#cancel_reason').focus();
}

//엑셀 다운
function ExcelDown() {

	const scd = $('#scd').val();
	const pcd = $('#pcd').val();
	const etyp = $('#etyp').val();
	const sinp = $('#sinp').val();

	const path = 'oms_sub02_01excel';
	const params = {
		scd,
		pcd,
		etyp,
		sinp
	}

	post(path, params);
}

function refresh() {
	$('#pnm').val('');
	$('#pcd').val('');
	$('#etyp').val('');
	$('#sinp').val('');
}


//검색 쿼리
function search_go(num) {

	const scd = $('#scd').val();
	const pcd = $('#pcd').val();
	const etyp = $('#etyp').val();
	const sinp = $('#sinp').val();
	const pageNum = num;

	if (isEmpty(scd)) {
		swal(
			"매장명은 필수 입력값입니다."
		).then(function() {
			$('#snm_img').click();
		});

		return
	}

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

	$.ajax({
		url: 'oms_sub02_01_process',
		dataType: 'json',
		type: 'POST',
		data: {
			scd,
			pcd,
			etyp,
			sinp,
			pageNum
		},
		success: function(data) {

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

			let obLength = Object.keys(data.productList).length;
			if (obLength == 0) {
				swal({
					text: "조회된 결과가 없습니다.",
					icon: "warning"
				});
				$("#excel_download").attr("disabled", true);
			} else if (obLength > 0) {

				$(data.productList).each(function(i, e) {
					$('tbody').append('<tr class="row ajaxTr">' +
						'<td style="padding:2px">' + e.rn + '</td>' +
						'<td>' + e.menu_CD + '</td>' +
						'<td>' + e.menu_NM + '</td>' +
						'<td>' + moneyFormat(e.price) + '</td>' +
						'<td>' + e.menu_STATE + '</td>' +
						'<td>' + unixTimeFormat(e.menu_STATE_MDF_DATE) + '</td>' +
						'<td>' + e.ctgr_NM + '</td>' +
						'<td>' + e.reg_USER_NM + '</td>' +
						'<td>' + unixTimeFormat(e.reg_DATE) + '</td>' +
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

function sampleDownload() {
	const path = 'SampleDown';
	const params = {
		stat: 'Menu'
	}
	post(path, params);
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
		text: "기존 메뉴를 지우고 새로 갱신합니다.",
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
				url: 'productUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					const scd = $('#scd').val();
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						location.reload();
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

function excelpartupload() {
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
		text: "메뉴를 추가합니다.",
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
				url: 'productPartUpload',
				enctype: "multipart/form-data",
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				cache: false,
				success: function(data) {
					const scd = $('#scd').val();
					$.unblockUI();
					swal({
						text: "\"" + data + "\" 파일이 정상 업로드 되었습니다.",
						icon: "success"
					}).then(function() {
						$("#upload_name").val('');
						$("#excelFile").val('');
						$('.overlay').fadeOut();
						$('.hide_div').removeClass('show_div');
						if (isNotEmpty(scd)) {
							search_go();
						}else{
							location.reload();
						}
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

//더블클릭 시 
$(document).on("dblclick", "td", function() {
	const checkBtn = $(this);
	const tr = checkBtn.parent();
	const td = tr.children();
	const plucd = td.eq(1).text();
	const scd = $("#scd").val();

	let form = document.createElement("form");
	form.target = "_blank";
	form.method = "POST";
	form.action = "oms_sub02_01_detail";
	form.style.display = "none";

	let Inputscd = document.createElement("input");
	Inputscd.type = "text";
	Inputscd.name = "scd";
	Inputscd.value = scd;

	let Inputplucd = document.createElement("input");
	Inputplucd.type = "text";
	Inputplucd.name = "plucd";
	Inputplucd.value = plucd;

	form.appendChild(Inputscd);
	form.appendChild(Inputplucd);

	document.body.appendChild(form);

	form.submit();

	document.body.removeChild(form);

});