package Client;

import javax.swing.JPanel;

import Server.Account;
import Server.Client;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SpringLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.ListCellRenderer;

public class OnlinePane extends JPanel implements ListCellRenderer<Account_Client_side> {
	private Account client;
	/**
	 * Create the panel.
	 */
	private JPanel avatarLabel;
	private JPanel TextLabel;
	private JLabel ten_lb;
	private JLabel anh_lb;
	private JLabel des_lb;
	public OnlinePane() {
		setSize(213, 65);
		avatarLabel=new JPanel(new BorderLayout());
		TextLabel=new JPanel(new GridLayout(1, 0));
		des_lb=new JLabel();
		anh_lb=new JLabel();
		avatarLabel.add(anh_lb,BorderLayout.CENTER);
		anh_lb.setPreferredSize(new Dimension(100, 100));
		ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\anh-nguoi-dep-trung-quoc.jpg");
		Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Icon icon=new ImageIcon(ChangeImg);
		
		anh_lb.setIcon(icon);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(5,5,5,5));
		ten_lb = new JLabel("Việt");
		
		TextLabel.add(ten_lb);
		TextLabel.add(des_lb);
		
		add(avatarLabel,BorderLayout.WEST);
		add(TextLabel,BorderLayout.CENTER);
		

	}
	
	

	@Override
	public Component getListCellRendererComponent(JList<? extends Account_Client_side> list, Account_Client_side value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\anh-nguoi-dep-trung-quoc.jpg");
		Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
		Icon icon=new ImageIcon(ChangeImg);
		System.out.println(value.getAvatar());
		anh_lb.setIcon(icon);
		ten_lb.setText(value.getUserName());
		des_lb.setText(value.getDescription());
		if(isSelected) {
			setBackground(Color.GRAY); 
		}
		else {
            setBackground(Color.WHITE); 

		}
		return this;
	}
	
}
