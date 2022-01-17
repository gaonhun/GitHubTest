<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>매장 리스트 조회</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_error.css" />
<script src="./resources/js/oms_common.js"></script>
<script src="./resources/js/sweetalert.min.js"></script>

<script>
$(document).ready(function(){
	
	const errCode = ${requestScope['javax.servlet.error.status_code']};
	
	if(errCode == 400){
		swal({
				text: "잘못 된 요청입니다.(400 ERROR)",
				icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : history.back();
		});
	}else if(errCode == 401){
		swal({
			text: "요청하신 페이지에 대한 접근이 금지되었습니다. (401 ERROR)",
			icon: "error"
		}).then(function() {
			location.replace = "/";
		});
	}else if(errCode == 403){
		swal({
			text: "요청하신 페이지에 대한 권한이 없습니다.",
			icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : history.back();
		});
	}else if(errCode == 404){
		swal({
				text: "요청하신 페이지를 찾을 수 없습니다. (404 ERROR)",
				icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : history.back();
		});
	}else if(errCode == 405){
		swal({
				text: "요청된 메소드가 허용되지 않습니다. (405 ERROR)",
				icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : history.back();
		});
	}else if(errCode == 500){
		swal({
				text: "잘못 된 접근입니다. (500 ERROR)",
				icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : location.replace("/");
		});
	}else if(errCode == 503){
		swal({
				text: "서비스를 사용할 수 없습니다. (503 ERROR)",
				icon: "error"
		}).then(function() {
			history.length == 1 ? window.close() : history.back();
		});
	}
});
</script>
<body>
</body>
</html>