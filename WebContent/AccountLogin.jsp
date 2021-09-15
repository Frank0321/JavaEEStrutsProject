<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>AccountLogin</title>
<script type="text/javascript">
	function myFunction() {
		var x = document.getElementById("pwd");
		if (x.type === "password") {
			x.type = "text";
		} else {
			x.type = "password";
		}
	}
</script>
</head>
<body>
	<form action="LoginAction.do" method="post">
	<h2>使用者登入</h2>
		<input type="hidden" name="action" value="login" /> ID:<input
			type="text" name="id" value = "${requestScope.inputID}"/> <br />
		<br /> PWD:<input id="pwd" type="password" name="pwd" />
		<br />
		<br /> <input type="checkbox" onclick="myFunction()">Show
		Password <br />
		<br /> <input type="submit" />
	</form>
	<h3 style="color:red;">${requestScope.message}</h3>
	<%request.removeAttribute("message"); %>
	
	
	<br/><br/><br/><br/>
	<p>(供測試使用)</p>
	<p> 使用者名稱, 帳號, 密碼 </p>
	<p>Jay, A124243295, 1234</p>
	<p>Jolin, J213664153, 123456</p>
	<p>Tiffany, D201663865, 1234</p>
</body>
</html>