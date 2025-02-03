package Model;

import java.security.*;
import java.util.HexFormat;
import java.time.*;
import java.util.UUID;

public class ShortLink {
	private String longLink;
	private String shortLink;
	private String uuid;
	private int transition = 0;
	private int availTransition;
	private LocalDate ttl;
	
	public ShortLink() {
		
	}
	public ShortLink(String id, String link) {
		setUUID(id);
		setLongLink(link);
		setShortLink(shortLinkGenerator(link));
		setAvailTransition(20);
		setDate(lastDate(7));
	}
	public void shortLink(String link, int avail, int ttl) {
		setLongLink(link);
		setShortLink(link);
		setAvailTransition(avail);
		setDate(lastDate(ttl));
	}
	
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
	private LocalDate lastDate(int days) {
		LocalDate createDay = LocalDate.now();
		LocalDate lastDay = createDay.plusDays(days);
		return lastDay;
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
	
	// Метод для генерации короткой ссылки на основе хэша MD5
	private String shortLinkGenerator(String longlink) {
		int i = 0;
		int d = 6;
		try {
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
	
	private boolean checkShLink(String shLink) {
		boolean check = false;
		check = DataBase.checkShLink(DataBase.linkBaseCon(), shLink);
		return check;
	}
	
	public void saveLink() {
		DataBase.insertNewLink(DataBase.linkBaseCon(), this);
	}
	
}
