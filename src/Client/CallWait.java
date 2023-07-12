package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class CallWait extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 * @param sender 
	 */
	public CallWait(int idroom, int portVoice, String whoSend, BufferedWriter sender) {
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 333, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle(whoSend+"đang gọi");
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lb_avatar = new JLabel("Avatar");
		lb_avatar.setBounds(95, 20, 91, 100);
		contentPane.add(lb_avatar);
		for (Account_Client_side acc : ClientChat.UserOnlinePanel_members) {
			if(acc.getUserName().equals(whoSend)) {
				lb_avatar.setIcon(new ImageIcon(acc.getAvatar()));
				break;
			}
			
		}
		JButton btn_nghe = new JButton("Nghe");
		btn_nghe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			try {
				SockerHandler.callFrame2=new CallFrame2(idroom, portVoice, whoSend, "Đã kết nối",sender);
				sender.write("accept voice");
				sender.newLine();
				sender.write(idroom+"");
				sender.newLine();
				sender.flush();
				dispose();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			}
		});
		btn_nghe.setBounds(47, 148, 85, 21);
		contentPane.add(btn_nghe);
		
		JButton btn_cancel = new JButton("Cúp máy");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				try {
					sender.write("user deny call");
					sender.newLine();
					sender.write(idroom+"");
					sender.newLine();
					sender.write(whoSend);
					sender.newLine();
					
					sender.flush();
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btn_cancel.setBounds(170, 148, 85, 21);
		contentPane.add(btn_cancel);
		setVisible(true);
	}

	public CallWait() {
		// TODO Auto-generated constructor stub
	}
	

	

	

}
