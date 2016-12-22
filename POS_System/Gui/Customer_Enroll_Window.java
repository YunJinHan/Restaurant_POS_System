package Gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import POS_System.POS_Controller;

public class Customer_Enroll_Window {

	public JFrame Customer_Enroll_Frame = new JFrame();
	private JPanel Customer_Enroll_Panel = new JPanel();
	private JLabel Customer_Name = new JLabel("고객명");
	private JLabel Customer_Brith = new JLabel("생일(4자리)");
	private JLabel Customer_Tel = new JLabel("연락처");
	public JTextField Customer_Name_Input = new JTextField();
	public JTextField Customer_Brith_Input = new JTextField();
	public JTextField Customer_Tel_Input = new JTextField();
	public JButton Register_Button = new JButton("가입신청");
	public JButton Cancel_Button = new JButton("취소");

	public Customer_Enroll_Window(POS_Controller p) {

		Register_Button.addActionListener(p);
		Cancel_Button.addActionListener(p);

		Customer_Name.setBounds(30, 30, 100, 30);
		Customer_Brith.setBounds(30, 90, 120, 30);
		Customer_Tel.setBounds(30, 150, 100, 30);
		Customer_Name_Input.setBounds(150, 30, 110, 35);
		Customer_Brith_Input.setBounds(150, 90, 110, 35);
		Customer_Tel_Input.setBounds(150, 150, 110, 35);
		Register_Button.setBounds(40, 210, 100, 40);
		Cancel_Button.setBounds(160, 210, 100, 40);

		Customer_Name.setFont(new Font("SansSerif", 1, 20));
		Customer_Brith.setFont(new Font("SansSerif", 1, 20));
		Customer_Tel.setFont(new Font("SansSerif", 1, 20));

		Customer_Enroll_Panel.setLayout(null);
		Customer_Enroll_Panel.add(Customer_Name);
		Customer_Enroll_Panel.add(Customer_Brith);
		Customer_Enroll_Panel.add(Customer_Tel);
		Customer_Enroll_Panel.add(Customer_Name_Input);
		Customer_Enroll_Panel.add(Customer_Brith_Input);
		Customer_Enroll_Panel.add(Customer_Tel_Input);
		Customer_Enroll_Panel.add(Register_Button);
		Customer_Enroll_Panel.add(Cancel_Button);

		Customer_Enroll_Frame.add(Customer_Enroll_Panel);

		Customer_Enroll_Frame.setResizable(false);
		Customer_Enroll_Frame.setVisible(false);
		Customer_Enroll_Frame.setTitle("회원 등록");
		Customer_Enroll_Frame.setBounds(500, 300, 300, 300);
	}
}
