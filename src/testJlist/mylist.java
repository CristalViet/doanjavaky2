package testJlist;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class mylist extends JFrame {

	private JPanel contentPane;
	
	private  JList<myobject>list=new JList<myobject>();
	private  JList<myobject>list2=new JList<myobject>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mylist frame = new mylist();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mylist() {
		
//		contentPane = new JPanel();
//		contentPane.setLayout(null);
		setBounds(100, 100, 230, 511);
//		setContentPane(contentPane);
		getContentPane().add(createMainPain());
		
		
		list2.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1) { // Kiểm tra số lần click (ở đây là 1 click)
		            int selectedIndex = list2.getSelectedIndex(); // Lấy chỉ số của item được chọn
		            myobject selectedAccount = list2.getSelectedValue(); // Lấy giá trị của item được chọn
		            JOptionPane.showMessageDialog(null, "Hello"+selectedAccount);
		            // Xử lý sự kiện khi item được chọn
		            // ...
		        }
		    }
		});
		
		
//	
//		contentPane.add(createMainPain());
//		
		 
		
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public JPanel createMainPain() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
	
		
		panel.add(new JScrollPane(list2=testlist()));
		return panel;

	
	}
	public JList<myobject> testlist() {
		DefaultListModel<myobject>defaultListMode=new DefaultListModel<myobject>();
		for(int i=0;i<10;i++) {
			
			ImageIcon image=new ImageIcon("D:\\doananjavaky2\\img\\Copy-of-image3-social-wellness.png");
			Image ChangeImg=image.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
			Icon icon=new ImageIcon(ChangeImg);
			defaultListMode.addElement(new myobject("name"+i,"add"+i,icon));
		}
	

		JList<myobject> list3=new JList<myobject>(defaultListMode); 
//		list3.setModel(defaultListMode);
		list3.setCellRenderer(new mypanel());
		return list3;
	
	}
	
}
