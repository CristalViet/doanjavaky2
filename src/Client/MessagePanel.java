package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MessagePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public DataMessage data;
	private Component contentComponent ;

	public MessagePanel(DataMessage data) {
		this.data=data;
		Dimension thisMaxSize = this.getMaximumSize();
		JLabel whoSendLabel = new JLabel(
				(data.getUserSend().equals(SockerHandler.getUserName()) ? "Bạn" : data.getUserSend()) + ": ");
		whoSendLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		JPanel contentPanel = new JPanel();
	
		contentComponent=whoSendLabel;
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		contentPanel.setBackground(Color.white);
		
		String type=data.getType();
		if(type.equals("notify")) {
			JTextArea textContent = new JTextArea(data.getContent());
			textContent.setFont(new Font("Dialog", Font.PLAIN, 15));
			textContent.setEditable(false);
			// textContent.setBackground(new Color(52, 149, 235));
			// textContent.setAlignmentY(SwingConstants.NORTH);
			contentComponent=textContent;
			contentPanel.add(textContent);

			int lineCount = data.getContent().split("\r\n|\r|\n").length;
			if (lineCount > 1) {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 19 * lineCount));
			} else {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 25));
			}
		}
		else if(type.equals("text")) {
			JTextArea textContent = new JTextArea(data.getContent());
			textContent.setFont(new Font("Dialog", Font.PLAIN, 15));
			textContent.setEditable(false);
			// textContent.setBackground(new Color(52, 149, 235));
			// textContent.setAlignmentY(SwingConstants.NORTH);
			contentComponent=textContent;
			contentPanel.add(textContent);

			int lineCount = data.getContent().split("\r\n|\r|\n").length;
			if (lineCount > 1) {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 19 * lineCount));
			} else {
				this.setMaximumSize(new Dimension(thisMaxSize.width, 25));
			}

		}
		else if(type.equals("emoji")) {
			JLabel lb_anh=new JLabel();
			lb_anh.setSize(20, 20);
			if(data.getContent().equals("sad"))
			{
				ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_sad.png");
				Image ChangeImg=image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon icon=new ImageIcon(ChangeImg);
				lb_anh.setIcon(icon);
			}
			else if (data.getContent().equals("haha"))
			{
				ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_haha.png");
				Image ChangeImg=image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon icon=new ImageIcon(ChangeImg);
				lb_anh.setIcon(icon);
			}
			else if (data.getContent().equals("smile"))
			{
				ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_smile.png");
				Image ChangeImg=image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon icon=new ImageIcon(ChangeImg);
				lb_anh.setIcon(icon);
			}
			else if (data.getContent().equals("nervous"))
			{
				ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\emoji\\emoji_nervous.png");
				Image ChangeImg=image.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				Icon icon=new ImageIcon(ChangeImg);
				lb_anh.setIcon(icon);
			}
			contentPanel.add(lb_anh);
			this.setMaximumSize(new Dimension(thisMaxSize.width, 25));
		}
		else if(type.equals("file")) {
			
		}
		else if(type.equals("audio")) {
			
		}
		
	

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		whoSendLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(whoSendLabel);
		contentPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(contentPanel);
		updatePreferredSize();
		this.setBackground(null);
	}
	private void updatePreferredSize() {
	    Dimension contentSize = contentComponent.getPreferredSize();
	    Dimension thisMaxSize = this.getMaximumSize();
	    this.setMaximumSize(new Dimension(thisMaxSize.width, contentSize.height));
	}
	
	
}
