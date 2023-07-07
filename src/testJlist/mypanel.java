package testJlist;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

public class mypanel extends JPanel implements ListCellRenderer<myobject> {
	private  JLabel lblNewLabel =new JLabel();
	private  JLabel lblNewLabel_2=new JLabel();
	private   JLabel lblNewLabel_1=new JLabel();
	private JPanel panelText;
	private JPanel panelIcon;
	/**
	 * Create the panel.
	 */
	public mypanel() {
		setSize(100, 100);
		setLayout(new BorderLayout(5, 5));
		setBorder(new EmptyBorder(10,10,10,10));
		panelText = new JPanel(new GridLayout(0, 1));
		panelIcon= new JPanel();
		panelIcon.setBorder(new EmptyBorder(5,5,5,5));
	
	
		 lblNewLabel = new JLabel("anh");
	
		
		
		 lblNewLabel_1 = new JLabel("Hello");
		 lblNewLabel_2 = new JLabel("Hello");

		 panelText.add(lblNewLabel_1);
		panelText.add(lblNewLabel_2);
		setBackground(Color.LIGHT_GRAY);
		panelIcon.add(lblNewLabel);
		

		
		
		
		add(panelIcon,BorderLayout.WEST);
		add(panelText,BorderLayout.CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends myobject> jlist, myobject value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
	 
		lblNewLabel.setIcon(value.icon);
		lblNewLabel_1.setText(value.name);
	 	System.out.println("Test: "+lblNewLabel_1.getText());
		lblNewLabel_2.setText(value.add);
		 setEnabled(jlist.isEnabled());
//		setOpaque(true);
		System.out.println("Đa chay");
		return this;
	}
}
