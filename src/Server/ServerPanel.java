package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.border.SoftBevelBorder;

import Client.ClientPanel;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.EtchedBorder;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.ListSelectionModel;

public class ServerPanel extends JFrame {

	private JPanel contentPane;
	private JTextField tx_port;
	static ServerSocket server;
	boolean isServerOpen=false;
	public static  List<String>userlist2=new ArrayList<>();
	public static JList<String>list =new JList<>();
	public static List<RoomServerSide>RoomList=new ArrayList<>();
	JLabel lb_ipaddress;
	JLabel lb_status ;
	JLabel lb_port;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerPanel frame = new ServerPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tx_port = new JTextField();
		
		tx_port.setBounds(37, 133, 173, 19);
		contentPane.add(tx_port);
		tx_port.setColumns(10);
		
		JButton start_btn = new JButton("Start");
		start_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isServerOpen==false)
				{	
					
					
					int port=Integer.parseInt( tx_port.getText());
					lb_status.setText("On");
					isServerOpen=true;
					startServer(port);
					String ipAdd="LocalHost";
					lb_ipaddress.setText(ipAdd);
					lb_port.setText(""+port);
					
				}
				else System.out.println("Server đã chạy");
			}
		});
		start_btn.setBounds(235, 132, 85, 21);
		contentPane.add(start_btn);
		
		JButton close_btn = new JButton("Đóng server");
		close_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeServer();
				lb_status.setText("OFF");
				isServerOpen=false;
				lb_status.setText("Off");
				lb_port.setText("None"
						+ "");
				
			}
		});
		close_btn.setBounds(235, 175, 101, 21);
		contentPane.add(close_btn);
		
		JLabel lblNewLabel = new JLabel("List user online");
		lblNewLabel.setBounds(430, 30, 204, 13);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(430, 67, 261, 306);
		contentPane.add(scrollPane);
		
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				String ten=list.getSelectedValue();
				System.out.println("Đang chọn :"+ten);
				for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
					if(clientHandler.client.userName.equals(ten)) {
						try {
							clientHandler.client.sender.write("kill");
							clientHandler.client.sender.newLine();
							clientHandler.client.sender.flush();
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}); 
			
	
		scrollPane.setViewportView(list);
		
		JLabel lblNewLabel_1 = new JLabel("Port");
		lblNewLabel_1.setBounds(37, 110, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(51, 257, 45, 13);
		contentPane.add(lblNewLabel_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Server Status", TitledBorder.CENTER, TitledBorder.TOP, null, Color.GREEN));
		panel.setBounds(28, 206, 252, 190);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Status :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(22, 34, 61, 13);
		panel.add(lblNewLabel_3);
		
		lb_status = new JLabel("Off");
		lb_status.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_status.setForeground(Color.WHITE);
		lb_status.setBounds(93, 34, 61, 15);
		panel.add(lb_status);
		
		JLabel lblNewLabel_5 = new JLabel("IP address :");
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(22, 71, 86, 13);
		panel.add(lblNewLabel_5);
		
		lb_ipaddress = new JLabel("None");
		lb_ipaddress.setForeground(Color.WHITE);
		lb_ipaddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_ipaddress.setBounds(118, 73, 124, 13);
		panel.add(lb_ipaddress);
		
		JLabel lblNewLabel_6 = new JLabel("Number of users :");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setBounds(22, 110, 132, 20);
		panel.add(lblNewLabel_6);
		
		JLabel lb_numberOfUsers = new JLabel("0");
		lb_numberOfUsers.setForeground(Color.WHITE);
		lb_numberOfUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_numberOfUsers.setBounds(164, 116, 45, 13);
		panel.add(lb_numberOfUsers);
		
		JLabel lblNewLabel_4 = new JLabel("Port :");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(22, 140, 45, 13);
		panel.add(lblNewLabel_4);
		
		lb_port = new JLabel("None");
		lb_port.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lb_port.setForeground(Color.WHITE);
		lb_port.setBackground(Color.BLACK);
		lb_port.setBounds(80, 140, 45, 13);
		panel.add(lb_port);
	}
	public void startServer(int port) {
		try {
			//nap account
			
			server=new ServerSocket(port);
			new Thread(()->{
				napAccount();
				while(isServerOpen&&!Thread.currentThread().isInterrupted()) {
					
				  	
					  
						
						try {
							System.out.println("Waiting for user");
							Socket s;
							s = server.accept();
							
							
							 ClientHandler clientHandler=new ClientHandler(s);
							 clientHandler.start();
							 
							 InAccountList();
							 
							
							 
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						 
					
					 
				}
			}).start();
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeServer() {
		if(isServerOpen) {
			try {
				server.close();
				System.out.println("Server da dong");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static int CountClient() {
		int count=0;
		for (String string : userlist2) {
			   count++;
		}
		return count;
	}
	public static void UpdateJlistUserOnline() {
		
		DefaultListModel<String>dml=new DefaultListModel<>();
		
		list.removeAll();
		
		
		for (String string : userlist2) {
			dml.addElement(string);
			System.out.println(string);
		}
		list.setModel(dml);
	
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
           Account.listAccount.clear();
 		    while(smt.next()) {
 		    	String username=smt.getNString(1);
 		    	String password=smt.getNString(2);
 		    	
 		    	String avatarLink=smt.getNString(3);
 		    	String description=smt.getNString(4);
 		    	
 		    	Account ac=new Account(username, password, avatarLink, description);
 		    	Account.listAccount.add(ac);
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
	public void InAccountList() {
		for (Account ac : Account.listAccount) {
			System.out.println(ac.getUsername());
		}
	}
}

