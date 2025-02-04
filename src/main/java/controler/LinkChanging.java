package controler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Model.DataBase;
import Model.ShortLink;

@WebServlet("/LinkChanging")
public class LinkChanging extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if(cookies != null) {
            for(Cookie c: cookies) {
                if("uuid".equals(c.getName())) {
                    cookie = c;
                	break;}
        	}}
		String uuid = cookie.getValue();
		String longLink = request.getParameter("LongLink");
		String shortLink = request.getParameter("ShortLink");
		int transition = Integer.parseInt(request.getParameter("Transition"));
		int availTransition = Integer.parseInt(request.getParameter("AvailTransition"));
		String date = request.getParameter("TimeToLife");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		ShortLink newLink = new ShortLink();
		newLink.setUUID(uuid);
		newLink.setLongLink(longLink);
		newLink.setShortLink(shortLink);
		newLink.setTransition(transition);
		newLink.setAvailTransition(availTransition);
		newLink.setDate(localDate);
		DataBase.changeLink(DataBase.linkBaseCon(), newLink);
		response.sendRedirect("LC");
	}
}





