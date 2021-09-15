<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Language" content="zh-tw">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>販賣機-後臺</title>
<script type="text/javascript">
	
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/Backend/VM_Backend_Title.jsp" />
	<h2>商品列表</h2>
	
	<h3 style="color:red;">${sessionScope.message}</h3>
		<%session.removeAttribute("message"); %>
	<br />
	<div style="margin-left: 25px;">
		<table border="1">
			<tbody>
				<tr height="50">
					<td width="150" align="center"><b>商品編號</b></td>
					<td width="150" align="center"><b>商品名稱</b></td>
					<td width="100" align="center"><b>商品價格</b></td>
					<td width="100" align="center"><b>現有庫存</b></td>
					<td width="100" align="center"><b>商品狀態</b></td>
					<td width="100" align="center"><b>商品刪除</b></td>
				</tr>
				<%int i = 0 ;%>
				<c:forEach items="${sessionScope.goods}" var="good">
					<tr height="30" align="center">
						<%i++ ;%>
						<td><%=i%></td>
						<td>${good.goodsName}</td>
						<td>${good.goodsPrice}</td>
						<td>${good.goodsQuantity}</td>
						<c:if test="${good.status eq true}">
							<td>上架</td>
						</c:if>
						<c:if test="${good.status eq false}">
							<td style="color:red;">下架</td>
						</c:if>
						<td>
							<c:url value="/BackendActionUrl.do" var="deleUrl">
								<c:param name="action" value="goodsdelete" />
								<c:param name="deleID" value="${good.goodsID}" />
							</c:url>
							<a href="${deleUrl}">刪除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>