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
	function goodSelected(){
	    document.ReplenishmentForm.action.value = "goodsReplenishmentView";
	    document.ReplenishmentForm.submit();
	}
	
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/Backend/VM_Backend_Title.jsp" />
	<h2>�ӫ~���@�@�~</h2>
	
	<h3 style="color:red;">${sessionScope.message}</h3>
		<%session.removeAttribute("message"); %>
	<br />
	<div style="margin-left: 25px;">
		<form name="ReplenishmentForm" action="BackendActionUrl.do" method="post">
			<input type="hidden" name="action" value="goodsReplenishment" />
			<p>
				���ƦW�١G <select name="goodsID" onchange="goodSelected();">
					<option value="">----- �п�� -----</option>
					<c:forEach items="${sessionScope.goods}" var="good">
					<option <c:if test="${good.goodsID eq Replenishment.goodsID}">selected</c:if> 
					value="${good.goodsID}">
						${good.goodsName}
					</option>
					</c:forEach>
				</select>
			</p>
			<p>
				������G <input type="number" name="goodsPrice" size="5" value="${Replenishment.goodsPrice}"
					min="0" max="1000">
			</p>
			<p>
				�ثe�ƶq : ${Replenishment.goodsQuantity}
			</p>
			<p>
				�ɳf�ƶq�G <input type="number" name="goodsQuantity" size="5" value="0" min="0" max="1000">
			</p>
			<p>
				�ثe���A :
				�@<c:if test="${Replenishment.status eq true}">
					�W�[
				</c:if>
				<c:if test="${Replenishment.status eq false}">
					�U�[
				</c:if>�@�@
			</p>
			<p>
				�ק窱�A�G <select name="status">
					
					<option value="1">�W�[</option>
					<option value="0">�U�[</option>
				</select>
			</p>
			
<!-- 			<p> -->
<!-- 				<input type="checkbox" name="boom" title="�}�ҫ�30���۰ʵn�X !" value="1"/>session ���z���s -->
<!-- 			</p> -->
			<p>
				
				<input type="submit" value="�e�X">
			</p>
		</form>
	</div>

</body>
</html>