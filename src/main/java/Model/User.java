package Model;

import java.util.UUID;
import java.security.*;
import java.util.HexFormat;
import java.util.Base64;


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
	public void setPass(String pass) {
		this.hashpass = hashPassGen(pass);
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
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
	private String uuidGeneration() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
