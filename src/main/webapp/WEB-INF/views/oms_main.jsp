<%@page import="gaon.oms.project.model.dto.K7_USER_ACCOUNT_OMS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>메인 화면</title>
</head>
<script src="./resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="./resources/css/oms_common.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/oms_main.css" />
<script src="./resources/js/oms_common.js"></script>
<body>
	<div class="subnav_div">
		<jsp:include page="oms_subnav.jsp"></jsp:include>
	</div>
		<div class="page_div">
		<header>
 			<img src="./resources/img/top_logo.png" id="top_logo" />
		</header>
		<div class="main_div">
			<section class="table_section">
				<div class="t_header">
					<p class="title_p">전일주문통계</p>
				</div>
				<div class ="table_div">
					<table>
						<tr>
							<th class='title'>채널사명</th>
							<th class='title'>전체건수</th>
							<th class='title'>주문합계(원)</th>
							<th class='title'>성공건수</th>
							<th class='title'>성공합계(원)</th>
							<th class='title'>실패건수</th>
							<th class='title'>실패합계(원)</th>
						</tr>
						<c:forEach var="yesList" items="${yesList}" varStatus="status">
							<c:if test="${yesList.ORDER_CH_NM ne null }">
								<tr>
									<td style="text-align: left;">${yesList.ORDER_CH_NM}</td>
									<td><fmt:formatNumber value="${yesList.TOTAL_COUNT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value="${yesList.TOTAL_AMT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value='${yesList.COMMIT_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${yesList.COMMIT_AMT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${yesList.CANCEL_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${yesList.CANCEL_AMT}' pattern='#,###' /></td>
								</tr>
							</c:if>
						</c:forEach>
						<c:forEach begin="${fn:length(yesList)}" end="9">
							<tr>
								<td style="border: 0px"></td>
							</tr>
						</c:forEach>
						<c:forEach var="total" items="${yesList}" varStatus="status">
							<c:if test="${empty total.ORDER_CH_NM && total.TOTAL_COUNT ne null}">
								<tr>
									<td>합계</td>
									<td><fmt:formatNumber value="${total.TOTAL_COUNT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value="${total.TOTAL_AMT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.COMMIT_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.COMMIT_AMT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.CANCEL_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.CANCEL_AMT}' pattern='#,###' /></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</section>
			<section class="table_section">
				<div class="t_header">
					<p class="title_p">당일주문통계</p>
				</div>
				<div class ="table_div">
					<table>
						<tr>
							<th class='title'>채널사명</th>
							<th class='title'>전체건수</th>
							<th class='title'>주문합계(원)</th>
							<th class='title'>성공건수</th>
							<th class='title'>성공합계(원)</th>
							<th class='title'>실패건수</th>
							<th class='title'>실패합계(원)</th>
						</tr>
						<c:forEach var="todayList" items="${todayList}" varStatus="status">
							<c:if test="${todayList.ORDER_CH_NM ne null }">
								<tr>
									<td style="text-align: left;">${todayList.ORDER_CH_NM}</td>
									<td><fmt:formatNumber value="${todayList.TOTAL_COUNT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value="${todayList.TOTAL_AMT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value='${todayList.COMMIT_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${todayList.COMMIT_AMT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${todayList.CANCEL_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${todayList.CANCEL_AMT}' pattern='#,###' /></td>
								</tr>
							</c:if>
						</c:forEach>
						<c:forEach begin="${fn:length(todayList)}" end="9">
							<tr>
								<td style="border: 0px;"></td>
							</tr>
						</c:forEach>
						<c:forEach var="total" items="${todayList}" varStatus="status">
							<c:if test="${empty total.ORDER_CH_NM && total.TOTAL_COUNT ne null}">
								<tr>
									<td>합계</td>
									<td><fmt:formatNumber value="${total.TOTAL_COUNT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value="${total.TOTAL_AMT}" pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.COMMIT_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.COMMIT_AMT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.CANCEL_COUNT}' pattern='#,###' /></td>
									<td><fmt:formatNumber value='${total.CANCEL_AMT}' pattern='#,###' /></td>
								</tr>
							</c:if>
						</c:forEach>
					</table>
				</div>
			</section>
		</div>
	</div>
</body>
</html>