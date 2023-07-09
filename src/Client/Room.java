package Client;

import java.util.ArrayList;
import java.util.List;

public class Room  {
	private int id;
	private String name;
	public String type;
	private List<String> user;
	private List<DataMessage> listMessage;
	
	
	
	public Room(int id, String name, String type, List<String> user) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.user = user;
		this.listMessage = new ArrayList<>();
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public static Room findRoom(List<Room> roomList, int id) {
		for (Room room : roomList)
			if (room.id == id)
				return room;
		return null;
	}
	public static void InRoom(List<Room> roomList) {
		for (Room room : roomList)
		System.out.println(room);
	}
	public static Room findPrivateRoom(List<Room> roomList, String otherUser) {
		if(roomList==null)
			return null;
		for (Room room : roomList) {
			if (room.getType().equals("private") && room.name.equals(otherUser))
				return room;
		}
		return null;
	}
	public static Room findGroup(List<Room> roomList, String groupName) {
		for (Room room : roomList) {
			if (room.type.equals("group") && room.name.equals(groupName))
				return room;
		}
		return null;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", name=" + name + ", type=" + type + ", user=" + user + ", listMessage="
				+ listMessage + "]";
	}
	
	
}
