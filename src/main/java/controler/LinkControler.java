package controler;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import java.io.IOException;

import Model.DataBase;
import Model.ShortLink;
import Model.User;

@WebServlet("/LinkControler")
public class LinkControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LinkControler() {
        super();
        
    }
    /**
     * Получаем пост запрос с главной страницы приложения содержащих адрес длинной ссылки которую хочет сократить пользователь
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Инициализируем переменные для куки, а также переменную пользователя.
		 * Затем считываем куки и при наличии там uuid пользователя создаем ссылку от его имени
		 * В случае отсутствия генерируем гостевой uuid и создаем ссылку от его имени, в этом случае гость сможет создавать
		 * ссылки под своим uuid пока uuid остается записан в куки или же он может зайти по uuid, используя соответствующий функционал приложения
		 */
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		User user = null;
		if(cookies != null) {
            for(Cookie c: cookies) {
                if("uuid".equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }
		if (cookie == null) {
			user = new User();
			response.addCookie(new Cookie("uuid", user.getUuid()));
			DataBase.saveUser(DataBase.linkBaseCon(), user);
		} else {
			user = DataBase.getUser(DataBase.linkBaseCon(), cookie.getValue());
		}
		String longLink = request.getParameter("longlink");
		/**
		 * Проверяем создавалась ли короткая ссылка на указанный адрес ранее для этого пользователя, если создавалась возвращает уже созданную,
		 * если нет производит генерацию новой ссылки и возвращает её значение в ответе пользователю. Ответ пересылается на главную страницу. 
		 */
		if (DataBase.checkLinkThisUser(DataBase.linkBaseCon(), user.getUuid(), longLink) != null) {
			request.setAttribute("shortLink", DataBase.checkLinkThisUser(DataBase.linkBaseCon(), user.getUuid(), longLink));
			ServletContext servletContext = getServletContext();
			RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/SimpleLink");
			requestDispatcher.forward(request, response);
		} else {
			ShortLink shortLink = new ShortLink(user.getUuid(), longLink);
			shortLink.saveLink();
			request.setAttribute("shortLink", "http://localhost:8080/SimpleLinkapp/lnk/" + shortLink.getShortLink());
			getServletContext().getRequestDispatcher("/SimpleLink").forward(request, response);
		}
	}

}
