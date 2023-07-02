package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Server.Client;
import Server.ClientHandler;
import Server.ServerPanel;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class ClientPanel extends JFrame {

	private JPanel contentPane;
	private Socket server;
	private OutputStream Os;
	private InputStream Is;
	private BufferedReader receive;
	private BufferedWriter sender;
	private JTextField textField_username;
	private JPasswordField passwordField;
	

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClientPanel frame = new ClientPanel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public ClientPanel() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 307);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Hola Chat");
		lblNewLabel_1.setForeground(new Color(255, 0, 0));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblNewLabel_1.setBounds(297, 8, 228, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("UserName");
		lblNewLabel_2.setBounds(297, 59, 64, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(297, 113, 64, 13);
		contentPane.add(lblNewLabel);
		
		textField_username = new JTextField();
		textField_username.setBounds(297, 84, 191, 19);
		contentPane.add(textField_username);
		textField_username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(297, 136, 191, 19);
		contentPane.add(passwordField);
		
		JButton logn_btn = new JButton("Login");
		logn_btn.addActionListener(new ActionListener() {
			private SockerHandler sockerHandler;

			public void actionPerformed(ActionEvent e) {
				String username=textField_username.getText();
				String password=passwordField.getText();
				System.out.println(password);
				int port=1000;
				new Thread(()->{
					try {
						sockerHandler = new SockerHandler(username, password, port);
						sockerHandler.start("login");
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}).start();
				dispose();
			}
		});
		logn_btn.setBounds(403, 188, 85, 21);
		contentPane.add(logn_btn);
		
		JButton signup_btn = new JButton("Sign Up");
		signup_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Signup signup= new Signup();
				
			}
		});
		signup_btn.setBounds(297, 188, 85, 21);
		contentPane.add(signup_btn);
		
		JLabel lblNewLabel_3 = new JLabel("Forget Password ?");
		lblNewLabel_3.setBounds(297, 165, 100, 13);
		contentPane.add(lblNewLabel_3);
		
		JLabel img_left = new JLabel("");
		img_left.setBounds(0, 0, 257, 287);
		ImageIcon img=new ImageIcon("D:\\doananjavaky2\\img\\Copy-of-image3-social-wellness.png");
		Image img_2=img.getImage();
		Image img_new=img_2.getScaledInstance(img_left.getWidth(), img_left.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon img_set=new ImageIcon(img_new);
		img_left.setIcon(img_set);
		contentPane.add(img_left);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
