
// DataBase - TermProject
// GUI - Staff_Enroll_Window
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import POS_System.POS_Controller;

public class Staff_Enroll_Window {

	public JFrame Staff_Enroll_Frame = new JFrame();
	private JPanel Staff_Enroll_Panel = new JPanel();
	private JLabel Staff_Name = new JLabel("직원명");
	private JLabel Staff_Rank = new JLabel("직급");
	public JTextField Staff_Name_Input = new JTextField();
	public JComboBox<String> Staff_Rank_Box = new JComboBox<String>();
	public JButton Register_Button = new JButton("등록");
	public JButton Cancel_Button = new JButton("취소");

	public Staff_Enroll_Window(POS_Controller p) {

		Staff_Name.setBounds(40, 20, 100, 30);
		Staff_Rank.setBounds(40, 70, 100, 30);
		Staff_Name_Input.setBounds(120, 20, 130, 35);
		Staff_Rank_Box.setBounds(120, 70, 130, 35);
		Register_Button.setBounds(40, 120, 100, 40);
		Cancel_Button.setBounds(160, 120, 100, 40);

		Staff_Name.setFont(new Font("SansSerif", 1, 20));
		Staff_Rank.setFont(new Font("SansSerif", 1, 20));

		Staff_Enroll_Panel.setLayout(null);
		Staff_Enroll_Panel.add(Staff_Name);
		Staff_Enroll_Panel.add(Staff_Rank);
		Staff_Enroll_Panel.add(Staff_Name_Input);
		Staff_Enroll_Panel.add(Staff_Rank_Box);
		Staff_Enroll_Panel.add(Register_Button);
		Staff_Enroll_Panel.add(Cancel_Button);
		
		Staff_Rank_Box.addItem("Supervisor");
		Staff_Rank_Box.addItem("Staff");

		Staff_Enroll_Frame.add(Staff_Enroll_Panel);

		Staff_Enroll_Frame.setResizable(false);
		Staff_Enroll_Frame.setVisible(false);
		Staff_Enroll_Frame.setTitle("직원 등록");
		Staff_Enroll_Frame.setBounds(500, 300, 300, 200);
		
		Register_Button.addActionListener(p);
		Cancel_Button.addActionListener(p);
		Staff_Rank_Box.addActionListener(p);
	}
}
