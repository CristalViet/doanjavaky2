package Server;

import java.util.List;

public class RoomServerSide {
	private int id;
	private String name;
	private List<String> user;
	
	public RoomServerSide(int id, String name, List<String> listUser) {
		super();
		this.id = id;
		this.name = name;
		this.user = listUser;
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
	public static RoomServerSide findRoom(List<RoomServerSide> roomList, int id) {
		for (RoomServerSide room : roomList)
			if (room.id == id)
				return room;
		return null;
	}
	public static Boolean CheckRoombyName(List<RoomServerSide> roomList, String name) {
		for (RoomServerSide room : roomList)
			if (room.name.equals(name))
				return true;
		return false;
	}

	@Override
	public String toString() {
		return "RoomServerSide [id=" + id + ", name=" + name + ", user=" + user + "]";
	}
	
	
	
}
