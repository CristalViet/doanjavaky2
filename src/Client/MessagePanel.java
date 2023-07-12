package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

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
			contentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			contentPanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String fileName = data.getContent();
					String extension = fileName.split("\\.")[1];

					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("Chọn đường dẫn download");
					jfc.setFileFilter(new FileNameExtensionFilter(extension.toUpperCase() + " files", extension));
					jfc.setSelectedFile(new File(fileName));
					int result = jfc.showSaveDialog(contentPanel);
					jfc.setVisible(true);

					if (result == JFileChooser.APPROVE_OPTION) {
						String filePath = jfc.getSelectedFile().getAbsolutePath();
						if (!filePath.endsWith("." + extension))
							filePath += "." + extension;

						Room room = Room.findRoom(SockerHandler.allRooms, ClientChat.chattingRoom);
						int messageIndex = -1;
						for (int i = 0; i < room.getListMessage().size(); i++) {
							if (room.getListMessage().get(i) == data) {
								messageIndex = i;
								break;
							}
						}
						SockerHandler.downloadFile(room.getId(), messageIndex, fileName, filePath);
					}
				}
			});
			JLabel fileIcon = new JLabel();
			try {
				String extension = data.getContent().split("\\.")[1];
				Random r = new Random();
				File tempFile = new File("temp" + r.nextInt() + r.nextInt() + r.nextInt() + "." + extension);
				tempFile.createNewFile();
				fileIcon.setIcon(FileSystemView.getFileSystemView().getSystemIcon(tempFile));
				tempFile.delete();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			JLabel fileNameLabel = new JLabel("<HTML><U>" + data.getContent() + "</U></HTML>");
			fileNameLabel.setFont(new Font("Dialog", Font.PLAIN, 15));

			contentPanel.add(fileIcon);
			contentPanel.add(fileNameLabel);

			this.setMaximumSize(new Dimension(thisMaxSize.width, 30));
			
		}
		else if(type.equals("audio")) {
			
		}
		else if(type.equals("voice")) {
			JLabel fileIcon = new JLabel();
			
			fileIcon.setIcon(new ImageIcon("D:\\doananjavaky2\\img\\phone-contact.png"));
			JLabel fileNameLabel = new JLabel("<HTML><U>" + "Cuộc gọi từ"+whoSendLabel.getText() + "</U></HTML>");
			contentPanel.add(fileIcon);
			contentPanel.add(fileNameLabel);
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
