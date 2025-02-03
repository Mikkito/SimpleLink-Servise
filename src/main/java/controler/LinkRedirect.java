package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import Model.DataBase;

@WebServlet("/lnk/*")
public class LinkRedirect extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String respPath = "";
		String path = request.getRequestURI();
		String[] links = path.split("/");
		respPath = ShortLongTransition(links[links.length - 1]);
		DataBase.increaseTransition(DataBase.linkBaseCon(), links[links.length - 1]);
		
		response.sendRedirect(respPath);}
	
	private String ShortLongTransition(String shortLink) {
		String longLink = "";
		longLink = DataBase.readLink(DataBase.linkBaseCon(), shortLink);
		return longLink;
	}
}
