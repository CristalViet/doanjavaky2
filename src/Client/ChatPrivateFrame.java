package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;

public class ChatPrivateFrame extends JPanel {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChatPrivateFrame frame = new ChatPrivateFrame();
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
	public ChatPrivateFrame() {

		setBounds(100, 100, 563, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 361, 262);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JLabel avatar_nguoinhan = new JLabel("New label");
		avatar_nguoinhan.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		avatar_nguoinhan.setBounds(436, 25, 103, 105);
		contentPane.add(avatar_nguoinhan);
		
		JLabel avatar_nguoigui = new JLabel("New label");
		avatar_nguoigui.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		avatar_nguoigui.setBounds(436, 281, 103, 105);
		contentPane.add(avatar_nguoigui);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 306, 317, 80);
		contentPane.add(scrollPane_1);
		
		JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("VoiceCall");
		btnNewButton.setBounds(436, 141, 93, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("VideoCall");
		btnNewButton_1.setBounds(436, 201, 93, 50);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Buzz");
		btnNewButton_2.setBounds(10, 282, 85, 21);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setBounds(334, 365, 66, 21);
		contentPane.add(btnNewButton_3);
		setVisible(true);
	}
	
}
