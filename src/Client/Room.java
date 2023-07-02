package Client;

import java.util.List;

import Server.DataMessage;

public class Room  {
	private int id;
	private String name;
	private List<String> user;
	private List<DataMessage> listMessage;
	public Room(int id, String name, List<String> user ) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUser() {
		return user;
	}
	public void setUser(List<String> user) {
		this.user = user;
	}
	public List<DataMessage> getListMessage() {
		return listMessage;
	}
	public void setListMessage(List<DataMessage> listMessage) {
		this.listMessage = listMessage;
	}
	
	
	
}
