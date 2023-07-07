package Client;

import java.awt.Color;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Server.DataMessage;

import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Font;

public class ChatPanel extends JPanel {
	public DataMessage data;
	public ChatPanel(DataMessage data) {
		this.data=data;
		// TODO Auto-generated constructor stub
		JLabel whosend=new JLabel("Việt");
		
		whosend.setFont(new Font("Dialog", Font.BOLD, 15));
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		whosend.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(whosend);
		JPanel contentPanel=new JPanel();
		contentPanel.setAlignmentY(TOP_ALIGNMENT);

		contentPanel.setBackground(Color.WHITE);
		this.add(contentPanel);
		setBackground(null);
		setVisible(true);
		
	}
	
}
