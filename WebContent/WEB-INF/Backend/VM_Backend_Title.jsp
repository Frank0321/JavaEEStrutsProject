<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>�c���-��O</title>
</head>
<body>
	<h1 style="color:blue;">
		${sessionScope.account.name}  �z�n !
	</h1>
	<h1>
		Vending Machine Backend Service <a href="LoginAction.do?action=logout"
			align="left">(�n�X)</a>
	</h1>
	<br />
	<table border="1"
		style="border-collapse: collapse;; margin-left: 25px;">
		<tr>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=queryGoods">�ӫ~�C��</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsReplenishmentView">�ӫ~���@�@�~</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsCreateView">�ӫ~�s�W�W�[</a></td>
			<td width="200" height="50" align="center"><a
				href="BackendActionUrl.do?action=goodsSaleReportView">�P�����</a></td>
			<!-- �o�@�ӨS���q�L CharacterEncodingFilter ?? -->
			<td width="200" height="50" align="center"><a
				href="FrontendActionUrl.do?action=initial">�e�x����</a></td>
		</tr>
	</table>
	<br />
	<br />
	<HR>


</body>
</html>