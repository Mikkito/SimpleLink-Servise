<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.Cookie, java.util.List, java.util.ArrayList, Model.DataBase, Model.ShortLink" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Cache-Control" content="no-store">
        <title>SimpleLink</title>
        <link href="Styles/LC.css" rel="stylesheet" />
        <script src="Script/LC.js"></script>
    </head>
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
                        	out.write("<button class=\"menu-link\" onclick=\"general()\">Главная</button>");
                        	out.write("<button class=\"menu-link\" onclick=\"output()\">Выйти</button>");
                        	break;}
                	} if (nameChar == null){
                		for(Cookie c: cookies) {
                        	if("uuid".equals(c.getName())) {
                            	out.write("<button class=\"menu-link\" onclick=\"general()\">Главная</button>");
                            	out.write("<button class=\"menu-link\" onclick=\"output()\">Выйти</button>");
                            	break;}
                    	}
                	}
                }	else if (cookies == null || nameChar == null) {
                	out.write("<button class=\"menu-link\" onclick=\"login()\">Войти</button>");
                    out.write("<button class=\"menu-link\" onclick=\"registration()\">Зарегистрироваться</button>");}%>
            </div>
            <div class="LinkMenu">
                <form method="POST" action="/SimpleLinkapp/LinkChanging">
	                <div class="longlink-div">
	                	<p class="form-text">Длинная ссылка:</p>
                		<input type="text" class="LongLink" name="LongLink" value="">
                	</div>
	                <div class="shortlink-div">
	                	<p class="form-text">Короткая ссылка:</p>
                		<input type="text" class="ShortLink" name="ShortLink" value="">
                	</div>
                	<div class="transition-div">
	                	<p class="form-text">Количество совершенных переходов:</p>
                		<input type="text" class="Transition" name="Transition" value="">
                	</div>
                	<div class="avail-div">
                		<p class="form-text">Доступные переходы:</p>
                		<input type="text" class="AvailTransition" name="AvailTransition" value="">
                	</div>
                	<div class="time-div">
                		<p class="form-text">Ссылка доступна до:</p>
                		<input type="text" class="TimeToLife" name="TimeToLife" value="">
                	</div>
                	<button class="ChangeLink">Изменить</button>
                </form>	
                	<button class="DropMenu" onclick="closeLinkMenu()">Отмена</button>
            </div>
            <div class = "result-table">
            	<% String uuid = null;
            	for(Cookie c: cookies) {
                    if("uuid".equals(c.getName())){
                    	uuid = c.getValue();
                    }
            	}
            	ArrayList<ShortLink> usersLinks = (ArrayList<ShortLink>) DataBase.readLinkInfo(DataBase.linkBaseCon(), uuid);%>
            	<table>
            		<thead></thead>
            	<%
            	for (int i = 0; i < usersLinks.size(); i++){
            		ShortLink link = usersLinks.get(i);
            		String longLink = link.getLongLink();
            		String shortLink = "http://localhost:8080/SimpleLinkapp/lnk/" + link.getShortLink();
            		int transition = link.getTransition();
            		int atransition = link.getAvailTransition();
            		String timeToLife = link.getTtl().toString();
            		out.write("<tr class=\"" + i + "\"><td class=\"" + i + ".1\">" + longLink + "</td><td class=\"" + i + ".2\">" + shortLink + "</td>" +
            		"<td class=\"" + i + ".3\">" + transition + "</td><td class=\"" + i + ".4\">" + atransition + "</td>" +
            		"<td class=\"" + i + ".5\">" + timeToLife + "</td>" +
            		"<td><button class=\"LinkMenuButton\" onclick=\"openLinkMenu(" + i + ")\">Изменить ссылку</button></td>" +
            		"<td><button class=\"DeleteLink\" onclick=\"deleteLink(" + i + ")\">Удалить ссылку</button></td></tr>");
            	}%>
            	</table>
            </div>
        </main>
    </body>

    </html>