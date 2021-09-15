<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>�c���</title>
<script type="text/javascript">
	function buyQuantityfun(){
		
		document.FrontendForm.action.value = "buyGoodsView";
		//document.FrontendForm.set.page.value = ${requestScope.page};
	    document.FrontendForm.submit()
	}
	
</script>
</head>
<body align="center">
<br/><br/>

<!-- <form action="FrontendActionUrl.do?action=buyGoods" method="post"> -->
<form name="FrontendForm" action="FrontendActionUrl.do" method="post">
	<input type="hidden" name="action" value="buyGoods" />
	<input type="hidden" name="hiddenpage" value="${requestScope.page}" />
	<table width="1000" height="400" align="center">
		<tr>
			<td width="400" height="200">			
				<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200" >			
				<h1>${sessionScope.account.name}�z�n !  �w����{</h1>		
				<a href="BackendActionUrl.do?action=queryGoods" align="left" >��O����</a>&nbsp; &nbsp;
				<a href="LoginAction.do?action=logout" align="left">�n�X</a>
				<br/>
				<font style="color: blue;" >
				${sessionScope.message}
				</font>
				<%session.removeAttribute("message"); %>
				<br/>
				
				<font face="�L�n������" size="4" >
					<b>��J:</b>
					<input type="number" name="inputMoney" max="100000" min="0"  size="5" value="0">
					<b>��</b>				
					<b><input type="submit" value="�e�X">					
					<br/><br/>
					&nbsp;&nbsp;&nbsp; 
					��s:${sessionScope.returnMoney} ��</b>
					<%session.removeAttribute("returnMoney"); %>
				</font>			
			</td>
				
			<td width="600" height="400">
				<table border="1" style="border-collapse: collapse"> 
<!-- 				�j�M�\�� :�@<input name="search" type="search" >	 -->
<!-- 				<br/> -->
					<c:forEach var="i" begin="0" end="1" varStatus="loop">	
					<tr>
						<c:forEach var="item" begin="${i*3}" end="${i*3+2}" varStatus="loop">
						<c:if test="${not empty requestScope.showgoods.get(item)}">
							<td width="300">						
							<font face="�L�n������" size="5">	
								${requestScope.showgoods.get(item).goodsName} 
							</font>
							<br/>
							<font face="�L�n������" size="4" style="color: gray;" >
								<!-- �Ҧp: �i�f�i�� 30��/�� -->	
								${requestScope.showgoods.get(item).goodsPrice} ��/��  
							</font>						
							<!-- �U�ӫ~�Ϥ� -->
							<img border="0" src="${requestScope.showgoods.get(item).goodsImage}" 
							width="150" height="150">					
							<br/>
							<font face="�L�n������" size="3">
								<input type="hidden" name="goodsID" 
								value="${requestScope.showgoods.get(item).goodsID}">
								
								<c:choose>
									<c:when test="${sessionScope.tempBuyMap.containsKey
 									 (requestScope.showgoods.get(item).goodsID) eq true}"> 
										 �ʶR<input type="number" name="buyQuantity" min="0"
								 		max="${requestScope.showgoods.get(item).goodsQuantity}" size="5" 
								 		value="${sessionScope.tempBuyMap.get
 									 	(requestScope.showgoods.get(item).goodsID)}"
									 	onchange="buyQuantityfun();">��
									</c:when>
									<c:otherwise>
										�ʶR<input type="number" name="buyQuantity" min="0"
								 		max="${requestScope.showgoods.get(item).goodsQuantity}" size="5" 
								 		value="0" onchange="buyQuantityfun();">��
									</c:otherwise>
								</c:choose>
								
									
								<!-- ��ܮw�s�ƶq -->
								<br>
								<p style="color: red;">
								(�w�s ${requestScope.showgoods.get(item).goodsQuantity} ��)
								</p>
							</font>	
							</c:if>
						</c:forEach>
					</tr>	
					</c:forEach>
				</table>
				<br/>
					<c:url value='/FrontendActionUrl.do?action=buyGoodsView' var="link">
						<c:param name="page" value="${requestScope.page-1}" />
					</c:url>
					<c:choose>
						<c:when test="${requestScope.page eq 1}">
							<
						</c:when>
						<c:otherwise>
							<a href="${link}" style="text-decoration:none" > "<" </a>
						</c:otherwise>
					</c:choose>
					
				<c:forEach items="${requestScope.rowList}" var="row">
					<c:url value='/FrontendActionUrl.do?action=buyGoodsView' var="link">
						<c:param name="page" value="${row}" />
					</c:url>
					<c:choose>
						<c:when test="${requestScope.page eq row}">
							<a href="${link}"><b>${row}</b></a>
						</c:when>
						<c:otherwise>
							<a href="${link}" style="text-decoration:none" >${row}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:url value='/FrontendActionUrl.do?action=buyGoodsView' var="link">
						<c:param name="page" value="${requestScope.page+1}" />
					</c:url>
					<c:choose>
						<c:when test="${requestScope.next eq 1}">
							<a href="${link}" style="text-decoration:none" > ">" </a>
						</c:when>
						<c:otherwise>
							>
						</c:otherwise>
					</c:choose>
					

			</td>			
		</tr>		
	</table>
</form>

</body>
</html>