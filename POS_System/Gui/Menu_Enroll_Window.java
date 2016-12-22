
// DataBase - TermProject
// GUI - Menu_Enroll_Window
// ��ǻ�� ���а�
// �й� : 2012036901
// �̸� : �� �� ��
// 2016.05.23

package Gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import POS_System.POS_Controller;

public class Menu_Enroll_Window {

	public JFrame Menu_Enroll_Frame = new JFrame();
	private JPanel Menu_Enroll_Panel = new JPanel();
	private JLabel Menu_Name = new JLabel("�޴���");
	private JLabel Menu_Price = new JLabel("����");
	public JTextField Menu_Name_Input = new JTextField();
	public JTextField Menu_Price_Input = new JTextField();
	public JButton Register_Button = new JButton("���");
	public JButton Cancel_Button = new JButton("���");

	public Menu_Enroll_Window(POS_Controller p) {

		Register_Button.addActionListener(p);
		Cancel_Button.addActionListener(p);

		Menu_Name.setBounds(45, 23, 100, 30);
		Menu_Price.setBounds(45, 73, 100, 30);
		Menu_Name_Input.setBounds(120, 20, 130, 35);
		Menu_Price_Input.setBounds(120, 70, 130, 35);
		Register_Button.setBounds(35, 120, 100, 40);
		Cancel_Button.setBounds(165, 120, 100, 40);

		Menu_Name.setFont(new Font("SansSerif", 1, 20));
		Menu_Price.setFont(new Font("SansSerif", 1, 20));

		Menu_Enroll_Panel.setLayout(null);
		Menu_Enroll_Panel.add(Menu_Name);
		Menu_Enroll_Panel.add(Menu_Price);
		Menu_Enroll_Panel.add(Menu_Name_Input);
		Menu_Enroll_Panel.add(Menu_Price_Input);
		Menu_Enroll_Panel.add(Register_Button);
		Menu_Enroll_Panel.add(Cancel_Button);

		Menu_Enroll_Frame.add(Menu_Enroll_Panel);

		Menu_Enroll_Frame.setResizable(false);
		Menu_Enroll_Frame.setVisible(false);
		Menu_Enroll_Frame.setTitle("�޴����");
		Menu_Enroll_Frame.setBounds(500, 300, 300, 200);
	}
}
