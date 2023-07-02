package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Server.Account;
import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;
import net.miginfocom.swing.MigLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;

public class ClientChat extends JFrame implements  ActionListener {

	private JPanel contentPane;
	 
	 List<Account>UserOnlinePanel_members;
	JButton Send_btn;
	public static JTextPane textPane_display;
	JTextArea textArea_message;
	 String userName;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ClientChat(String userName,BufferedWriter sender) {
		this.userName=userName;
		System.out.println("So luong account:");	
		setTitle(userName);
		setBounds(100, 100, 900, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Online List");
		lblNewLabel.setBounds(10, 10, 95, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Chat");
		lblNewLabel_1.setBounds(146, 33, 131, 26);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(146, 64, 273, 255);
		contentPane.add(scrollPane_1);
		
		textPane_display = new JTextPane();
		scrollPane_1.setViewportView(textPane_display);
		
		Send_btn = new JButton("Send");
		Send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendText(sender);
			}
		});
		Send_btn.setBounds(440, 332, 69, 23);
		contentPane.add(Send_btn);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(594, 33, 69, 337);
		contentPane.add(scrollPane_2);
		
		JPanel panel = new JPanel();
		scrollPane_2.setViewportView(panel);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Room Chat");
		lblNewLabel_2.setBounds(594, 9, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(156, 329, 263, 52);
		contentPane.add(scrollPane_3);
		
		textArea_message = new JTextArea();
		scrollPane_3.setViewportView(textArea_message);
		
		
		
		
		
		JScrollPane scrollPane_online = new JScrollPane();
		scrollPane_online.setLocation(5, 33);
		scrollPane_online.setSize(100, 382);
		contentPane.add(scrollPane_online);
		
		
		JPanel Panel_online_contain=new JPanel(new MigLayout());
	
		scrollPane_online.setViewportView(Panel_online_contain);
		OnlinePane onlinePane=new OnlinePane();
		OnlinePane onlinePane2=new OnlinePane();
		Panel_online_contain.setLayout(new GridLayout(0, 1, 10, 0));
		Panel_online_contain.add(onlinePane);
		Panel_online_contain.add(onlinePane2);
		
		

		
	
		
		
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//				
//					ClientChat frame = new ClientChat();
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String nguonsukien=e.getActionCommand();
		switch (nguonsukien) {
		case "Send":
			
			break;
		case "client exit":
			
			break;
		default:
			break;
		}
		
	}
	
	public void SendText(BufferedWriter sender) {
		
		try {
			String message=textArea_message.getText();
			textPane_display.setText(textPane_display.getText()+""
					+ "Bạn: "+message+"\n");
			sender.write("request sendtext");
			sender.newLine();
			sender.write(message);
			sender.newLine();
			sender.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void UserExit(BufferedWriter sender) {
		
		try {
			
			sender.write("user exit");
			sender.newLine();
			sender.write(userName);
			sender.newLine();
			sender.flush();
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void UpdateOnlineUserList() {
		DefaultListModel<String>listDML=new DefaultListModel<>();
		for (ClientHandler clientHandler : ClientHandler.clientHandlers) {
			if(!clientHandler.getClient().userName.equals(userName))
			{
				String tenCLient =clientHandler.getClient().userName;
				listDML.addElement(tenCLient);
				System.out.println("Ten add la : "+tenCLient);
			}
		}
		
		

	
//		if(listUsertText!=null)
//		{	 DefaultListModel<String>dml=new DefaultListModel<>();
//			dml=(DefaultListModel<String>) listUsertText;
//			user_online_list.setModel(dml);
//		}
		
	}
}
