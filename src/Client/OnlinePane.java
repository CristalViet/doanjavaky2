package Client;

import javax.swing.JPanel;

import Server.Account;
import Server.Client;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class OnlinePane extends JPanel {
	private final JLabel lb_anh = new JLabel("Ảnh");
	private Account client;
	/**
	 * Create the panel.
	 */
	
	public OnlinePane() {
		setLayout(null);
		lb_anh.setBounds(0, 0, 39, 48);
		add(lb_anh);
		
		JLabel lblNewLabel = new JLabel("Hello");
		lblNewLabel.setBounds(40, 0, 78, 48);
		ImageIcon img=new ImageIcon("D://doananjavaky2/img/anh-nguoi-dep-trung-quoc.jpg");
		Image img_2=img.getImage();
		Image img_new=img_2.getScaledInstance(lb_anh.getWidth(), lb_anh.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon img_set=new ImageIcon(img_new);
		lb_anh.setIcon(img_set);
		add(lblNewLabel);

	}
	
}
