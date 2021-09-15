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
	function goodSelected(){
	    document.ReplenishmentForm.action.value = "goodsReplenishmentView";
	    document.ReplenishmentForm.submit();
	}
	
</script>
</head>
<body>
	<jsp:include page="/WEB-INF/Backend/VM_Backend_Title.jsp" />
	<h2>商品維護作業</h2>
	
	<h3 style="color:red;">${sessionScope.message}</h3>
		<%session.removeAttribute("message"); %>
	<br />
	<div style="margin-left: 25px;">
		<form name="ReplenishmentForm" action="BackendActionUrl.do" method="post">
			<input type="hidden" name="action" value="goodsReplenishment" />
			<p>
				飲料名稱： <select name="goodsID" onchange="goodSelected();">
					<option value="">----- 請選擇 -----</option>
					<c:forEach items="${sessionScope.goods}" var="good">
					<option <c:if test="${good.goodsID eq Replenishment.goodsID}">selected</c:if> 
					value="${good.goodsID}">
						${good.goodsName}
					</option>
					</c:forEach>
				</select>
			</p>
			<p>
				更改價格： <input type="number" name="goodsPrice" size="5" value="${Replenishment.goodsPrice}"
					min="0" max="1000">
			</p>
			<p>
				目前數量 : ${Replenishment.goodsQuantity}
			</p>
			<p>
				補貨數量： <input type="number" name="goodsQuantity" size="5" value="0" min="0" max="1000">
			</p>
			<p>
				目前狀態 :
				　<c:if test="${Replenishment.status eq true}">
					上架
				</c:if>
				<c:if test="${Replenishment.status eq false}">
					下架
				</c:if>　　
			</p>
			<p>
				修改狀態： <select name="status">
					
					<option value="1">上架</option>
					<option value="0">下架</option>
				</select>
			</p>
			
<!-- 			<p> -->
<!-- 				<input type="checkbox" name="boom" title="開啟後30秒後自動登出 !" value="1"/>session 自爆按鈕 -->
<!-- 			</p> -->
			<p>
				
				<input type="submit" value="送出">
			</p>
		</form>
	</div>

</body>
</html>