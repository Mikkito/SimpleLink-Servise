<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control" content="no-store">
<title>Registration</title>
<link href="Styles/Registration.css" rel="stylesheet"/>
</head>
<body>
	<header>
		<div class="header">
			<div class="logo">
				<h3 class="logotype">Simple Link</h3>
			</div>
		</div>
	</header>
	<div class="registr-form">
		<form method="POST" action="/SimpleLinkapp/RegistrUser">
		<p>Введите имя пользователя</p>
		<input type="text" name="user-name">
		<p>Введите пароль</p>
		<input type="password" name="pass">
		<p>Повторите пароль</p>
		<input type="password" name="passrepeat">
		<button class="registration">Зарегистрироваться</button>
		</form>
	</div>
</body>
</html>