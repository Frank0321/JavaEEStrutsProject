<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>販賣機-後臺</title>
</head>
<body>
	<h1 style="color:blue;">
		${sessionScope.account.name}  您好 !
	</h1>
	<h1>
		Vending Machine Backend Service <a href="LoginAction.do?action=logout"
			align="left">(登出)</a>
	</h1>
	<br />
	<table border="1"
		style="border-collapse: collapse;; margin-left: 25px;">
		<tr>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=queryGoods">商品列表</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsReplenishmentView">商品維護作業</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsCreateView">商品新增上架</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsSaleReportView">銷售報表</a></td>
			<!-- 這一個沒有通過 CharacterEncodingFilter ?? -->
			<td width="200" height="50" align="center"><a
				href="FrontendActionUrl.do?action=initial">前台頁面</a></td>
		</tr>
	</table>
	<br />
	<br />
	<HR>


</body>
</html>