package Client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CallFrame2 extends JFrame {

	private JPanel contentPane;
	public static JLabel lb_des;
	public static int portServer;
	public static player_thread p;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @param receiverPeople 
	 * @param sender 
	 */
	public static AudioFormat getAudioFormat() {
	    float sampleRate = 8000.0f;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = false;
	    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	    return new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, (sampleSizeInBits / 8) * channels, sampleRate, bigEndian);
	}
	public SourceDataLine audio_out;
	public CallFrame2(int chattingRoom, int portVoice, String receiverPeople, String trangthai, BufferedWriter sender) {
		portServer=portVoice;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 315, 243);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		String avatarlink = null;
		JLabel lb_avatar = new JLabel();
		for (Account_Client_side acc : SockerHandler.clientAccountList) {
			if(acc.getUserName().equals(receiverPeople)) {
				avatarlink=acc.getAvatar();
			}
			break;
		}
		
		lb_avatar.setPreferredSize(new Dimension(100, 100));
		ImageIcon image=new ImageIcon(avatarlink);
		Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Icon icon=new ImageIcon(ChangeImg);
		
				
		lb_avatar.setIcon(icon);
		lb_avatar.setBounds(11, 23, 100, 100);
		contentPane.add(lb_avatar);
		
		lb_des = new JLabel("Trạng thái");
		lb_des.setText(trangthai);
		lb_des.setBounds(134, 51, 72, 13);
		contentPane.add(lb_des);
		
		JButton btnNewButton = new JButton("Kết thúc");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sender.write("end call");
					sender.newLine();
					sender.write(chattingRoom+"");
					sender.newLine();
					sender.flush();
					Server_voice.calling=false;
					p.din.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(121, 125, 85, 21);
		contentPane.add(btnNewButton);
		setVisible(true);
		init_audio();
	}


public CallFrame2() {
		// TODO Auto-generated constructor stub
	}


	//	public CallFrame2() {
//		// TODO Auto-generated constructor stub
//	}
	public void init_audio() {
		
		try {
			AudioFormat format=getAudioFormat();
			DataLine.Info	info_out	=new DataLine.Info(SourceDataLine.class, format);
			if(!AudioSystem.isLineSupported(info_out)) {
				System.out.println("Not support");
				System.exit(0);
			}
			audio_out=(SourceDataLine)AudioSystem.getLine(info_out);
			audio_out.open(format);
			audio_out.start();
			p=new player_thread();
			p.din=new DatagramSocket(portServer);
			p.audio_out=audio_out;
			Server_voice.calling=true;
			p.start();
			
		} catch (LineUnavailableException | SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
