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

@WebServlet("/RegistrUser")
public class RegistrUser extends HttpServlet {
	public RegistrUser() {
        super();   
    }
	
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
				Cookie[] cookies = request.getCookies();
				response.addCookie(new Cookie("name", newUser.getName()));
				response.addCookie(new Cookie("uuid", newUser.getUuid()));
				response.sendRedirect("/SimpleLinkapp/");
			} else {
				response.sendRedirect("Registration");
			}
		}
		
	}
	
}
