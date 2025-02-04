package controler;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Model.DataBase;
/**
 * Сервлет реализующий перенаправление по коротким ссылкам
 */
@WebServlet("/lnk/*")
public class LinkRedirect extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/**
		 * Получаем все запросы начинающиеся с lnk
		 * получаем тело запроса, разбиваем его на массив строк по разделителю /
		 * передаем последнее значение являющееся токеном короткой ссылки в функцию ShortLongTransition
		 * и полученное из нее значение длинной ссылки записываем в переменную
		 * вызываем функцию для увеличения числа переходов по короткой ссылке
		 * вызываем функцию transitionControl, выполняющую проверку остались ли ещё переходы или данный является последним
		 * выполняем переадресацию на полученную ранее длинную ссылку
		 */
		String respPath = "";
		String path = request.getRequestURI();
		String[] links = path.split("/");
		respPath = ShortLongTransition(links[links.length - 1]);
		DataBase.increaseTransition(DataBase.linkBaseCon(), links[links.length - 1]);
		DataBase.transitionControl(DataBase.linkBaseCon(), links[links.length - 1]);
		response.sendRedirect("http://" + respPath);}
	
	private String ShortLongTransition(String shortLink) {
		String longLink = "";
		longLink = DataBase.readLink(DataBase.linkBaseCon(), shortLink);
		return longLink;
	}
}
