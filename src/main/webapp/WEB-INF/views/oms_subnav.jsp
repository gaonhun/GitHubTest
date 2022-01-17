<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
</head>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_subnav.css" />
<script src="./resources/js/sweetalert.min.js"></script>
<script>
	$(function() {
		
		$(".mainmenu li").click(function() {
			$(this).children(".submenu").stop().slideToggle();
		});

		$('.submenu').click(function() {
			$(this).css('display', 'block');
		});
            
		$('.submenu').hover(function() {
			$(this).css('color', '#F39800');
		});
            
		$('.submenu').mouseleave(function() {
			$(this).css('color', 'white');
		});
	});

	function go_url(num){
		if(num == 0){
			location.href = "oms_main"
		}else if(num == 0101){
			window.open("oms_sub01_01");
		}else if(num == 0201){
			window.open("oms_sub02_01");
		}else if(num == 0301){
			window.open("oms_sub03_01");
		}else if(num == 0401){
			window.open("oms_sub04_01");
		}else if(num == 0402){
			window.open("oms_sub04_02");
		}else if(num == 0501){
			window.open("oms_sub05_01");
		}else if(num == 0502){
			window.open("oms_sub05_02");
		}else if(num == 0503){
			window.open("oms_sub05_03");
		}
	}
</script>
<body>
	<c:if test="${empty UAccount}">
		<script>
			swal({
				text : "로그인 후 이용할 수 있는 페이지입니다.",
				icon : "warning"
			}).then(function() {
				location.replace("/");
			});
		</script>
	</c:if>
	<div class="nav_bar">
		<section id="user_Info">
			<img src="./resources/img/K7_logo.png" style="width: 33px;" onclick="go_url(0);" />
			<p>${UAccount.USER_NM }님 반갑습니다.</p>
		</section>  
		<section id="main-list">
			<ul class="mainmenu">
				<c:if test="${UAccount.USER_LV ne 'C'}">
					<li id="main-item"><img src="./resources/img/icon_shop.png" /> &nbsp; 매장관리<img
						src="./resources/img/btn_list.png" class="list" />
						<ul class="submenu" onclick="go_url(0101);">매장리스트조회</ul>
					</li>
				</c:if>
				<c:if test="${UAccount.USER_LV ne 'C'}">
					<li id="main-item"><img src="./resources/img/icon_product.png" /> &nbsp; 메뉴관리<img
						src="./resources/img/btn_list.png" class="list" />
						<ul class="submenu" onclick="go_url(0201);">메뉴리스트조회</ul>
					</li>
				</c:if>
				<li id="main-item"><img src="./resources/img/icon_order.png" /> &nbsp; 주문내역관리<img
					src="./resources/img/btn_list.png" class="list" />
					<ul class="submenu" onclick="go_url(0301);">주문내역조회</ul>
				</li>
				<!--
				<li id="main-item"><img src="./resources/img/icon_system.png" /> &nbsp; 정산내역관리<img
					src="./resources/img/btn_list.png" class="list" />
					<ul class="submenu" onclick="go_url(0401);">주문채널사 정산</ul>
					<ul class="submenu" onclick="go_url(0402);">배송채널사 정산</ul>
				</li>
				-->
				<c:if test="${UAccount.USER_LV eq 'M' || UAccount.USER_LV eq 'A' || UAccount.USER_LV eq 'H'}">
					<li id="main-item"><img src="./resources/img/icon_system.png" /> &nbsp; 시스템관리<img
						src="./resources/img/btn_list.png" class="list" />
						<c:if test="${UAccount.USER_LV eq 'M'}">
							<ul class="submenu" onclick="go_url(0501);">계정관리</ul>
						</c:if>
						<c:if test="${UAccount.USER_LV eq 'M' || UAccount.USER_LV eq 'A' || UAccount.USER_LV eq 'H'}">
							<ul class="submenu" onclick="go_url(0502);">채널사 관리</ul>
						</c:if>
						<c:if test="${UAccount.USER_LV eq 'M' || UAccount.USER_LV eq 'A'}">
							<ul class="submenu" onclick="go_url(0503);">배달대행사 관리</ul>
						</c:if>
					</li>
				</c:if>
			</ul>
		</section>
		<section id="nav_footer">
			<p class="call_title">[주문채널 콜센터]</p>
			<p class="call_content">
				<c:forEach var="franchList" items="${franchList}" varStatus="status">
					<c:out value="${franchList.CPN_NM }" /> : <c:out value="${franchList.CPN_TEL }"/>
					<br>
				</c:forEach>
			</p>
			<p class="call_title">[배달 대행 콜센터]</p>
			<p class="call_content">
				<c:forEach var="deliveryList" items="${deliveryList}" varStatus="status">
					<c:out value="${deliveryList.CPN_NM }" /> : <c:out value="${deliveryList.CPN_TEL }"/>
					<br>
				</c:forEach>
			</p>
			<p id="logout">
				<a href="logoutCustomer">로그아웃</a>
			</p>
		</section>
	</div>
</body>
</html>