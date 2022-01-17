<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>

<head>
<title>계정 등록</title>
</head>
<!-- <link rel="stylesheet" type="text/css" href="./resources/css/confirm_modal.css"> -->
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_sub05_01_insert.css">
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
<script>
let idStat = false;
let pwdStat = false;
let pwdStat2 = false;
let nmStat = false;
let phStat = false;

const idchk = RegExp(/^[A-Za-z0-9_]{4,12}$/); //한글과 영어, 숫자만 사용하였는지 검사
const pwdChk = RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=\*]).*$/); // 8 ~ 15자리 영문,숫자,특수문자 포함
const nmChk = RegExp(/^[가-힣|a-z|A-Z|0-9|]{3,}$/); //한글 또는 영어 and 혼용 불가, 2자 이상
const phChk = RegExp(/^\d{3}-\d{3,4}-\d{4}$/); // 핸드폰 번호 체크
const emChk = RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i); //이메일

$(document).ready(function() {
	
	$("#user_id").focus();
	$("#ifLv").hide();

	$("#userLv").change(function() {
		const userLv = $('#userLv option:selected').val();
		if(userLv == 'H' || userLv == 'C' || userLv == 'S'){		
			$("#ifLv").show();
		}else {
			$("#ifLv").hide();
			$("#cd_check").text('');
			$("#cpncd").val('');
		}
	});
	// ID 체크
	$("#user_id").blur(function() {
	    // id = "id_reg" / name = "userId"
	    const user_id = $('#user_id').val();
	    
	    $.ajax({
	        url:  "userCheck",
	        dataType: 'json',
	        type: 'POST',
	        async: false,
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
	
	});
	
	//비밀번호 체크
	$('#pwd').on("keyup", function(){
		const pwd = $("#pwd").val();
		 
		 if(pwdChk.test(pwd)){
			pwdStat = true;
         	$("#pwd_check").text("사용 가능한 비밀번호입니다.");
            $("#pwd_check").css("color", "#4BB543");
		 }else {
			pwdStat = false;
         	$('#pwd_check').text("영문, 숫자, 특수문자 포함 8 ~ 15자리로 입력해주세요");
            $('#pwd_check').css('color', 'red');
		}
	});

	//비밀번호 확인
	$('#pwd, #pwd_Confirm').on("keyup", function(){
		
		if(isEmpty($(this).val())){
			$('#pwd2_check').text('');
			pwdStat2 = false;
		}else{
			if($('#pwd').val() != $('#pwd_Confirm').val()){
				pwdStat2 = false;
				$('#pwd2_check').text('비밀번호 일치하지 않습니다.');
				$('#pwd2_check').css('color', 'red');
				
			} else{
		        pwdStat2 = true;
				$('#pwd2_check').text('비밀번호가 일치합니다.');
				$('#pwd2_check').css('color', '#4BB543');
			}
		}
	});
	
	$('#phone').on("keyup", function(){
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
	
	$('#user_nm').on("blur", function(){
		const user_nm = $("#user_nm").val();
	    
	    $.ajax({
	        url:  "nmCheck",
	        dataType: 'json',
	        type: 'POST',
	        async: false,
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
	});
});

function setEmailDomain(domain){
    $("#email_domain").val(domain);
}

function insert() {
	const user_id = $('#user_id').val();
	const pwd = $('#pwd').val();
	const pwd2 = $('#pwd_Confirm').val();
	const user_nm = $('#user_nm').val();
	const userLv = $('#userLv option:selected').val();
	const phone = $('#phone').val();
	const email_id = $("#email_id").val();
	const email_domain = $("#email_domain").val();
	const addr = $("#addr").val();
	const addr_dt = $("#addr_dt").val();
	const cpncd = $('#cpncd').val();
	let email ="";
	
	if(userLv == 'H' || userLv == 'C' || userLv == 'S'){
		if(isEmpty(cpncd)){
			$('#cd_check').text("업체 코드를 입력해주세요.");
	        $('#cd_check').css('color', 'red');
	        
	        return;
		}else{
			$('#cd_check').text("");
		}
	}
	
	if(isNotEmpty(email_id) && isNotEmpty(email_domain)){
		email = email_id+"@"+email_domain;
	}
	if(isEmpty(userLv)){
		$('#lv_check').text("권한을 선택해주세요.");
        $('#lv_check').css('color', 'red');
		return;
	}else if(isEmpty(phone)){
		$('#ph_check').text("핸드폰 번호를 입력해주세요.");
        $('#ph_check').css('color', 'red');
		return;
	}	
	if(idStat == true && pwdStat == true && pwdStat2 == true && nmStat == true && phStat == true){
		$.ajax({
		    url: "userInsert",
		    type: "POST",
		    async: false,
		    data: {
		    	user_id,
		        pwd,
		        user_nm,
		        userLv,
		        phone,
		        email,
		        addr,
		        addr_dt,
		        cpncd
		    },
		    success: function(data) {
		    	 if (data == "true") {
		    		swal({
		 				text: "등록이 완료되었습니다.",
		 				icon: "success"
			 		}).then(function() {
			 			opener.parent.search_go();
			 			window.close();
			 		});
		        } else {
		        	swal({
		        		text: '등록을 실패했습니다.',
		            	icon: "error"
			 		}).then(function() {
			 			location.href = "oms_sub05_01_insert";
			 		});     
		        }
		    }
		});		
	}else{
		if(isEmpty(user_id)){
			swal("아이디를 입력해 주세요.").then(function() {
				$('#user_id').focus();
			});
			return
		}else if(isEmpty(pwd)){
			swal("비밀번호를 입력해 주세요.").then(function() {
				$('#pwd').focus()
			});
			return
		}else if(isEmpty(pwd2)){
			swal("비밀번호를 확인 해 주세요.").then(function() {
				$('#pwd_Confirm').focus();
			});
			return
		}else if(isEmpty(user_nm)){
			swal("이름을 입력해주세요.").then(function() {
				$('#user_nm').focus();
			});
			return
		}else if(isEmpty(userLv)){
			swal("권한을 선택해주세요.");
			return
		}
	}
	
}

</script>

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
			
			<div id="header_title">시스템관리 > 계정등록</div>
			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<div class='div_header'>
			<div class="btn_div">
				<button type="button" class='btn_insert' id="btn_insert" onclick='insert();'>등록</button>
				<button class='btn_search' onclick="window.close()">닫기</button>
			</div>
		</div>
		<div class='main_div'>
			<table id="f_table">
				<tr>
					<th colspan='2' class='mtitle'>계정 등록</th>
				</tr>
				<tr>
					<th class='stitle'>계정명<p id="emp">*</p></th>
					<td><input type="text" name="user_id" id="user_id" autocomplete="off" placeholder="영문, 숫자 4 ~ 12자리"/>
				</tr>
				<tr>
					<th class='stitle'>비밀번호<p id="emp">*</p></th>
					<td><input type="password" name="pwd" id="pwd" autocomplete="off" placeholder="영문, 숫자, 특수문자 포함 8 ~ 15자리"/>
				</tr>
				<tr>
					<th class='stitle'>비밀번호 확인<p id="emp">*</p></th>
					<td><input type="password" name="pwd_Confirm" id="pwd_Confirm" autocomplete="off" />
				</tr>
				<tr>
					<th class='stitle'>사용자명<p id="emp">*</p></th>
					<td><input type="text" name="user_nm" id="user_nm" autocomplete="off" placeholder="한글, 영문 3글자 이상"/>
				</tr>
				<tr>
					<th class='stitle'>핸드폰번호<p id="emp">*</p></th>
					<td><input type="text" name="phone" id="phone" autocomplete="off" onKeyup="inputPhoneNumber(this);" maxlength="13" placeholder='숫자'/>
				</tr>
				<tr>
					<th class='stitle'>이메일</th>
					<td>
						<input type="text" id="email_id" class="em_input" placeholder="이메일" maxlength="18" > @ 
						<input type="text" id="email_domain" class="em_input" placeholder="이메일 도메인" maxlength="18"> 
						<select class="select_box" title="도메인" onclick="setEmailDomain(this.value);return false;">
						    <option value="">-선택-</option>
						    <option value="naver.com">naver.com</option>
						    <option value="gmail.com">gmail.com</option>
						    <option value="hanmail.net">hanmail.net</option>
						    <option value="hotmail.com">hotmail.com</option>
						    <option value="korea.com">korea.com</option>
						    <option value="nate.com">nate.com</option>
						    <option value="yahoo.com">yahoo.com</option>
						    <option value="lotte.net">lotte.net</option>
						</select>
					</td>
				<tr>
					<th class='stitle'>주소</th>
					<td>
						<input type="text" id="addr" class="addr" placeholder="주소" /> 
						<input type="text" id="addr_dt" class="addr_dt" placeholder="상세주소"/> 
					</td>
				</tr>
				<tr>
					<th class='stitle'>사용자레벨<p id="emp">*</p></th>
					<td>
					<select class='select_box' id="userLv" name='userLv'>
						<option value='' disabled selected hidden>권한</option>
						<c:forEach var="userLv" items="${userLv }">
							<option value='${userLv.USER_LV }'>${userLv.LEVEL_EXP }</option>
						</c:forEach>
					</select>
				</tr>
				<tr id="ifLv">
					<th class='stitle'>업체코드<p id="emp">*</p></th>
					<td>
						<input type="text" id="cpncd" class="cpncd" placeholder="업체코드" /> 
					</td>
				</tr>
				<tr>
					<th class='stitle'>등록일시</th>
					<td id='signtm' style="background-color: #F4F4F4;" />
				</tr>
				<tr>
					<th class='stitle'>수정일시</th>
					<td id='moditm' style="background-color: #F4F4F4;" />
				</tr>
			</table>
			<table id="s_table">
				<tr>
					<td style="height: 38px"/>
				</tr>
				<tr>
					<td class="check_font" id="id_check" />
				</tr>
				<tr>
					<td class="check_font" id="pwd_check" />
				</tr>
				<tr>
					<td class="check_font" id="pwd2_check" />
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
					<td class="check_font" id="lv_check" style="height: 43px"/>
				</tr>
				<tr>
					<td class="check_font" id="cd_check"/>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>