package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;

public class SockerHandler extends Thread {
	
	public String userName;
	public String password;
	public Socket Server;
	public BufferedWriter sender;
	public BufferedReader receiver;
	public String avatarLink;
	public String description;
	Thread receiveAndProcessThread;
	
	public SockerHandler(String userName, String password, int port) throws IOException {
		super();
		Server = new Socket("localhost", port);
		this.userName=userName;
		this.password=password;
		InputStream is=Server.getInputStream();
		OutputStream os=Server.getOutputStream();
		this.sender = new BufferedWriter(new OutputStreamWriter(os));
		this.receiver = new BufferedReader(new InputStreamReader(is));
		
		
	}
	public SockerHandler(String userName, String password,String avatarLink, String description ,int port) throws IOException {
		super();
		Server = new Socket("localhost", port);
		this.userName=userName;
		this.password=password;
		InputStream is=Server.getInputStream();
		OutputStream os=Server.getOutputStream();
		this.sender = new BufferedWriter(new OutputStreamWriter(os));
		this.receiver = new BufferedReader(new InputStreamReader(is));
		this.avatarLink=avatarLink;
		this.description=description;
		
	}
	
	public void login(String trangthai) throws IOException{
		String Result;	
		if(trangthai.equals("dang nhap"))
			{
				sender.write("new login");
				sender.newLine();
				sender.write(userName);
				sender.newLine();
				sender.write(password);
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
									
									clientChat.UpdateOnlineUserList();
									
									
									break;
								}
								case "exist login":{
									
								}
								case "kill": {
									
									clientChat.dispose();
									sender.write("user exit");
									sender.newLine();
									sender.flush();
									receiveAndProcessThread.stop();
									
									
									break;
								}
								case "textToUser":{
									String msg=receiver.readLine();
									System.out.println("Nhan duoc "+msg);
									clientChat.textPane_display.setText(ClientChat.textPane_display.getText()+msg);
									break;
								}
							}
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						  }						  
					 	});
					 receiveAndProcessThread.start();
					 if (receiveAndProcessThread.isAlive()) {
						 	sender.write("user exit");
							sender.newLine();
							sender.write(userName);
							sender.newLine();
							sender.flush();
							System.out.println("Đã thoát");
					}
			    	 
			    	   
			    	   
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
	public void SendTextToRoomChat(int idroom, String content) {
			
			try {
				sender.write("request send text to room");
				sender.newLine();
				sender.write(idroom);
				sender.newLine();
				sender.write(content);
				sender.newLine();
				sender.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void CreatePrivateRoom(String otherUserName) {
	
			try {
				sender.write("request create room");
				sender.newLine();
				sender.write(otherUserName);
				sender.newLine();
				sender.write("private");
				sender.newLine();
				sender.write(2);
				sender.write(userName);
				sender.newLine();
				sender.write(otherUserName);
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
	
	
	
}
