<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Cache-Control" content="no-store">
        <title>SimpleLink</title>
        <link href="Styles/GeneralStyle.css" rel="stylesheet" />
        <script src="Script/GeneralPage.js"></script>
    </head>
	<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
    <body>
        <header>
            <div class="header">
                <div class="user-account">
                    <button class="account-menu" id="account-menu" onclick="ddmenu()">
                        <h3 class="name">
                        <% 	Cookie[] cookies = request.getCookies();
                        char[] nameChar = null;
                        if(cookies != null) {
                            for(Cookie c: cookies) {
                                if("name".equals(c.getName())) {
                                    if (c.getValue() == "guest"){
                                    	out.println("?");
                                    } else{
	                                	nameChar = c.getValue().toCharArray();
	                                	out.println(nameChar[0]);
	                                	break;}}
                        	}
                            if (nameChar == null){
                            	out.println("?");
                            }
                        } else if(cookies == null && nameChar == null) { out.println("?");}%>
                        </h3>
                    </button>
                </div>
            </div>
        </header>
        <main>
            <div class="user-menu" id="menu">
            	<%  if(cookies != null) {
                    for(Cookie c: cookies) {
                        if("name".equals(c.getName())) {
                        	out.write("<button class=\"menu-link\" onclick=\"lk()\">Личный кабинет</button>");
                        	out.write("<button class=\"menu-link\" onclick=\"output()\">Выйти</button>");
                        	break;}
                	}
                    if(nameChar == null){
                    	out.write("<button class=\"menu-link\" onclick=\"login()\">Войти</button>");
                        out.write("<button class=\"menu-link\" onclick=\"registration()\">Зарегистрироваться</button>");
                        out.write("<button class=\"menu-link\" onclick=\"loginByUuid()\">Вход по id </button>");
                    }
                }	else if (cookies == null && nameChar == null) {
                		out.write("<button class=\"menu-link\" onclick=\"login()\">Войти</button>");
                    	out.write("<button class=\"menu-link\" onclick=\"registration()\">Зарегистрироваться</button>");
                    	out.write("<button class=\"menu-link\" onclick=\"loginByUuid()\">Вход по id </button>");}%>
            </div>
            <div class="logo">
                <h1 class="logo-text">
                    <p class="first">Simple</p>
                    <p class="second">Link</p>
                </h1>
            </div>
            <div class="input-line">
                <form method="POST" action="/SimpleLinkapp/LinkControler">
                    <div class="input-link">
                        <input type="text" id="longlink" name="longlink" placeholder="Input your link">
                    </div>
                    <div class="input-button">
                        <button class="generation" id="genbutt">
                        Reduce
                    </button>
                    </div>
                </form>
            </div>
            <div class="loginByUuid">
            	<form method="POST" action="/SimpleLinkapp/LoginUuid">
            		<p class="LoginID">Введите ваш UUID</p>
            		<input class="uuid-place" id="uuid" type="text" name="uuid">
            		<input class="submit" type="submit" value="Войти">
            	</form>
            </div>
            
            <div class="shortlink-place">
            	<p>${shortLink}</p>
            </div>
            
        </main>
    </body>

    </html>