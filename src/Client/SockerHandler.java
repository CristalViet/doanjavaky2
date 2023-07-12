package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	public static Socket Server;
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
	public static int portVoice=1000;
	public static CallFrame2 callFrame2;
	public static   CallFrame callframe;
	public static CallWait callwait;
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
				sender.write(ac.userName+"");
				sender.newLine();
				sender.write(password+"");
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
						  callframe=new CallFrame();
						   callwait=new CallWait();
						  callFrame2=new CallFrame2();
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
									String username=receiver.readLine();
									clientChat.deleteUserOnlinePanel_members(username);
									ClientChat.UpdateOnlineUserList();
									clientChat.reloadUI_online();
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
								case "file from user to room": {
									String user = receiver.readLine();
									int roomID = Integer.parseInt(receiver.readLine());
									String fileName = receiver.readLine();
									System.out.println("Recevie file " + fileName + " from " + user + " to room " + roomID);
									ClientChat.addNewMessage(roomID, "file", user, fileName);
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
								case "response download file":{
									int fileSize = Integer.parseInt(receiver.readLine());
									File file = new File(downloadToPath);
									byte[] buffer = new byte[1024];
									InputStream in = Server.getInputStream();
									OutputStream out = new FileOutputStream(file);

									int count;
									int receivedFileSize = 0;
									while ((count = in.read(buffer)) > 0) {
										out.write(buffer, 0, count);
										receivedFileSize += count;
										if (receivedFileSize >= fileSize)
											break;
									}

									out.close();
									break;
								}
								case"return generate port"  :{
									portVoice=Integer.parseInt(receiver.readLine());
									
									break;
								}
								case"user deny call"  :{
									callframe.lb_des.setText("Người dùng từ chối cuộc gọi");
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									callframe.dispose();
									
									break;
								}
								case "accept voice":{
									callframe.lb_des.setText("Đã kết nối");
									break;
								}
								case"voice from user to room"  :{
									String WhoSend=receiver.readLine();
									int idroom=Integer.parseInt(receiver.readLine());
									
									portVoice=Integer.parseInt(receiver.readLine());
									
									System.out.println("request make voice "+" from " + WhoSend + " to room " + idroom);
									ClientChat.addNewMessage(idroom, "voice", WhoSend, portVoice+"");
									
									Room room=Room.findRoom(allRooms, idroom);
									String userName_receive = null;
									String trangthaigoi="Đang gọi";
									if(WhoSend.equals(userName)) {
										for (String user : room.getUser()) {
											if(!user.equals(WhoSend))
											{
												userName_receive=user;
												break;
											}
										}
									 	 callframe= new CallFrame(idroom, portVoice, userName_receive,trangthaigoi,sender);
										
									}
									else {
										
										callwait=new CallWait(idroom,portVoice,WhoSend,sender);
									}
									break;
								}
								
								case"end call"  :{
									callframe.dispose();
									callFrame2.dispose();
								
									break;
								}
								
							}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								System.out.println(e);
//								try {
//									sender.write("user exit");
//									sender.newLine();
//									sender.flush();
//								} catch (IOException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
							
							
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
			sender.write(avatarLink+"");
			sender.newLine();
			sender.write(description+"");
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
	public static void GetGeneratePort() {
		try {
			sender.write("get generate port");
			sender.newLine();
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void VoiceChat(int idroom) {
		try {
			sender.write("voice chat");
			sender.newLine();
			sender.write(idroom+"");
			sender.newLine();
			sender.write(portVoice+"");
			sender.newLine();
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
			sender.write("" + room.getListMessage().size());
			sender.newLine();
			sender.write(fileName);
			sender.newLine();
			sender.write("" + file.length());
			sender.newLine();
			sender.flush();
			byte[] buffer = new byte[1024];
			InputStream in = new FileInputStream(file);
			OutputStream out = Server.getOutputStream();

			int count;
			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}

			in.close();
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Loi exception ne");
		}
	}
	public static String downloadToPath;

	public static void  downloadFile(int idroom,int messageIndex, String fileName, String filepath) {
		downloadToPath = filepath;
		try {
			sender.write("request download file");
			sender.newLine();
			sender.write("" + idroom);
			sender.newLine();
			sender.write("" + messageIndex);
			sender.newLine();
			sender.write(fileName);
			sender.newLine();
			sender.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
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
