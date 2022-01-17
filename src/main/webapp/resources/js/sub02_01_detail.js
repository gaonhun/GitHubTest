//수정모드 true/false
let editStat = false;

let mdfForm = new Array();

$(document).ready(function() {
	$('.clicker').one('click', function() {

		const thisRow = '#' + $(this).attr("id");
		const classRow = $(this).attr("id");

		const tr = $(this);
		const td = tr.children();

		const opcd = td.eq(0).text();

		$.ajax({
			url: 'getItemCode_process',
			dataType: 'json',
			type: 'POST',
			data: { opcd },
			success: function(data) {

				let rownum = Object.keys(data).length + 1;

				$(thisRow).after(
					'<tr class="' + classRow + '">' +
					'<td style="width:0.1%" class="inItem" rowspan=' + rownum + '></th>' +
					'<th style="width:12.9%">' + '아이템코드' + '</th>' +
					'<th style="width:12%">' + '아이템명' + '</th>' +
					'<th style="width:8%">' + '단가' + '</th>' +
					'<th style="width:8%;">' + '정렬순서' + '</th>' +
					'<td colspan="5" rowspan=' + rownum + '></th>' +
					'</tr>'
				);

				$(data).each(function(i, e) {

					$(thisRow).next().after('<tr class="itemTd ' + classRow + '">' +
						'<td>' + e.item_CD + '</td>' +
						'<td>' + e.item_NM + '</td>' +
						'<td>' + moneyFormat(e.price) + '</td>' +
						'<td>' + e.sequence + '</td>' +
						'</tr>'
					);
				});
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

//수정 버튼 클릭시
function edit() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('#edit_button').css('display', 'none');
	$('#cancel_button').css('display', 'inline');
	$('#save_button').css('display', 'inline');
	$('#defTb').addClass('defTb');
	$('.select_box').attr('disabled', false);
	$(td).addClass('row');
	$('.select_box').addClass('edit_sbox');
	$('.defTd').addClass('uneditable');
	$('#warn_p').css('display','inline-block');
	//수정모드 true
	editStat = true;

	swal("수정 모드가 켜졌습니다.");
}

//취소 버튼 클릭시
function cancel() {
	const td = $('.tdInput, .tdTextAr').parent();
	$('#cancel_button').css('display', 'none');
	$('#save_button').css('display', 'none');
	$('#edit_button').css('display', 'inline');
	$(".editCompleted").removeClass("editCompleted");
	$('#defTb').removeClass('defTb');
	$('.select_box').attr('disabled', true);
	$(td).removeClass('row');
	$('.select_box').removeClass('edit_sbox');
	$('.defTd').removeClass('uneditable');
	$('#warn_p').css('display','none');
	//Form 리셋
	$('form').each(function() {
		this.reset();
	});

	mdfForm.length = 0;

	editStat = false;

}

//마우스 over 시 input줄 회색
$(document).on("mouseover", ".row", function() {
	const td = $(this);
	const input = td.children(".tdInput, .tdTextAr");

	//해당 input이 readonly일 경우만 addClass
	if (editStat) {
		$(input).addClass('input_hover');
	}
});
//마우스 leave시 원래대로
$(document).on("mouseleave", "tr", function() {
	const td = $(this);
	const input = td.children(".tdInput, .tdTextAr");

	$(input).removeClass('input_hover');
});


$(document).on("mouseleave", "td", function() {
	const td = $(this);
	const tr = td.parent();
	const tdAll = tr.children();
	const inputAll = tdAll.children();
	const input = td.children(".tdInput, .tdTextAr");

	if (editStat) {
		$(input).removeClass('input_hover');
	} else {
		$(inputAll).removeClass('input_hover');
	}
});

//input click시 수정 감지 및 데이터 저장 ( 수정모드 )
$(document).on("click", "td", function() {
	if (editStat) {
		const tdOne = $(this);
		const inputOne = tdOne.children();

		const tr = tdOne.parent();
		const td = tr.children();
		const input = td.children();

		if (inputOne.is('.tdInput, .tdTextAr')) {
			$(inputOne).attr("readonly", false);
			$(inputOne).addClass("writeonly");
			$(tr).css("pointer-events", "none");
		}

		$(inputOne).focus();

		$(inputOne).on("change", function() {

			$(tr).css("pointer-events", "auto");
			$(input).removeClass("writeonly");
			$(input).attr("readonly", true);

			$(inputOne).addClass("editCompleted");
			$(tdOne).addClass("editCompleted");
			$(tdOne).removeClass("row");

			mdfForm.push($(inputOne).closest('form').attr('id'));

			mdfForm = multiDimensionalUnique(mdfForm);
		});
	}
});

//input에서 focusout시 원래대로
$(document).on("focusout", ".tdInput, .tdTextAr", function() {
	const inputOne = $(this);
	const tdOne = inputOne.parent();

	const tr = tdOne.parent();
	const td = tr.children();
	const input = td.children();

	$(tr).css("pointer-events", "auto");
	$(input).removeClass("writeonly");
	$(input).attr("readonly", true);

});

//저장시 수정된 배열 전송
function save() {
	swal({
		text: "저장하시겠습니까?",
		buttons: true,
	}).then((willDelete) => {
		if (willDelete) {

			console.log(mdfForm);

			if (isEmpty(mdfForm)) {
				swal({
					text: "수정 된 데이터가 없습니다.",
					icon: "warning"
				});
				cancel();
				return

			} else {
				const menu_cd = $('#MENU_CD').text();
				const option_cd = $('#OPTION_CD').text();
				let menu_master = "";
				for (let i = 0; i < mdfForm.length; i++) {
					if (mdfForm[i] == 'menu_master') {
						menu_master = $('#menu_master').serializeObject();
					}
				}
				$.ajax({
					url: 'oms_sub02_01edit',
					contentType: 'application/json',
					dataType: 'json',
					type: 'POST',
					async: false,
					data: JSON.stringify({
						menu_cd,
						option_cd,
						menu_master
					}),
					success: function(data) {
						swal({
							text: "저장되었습니다.",
							icon: "success"
						}).then(function() {
							location.reload();
							opener.parent.search_go();
						});
					},
					error: function(request, status, error) {
						const errCd = request.responseText;
						let errMsg = errCodeCheck(errCd);
						if (errMsg == false) {
							swal("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error).then(function() {
								return;
							});
						}
						swal({
							text: errMsg,
							icon: "error"
						}).then(function() {
							return;
						});
					}
				});
			}
		}
	});
}