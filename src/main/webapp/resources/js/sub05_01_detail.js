//수정모드 true/false
let editStat = false;
let changeStat = false;

let idStat = false;
let pwdStat = false;
let nmStat = false;

let exId;
let exPwd;
let exNm;

const idchk = RegExp(/^[A-Za-z0-9_]{4,12}$/); //한글과 영어, 숫자만 사용하였는지 검사
const pwdChk = RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=\*]).*$/); // 8 ~ 15자리 영문,숫자,특수문자 포함
const nmChk = RegExp(/^[가-힣|a-z|A-Z|0-9|]{3,}$/); //한글 또는 영어 and 혼용 불가, 2자 이상
const phChk = RegExp(/^\d{3}-\d{3,4}-\d{4}$/); // 핸드폰 번호 체크

$(document).ready(function(){
	exId = $('#user_id').val();
	exPwd = $('#pwd').val();
	exNm = $("#user_nm").val();
	
	const defLv = $('#userLv option:selected').val();
	
	if(defLv == 'M' || defLv == 'A'){
		$("#ifLv").hide();
	}else {
		$("#ifLv").show();
	}
	

	$("#userLv").change(function() {
		const userLv = $("#userLv").val();
		if(userLv == 'H' || userLv == 'C' || userLv == 'S'){
			$("#ifLv").show();
			$("#cpncd").val('');
		}else {
			$("#ifLv").hide();
			$("#cd_check").text('');
			$("#cpncd").val('');
		}
	});
	
	// ID 체크
	$("#user_id").change(function() {
	    // id = "id_reg" / name = "userId"
	    const user_id = $('#user_id').val();
	    if (user_id == exId) {
			idStat = true;
			$("#id_check").text("기존 아이디와 동일합니다.");
            $("#id_check").css("color", "#4BB543");
		}else{
		    $.ajax({
		        url:  "userCheck",
		        dataType: 'json',
		        type: 'POST',
		        data: {
		        	user_id
		    	},
		        success: function(data) {
		
		            if (data.userId == 1) {
		                // 1 : 아이디가 중복되는 문구
		                idStat = false;
		                $("#id_check").text("사용중인 아이디입니다.");
		                $("#id_check").css("color", "red");
		            } else {
		                if (idchk.test(user_id)) {
		                    // 0 : 아이디 길이 / 문자열 검사
							idStat = true;
		                	$("#id_check").text("사용 가능한 아이디입니다.");
		                    $("#id_check").css("color", "#4BB543");
		
		                } else if (user_id == "") {
		                	idStat = false;
		                    $('#id_check').text('아이디를 입력해주세요.');
		                    $('#id_check').css('color', 'red');
		                } else {
		                	idStat = false;
		                	$('#id_check').text("아이디는 영문이나 숫자, 언더바 포함 4 ~ 12자리만 가능합니다.");
		                    $('#id_check').css('color', 'red');
		                }
		
		            }
		        },
		        error: function() {
		        	swal({
		        		title: '에러',
	                	text: '에러가 발생해서 창을 종료합니다.',
	                	icon: "error"
	                });
		        	window.close();
		        }
		    });
	    }
	});
	
	//비밀번호 체크
	$('#pwd').keyup(function(){
		const pwd = $("#pwd").val();
		 
		if(exPwd == pwd){
			$("#pwd_check").text("기존 비밀번호와 동일합니다.");
			$("#pwd_check").css("color", "#4BB543");
	    	pwdStat = true;
	    }else if(pwd.length > 15){
	    	pwdStat = false;
	    	$("#pwd_check").text("비밀번호가 너무 깁니다.");
	        $("#pwd_check").css("color", "red");
	    }else if(pwdChk.test(pwd)){
			pwdStat = true;
			$("#pwd_check").text("사용 가능한 비밀번호입니다.");
			$("#pwd_check").css("color", "#4BB543");
		}else {
			pwdStat = false;
			$('#pwd_check').text("영문, 숫자, 특수문자 포함 8 ~ 15자리로 입력해주세요");
			$('#pwd_check').css('color', 'red');
		}
	});
	
	$('#phone').keyup(function(){
		const phone = $(this).val();
		
		if (phChk.test(phone)) {
			phStat = true;
	    	$("#ph_check").text("사용이 가능합니다.");
	        $("#ph_check").css("color", "#4BB543");
	    }else{
	    	phStat = false;
	    	$('#ph_check').text('핸드폰번호 형식에 맞지 않습니다.');
			$('#ph_check').css('color', 'red');
	    }
	});
	
	$('#user_nm').change(function(){
		const user_nm = $("#user_nm").val();
	    if (user_nm == exNm) {
	    	nmStat = true;
	    	$('#nm_check').html('기존 이름과 동일합니다.');
		    $('#nm_check').css('color', '#4BB543');
	    }else{
		    $.ajax({
		        url:  "nmCheck",
		        dataType: 'json',
		        type: 'POST',
		        data: {
		        	user_nm
		    	},
		        success: function(data) {
		            if (data.userNm == 1) {
		                // 1 : 아이디가 중복되는 문구
		                nmStat = false;
		                $("#nm_check").text("사용중인 이름입니다.");
		                $("#nm_check").css("color", "red");
		            } else {
		
		                if (nmChk.test(user_nm)) {
		                    // 0 : 아이디 길이 / 문자열 검사
							nmStat = true;
							$('#nm_check').html('사용 가능한 이름입니다.');
						    $('#nm_check').css('color', '#4BB543');
		
		                } else if (user_nm == "") {
		                	nmStat = false;
		                    $('#nm_check').text('사용자명을 입력해주세요');
		                    $('#nm_check').css('color', 'red');
		                } else {
		                	nmStat = false;
		                	$('#nm_check').text("사용자명 형식에 맞지 않습니다.");
		                    $('#nm_check').css('color', 'red');
		                }
		
		            }
		        },
		        error: function() {
		        	swal({
		        		title: '에러',
	                	text: '에러가 발생해서 창을 종료합니다.',
	                	icon: "error"
	                });
		        	window.close();
		        }
		    });
		}
	});
});

//수정 버튼 클릭시
function edit() {
	const td = $('.tdInput').parent();
	$('#edit_button').css('display', 'none');
	$('#cancel_button').css('display', 'inline');
	$('#save_button').css('display', 'inline');
	$('.select_box').attr('disabled', false);
	$(td).addClass('row');
	$('.select_box').addClass('edit_sbox');
	$('.defTd').addClass('uneditable');
	//수정모드 true
	editStat = true;

	swal("수정 모드가 켜졌습니다.");
}

//취소 버튼 클릭시
function cancel() {
	const td = $('.tdInput').parent();
	const checkTd = $('#s_table').children().children();
	
	$('#cancel_button').css('display', 'none');
	$('#save_button').css('display', 'none');
	$('#edit_button').css('display', 'inline');
	$(".editCompleted").removeClass("editCompleted");
	$('.select_box').attr('disabled', true);
	$(td).removeClass('row');
	$('.select_box').removeClass('edit_sbox');
	$('.defTd').removeClass('uneditable');
	//Form 리셋
	$('form')[0].reset();
	$(checkTd).text('');

	editStat = false;
	changeStat = false;
	
	const defLv = $('#userLv option:selected').val();
	if(defLv == 'M' || defLv == 'A'){
		$("#ifLv").hide();
		$("#cpncd").val('');
		$("#cd_check").text('');
	}else {
		$("#ifLv").show();
		
	}

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


//마우스 over 시 input줄 회색
$(document).on("mouseover", ".row", function() {
	const tr = $(this);
	const td = tr.children();
	const input = td.children(".tdInput");
	const inputAll = td.children();

	$(inputAll).addClass('input_hover');

	//해당 input이 readonly일 경우만 addClass
	if (editStat) {
		$(input).addClass('input_hover');
	} else {

	}
});

$(document).on("mouseleave", "td", function() {
	const td = $(this);
	const tr = td.parent();
	const tdAll = tr.children();
	const inputAll = tdAll.children();
	const input = td.children(".tdInput");

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

		if (inputOne.is('.tdInput')) {
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
			
			changeStat = true;

		});
	}
});

//input에서 focusout시 원래대로
$(document).on("focusout", ".tdInput", function() {
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

			if (!changeStat) {
				swal({
					text: "수정 된 데이터가 없습니다.",
					icon: "warning"
				});
				cancel();
				return

			} else {
				const user_cd = $('#user_cd').val();
				const user_id = $('#user_id').val();
				const pwd = $('#pwd').val();
				const user_nm = $('#user_nm').val();
				const userLv = $('#userLv option:selected').val();
				const phone = $('#phone').val();
				const email = $("#email").val();
				const addr = $("#addr").val();
				const addr_dt = $("#addr_dt").val();
				const cpncd = $('#cpncd').val();
			    if(userLv == 'H' || userLv == 'C' || userLv == 'S'){
					if(isEmpty(cpncd)){
						$('#cd_check').text("업체 코드를 입력해주세요.");
				        $('#cd_check').css('color', 'red');
				        
				        return;
					}else{
						$('#cd_check').text("");
					}
				}
			    
			    if(isEmpty(userLv)){
					$('#lv_check').text("권한을 입력해주세요.");
			        $('#lv_check').css('color', 'red');
					return;
				}else if(isEmpty(phone)){
					$('#ph_check').text("핸드폰 번호를 입력해주세요.");
			        $('#ph_check').css('color', 'red');
					return;
				}	
				
				if (user_id == exId) {
					idStat = true;
				}
				if(pwd  == exPwd){
					pwdStat = true;
				}
				if(user_nm  == exNm){
					nmStat = true;
				}
							    
			    if(idStat == true && pwdStat == true && nmStat == true){
					$.ajax({
					    url: "userUpdate",
					    type: "POST",
					    data: {
					    	user_cd,
					    	user_id,
					        pwd,
					        user_nm,
					        userLv,
					        cpncd,
					        phone,
					        email,
					        addr,
					        addr_dt
					    },
					    success: function(data) {
					    	 if (data == "true") {
					    		swal({
					 				text: "수정이 완료되었습니다.",
					 				icon: "success"
						 		}).then(function() {
						 			location.reload();
						 			window.opener.search_go();
						 		});
					        } else {
					        	swal({
					        		text: '수정에 실패했습니다.',
					            	icon: "error"
						 		});  
					        }
					    }
					});
				}
			}
		}
	});
}