package Model;

import java.security.*;
import java.util.HexFormat;
import java.time.*;

public class ShortLink {
	private String longLink;
	private String shortLink;
	private String uuid;
	private int transition = 0;
	private int availTransition;
	private LocalDate ttl;
	
	public ShortLink() {
		
	}
	// Стандартный конструктор генерирующий короткую ссылку по uuid пользователя и длинной ссылке
	public ShortLink(String id, String link) {
		setUUID(id);
		setLongLink(link);
		setShortLink(shortLinkGenerator(link));
		setAvailTransition(20);
		setDate(lastDate(7));
	}
	// Пользовательский конструктор для создания ссылки с определенными значениями срока жизни и переходов
	public void shortLink(String link, int avail, int ttl) {
		setLongLink(link);
		setShortLink(link);
		setAvailTransition(avail);
		setDate(lastDate(ttl));
	}
	/**
	 * Блок геттеро и сеттеров, для записи значений в объект и получения их из объекта соответственно
	 */
	public void setUUID(String id) {
		this.uuid = id;
	}
	public void setLongLink(String link) {
		longLink = link;
	}
	public void setShortLink(String link) {
		this.shortLink = link;
	}
	public void setTransition(int transit) {
		this.transition = transit;
	}
	public void setAvailTransition(int transit) {
		availTransition = transit;
	}
	public void setDate(LocalDate date) {
		this.ttl = date;
	}
	public String getUuid() {
		return uuid;
	}
	public String getLongLink() {
		return longLink;
	}
	public String getShortLink() {
		return shortLink;
	}
	public int getTransition() {
		return transition;
	}
	public int getAvailTransition() {
		return availTransition;
	}
	public LocalDate getTtl() {
		return ttl;
	}
	/**
	 * Метод увеличивает текущую дату на заданное количество дней и возвращает день, когда ссылка
	 * должна стать недействительной
	 * @param days количество дней которые ссылка будет существовать
	 * @return возвращает значение типа LocalDate которое на days больше текущей даты на сервере
	 */
	private LocalDate lastDate(int days) {
		LocalDate createDay = LocalDate.now();
		LocalDate lastDay = createDay.plusDays(days);
		return lastDay;
	}
	/**
	 *  Метод для генерации короткой ссылки на основе хэша MD5
	 * @param longlink строка длинной ссылки для которой необходимо сгенерировать короткую
	 * @return Короткая ссылка
	 */
	private String shortLinkGenerator(String longlink) {
		int i = 0;
		int d = 6;
		try {
			/** На основе строки длинной ссылки и uuid пользователя получаем зашифрованную алгоритмом MD5 строку
			 * Для начала пытаемся сгенерировать строку из 6 символов и берем первые 6 от сгенерированной, далее
			 * производим проверку ссылок в базе данных на возникновение коллизии, в случае ее возникновения сдвигаемся на
			 * одно значение по строке и берем другие 6 символов, если шести символьной строки подобрать не удалось расширяем до 7
			 * и снова обходим строку.
			 */
			String sLink = longlink + uuid;
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md5.digest(sLink.getBytes());
			String hashLink = HexFormat.of().formatHex(messageDigest);
			String shLink = hashLink.substring(i, i + d);
			while (true) {
				if (i + d <= hashLink.length()) {
					shLink = hashLink.substring(i, i + d);
					if (!checkShLink(shLink))
						return shLink;
					if (i + d == hashLink.length()) {
						i = 0;
						d++;
					}
					i++;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Метод для проверки наличия ссылки в базе
	 * @param shLink передаем токен короткой ссылки
	 * @return получаем true при наличии совпадений и false, если совпадений нет
	 */
	private boolean checkShLink(String shLink) {
		boolean check = false;
		check = DataBase.checkShLink(DataBase.linkBaseCon(), shLink);
		return check;
	}
	/**
	 * Метод выполняет сохранение ссылки в базу
	 */
	public void saveLink() {
		DataBase.insertNewLink(DataBase.linkBaseCon(), this);
	}
	
}
