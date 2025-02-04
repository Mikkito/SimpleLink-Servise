package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

import Model.DataBase;
import Model.User;
/**
 * Данный сервлет отвечает за возможность авторизации по uuid, в соответствии с требованиями ТЗ
 * В сервлете мы получаем форму с главной страницы производим поиск пользователя в базе и записываем его данные в Cookie
 * После чего осуществляем его перенаправление на страницу.
 */
@WebServlet("/LoginUuid")
public class LoginUuid extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Получаем из запроса введенный пользователем uuid.
		 * Производим проверку наличия пользователя в базе, если пользователь существует получаем его имя и uuid
		 * полученные данные записываем в куки для поддержания сессии и выполняем перенаправление на страницу личного кабинета,
		 * если отсутствует перенаправляем пользователя на главную
		 */
		String uuid = request.getParameter("uuid");
		String verifiedUuid = DataBase.checkReturnUuid(DataBase.linkBaseCon(), uuid);
		if (verifiedUuid != "") {
			User user = DataBase.getUser(DataBase.linkBaseCon(), verifiedUuid);
			Cookie cookieUuid = new Cookie("uuid", user.getUuid());
			Cookie cookieName = new Cookie("name", user.getName());
			response.addCookie(cookieUuid);
			response.addCookie(cookieName);
			getServletContext().getRequestDispatcher("/LC").forward(request, response);}
		else {
			response.sendRedirect("SimpleLink");
		}
	}
}
