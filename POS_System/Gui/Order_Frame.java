
// DataBase - TermProject
// GUI - Order_Frame
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import POS_System.POS_Controller;

public class Order_Frame extends JPanel {
	public JTextArea Order_Field = new JTextArea();
	public JTextField Customer_Name_Input = new JTextField();
	public JLabel Customer_Name = new JLabel("고객명");
	public JLabel Table_Number = new JLabel("테이블명");
	public JButton Order = new JButton("주문");
	public JButton Cancel = new JButton("취소");
	public JButton Payment = new JButton("결제");
	public JComboBox<String> Table_Number_Box = new JComboBox<String>();
	public JScrollPane Scroll_Table_Information = new JScrollPane();

	public Order_Frame(POS_Controller p) {
		TitledBorder Frame_Name = new TitledBorder("주문 내역");
		Frame_Name.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		Frame_Name.setTitleFont(new Font("SansSerif", 1, 20));

		Order_Field.setBorder(new LineBorder(Color.BLACK, 2));
		Order_Field.setEditable(false);

		Scroll_Table_Information.setViewportView(Order_Field);
		Scroll_Table_Information.setBounds(10, 30, 280, 250);
		this.add(Scroll_Table_Information);

		Customer_Name.setBounds(310, 35, 100, 20);
		this.add(Customer_Name);

		Customer_Name_Input.setBounds(300, 60, 90, 30);
		this.add(Customer_Name_Input);

		Table_Number.setBounds(310, 105, 100, 20);
		this.add(Table_Number);

		Table_Number_Box.setBounds(300, 130, 90, 30);
		for (int i = 0; i < 20; i++) {
			Table_Number_Box.addItem(Integer.toString(i + 1));
		}
		Table_Number_Box.addActionListener(p);
		this.add(Table_Number_Box);

		Order.setBounds(300, 170, 90, 30);
		Order.addActionListener(p);
		this.add(Order);

		Cancel.setBounds(300, 210, 90, 30);
		Cancel.addActionListener(p);
		this.add(Cancel);

		Payment.setBounds(300, 250, 90, 30);
		Payment.addActionListener(p);
		this.add(Payment);

		this.setLayout(null);
		this.setBounds(405, 130, 400, 300);
		this.setBorder(Frame_Name);
	}
}
