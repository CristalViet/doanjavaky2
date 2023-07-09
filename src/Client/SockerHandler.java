package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Server.Account;
import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;

public class SockerHandler extends Thread {
	
	public static  String userName;
	public String password;
	public Socket Server;
	public static BufferedWriter sender;
	public BufferedReader receiver;
	public String avatarLink;
	public String description ;
	public static InputStream is;
	public static OutputStream os;
	Thread receiveAndProcessThread;
	static ArrayList<Account_Client_side>clientAccountList=new ArrayList<>();
	public static List<Room>  allRooms=new ArrayList<>();
	public List<String>listIDphong=new ArrayList<>();
	public SockerHandler(String userName, String password, int port) throws IOException {
		super();
		Server = new Socket("localhost", port);
		this.userName=userName;
		this.password=password;
		 is=Server.getInputStream();
		 os=Server.getOutputStream();
		this.sender = new BufferedWriter(new OutputStreamWriter(os));
		this.receiver = new BufferedReader(new InputStreamReader(is));
		
		
	}
	public SockerHandler(String userName, String password,String avatarLink, String description ,int port) throws IOException {
		super();
		Server = new Socket("localhost", port);
		this.userName=userName;
		this.password=password;
		 is=Server.getInputStream();
		 os=Server.getOutputStream();
		this.sender = new BufferedWriter(new OutputStreamWriter(os));
		this.receiver = new BufferedReader(new InputStreamReader(is));
		this.avatarLink=avatarLink;
		this.description=description;
		
	}
	
	public void login(String trangthai) throws IOException{
		String Result;	
		napAccount();
		Account_Client_side ac= findAccount(userName);
		if(trangthai.equals("dang nhap"))
			{
				sender.write("new login");
				sender.newLine();
				sender.write(ac.userName);
				sender.newLine();
				sender.write(password);
				sender.newLine();
				System.out.println("Link avatar:"+ac.Avatar);
				sender.write(ac.Avatar+"");
				sender.newLine();
				sender.write(ac.Description+"");
				sender.newLine();
				sender.flush();
				
				Result=receiver.readLine();
				System.out.println("gui ch");
			}
			else Result="login success";
			
			
		
					
			
				
				 if(Result.equals("login success")) {
					 receiveAndProcessThread=new Thread(()->{
						  ClientChat clientChat=new ClientChat(userName,sender);
						  while (true) {
							  try {
								String header = receiver.readLine();
								
								System.out.println("Header " + header);
								if (header == null)
									throw new IOException();
								switch (header) {
								case "new user online": {
									// Thêm acc của người mới đăng nhập
									String username=receiver.readLine();
									String avatarLink=receiver.readLine();
									String des=receiver.readLine();
									System.out.println("Add ne"+username);
									ClientChat.UserOnlinePanel_members.add(new Account_Client_side(username, avatarLink, des));
									ClientChat.UpdateOnlineUserList();
									
									clientChat.printOnlineUserList();
									clientChat.reloadUI_online();
									break;
								}
								case "new room": {
									String roomIDString=receiver.readLine();
									System.out.println(roomIDString);
									
									int roomID = Integer.parseInt(roomIDString);
									listIDphong.add(roomIDString);
									String whoCreate = receiver.readLine();
									System.out.println("ng tao"+whoCreate);
									String name = receiver.readLine();
									String type = receiver.readLine();
									String numberString=receiver.readLine();
								
									int roomUserCount = Integer.parseInt(numberString);
									List<String> users = new ArrayList<String>();
									for (int i = 0; i < roomUserCount; i++)
									{
										String thanhvien=receiver.readLine();
										System.out.println("Thanh vien:"+thanhvien);
										users.add(thanhvien);
									}
										

									Room newRoom = new Room(roomID, name, type, users);
									this.allRooms.add(newRoom);
									//set  panel
//									ClientChat.newRoomTab(newRoom);
									//
									Room.InRoom(allRooms);
									clientChat.newRoomTab(newRoom);
									if(type.equals("private")) {
										String nguoidungkhac=new String();
										for (String string : users) {
											if(!string.equals(userName)) {
												System.out.println(nguoidungkhac);
											 nguoidungkhac=string;
											}
											
										}
										ClientChat.chattingRoom= Room.findPrivateRoom(SockerHandler.allRooms, nguoidungkhac).getId();
										System.out.println("Id room hien tai"+ClientChat.chattingRoom);
									}
//									
									ClientChat.addNewMessage(newRoom.getId(), "notify", whoCreate,type.equals("group") ? "Đã tạo group" : "Đã mở chat");
//									Main.mainScreen.updateGroupJList();
									
									
									break;
								}
								case "load user before":{
									String username=receiver.readLine();
									String avatarLink=receiver.readLine();
									String des=receiver.readLine();
									
									ClientChat.UserOnlinePanel_members.add(new Account_Client_side(username, avatarLink, des));
									ClientChat.UpdateOnlineUserList();
									
									clientChat.printOnlineUserList();
									clientChat.reloadUI_online();
									
								}
								case "load UI":{
									clientChat.reloadUI_online();
									break;
								}
								case "exist login":{
									break;
								}
								case "kill": {
									
									clientChat.dispose();
									sender.write("user exit");
									sender.newLine();
									sender.flush();
									receiveAndProcessThread.stop();
									
									
									break;
								}
								case "user quit":{
									
									break;
								}
								case "text from user to room":{
									String user = receiver.readLine();
									int roomID = Integer.parseInt(receiver.readLine());
									String content = "";
									char c;
									do {
										c = (char) receiver.read();
										if (c != '\0')
											content += c;
									} while (c != '\0');
									ClientChat.addNewMessage(roomID, "text", user, content);
									break;
								}
								case "emoji from user to room":{
									String user = receiver.readLine();
									System.out.println("nguoi gui emoji"+user);
									int roomID = Integer.parseInt(receiver.readLine());
									String nameEmoji=receiver.readLine();
									ClientChat.addNewMessage(roomID, "emoji", user, nameEmoji);
									break;
								}
								
							}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								try {
									sender.write("user exit");
									sender.newLine();
									sender.flush();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							
							
							}
							 
						  }						  
					 	});
					 receiveAndProcessThread.start();
					  
			    	 
			    	   
			    	   
			        }
				 else if(Result.equals("exist login")) JOptionPane.showMessageDialog(null, "Tài khoản đã được đăng nhập ở thiết bị khác");
				 
				 
				 
			
			
	}
	public void signup() {
		try {
			sender.write("new signup");
			sender.newLine();
			sender.write(userName);
			sender.newLine();
			sender.write(password);
			sender.newLine();
			sender.write(avatarLink);
			sender.newLine();
			sender.write(description);
			sender.newLine();
			sender.flush();
			System.out.println("gui ch");
			
			String Result=receiver.readLine();
			if(Result.equals("signup success"))
			{
				login("dang ky");
				System.out.println("Dang nhap thanh cong");
			}
			else {
				new Signup();
				JOptionPane.showConfirmDialog(null, "Đăng ký thất bại");
				this.stop();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public void run(String method) {
		// TODO Auto-generated method stub
		
		if(method.equals("login"))
		{	
			new Thread(()->{
				try {
					login("dang nhap");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}).start();
		
		}
		else new Thread(()->{
			signup();
		}).start();
	}


	public void start(String method) {
		// TODO Auto-generated method stub
		run(method);
		
	}
	public static void SendTextToRoomChat(int idroom, String content) {
			
			try {
				sender.write("send text to room");
				sender.newLine();
				sender.write(idroom+"");
				sender.newLine();
				
				sender.write(content);
				sender.write('\0');
				sender.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	public static void sendFileToRoom(int idroom,String fileName,String filePath) {
		try {
			System.out.println("Send file " + fileName + " to room " + idroom);
			File file = new File(filePath);
			Room room = Room.findRoom(allRooms, idroom);
			sender.write("send file");
			sender.newLine();
			sender.write(idroom+"");
			sender.newLine();
			sender.write(fileName);
			sender.newLine();
			sender.write("" + file.length());
			sender.newLine();
			sender.flush();
			byte[] buffer = new byte[1024];
			InputStream in = new FileInputStream(file);
			OutputStream out = os;

			int count;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}

			in.close();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void  sendEmoji(int idroom,String tenEmoji) {
		try {
			sender.write("send emoji");
			sender.newLine();
			sender.write(idroom+"");
			sender.newLine();
			sender.write(tenEmoji);
			sender.newLine();
			sender.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void CreatePrivateRoom(String otherUserName) {
	
			try {
				sender.write("request create room");
				sender.newLine();
				sender.write(otherUserName);
				sender.newLine();
				sender.write("private");
				sender.newLine();
				sender.write("2");
				sender.newLine();
				sender.write(userName);
				sender.newLine();
				sender.write(otherUserName);
				sender.newLine();
				sender.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}                           
	public void Disconnect() {
		try {
			sender.write("user exit");
			sender.newLine();
			sender.write(userName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void napAccount() {
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
 		String url="jdbc:sqlserver://localhost:1433;databaseName=chat;integratedSecurity=false;trustServerCertificate=true";
 		String user="viet2";
 		String pass="123";
 		try {
 			Class.forName(driverName);
 			Connection con=DriverManager.getConnection(url, user, pass);
 			Statement stmt=con.createStatement();
 			String sql1="select* from account";
           ResultSet smt=stmt.executeQuery(sql1);
 		         //duyet tung dong trong result
           SockerHandler.clientAccountList.clear();
 		    while(smt.next()) {
 		    	String username=smt.getNString(1);
 		    	
 		    	
 		    	String avatarLink=smt.getNString(3);
 		    	String description=smt.getNString(4);
 		    	
 		    	Account_Client_side ac=new Account_Client_side(username, avatarLink, description);
 		    	SockerHandler.clientAccountList.add(ac);
 		    }
 		        
 		} catch (ClassNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		
 		
 			
 		 catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
	}
	public static Account_Client_side findAccount(String username){
		for (Account_Client_side account_Client_side : clientAccountList) {
			if(account_Client_side.userName.equals(username)) {
				return account_Client_side;
			}
		}
		return null;
	}
	public static String getUserName() {
		return userName;
	}
	
	
}
