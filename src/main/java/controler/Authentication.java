package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;

import Model.DataBase;
import Model.User;

@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	public Authentication() {
        super();   
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("user-name");
		String password = request.getParameter("pass");
		User user = new User(name, password);
		String uuid = DataBase.checkUser(DataBase.linkBaseCon(), user);
		if (!uuid.equals("FALSE")) {
			response.addCookie(new Cookie("name", name));
			response.addCookie(new Cookie("uuid", uuid));
			response.sendRedirect("/SimpleLinkapp/");
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login");
			request.setAttribute("Error", "Неверный логин или пароль");
			dispatcher.forward(request, response);
		}

}}
