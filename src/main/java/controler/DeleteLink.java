package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Model.DataBase;
/**
 * Сервлет предназначенный для удаления ссылки из базы данных
 */
@WebServlet("/DeleteLink")
public class DeleteLink extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Получаем POST запрос в кодировке x-www-urlencoded используя reader считываем закодированную строку
		 * после чего разбиваем ее по значениям используя метод split с разделителем &
		 * получаем значения uuid и shortLink, удаляя методом replaceALL подстроки указывающие названия переменных
		 * Используем метод deleteLink для удаления ссылки из базы данных
		 */
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = request.getReader().readLine())  != null) {
			body.append(line);
		}
		String[] postBody = body.toString().split("&");
		String uuid = postBody[0].replaceAll("uuid=", "");
		String shortLink = postBody[1].replaceAll("shortLink=", "");
		DataBase.deleteLink(DataBase.linkBaseCon(), uuid, shortLink);
	}
}
