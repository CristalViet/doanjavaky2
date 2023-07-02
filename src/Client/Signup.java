package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;



import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;

public class Signup extends JFrame {

	private JPanel contentPane;
	private JTextField tx_tendangnhap;
	private String avatarLink;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					signup frame = new signup();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Signup() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 352);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sign UP");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(181, 0, 93, 61);
		contentPane.add(lblNewLabel);
		
		tx_tendangnhap = new JTextField();
		tx_tendangnhap.setBounds(41, 57, 116, 19);
		contentPane.add(tx_tendangnhap);
		tx_tendangnhap.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tên đăng nhập");
		lblNewLabel_1.setBounds(41, 38, 95, 13);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
		lblNewLabel_2.setBounds(41, 100, 56, 13);
		contentPane.add(lblNewLabel_2);
		
		JButton btn_signup = new JButton("Đăng ký");
		
		btn_signup.setBounds(51, 266, 85, 21);
		contentPane.add(btn_signup);
		
		JButton btn_home = new JButton("Home");
		btn_home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ClientPanel();
			}
		});
		btn_home.setBounds(161, 266, 133, 21);
		contentPane.add(btn_home);
		
		JLabel lb_anh = new JLabel("Ảnh");
		lb_anh.setBounds(191, 57, 107, 117);
		contentPane.add(lb_anh);
		
		JLabel lblNewLabel_4 = new JLabel("Nhập lại mật khẩu");
		lblNewLabel_4.setBounds(41, 158, 127, 16);
		contentPane.add(lblNewLabel_4);
		
		JFormattedTextField tx_password = new JFormattedTextField();
		tx_password.setBounds(41, 126, 116, 22);
		contentPane.add(tx_password);
		
		JFormattedTextField tx_repassword = new JFormattedTextField();
		tx_repassword.setBounds(41, 187, 116, 22);
		contentPane.add(tx_repassword);
		
		JButton btn_themAnh = new JButton("Thêm ảnh");
		btn_themAnh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser=new JFileChooser();
				FileNameExtensionFilter imageFilter=new FileNameExtensionFilter("Định dạng ảnh", "jpg","png");
				fileChooser.setFileFilter(imageFilter);
				fileChooser.setMultiSelectionEnabled(false);
				int x=fileChooser.showDialog(null, "chọn file");
				if(x==JFileChooser.APPROVE_OPTION)
				{
					File f=fileChooser.getSelectedFile();
					ImageIcon icon=new ImageIcon(f.getAbsolutePath());
					Image image=icon.getImage();
					Image imagenew=image.getScaledInstance(lb_anh.getWidth(), lb_anh.getHeight(), Image.SCALE_SMOOTH);
				    icon=new ImageIcon(imagenew);
				    avatarLink=f.getAbsolutePath();
				    lb_anh.setIcon(icon);
					
				}
			}
		});
		btn_themAnh.setBounds(195, 186, 99, 25);
		contentPane.add(btn_themAnh);
		
		JTextPane textPane_description = new JTextPane();
		textPane_description.setBounds(328, 90, 131, 155);
		contentPane.add(textPane_description);
		
		JLabel lblNewLabel_3 = new JLabel("Mô tả bản thân");
		lblNewLabel_3.setBounds(328, 59, 107, 16);
		contentPane.add(lblNewLabel_3);
		
		//action listeners
		btn_signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tendangnhap=tx_tendangnhap.getText();
				String password=tx_password.getText();
				String repassword=tx_repassword.getText();
				String description=textPane_description.getText();
				if(password.equals(repassword)) {
					
					new Thread(()->{
						try {
							SockerHandler sockerHandler = new SockerHandler(tendangnhap, password, avatarLink, description,1000);
							sockerHandler.start("signup");
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}).start();
					dispose();
				}
			}
		});
	setVisible(true);
	}
}
