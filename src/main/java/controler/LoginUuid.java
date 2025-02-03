package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

@WebServlet("/LoginUuid")
public class LoginUuid extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uuid = request.getParameter("uuid");
		Cookie cookie = new Cookie("uuid", uuid);
		response.addCookie(cookie);
		getServletContext().getRequestDispatcher("/LC").forward(request, response);
	}
}
