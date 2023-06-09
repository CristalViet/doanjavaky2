package Client;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Server.Account;
import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;
import net.miginfocom.swing.MigLayout;
import testJlist.myobject;
import testJlist.mypanel;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.ScrollPaneConstants;

public class ClientChat extends JFrame implements  ActionListener {

	private JPanel contentPane;
	public static   JList<Account_Client_side> list2=new JList<Account_Client_side>();
	
	public static List<Account_Client_side>UserOnlinePanel_members=new ArrayList<>();
	JButton Send_btn;
	private   JPanel panel_contain_online_user=new JPanel();
	public static JPanel Panel_ChatView;
	static JTabbedPane roomTabbedPane;
	static List<RoomMessagesPanel> roomMessagesPanels;
	public JButton sad_btn;
	JButton smile_btn;
	JButton nervous_btn;
	JButton haha_btn;
	JButton file_btn;
	JButton voice_btn;
	ChatPrivateFrame chatPrivateFrame;
	JTextArea textArea_message;
	 static String userName;
	 public static int chattingRoom = -1;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ClientChat(String userName,BufferedWriter sender) {
		this.userName=userName;
		SockerHandler.GetGeneratePort();
		
		setTitle(userName);
		setBounds(100, 100, 853, 589);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Online List");
		lblNewLabel.setBounds(20, 40, 95, 13);
		contentPane.add(lblNewLabel);
		
		Send_btn = new JButton("");
		Send_btn.setEnabled(false);
		
	
		ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\send_icon.png");
		Image ChangeImg=image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	
		Icon icon=new ImageIcon(ChangeImg);
		
		Send_btn.setIcon(icon);
		Send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String content = textArea_message.getText();
				
				if (content.isEmpty())
				{
					
				}
				else if (chattingRoom != -1)
					SockerHandler.SendTextToRoomChat(chattingRoom, content);
				textArea_message.setText("");
			
			}
		});
		Send_btn.setBounds(765, 446, 50, 50);
		contentPane.add(Send_btn);
		
//		for(int i=0;i<10;i++) {
//			
//		}
		String ten="Việt";
		
		panel_contain_online_user = new JPanel(new BorderLayout());
		panel_contain_online_user.setBounds(8, 69, 307, 456);
		contentPane.add(panel_contain_online_user);
		
		JScrollPane scrollPane_chatview = new JScrollPane();
		scrollPane_chatview.setBounds(380, 69, 357, 261);
		contentPane.add(scrollPane_chatview);
		
		Panel_ChatView = new JPanel();
		Panel_ChatView.setBackground(Color.WHITE);
		scrollPane_chatview.setViewportView(Panel_ChatView);
		Panel_ChatView.setLayout(new GridLayout(1, 0));
		roomMessagesPanels = new ArrayList<RoomMessagesPanel>();
		roomTabbedPane = new JTabbedPane();
		roomTabbedPane.addChangeListener(new ChangeListener() {
		
			@Override
			public void stateChanged(ChangeEvent e) {
				JScrollPane selectedTab = (JScrollPane) roomTabbedPane.getSelectedComponent();
				if (selectedTab != null) {
					RoomMessagesPanel selectedMessagePanel = (RoomMessagesPanel) selectedTab.getViewport().getView();
					chattingRoom = selectedMessagePanel.room.getId();
//					updateRoomUsersJList();
				}
			}

			
		});
		Panel_ChatView.add(roomTabbedPane);
		
		 sad_btn = new JButton("");
		 sad_btn.setEnabled(false);
	
		sad_btn.setIcon(new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_sad.png"));
		sad_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	          SockerHandler.sendEmoji(chattingRoom,"sad");
	     
			}
		});
		sad_btn.setBounds(380, 340, 50, 50);
		contentPane.add(sad_btn);
		
		 smile_btn = new JButton("");
		 smile_btn.setEnabled(false);
		smile_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 SockerHandler.sendEmoji(chattingRoom,"smile");
			}
		});
		smile_btn.setIcon(new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_smile.png"));
		smile_btn.setBounds(441, 340, 50, 50);
		contentPane.add(smile_btn);
		
		 nervous_btn = new JButton("");
		 nervous_btn.setEnabled(false);
		nervous_btn.setIcon(new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_nervous.png"));
		nervous_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 SockerHandler.sendEmoji(chattingRoom,"nervous");
			}
		});
		nervous_btn.setBounds(501, 340, 50, 50);
		contentPane.add(nervous_btn);
		
		 haha_btn = new JButton("");
		 haha_btn.setEnabled(false);
		haha_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 SockerHandler.sendEmoji(chattingRoom,"haha");
			}
		});
		haha_btn.setIcon(new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_haha.png"));
		haha_btn.setBounds(561, 340, 50, 50);
		contentPane.add(haha_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(380, 446, 357, 56);
		contentPane.add(scrollPane);
		
		textArea_message = new JTextArea();
		scrollPane.setViewportView(textArea_message);
		
		 file_btn = new JButton("File");
		 file_btn.setEnabled(false);
		file_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chattingRoom != -1)
				{
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Chọn file để gửi");
					int result = jfc.showDialog(null, "Chọn file");
					jfc.setVisible(true);

					if (result == JFileChooser.APPROVE_OPTION) {
						String fileName = jfc.getSelectedFile().getName();
						String filePath = jfc.getSelectedFile().getAbsolutePath();

						SockerHandler.sendFileToRoom(chattingRoom, fileName, filePath);
					}
				}
				
				
			}
		});
		file_btn.setBounds(621, 340, 58, 50);
		contentPane.add(file_btn);
		
		 voice_btn = new JButton("VoiceCall");
		voice_btn.setEnabled(false);
	
		voice_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					String ReceiverPeople="";
					Room room=Room.findRoom(SockerHandler.allRooms, chattingRoom);
					for (String user : room.getUser()) {
						if(!user.equals(SockerHandler.getUserName())) {
							ReceiverPeople=user;
						}
					}
				
				
				SockerHandler.VoiceChat(chattingRoom);
			}
		});
		voice_btn.setBounds(747, 176, 79, 56);
		contentPane.add(voice_btn);
		
		JButton webcam_btn = new JButton("VideoCAll");
		webcam_btn.setBounds(747, 242, 75, 56);
		contentPane.add(webcam_btn);
		
		
		
//		panel_contain_online_user.add(createRootPane(),BorderLayout.CENTER);
//	
//		panel_contain_online_user.repaint();
//		panel_contain_online_user.revalidate();
		
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
//	public JPanel createMainPain() {
//		JPanel panel = new JPanel(new BorderLayout());
//		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
//	
//		
//		panel.add(new JScrollPane(list2=testlist()));
//		return panel;
//
//	
//	}
	public void SendText(BufferedWriter sender) {
		
		try {
			String message=textArea_message.getText();

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
	
	public void newRoomTab(Room room) {
		sad_btn.setEnabled(true);
        haha_btn.setEnabled(true);
        Send_btn.setEnabled(true);
        nervous_btn.setEnabled(true);
        smile_btn.setEnabled(true);
        voice_btn.setEnabled(true);
        file_btn.setEnabled(true);
		RoomMessagesPanel roomMessagesPanel = new RoomMessagesPanel(room);
		roomMessagesPanels.add(roomMessagesPanel);

		for (DataMessage messageData : room.getListMessage())
			addNewMessageGUI(room.getId(), messageData);

		JScrollPane messagesScrollPane = new JScrollPane(roomMessagesPanel);
		messagesScrollPane.setMinimumSize(new Dimension(50, 100));
		messagesScrollPane.getViewport().setBackground(Color.white);

		roomTabbedPane.addTab(room.getName(), messagesScrollPane);
		roomTabbedPane.setTabComponentAt(roomTabbedPane.getTabCount() - 1,
				new TabComponent(room.getName(), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						roomMessagesPanels.remove(roomMessagesPanel);
						roomTabbedPane.remove(messagesScrollPane);
					}
				}));
		
	}


	public static class RoomMessagesPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public Room room;

		public RoomMessagesPanel(Room room) {
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.setBackground(Color.white);
			this.room = room;
		}

		public static RoomMessagesPanel findRoomMessagesPanel(List<RoomMessagesPanel> roomMessagesPanelList, int id) {
			if(roomMessagesPanelList==null) return null;
			for (RoomMessagesPanel roomMessagesPanel : roomMessagesPanelList) {
				if (roomMessagesPanel.room.getId() == id)
					return roomMessagesPanel;
			}
			return null;
		}
	}
	public static class TabComponent extends JPanel {

		private static final long serialVersionUID = 1L;

		public TabComponent(String tabTitle, ActionListener closeButtonListener) {
			JLabel titleLabel = new JLabel(tabTitle);
			JButton closeButton = new JButton(UIManager.getIcon("InternalFrame.closeIcon"));
			closeButton.addActionListener(closeButtonListener);
			closeButton.setPreferredSize(new Dimension(16, 16));

			this.setLayout(new FlowLayout());
			this.add(titleLabel);
			this.add(closeButton);
			this.setOpaque(false);
		}

	}
	
//	public void updateGroupJList() {
//		List<String> groupList = new ArrayList<String>();
//		for (Room room : Main.socketController.allRooms) {
//			if (room.type.equals("group"))
//				groupList.add(room.name);
//		}
//		groupJList.setListData(groupList.toArray(new String[0]));
//	}
	public JPanel createMainPane() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		list2=testlist();
		list2.addMouseListener(new MouseAdapter() {
			
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) { // Kiểm tra số lần click (ở đây là 1 click)
		            int selectedIndex = list2.getSelectedIndex(); // Lấy chỉ số của item được chọn
		            sad_btn.setEnabled(true);
		            haha_btn.setEnabled(true);
		            Send_btn.setEnabled(true);
		            nervous_btn.setEnabled(true);
		            smile_btn.setEnabled(true);
		            voice_btn.setEnabled(true);
		            file_btn.setEnabled(true);

		            Account_Client_side selectedAccount = list2.getSelectedValue(); // Lấy giá trị của item được chọn
		            // Xử lý sự kiện khi item được chọn
		            JOptionPane.showMessageDialog(null,"Da click"+selectedAccount);
		            ImageIcon image=new ImageIcon(selectedAccount.Avatar);
		    		Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		    		Icon icon=new ImageIcon(ChangeImg);
		    		
		            Room foundRoom = Room.findPrivateRoom(SockerHandler.allRooms, selectedAccount.userName);
		            if(foundRoom==null) {
		            	SockerHandler.CreatePrivateRoom(selectedAccount.getUserName());
		            	System.out.println("Da tao phong moi");
		            	
		            }
		            else {
		            	int roomTabIndex = -1;
		            	for (int i = 0; i < roomTabbedPane.getTabCount(); i++) {
							JScrollPane currentScrollPane = (JScrollPane) roomTabbedPane.getComponentAt(i);
							RoomMessagesPanel currentRoomMessagePanel = (RoomMessagesPanel) currentScrollPane.getViewport().getView();
							if (currentRoomMessagePanel.room.getId() == foundRoom.getId()) {
								roomTabIndex = i;
								break;
							}
						}
		            	if (roomTabIndex == -1) { // room tồn tại nhưng tab bị chéo trước đó
							newRoomTab(foundRoom);
							System.out.println("Da tao newromtab");
							roomTabbedPane.setSelectedIndex(roomTabbedPane.getTabCount() - 1);
						} else {
							roomTabbedPane.setSelectedIndex(roomTabIndex);
						}
		            }
		           
		            	
		            
		        
		          
		           
		       
		            // ...
		        }
		    }
		});
		panel.add(new JScrollPane(list2));
		
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
				if(!account_Client_side.getUserName().equals(userName)) {
					defaultListMode.addElement(account_Client_side);
				}
			
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
	
		panel_contain_online_user.removeAll(); 
	    panel_contain_online_user.add(createMainPane(), BorderLayout.CENTER); 
	    panel_contain_online_user.revalidate(); 
	    panel_contain_online_user.repaint(); 
	}
	public static void loadMessage(Room room) {
		for (DataMessage message : room.getListMessage()) {
			addNewMessageGUI(room.getId(), message);
		}
	}
	public static void addNewMessage(int roomID, String type, String whoSend, String content) {
		DataMessage messageData = new DataMessage(whoSend, type, content);
		Room receiveMessageRoom = Room.findRoom(SockerHandler.allRooms, roomID);
		receiveMessageRoom.getListMessage().add(messageData);
		
		addNewMessageGUI(roomID, messageData);
	}
	private static void addNewMessageGUI(int roomID, DataMessage messageData) {

		MessagePanel newMessagePanel = new MessagePanel(messageData);
		newMessagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		RoomMessagesPanel receiveMessageRoomMessagesPanel = RoomMessagesPanel.findRoomMessagesPanel(roomMessagesPanels,roomID);
		if(receiveMessageRoomMessagesPanel==null) {
			JOptionPane.showMessageDialog(null, "ReceiveMessage bị null");
		}
		else {
			receiveMessageRoomMessagesPanel.add(Box.createHorizontalGlue());
			receiveMessageRoomMessagesPanel.add(newMessagePanel);
			receiveMessageRoomMessagesPanel.validate();
			receiveMessageRoomMessagesPanel.repaint();
			roomTabbedPane.validate();
			roomTabbedPane.repaint();
		}
		
		
		

	}
	public static void deleteUserOnlinePanel_members(String username) {
		for (Account_Client_side account_Client_side : UserOnlinePanel_members) {
			if(username.equals(account_Client_side.getUserName())) {
				UserOnlinePanel_members.remove(account_Client_side);
				break;
			}
		}
	}
}
