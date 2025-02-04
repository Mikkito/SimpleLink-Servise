package Model;

import java.util.UUID;
import java.security.*;
import java.util.Base64;

/**
 * Класс моделирующий пользователя
 * пользователь имеет uuid по которому в основном его и будем идентифицировать
 * хэшированный пароль служащий пользователю для входа в приложение
 * имя пользователя служащее для удобства пользователя при входе на сайт
 */
public class User {
	private String userName;
	private String hashpass;
	private String uuid;
	
	public User(String name, String pass, String passRepeat) {
		this.userName = name;
		this.hashpass = hashPassGen(pass);
		this.uuid = uuidGeneration();
	}
	public User(String name, String pass) {
		this.userName = name;
		this.hashpass = hashPassGen(pass);
	}
	public User() {
		this.uuid = uuidGeneration();
		this.userName = "guest";
		this.hashpass = null;
	}
	/**
	 * Блок геттеров и сеттеров для передачи объекту класса параметров и получения их обратно
	 */
	public String getName() {
		return userName;
	}
	public String getPass() {
		return hashpass;
	}
	public String getUuid() {
		return uuid;
	}
	public void setName(String name) {
		this.userName = name;
	}
	// Сеттер пароля получает на вход пароль хэширует его и добавляет в объект хэш
	public void setPass(String pass) {
		this.hashpass = hashPassGen(pass);
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * Метод для хэширования пароля пользователя, метод SHA-256 взят случайно
	 * так как проект учебный стойкость шифрования не играет ключевой роли, по тем же причинам не реализовано
	 * добавление соли в пароль
	 * @param pass переменная пароля переданного пользователем
	 * @return возвращает хэш переданного пароля
	 */
	private String hashPassGen(String pass) {
		try {
			MessageDigest digester = MessageDigest.getInstance("SHA-256");
			byte[] passSymbol = pass.getBytes();
			byte[] digest = digester.digest(passSymbol);
			String hex = Base64.getEncoder().encodeToString(digest);
			return hex;
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
	/**
	 * Метод для генерации uuid пользователя для удобства работы с ним возвращаем в текстовом формате
	 * Для генерации используем стандартный метод класса UUID randomUUID
	 * @return возвращает сгенерированный uuid в текстовом формате
	 */
	private String uuidGeneration() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
