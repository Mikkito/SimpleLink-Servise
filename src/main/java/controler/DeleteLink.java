package controler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

import Model.DataBase;

@WebServlet("/DeleteLink")
public class DeleteLink extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = request.getReader().readLine())  != null) {
			body.append(line);
		}
		String[] postBody = body.toString().split("&");
		String uuid = postBody[0].replaceAll("uuid=", "");
		String shortLink = postBody[1].replaceAll("shortLink=http://localhost/SimpleLinkapp/lnk/", "");
		DataBase.deleteLink(DataBase.linkBaseCon(), uuid, shortLink);
		//response.sendRedirect("/SimpleLink");
	}
}
