<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<title>::로그인::</title>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1280">
<link href="./resources/css/oms_index.css" rel="stylesheet" type="text/css" />
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/oms_index.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>
</head>

<body>
	<div id="login_box">
		<img id="login_logo" src="./resources/img/bg_login.png" />
		<div class="login_form">
			<p id="login_text">Sign In</p>
			<div id="idsave"><input type="checkbox" id="idSaveCheck"><label for="idSaveCheck" id="idlabel"> ID저장</label></div>
			<input class="login_insert" id="userId" name="ACCOUNT" type="text" placeholder="아이디" /><br>
			<input class="login_insert" id="userPw" name="PASSWORD" type="password" placeholder="비밀번호" /><br> 
			<input type="button" id="loginbt" onclick="logincheck();">
		</div>
	</div>
</body>

</html>
