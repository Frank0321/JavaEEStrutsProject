<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>�c���-��O</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/Backend/VM_Backend_Title.jsp" />
	<h2>�P�����</h2>
	<br />
	<div style="margin-left: 25px;">
		<form action="BackendActionUrl.do" method="get">
			<input type="hidden" name="action" value="goodsSaleReport" /> �_
			<%String startDate = request.getParameter("queryStartDate"); %>
			<%String endDate = request.getParameter("queryEndDate"); %>
			&nbsp; <input type="date" name="queryStartDate"
				style="height: 25px; width: 180px; font-size: 16px; text-align: center;"
				value=<%=startDate %> />
			&nbsp; �� &nbsp; <input type="date" name="queryEndDate"
				style="height: 25px; width: 180px; font-size: 16px; text-align: center;" 
				value=<%=endDate %> />
			<input type="submit" value="�d��"
				style="margin-left: 25px; width: 50px; height: 32px" />
		</form>
		<br />
		<h3 style="color:red;">${sessionScope.message}</h3>
		<%session.removeAttribute("message"); %>
		<table border="1">
			<c:if test="${not empty requestScope.saleReports}">
				<tbody>
					<tr height="50">
						<td width="100"><b>�q��s��</b></td>
						<td width="100"><b>�U�ȩm�W</b></td>
						<td width="100"><b>�ʶR���</b></td>
						<td width="125"><b>���ƦW��</b></td>
						<td width="100"><b>�ʶR���</b></td>
						<td width="100"><b>�ʶR�ƶq</b></td>
						<td width="100"><b>�ʶR���B</b></td>
					</tr>
					<c:forEach items="${requestScope.saleReports}" var="saleReport">
						<tr height="30">
							<td>${saleReport.order_ID}</td>
							<td>${saleReport.customer_Name}</td>
							<td><fmt:formatDate value="${saleReport.order_Date}" pattern="yyyy/MM/dd" /></td>
							<td>${saleReport.goods_Name}</td>
							<td>${saleReport.buy_Quantity}</td>
							<td>${saleReport.goods_Buy_Price}</td>
							<td>${saleReport.cost}</td>			
						</tr>
					</c:forEach>


				</tbody>
			</c:if>
		</table>
	</div>

</body>
</html>