package Client;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class CallFrame extends JFrame {

	private JPanel contentPane;
	public static JLabel lb_des;
	public static int portVoice;
	public String add_server="localhost";
	recorder_thread r;
	public static AudioFormat getAudioFormat() {
	    float sampleRate = 8000.0f;
	    int sampleSizeInBits = 16;
	    int channels = 1;
	    boolean signed = true;
	    boolean bigEndian = false;
	    AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	    return new AudioFormat(encoding, sampleRate, sampleSizeInBits, channels, (sampleSizeInBits / 8) * channels, sampleRate, bigEndian);
	}
	TargetDataLine audio_in;
	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 * @param receiverPeople 
	 */
	
	public CallFrame(int chattingRoom, int portVoice, String receiverPeople, String trangthai, BufferedWriter sender) {
		
		this.portVoice=portVoice;
		initAudio();
		setTitle(SockerHandler.userName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
					Client_voice.calling=false;
					r.dout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(121, 125, 85, 21);
		contentPane.add(btnNewButton);
		setVisible(true);
	}


	





	public CallFrame() {
		// TODO Auto-generated constructor stub
	}








	public void initAudio() {
		
		try {
			AudioFormat format=getAudioFormat();
			DataLine.Info info=new DataLine.Info(TargetDataLine.class, format);
//			if(!AudioSystem.isLineSupported(info)) {
//				System.out.println("not support");
//				System.exit(0);
//			}
			audio_in=(TargetDataLine) AudioSystem.getLine(info);
			audio_in.open(format);
			audio_in.start();
			 r=new recorder_thread();
			InetAddress inet=InetAddress.getByName(add_server);
			r.audio_in=audio_in;
			r.dout=new DatagramSocket();
			r.server_ip=inet;
			r.server_port=portVoice;
			Client_voice.calling=true;
			r.start();
		} catch (LineUnavailableException | UnknownHostException | SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	

	
}

