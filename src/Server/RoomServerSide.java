package Server;

import java.util.List;

public class RoomServerSide {
	private int id;
	private String name;
	private List<Account> user;
	
	public RoomServerSide(int id, String name, List<Account> user) {
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
	public List<Account> getUser() {
		return user;
	}
	public void setUser(List<Account> user) {
		this.user = user;
	}
	public static RoomServerSide findRoom(List<RoomServerSide> roomList, int id) {
		for (RoomServerSide room : roomList)
			if (room.id == id)
				return room;
		return null;
	}
	
	
}
