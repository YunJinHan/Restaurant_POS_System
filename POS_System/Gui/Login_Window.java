package Gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import POS_System.POS_Controller;

public class Login_Window {

	public JFrame Login_Frame = new JFrame();
	private JPanel Login_Panel = new JPanel();
	private JLabel Name = new JLabel("이름");
	private JLabel ID = new JLabel("사원번호");
	public JTextField Name_Input = new JTextField();
	public JPasswordField ID_Input = new JPasswordField();
	public JButton Login_Button = new JButton("로그인");

	public Login_Window(POS_Controller p) {

		Login_Button.addActionListener(p);

		Name.setBounds(30, 17, 80, 40);
		ID.setBounds(30, 57, 90, 40);
		Name_Input.setBounds(125, 20, 100, 35);
		ID_Input.setBounds(125, 60, 100, 35);
		Login_Button.setBounds(235, 40, 80, 35);

		Name.setFont(new Font("SansSerif", 1, 20));
		ID.setFont(new Font("SansSerif", 1, 20));

		Login_Panel.setLayout(null);
		Login_Panel.add(Name);
		Login_Panel.add(ID);
		Login_Panel.add(Name_Input);
		Login_Panel.add(ID_Input);
		Login_Panel.add(Login_Button);

		Login_Frame.add(Login_Panel);

		Login_Frame.setResizable(false);
		Login_Frame.setVisible(true);
		Login_Frame.setTitle("사원 로그인");
		Login_Frame.setBounds(595, 300, 350, 140);
		Login_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
