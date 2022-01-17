<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>::인증번호 확인::</title>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1280">
<link href="./resources/css/oms_confirm.css" rel="stylesheet" type="text/css" />
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
</head>
<script>
const acc = sessionStorage.getItem('account');
const pwd = sessionStorage.getItem('password');
sessionStorage.clear(); // 전체삭제

 $(document).ready(function() {
     $('.login_insert').on('keypress', function(e) {
         if (e.keyCode == '13') {
             $('#loginbt').click();
         }
     });
 });

 function smsCheck() {
	 
	const smsNo = $('#smsNo').val();
	
	if(isEmpty(smsNo)){
     	swal('인증번호를 입력해주세요.').then(function() {
     		$('#smsNo').focus();
		});
    	
    	return;
	}
	
	if(smsNo.length > 6){
     	swal('인증번호는 6자리 입니다.').then(function() {
     		$('#smsNo').focus();
		});
    	
    	return;
	}
	
	$.ajax({
		type: "POST",
		url: "smsCheck",
		async: false,
		data: {
			smsNo,
			acc,
			pwd
		},
		success: function(data) {
			if (data.smsStat == true) {
				location.replace("oms_main");
			}else if(data.smsStat == "error"){
				swal({
					title: "인증번호 오류",
					text: "5회 이상 틀리셨습니다. 다시 로그인해주세요.",
					icon: "error"
				}).then(function() {
					location.replace("/");
				});
			}else {
			    swal('인증번호가 틀렸습니다.').then(function() {
		     		$('#smsNo').focus();
			    });
			}
         }
     });
 }

 $(document).ready(function() {
     $('#smsNo').focus();
 });

</script>

<body>
	<div id="login_box">
		<img id="login_logo" src="./resources/img/bg_login.png" />
		<div class="login_form">
			<p id="login_text">인증번호 확인</p>
			<input class="login_insert" id="smsNo" name="smsNo" type="text" placeholder="인증번호" /><br>
			<button type="button" id="loginbt" onclick="smsCheck();">확 인</button>
		</div>
	</div>
</body>

</html>
