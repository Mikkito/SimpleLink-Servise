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
 * Сервлет для регистрации пользователей
 */

@WebServlet("/RegistrUser")
public class RegistrUser extends HttpServlet {
	public RegistrUser() {
        super();   
    }
	/**
	 * Получаем из пост запроса отправленного со страницы регистрации параметры имени пользователя, пароля и его повтора
	 * Проверяем совпадают ли пароль и повтор пароля, при отсутствии совпадения пересылаем пользователя обратно на страницу регистрации
	 * В противном случаее создаем пользователя с соответствующими параметрами, производим проверку свободно ли имя пользователя,
	 * если доступно производим сохранение нового пользователя в базу данных создаем куки с именем и uuid, после чего перенаправляем пользователя
	 * на главную страницу. В случае, если имя занято перенаправляем на регистрацию
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("user-name");
		String password = request.getParameter("pass");
		String passwordRepeat = request.getParameter("passrepeat");
		if (!password.equals(passwordRepeat)) {
			response.sendRedirect("Registration");
		} else {
			User newUser = new User(name, password, passwordRepeat);
			if (!DataBase.checkUsername(DataBase.linkBaseCon(), newUser.getName())) {
				DataBase.saveUser(DataBase.linkBaseCon(), newUser);
				response.addCookie(new Cookie("name", newUser.getName()));
				response.addCookie(new Cookie("uuid", newUser.getUuid()));
				response.sendRedirect("/SimpleLinkapp/");
			} else {
				response.sendRedirect("Registration");
			}
		}
		
	}
	
}
