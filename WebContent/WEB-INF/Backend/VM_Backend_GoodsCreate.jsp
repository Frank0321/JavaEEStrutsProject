<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
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
	<h2>�ӫ~�s�W�W�[</h2>
	
	<h3 style="color:red;">${sessionScope.message}</h3>
		<%session.removeAttribute("message"); %>
	<br />
	<div style="margin-left: 25px;">
		<form action="BackendActionUrl.do?action=goodsCreate"
			enctype="multipart/form-data" method="post">
			<p>
				���ƦW�١G <input type="text" name="goodsName" size="10" />
			</p>
			<p>
				�]�w����G <input type="number" name="goodsPrice" size="5" value="0"
					min="0" max="1000" />
			</p>
			<p>
				��l�ƶq�G <input type="number" name="goodsQuantity" size="5" value="0"
					min="0" max="1000" />
			</p>
			<p>
				�ӫ~�Ϥ��G <input type="file" name="goodsImage" />
			</p>
			<p>
				�ӫ~���A�G <select name="status">
					<option value="1">�W�[</option>
					<option value="0">�U�[</option>
				</select>
			</p>
			<p>
				<input type="submit" value="�e�X">
			</p>
		</form>
		
		
		
	</div>
</body>
</html>