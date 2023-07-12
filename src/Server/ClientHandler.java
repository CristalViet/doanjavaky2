package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import Client.ClientChat;
import Client.Room;
import Client.SockerHandler;

public class ClientHandler extends Thread {
	Client client;
	public static List<ClientHandler> clientHandlers=new ArrayList<>();
	public static int RoomCurrentNumber=0;
	static OutputStream  os;
	static InputStream  is;
//	List<RoomServerSide>RoomList=new ArrayList<>();
	public ClientHandler(Socket socket) throws IOException {
		super();
		this.client = new Client();
		client.socket=socket;
		 os=socket.getOutputStream();
		client.sender=new BufferedWriter(new OutputStreamWriter(os,StandardCharsets.UTF_8));
		 is=socket.getInputStream();
		client.receiver=new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8));
		client.port=socket.getPort();
		
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
			
			try {
				while (true) {
					
					String CommandLine=client.receiver.readLine();
				
				
						System.out.println("Received request from user");
						System.out.println(CommandLine);
						switch (CommandLine+"") {
						case "new signup":{
							String UserName=client.receiver.readLine();
							String password=client.receiver.readLine();
							String avatarLink=client.receiver.readLine();
							
							String des=client.receiver.readLine();
							System.out.println(UserName+" "+password+" "+avatarLink);
							String Checker=CheckAccount(UserName, password);
							
							if(Checker.equals("khong ton tai")&&!CheckClientHandler(UserName))
							{
								
								client.userName=UserName;
								client.avatar=avatarLink;
								client.Description=des;
								clientHandlers.add(this);
								ServerPanel.userlist2.add(UserName);
								
								//add vào bảng client dang hoat dong
								DefaultListModel<String>list=new DefaultListModel<>();
								for (String client : ServerPanel.userlist2) {
									list.addElement(client);
								}
								ServerPanel.list.setModel(list);;
								
						
								System.out.print("Count client: "+ServerPanel.CountClient());
								client.sender.write("signup success");
								
								client.sender.newLine();
								client.sender.flush();
								CreateAccount(UserName, password, avatarLink, des);
								System.out.println("Server: signup success");
							}
							else {
								client.sender.write("signup failed");
								
								client.sender.newLine();
								client.sender.flush();
							}
							break;
						}
					
						case "new login": {
							
							String UserName=client.receiver.readLine();
							String password=client.receiver.readLine();
							String avatar=client.receiver.readLine();
							
							String des=client.receiver.readLine();
							System.out.println("Ten la"+UserName);
							System.out.println("Link anh:"+avatar);
							String Checker=CheckAccount(UserName, password);
							System.out.println("chay toi day");
							System.out.println(Checker);
							if(Checker.equals("thanh cong")&&!CheckClientHandler(UserName)) {
								client.userName=UserName;
								this.client.avatar=avatar;
								client.Description=des;
								clientHandlers.add(this);
								//Gắn avatar , des cho client rồi khi đi truyền client đó cho các user kh
								
								
								
								
								//list hien thi ben server
								ServerPanel.userlist2.add(UserName);
//								UpdateListUserOnline();
								//add vào bảng client dang hoat dong
								DefaultListModel<String>list=new DefaultListModel<>();
								for (String client : ServerPanel.userlist2) {
									list.addElement(client);
								}
								ServerPanel.list.setModel(list);;
								
						
								System.out.print("Count client: "+ServerPanel.CountClient());
								
								client.sender.write("login success");
								client.sender.newLine();
								client.sender.flush();
								// Nap list user cho user moi
								for (ClientHandler ClientHandler :ClientHandler.clientHandlers) {
									
										client.sender.write("load user before");
										client.sender.newLine();
										client.sender.write(ClientHandler.client.userName);
										client.sender.newLine();
										client.sender.write(ClientHandler.client.avatar+"");
										client.sender.newLine();
										client.sender.write(ClientHandler.client.Description+"");
										client.sender.newLine();
										client.sender.flush();
										
									
								}
								client.sender.write("load UI");
								client.sender.newLine();
								client.sender.flush();
								//gui thong tin user moi tham gia cho cac client khac
								for (ClientHandler ClientHandler :ClientHandler.clientHandlers) {
									if(client.userName.equals(ClientHandler.client.userName))
											continue;
									System.out.println("chay lien tuc");
									ClientHandler.client.sender.write("new user online");
									ClientHandler.client.sender.newLine();
									ClientHandler.client.sender.write(UserName);
									ClientHandler.client.sender.newLine();
									ClientHandler.client.sender.write(avatar);
									ClientHandler.client.sender.newLine();
									ClientHandler.client.sender.write(des);
									ClientHandler.client.sender.newLine();
									ClientHandler.client.sender.flush();
									client.sender.write("load UI");
									client.sender.newLine();
									client.sender.flush();
								
								}
								
								
							}
							else if(Checker.equals("sai mat khau")) {
								System.out.println("Dang nhap thap bai");
								client.sender.write("login failed");
								client.sender.newLine();
								client.sender.flush();
							}
							else if(CheckClientHandler(UserName))
							{
								client.sender.write("exist login");
								client.sender.newLine();
								client.sender.flush();
							}
							else System.out.println("Khong ton tai");
							break;
						}
						case "user exit":{
							RemoveClientHandler(this);
							UpdateClientHandlerListToServer();
					
							ServerPanel.UpdateJlistUserOnline2();
							break;
						}
						case "send emoji":{
							String roomid=this.client.receiver.readLine();
							int roomID = Integer.parseInt(roomid);
							String NameEmoji=client.receiver.readLine();
							RoomServerSide room = RoomServerSide.findRoom(ServerPanel.RoomList, roomID);
							
							for (String user : room.getUser()) {
								System.out.println("Send emoji from " + this.client.userName + " to " + user);
								if(!(ClientHandler.findClientHandler(user)==null)) {
									Client currentClient = ClientHandler.findClientHandler(user).client;
									if (currentClient != null) {
										currentClient.sender.write("emoji from user to room");
										currentClient.sender.newLine();
										currentClient.sender.write(this.client.userName);
										currentClient.sender.newLine();
										currentClient.sender.write("" + roomID);
										currentClient.sender.newLine();
										currentClient.sender.write(NameEmoji);
										currentClient.sender.newLine();
										currentClient.sender.flush();
			
									
									}
								}
								
							}
							
							break;
						}
					
						case "request create room":{
							String RoomName=client.receiver.readLine();
							String RoomType=client.receiver.readLine();
							System.out.println(RoomType);
							String so=client.receiver.readLine();
							System.out.println(so);
							int NumberOfUsers=Integer.parseInt(so);
							List<String>listUser=new ArrayList<>();
							for(int i=0;i<NumberOfUsers;i++) {
								listUser.add(client.receiver.readLine());
								
							}
							
//							tang so luong room
							RoomCurrentNumber++;
							System.out.println("so luong phong"+RoomCurrentNumber);
							RoomServerSide room=new RoomServerSide(RoomCurrentNumber, RoomName, listUser);
							
								try {
						
								
										ServerPanel.RoomList.add(room);
										for (String user : listUser) {
											BufferedWriter currentClientSender = findClientHandler(user).client.getSender();
											currentClientSender.write("new room");
											currentClientSender.newLine();
											System.out.println("Check id"+room.getId());
											currentClientSender.write("" + room.getId());
											currentClientSender.newLine();
											currentClientSender.write(this.client.userName);//who create
											currentClientSender.newLine();
											if (RoomType.equals("private")) 
												// private chat thì tên room của mỗi người sẽ là tên của người kia
												{
												for (String ten_nguoi_dung : listUser) {
													if(!ten_nguoi_dung.equals(user)) {
														System.out.println("Ten nguoi dung hien tai:"+user);
														System.out.println("Ten nguoi dung dat phong:"+ten_nguoi_dung);
														currentClientSender.write(ten_nguoi_dung);
														currentClientSender.newLine();
														break;
													}
												}
													
												}
												//nếu khác private chat thì là group chat
												else {
													currentClientSender.write(RoomName);
													currentClientSender.newLine();

													
												}
												currentClientSender.write(RoomType);
												currentClientSender.newLine();
												currentClientSender.write("" + listUser.size());
												currentClientSender.newLine();
												
												for (String userr : listUser) {
													currentClientSender.write(userr);
													currentClientSender.newLine();
												}
												
												currentClientSender.flush();
											 } 
									
									
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							
							
								
								
							
						
								
							for (RoomServerSide roomServerSide : ServerPanel.RoomList) {
								System.out.println(roomServerSide);
							}
							break;
						}
						case "send text to room":{
							String roomid=this.client.receiver.readLine();
							
							int roomID = Integer.parseInt(roomid);
							String content = "";
							char c;
							do {
								c = (char) this.client.receiver.read();
								if (c != '\0')
									content += c;
							} while (c != '\0');
							System.out.println(content);
							RoomServerSide room = RoomServerSide.findRoom(ServerPanel.RoomList, roomID);
							for (String user : room.getUser()) {
								System.out.println("Send text from " + this.client.userName + " to " + user);
								Client currentClient = ClientHandler.findClientHandler(user).client;
								if (currentClient != null) {
									currentClient.sender.write("text from user to room");
									currentClient.sender.newLine();
									currentClient.sender.write(this.client.userName);
									currentClient.sender.newLine();
									currentClient.sender.write("" + roomID);
									currentClient.sender.newLine();
									currentClient.sender.write(content);
									currentClient.sender.write('\0');
									currentClient.sender.flush();
								}
							}
							break;
						}
						
						case "send file":{
							String roomid=this.client.receiver.readLine();
							int roomMessagesCount = Integer.parseInt(this.client.receiver.readLine());
							String fileName = this.client.receiver.readLine();
							
							int fileSize = Integer.parseInt(this.client.receiver.readLine());
							int roomID = Integer.parseInt(roomid);
							
							
							
							int dotIndex = fileName.lastIndexOf('.');
							String saveFileName = "files/" + fileName.substring(0, dotIndex)
									+ String.format("%02d%04d", roomID, roomMessagesCount) + fileName.substring(dotIndex);
							System.out.println("thuc hien lenh dong ch");
							File file = new File(saveFileName);
							byte[] buffer = new byte[1024];
							InputStream in = this.client.socket.getInputStream();
							OutputStream out = new FileOutputStream(file);

							int receivedSize = 0;
							int count;
							while ((count = in.read(buffer)) > 0) {
								out.write(buffer, 0, count);
								receivedSize += count;
								if (receivedSize >= fileSize)
									break;
							}
							
							out.close();
							RoomServerSide room = RoomServerSide.findRoom(ServerPanel.RoomList, roomID);
							for (String user : room.getUser()) {
								Client currentClient = ClientHandler.findClientHandler(user).client;
								if (currentClient != null) {
									currentClient.sender.write("file from user to room");
									currentClient.sender.newLine();
									currentClient.sender.write(this.client.userName);
									currentClient.sender.newLine();
									currentClient.sender.write("" + roomID);
									currentClient.sender.newLine();
									currentClient.sender.write(fileName);
									currentClient.sender.newLine();
									currentClient.sender.flush();
								}
							}
							break;
						}
						case "request download file":{
							try {
								int roomID = Integer.parseInt(this.client.receiver.readLine());
								int messageIndex = Integer.parseInt(this.client.receiver.readLine());
								String fileName = this.client.receiver.readLine();

								int dotIndex = fileName.lastIndexOf('.');
								fileName = "files/" + fileName.substring(0, dotIndex)
										+ String.format("%02d%04d", roomID, messageIndex) + fileName.substring(dotIndex);
								File file = new File(fileName);

								this.client.sender.write("response download file");
								this.client.sender.newLine();
								this.client.sender.write("" + file.length());
								this.client.sender.newLine();
								this.client.sender.flush();

								byte[] buffer = new byte[1024];
								InputStream in = new FileInputStream(file);
								OutputStream out =client.socket.getOutputStream() ;

								int count;
								while ((count = in.read(buffer)) > 0) {
									out.write(buffer, 0, count);
								}
								out.flush();
								in.close();
								
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							break;
						}
						case "get generate port":{
							ServerPanel.portMax++;
							this.client.sender.write("return generate port");
							this.client.sender.newLine();
							this.client.sender.write(ServerPanel.portMax+"");
							this.client.sender.newLine();
							this.client.sender.flush();
							break;
						}
						case "user deny call":{
							int idroom=Integer.parseInt(this.client.receiver.readLine());
							String Whosend=this.client.receiver.readLine();
							for (ClientHandler clientHandler : clientHandlers) {
								if(clientHandler.client.getUserName().equals(Whosend)) {
									clientHandler.client.sender.write("user deny call");
									clientHandler.client.sender.newLine();
									clientHandler.client.sender.flush();
									break;
								}
							}
							
							
						
							break;
						}
						case "voice chat":{
							
							int roomID = Integer.parseInt(this.client.receiver.readLine());
							int port=Integer.parseInt(this.client.receiver.readLine());
							RoomServerSide room = RoomServerSide.findRoom(ServerPanel.RoomList, roomID);
							for (String user : room.getUser()) {
								System.out.println("Make voice from " + this.client.userName + " to " + user);
								Client currentClient = ClientHandler.findClientHandler(user).client;
								if (currentClient != null) {
									currentClient.sender.write("voice from user to room");
									currentClient.sender.newLine();
									currentClient.sender.write(this.client.userName);
									currentClient.sender.newLine();
									currentClient.sender.write("" + roomID);
									currentClient.sender.newLine();
									currentClient.sender.write(port+"");
									currentClient.sender.newLine();
									
									currentClient.sender.flush();
								}
							}
							
							break;
						}
						
						case "end call":{
							int idroom=Integer.parseInt(this.client.receiver.readLine());
							RoomServerSide room=RoomServerSide.findRoom(ServerPanel.RoomList, idroom);
							for (String user : room.getUser()) {
								System.out.println("end voice from " + this.client.userName + " to " + user);
								Client currentClient = ClientHandler.findClientHandler(user).client;
								if (currentClient != null) {
									currentClient.sender.write("end call");
									currentClient.sender.newLine();
									
									
									
									
									currentClient.sender.flush();
								}
							}
							break;
						}
						case "accept voice" :{
							int idroom=Integer.parseInt(this.client.receiver.readLine());
							RoomServerSide room=RoomServerSide.findRoom(ServerPanel.RoomList, idroom);
							for (String user : room.getUser()) {
								System.out.println("end voice from " + this.client.userName + " to " + user);
								if(!user.equals(this.client.userName)) {
									Client currentClient = ClientHandler.findClientHandler(user).client;
									if (currentClient != null) {
										currentClient.sender.write("accept voice");
										currentClient.sender.newLine();
										currentClient.sender.flush();
										
										
										
										
									}	
								}
								
							}
						}
					
					}
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				if(this.client.getUserName()!=null)
	 			{ 
					System.out.println("Thuc hien lenh");
	 				for (ClientHandler clientHandler : clientHandlers) {
	 					if(!clientHandler.equals(this))
	 					{
	 						try {
	 							clientHandler.client.sender.write("user quit");
	 							clientHandler.client.sender.newLine();
	 							clientHandler.client.sender.write(this.client.getUserName());
	 							clientHandler.client.sender.newLine();
	 							clientHandler.client.sender.flush();
	 							
							} catch (IOException e1) {
								// TODO Auto-generated catch block
//								e1.printStackTrace();
							}
	 						
	 						
	 					}
	 					
	 				}
	 				RemoveClientHandler(this);
	 				ServerPanel.UpdateJlistUserOnline2();
	 			}
			}
			
		
	}
//	public static void UpdateListUserOnline() {
//		Client.list_username.clear();
//		ServerPanel.userlist2.clear();
//		for (Client client :Client.list_client) {
//				Client.list_username.add(client.getUserName());
//				ServerPanel.userlist2.add(client.getUserName());
//				
//		}
//	}
	public static void UpdateClientHandlerListToServer() {
		Client.list_username.clear();
		ServerPanel.userlist2.clear();
		for (ClientHandler clientHandler : clientHandlers) {
			Client.list_username.add(clientHandler.client.getUserName());
			ServerPanel.userlist2.add(clientHandler.client.getUserName());
			
		}
		
	}
	public boolean CheckClientHandler(String username) {
		for (ClientHandler client : clientHandlers) {
			if(username.equals(client.getClient().userName)) {
				return true;
			}
		}
		return false;
	}
	public static ClientHandler findClientHandler(String name) {
		for (ClientHandler client : clientHandlers) {
			if(name.equals(client.getClient().userName)) {
				return client;
			}
		}
		return null;
	}
	public void RemoveClientHandler(ClientHandler clienthandler) {
		
		for (ClientHandler client : ClientHandler.clientHandlers) {
			   if(client.equals(clienthandler)) {
				   ClientHandler.clientHandlers.remove(clienthandler);
				   System.out.println("Da xoa");
				   return ;
			   }
		}
		System.out.println("Khong xoa");
	}
	
	public String CheckAccount(String username, String password) {
		for (Account account : Account.listAccount) {
			if(username.equals(account.getUsername())) {
				if(password.equals(account.getPassword())) {
				
					return "thanh cong";
				}
				else return "sai mat khau";
			}
			
		}
		return "khong ton tai";
	}
	
	public static void CreateAccount(String username, String password, String avatarlink, String Description) {
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
 		String url="jdbc:sqlserver://localhost:1433;databaseName=chat;integratedSecurity=false;trustServerCertificate=true";
 		String user="viet2";
 		String pass="123";
 		try {
 			Class.forName(driverName);
 			Connection con=DriverManager.getConnection(url, user, pass);
 			
 			String sql1="INSERT INTO account (username, password, avatarLink, description)"
 					+ " VALUES (?, ?, ?, ?);";
 			PreparedStatement preparedStatement=con.prepareStatement(sql1);
 			preparedStatement.setNString(1, username);
 			preparedStatement.setNString(2, password);
 			preparedStatement.setNString(3, avatarlink);
 			preparedStatement.setNString(4, Description);
           
           int result=preparedStatement.executeUpdate();
           Account ac=new Account(username, password, avatarlink, Description);
	    	Account.listAccount.add(ac);
        
 		         //duyet tung dong trong result
           
 		    
 		        
 		} catch (ClassNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			
 			
 		}
 		
 		
 			
 		 catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
	}
}
