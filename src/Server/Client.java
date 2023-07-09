package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class Client {
	public String userName;
	public int port;
	public Socket socket;
	public BufferedReader receiver;
	public BufferedWriter sender;
	public String Description;
	public String avatar;
//	public static ArrayList<Client> list_client=new ArrayList<>();
	public static List<String> list_username=new ArrayList<>();
	public Client(String userName, int port, Socket socket, BufferedReader receiver, BufferedWriter sender) {
		super();
		this.userName = userName;
		this.port = port;
		this.socket = socket;
		this.receiver = receiver;
		this.sender = sender;
	}
	public Client() {
		
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public BufferedReader getReceiver() {
		return receiver;
	}
	public void setReceiver(BufferedReader receiver) {
		this.receiver = receiver;
	}
	public BufferedWriter getSender() {
		return sender;
	}
	public void setSender(BufferedWriter sender) {
		this.sender = sender;
	}
	public static Client findClient(List<Client> clientList, String userName) {
		for (Client client : clientList) {
			if (client.userName.equals(userName))
				return client;
		}
		return null;
	}
	
}
