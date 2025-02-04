package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ArrayList;
import java.util.List;
/** В данном классе реализовано взаимодействие с базой данных postgressql.
 * Класс представляет из себя набор методов для подключения к базе данных и
 * методов, реализующих взаимодействия с базой данных, необходимых для работы приложения
*/
public class DataBase {
	/**
	 * Метод реализует подключение к созданной базе данных
	 * 
	 */
	public static Connection linkBaseCon() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/linkbase";
			Properties authorization = new Properties();
			authorization.put("user", "postgres");
			authorization.put("password", "postgres");
			connection = DriverManager.getConnection(url, authorization);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * Данный метод реализует добавление новой ссылки в базу данных
	 * @param connection подключение к базе данных, необходима для подключения к базе
	 * @param shortlink короткая ссылка которую нужно добавить в базу данных
	 */
	public static void insertNewLink(Connection connection, ShortLink shortlink) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO links (uuid, longlink, shortlink, transition, atransition, ttl) VALUES (?, ?, ?, ?, ?, ?)");
			statement.setString(1, shortlink.getUuid());
			statement.setString(2, shortlink.getLongLink());
			statement.setString(3, shortlink.getShortLink());
			statement.setInt(4, shortlink.getTransition());
			statement.setInt(5, shortlink.getAvailTransition());
			statement.setObject(6, shortlink.getTtl());
			statement.executeUpdate();
			connection.close(); 
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Метод для считывания длинной ссылки из базы данных при передачи короткой ссылки
	 * @param connection подключение к базе данных
	 * @param shortLink токен короткой ссылки в формате "4fwe24dx"
	 * @return Длинная ссылка на которую необходимо выполнить переход
	 * при отсутствии переводит на сайт сокращения ссылок
	 */
	public static String readLink(Connection connection, String shortLink) {
		String longLink = "";
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT longlink FROM links WHERE shortlink = ?");
			statement.setString(1, shortLink);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				longLink = resultSet.getString("longlink");
			} else {
				longLink = "http://localhost:8080/SimpleLinkapp/SimpleLink";
				}
		} catch (Exception e) {
			System.out.println(e);
		}
		return longLink;
	}
	/**
	 * Метод для получения информации о всех ссылках данного пользователя
	 * @param connection пересенная соединения с базой данных
	 * @param uuid uuid пользователя
	 * @return возвращает массив ссылок закрепленных за данным пользователем
	 */
	public static List<ShortLink> readLinkInfo(Connection connection, String uuid) {
		List<ShortLink> linkSet = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM links WHERE uuid = ?");
			statement.setString(1, uuid);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				ShortLink link = new ShortLink();
				link.setLongLink(resultSet.getString("longlink"));
				link.setShortLink(resultSet.getString("shortlink"));
				link.setTransition(resultSet.getInt("transition"));
				link.setAvailTransition(resultSet.getInt("atransition"));
				link.setDate(resultSet.getObject("ttl", LocalDate.class));
				linkSet.add(link);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return linkSet;
	}
	/**
	 * Метод для изменения ссылки пользователя
	 * @param connection передает соединение с базой данных
	 * @param newdata новая ссылка в ней содержатся данные которые нужно внести в базу
	 */
	public static void changeLink(Connection connection, ShortLink newdata) {
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE links SET uuid = ?, longlink = ?, shortlink = ?, atransition = ?, ttl = ?");
			statement.setString(1, newdata.getUuid());
			statement.setString(2, newdata.getLongLink());
			statement.setString(3, newdata.getShortLink());
			statement.setInt(4, newdata.getAvailTransition());
			statement.setObject(5, newdata.getTtl());
			statement.executeUpdate();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Метод служит для удаления ссылки пользователя
	 * @param connection переменная соединения с базой данных
	 * @param uuid uuid пользователя, его передача гарантирует, что другой пользователь не удалит чужую ссылку
	 * @param shortLink переменная содержащая токен короткой ссылки которую требуется удалить
	 */
	public static void deleteLink(Connection connection, String uuid, String shortLink) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM links WHERE uuid = ? AND shortLink = ?");
			statement.setString(1, uuid);
			statement.setString(2, shortLink);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	/**
	 * Проверяет наличие заданной короткой ссылки и может использоваться для контроля за
	 * отсутствием дублирования коротких ссылок
	 * @param connection переменная соединения с базой данных
	 * @param link пересенная содержащая токен короткой ссылки
	 * @return возвращает результат проверки true при наличии ссылки false при отсутствии
	 */
	public static boolean checkShLink(Connection connection, String link){
		boolean check = false;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT true FROM links WHERE shortlink = ? LIMIT 1");
			statement.setString(1, link);
			ResultSet resultSet = statement.executeQuery();
			check = resultSet.getBoolean(1);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return check;
	}
	/**
	 * Метод для увеличения числа совершенных переходов по ссылке
	 * совершает запрос к базе данных получает текущее число переходов
	 * затем увеличивает его на единицу и сохраняет значение в базе
	 * @param connection переменная соединения с базой данных
	 * @param link токен короткой ссылки по которой совершается переход
	 */
	public static void increaseTransition(Connection connection, String link) {
		int transition = 0;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT transition FROM links WHERE shortlink = ?");
			statement.setString(1, link);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				transition = resultSet.getInt("transition");
				transition++;
			}
			statement = connection.prepareStatement("UPDATE links SET transition = ? WHERE shortlink = ?");
			statement.setInt(1, transition);
			statement.setString(2, link);
			statement.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	/**
	 * Метод для контроля оставшихся переходов в случае если доступные переходы закончились удаляет ссылку
	 * @param connection переменная соединения
	 * @param link короткая ссылка по которой происходит преход. Указывается в формате токена
	 */
	public static void transitionControl(Connection connection, String link) {
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT transition, atransition FROM links WHERE shortlink = ?");
			statement.setString(1, link);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int transition = resultSet.getInt("transition");
				int atransition = resultSet.getInt("atransition");
				if (transition >= atransition) {
					statement = connection.prepareStatement("DELETE FROM links WHERE shortlink = ?");
					statement.setString(1, link);
					statement.executeUpdate();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/**
	 * Сохраняет пользователя в таблицу users базы данных
	 * @param connection переменная соединения
	 * @param newUser пользователь которого добавляем в базу данных
	 * @return Возвращает true, если операция выполнена успешно
	 */
	public static boolean saveUser(Connection connection, User newUser) {
		boolean success = false;
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO users(uuid, username, password) VALUES (?, ?, ?)");
			statement.setString(1, newUser.getUuid());
			statement.setString(2, newUser.getName());
			statement.setString(3, newUser.getPass());
			statement.executeUpdate();
			success = true;
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return success;
	}
	/**
	 * Метод реализующий проверку существования имени пользователя в базе данных
	 * @param connection переменная соединения
	 * @param name имя пользователя
	 * @return возвращает true при наличии пользователя с указанным именем в базе
	 */
	public static boolean checkUsername(Connection connection, String name) {
		boolean userExist = false;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT username FROM users WHERE username = ?");
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userExist = true;
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return userExist;
	}
	/**
	 * Проверка наличия пользователя в базе данных, функция предназначена для авторизации пользователя на сайте,
	 * используя хэш пароля и имя пользователя, она находит совпадения и возвращает uuid зарегистрированного пользователя или
	 * строку "FALSE", если пользователя с указанными данными не существует
	 * @param connection переменная соединения
	 * @param user переменная содержащая объект user с именем и хэшом пароля внутри
	 * @return возвращает uuid или "FALSE" при отсутствии пользователя в базе
	 */
	public static String checkUser(Connection connection, User user) {
		String uuid = "FALSE";
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT uuid, username FROM users WHERE username = ? AND password = ?");
			statement.setString(1, user.getName());
			statement.setString(2, user.getPass());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				uuid = resultSet.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return uuid;
	}
	/**
	 * Получение пользователя из таблицы возвращает объект User с параметрами uuid и username
	 * @param connection переменная соединения с базой данных
	 * @param uuid uuid пользователя
	 */
	public static User getUser(Connection connection, String uuid) {
		User user = null;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT uuid, username FROM users WHERE uuid = ?");
			statement.setString(1, uuid);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				user = new User();
				user.setUuid(resultSet.getString("uuid"));
				user.setName(resultSet.getString("username"));
			}
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}
	/**
	 * Метод написан для авторизации по uuid. Метод производит поиск переданного ему uuid в базе
	 * и при нахождении возвращает uuid обратно и возвращает пустую строку, если указанного uuid не обнаруженно
	 * @param connection переменная соединения с базой данных
	 * @param uuid uuid пользователя совпадение с которым будет искать функция
	 * @return возвращает uuid, если такой был найден или пустую строку, если переданного uuid нет в базе
	 */
	public static String checkReturnUuid(Connection connection, String uuid) {
		String trueUuid = "";
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT uuid FROM users WHERE uuid = ?");
			statement.setString(1, uuid);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				trueUuid = resultSet.getString("uuid");
			} else {
				return trueUuid;
			}
			connection.close();
		} catch (Exception e ) {
			System.out.println(e);
		}
		return trueUuid;
	}
	/**
	 * Метод возвращающий короткую ссылку пользователя по длинной ссылке и uuid пользователя
	 * @param connection переменная соединения с базой данных
	 * @param uuid значение uuid пользователя
	 * @param longLink значение длинной ссылки пользователя
	 * @return возвразяет короткую ссылку, если есть и null при отсутствии
	 */
	public static String checkLinkThisUser(Connection connection, String uuid, String longLink) {
		String shortLink = null;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT shortlink FROM links WHERE uuid = ? AND longlink = ?");
			statement.setString(1, uuid);
			statement.setString(2, longLink);
			ResultSet resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				return shortLink;
			}
			shortLink = resultSet.getString("shortlink");
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return shortLink;
	}
}
