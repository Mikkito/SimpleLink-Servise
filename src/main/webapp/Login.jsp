<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control" content="no-store">
<title>Login</title>
<link href="Styles/Login.css" rel="stylesheet"/>
</head>
<body>
	<header>
		<div class="header">
			<div class="logo">
				<h3 class="logotype">Simple Link</h3>
			</div>
		</div>
	</header>
	<div class="login-form">
		<form method="POST" action="/SimpleLinkapp/Authentication">
		<p>Введите имя пользователя</p>
		<input type="text" name="user-name">
		<p>Введите пароль</p>
		<input type="password" name="pass">
		<button class="login">Войти</button>
		</form>
	</div>
	<p class=error> <%if (request.getAttribute("Error") != null){ %> ${Error} <% } %> </p>
</body>
</html>