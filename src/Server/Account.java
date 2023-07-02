package Server;

import java.util.ArrayList;

public class Account {
	private String username;
	private String password;
	private String avatar;
	private String Description;
	public static ArrayList<Account>listAccount=new ArrayList<>();
	public Account(String username, String password, String avatar, String description) {
		super();
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		Description = description;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
}
