package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Server.Account;
import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;
import net.miginfocom.swing.MigLayout;
import testJlist.myobject;
import testJlist.mypanel;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Component;

public class ClientChat extends JFrame implements  ActionListener {

	private JPanel contentPane;
	private   JList<Account_Client_side> list2=new JList<Account_Client_side>();
	
	public static List<Account_Client_side>UserOnlinePanel_members=new ArrayList<>();
	JButton Send_btn;
	public static JTextPane textPane_display;
	public  JPanel panel_contain_online_user=new JPanel();
	JTextArea textArea_message;
	 static String userName;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ClientChat(String userName,BufferedWriter sender) {
		this.userName=userName;
		
		setTitle(userName);
		setBounds(100, 100, 900, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Online List");
		lblNewLabel.setBounds(20, 40, 95, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Chat");
		lblNewLabel_1.setBounds(437, 33, 131, 26);
		contentPane.add(lblNewLabel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(549, 228, 273, 255);
		contentPane.add(scrollPane_1);
		
		textPane_display = new JTextPane();
		scrollPane_1.setViewportView(textPane_display);
		
		Send_btn = new JButton("Send");
		Send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendText(sender);
			}
		});
		Send_btn.setBounds(486, 439, 69, 23);
		contentPane.add(Send_btn);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Room Chat");
		lblNewLabel_2.setBounds(20, 356, 95, 14);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(146, 363, 263, 52);
		contentPane.add(scrollPane_3);
		
		textArea_message = new JTextArea();
		scrollPane_3.setViewportView(textArea_message);
		
//		for(int i=0;i<10;i++) {
//			
//		}
		String ten="Việt";
		JPanel panel_contain_chat_view = new JPanel();
		panel_contain_chat_view.setBounds(378, 67, 371, 253);
		contentPane.add(panel_contain_chat_view);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(20, 373, 95, 167);
		contentPane.add(scrollPane_4);
		
		JList list_room = new JList();
		scrollPane_4.setViewportView(list_room);
		
		panel_contain_online_user = new JPanel(new BorderLayout());
		panel_contain_online_user.setBounds(8, 69, 352, 251);
		contentPane.add(panel_contain_online_user);
		
		panel_contain_online_user.add(createRootPane(),BorderLayout.CENTER);
		
		
		
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
	public JPanel createMainPain() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		
		panel.add(new JScrollPane(list2=testlist()));
		return panel;

	
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
	public static void UpdateOnlineUserList() {
		DefaultListModel<String>listDML=new DefaultListModel<>();
		for (Account_Client_side account_Client_side : UserOnlinePanel_members) {
			if(!account_Client_side.userName.equals(userName))
			{
				
				System.out.println("Ten add la : "+account_Client_side.userName);
			}
		}
			
		
		
		

	
//		if(listUsertText!=null)
//		{	 DefaultListModel<String>dml=new DefaultListModel<>();
//			dml=(DefaultListModel<String>) listUsertText;
//			user_online_list.setModel(dml);
//		}
		
	}
	public JPanel createMainPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		
		panel.add(new JScrollPane(list2=testlist()));
		return panel;

	
	}
	
	public  JList<Account_Client_side> testlist() {
		DefaultListModel<Account_Client_side>defaultListMode=new DefaultListModel<Account_Client_side>();
		if(UserOnlinePanel_members==null) {
			
		}
		else
			for (Account_Client_side account_Client_side : UserOnlinePanel_members) {
//				ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\Copy-of-image3-social-wellness.png");
//				Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
//				Icon icon=new ImageIcon(ChangeImg);
				defaultListMode.addElement(account_Client_side);
			}
			
			
		
	

		JList<Account_Client_side> list3=new JList<Account_Client_side>(defaultListMode); 
//		list3.setModel(defaultListMode);
		list3.setCellRenderer(new OnlinePane());
		return list3;
	
	}
	public static void printOnlineUserList() {
		System.out.println("Rong");
		for (Account_Client_side account_Client_side : UserOnlinePanel_members) {
			System.out.println(account_Client_side);
			System.out.println("Da chay list");
		}
	}
	public  void reloadUI_online() {
	
		panel_contain_online_user.add(createMainPain(),BorderLayout.CENTER);
		this.repaint();
	}
}
