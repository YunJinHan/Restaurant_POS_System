
// DataBase - TermProject
// GUI - Register_Frame
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import POS_System.POS_Controller;

public class Register_Frame extends JPanel {

	public POS_Controller p;
	public int Sales_Date_List_Length = 0;

	public JPanel Customer_Panel = new JPanel();
	public JPanel Sale_Panel = new JPanel();
	public JPanel Staff_Panel = new JPanel();
	public JPanel Menu_Panel = new JPanel();
	public JTabbedPane Register_Frame = new JTabbedPane();

	public JLabel Customer_Name = new JLabel("고객명");
	public JTextField Customer_Name_Input = new JTextField();
	public JTextArea Customer_Information = new JTextArea();
	public JButton Customer_Enroll = new JButton("가입");
	public JButton Customer_Search = new JButton("조회");

	public JLabel Sales_Date = new JLabel("기간");
	public JComboBox<String> Sales_Date_List = new JComboBox<String>();
	public JTextArea Sale_Information = new JTextArea();
	public JScrollPane Scroll_Sale_Information = new JScrollPane();

	public JLabel Staff_Name = new JLabel("직원명");
	public JTextField Staff_Name_Input = new JTextField();
	public JTextArea Staff_Information = new JTextArea();
	public JButton Staff_Enroll = new JButton("직원 등록");
	public JButton Staff_Search = new JButton("조회");

	public JLabel Menu_Name = new JLabel("메뉴명");
	public JTextField Menu_Name_Input = new JTextField();
	public JTextArea Menu_Information = new JTextArea();
	public JButton Menu_Enroll = new JButton("메뉴 등록");
	public JButton Menu_Search = new JButton("조회");

	public void Create_Sale_Box() {
		try {
			int Sale_Period = p.DB.Sale_Period(p).size();
			for (int i = Sales_Date_List_Length; i < Sale_Period; i++) {
				Sales_Date_List.addItem((String)p.DB.Sale_Period(p).get(i));
				Sales_Date_List_Length++;
			}
		} catch (SQLException sql) {
			// Exception 48
			p.Show_Message("테이블들의 데이터 관계에 오류가 있습니다.\n테이블을 전부 드랍 한 후 다시 시작하세요.","테이블 데이터 오류",1);
		}
	}

	public Register_Frame(POS_Controller p) {
		
		this.p = p;
		this.setLayout(new BorderLayout());

		Customer_Enroll.addActionListener(p);
		Customer_Search.addActionListener(p);
		Staff_Enroll.addActionListener(p);
		Staff_Search.addActionListener(p);
		Menu_Enroll.addActionListener(p);
		Menu_Search.addActionListener(p);
		Sales_Date_List.addActionListener(p);

		TitledBorder Frame_Name = new TitledBorder("등록/조회");
		Frame_Name.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		Frame_Name.setTitleFont(new Font("SansSerif", 1, 20));

		Customer_Name.setBounds(25, 10, 100, 20);
		Customer_Name_Input.setBounds(15, 30, 100, 30);
		Customer_Enroll.setBounds(145, 30, 100, 30);
		Customer_Search.setBounds(250, 30, 100, 30);
		Customer_Information.setBounds(8, 80, 350, 240);
		Customer_Information.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		Customer_Information.setEditable(false);
		Customer_Panel.setBounds(405, 430, 400, 400);
		Customer_Panel.setLayout(null);
		Customer_Panel.add(Customer_Name);
		Customer_Panel.add(Customer_Name_Input);
		Customer_Panel.add(Customer_Enroll);
		Customer_Panel.add(Customer_Search);
		Customer_Panel.add(Customer_Information);

		Sales_Date.setBounds(25, 30, 100, 30);
		Sales_Date_List.setBounds(60, 30, 150, 30);
		Sale_Information.setBounds(8, 80, 350, 240);
		Sale_Information.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		Sale_Information.setEditable(false);
		Sale_Panel.setBounds(405, 430, 400, 400);
		Sale_Panel.setLayout(null);
		Sale_Panel.add(Sales_Date);
		Sale_Panel.add(Sales_Date_List);
		Sale_Panel.add(Sale_Information);

		Staff_Name.setBounds(25, 10, 100, 20);
		Staff_Name_Input.setBounds(15, 30, 100, 30);
		Staff_Enroll.setBounds(145, 30, 100, 30);
		Staff_Search.setBounds(250, 30, 100, 30);
		Staff_Information.setBounds(8, 80, 350, 240);
		Staff_Information.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		Staff_Information.setEditable(false);
		Staff_Panel.setBounds(405, 430, 400, 400);
		Staff_Panel.setLayout(null);
		Staff_Panel.add(Staff_Name);
		Staff_Panel.add(Staff_Name_Input);
		Staff_Panel.add(Staff_Enroll);
		Staff_Panel.add(Staff_Search);
		Staff_Panel.add(Staff_Information);

		Menu_Name.setBounds(25, 10, 100, 20);
		Menu_Name_Input.setBounds(15, 30, 100, 30);
		Menu_Enroll.setBounds(145, 30, 100, 30);
		Menu_Search.setBounds(250, 30, 100, 30);
		Menu_Information.setBounds(8, 80, 350, 240);
		Menu_Information.setBorder(new LineBorder(Color.DARK_GRAY, 3));
		Menu_Information.setEditable(false);
		Menu_Panel.setBounds(405, 430, 400, 400);
		Menu_Panel.setLayout(null);
		Menu_Panel.add(Menu_Name);
		Menu_Panel.add(Menu_Name_Input);
		Menu_Panel.add(Menu_Enroll);
		Menu_Panel.add(Menu_Search);
		Menu_Panel.add(Menu_Information);

		Register_Frame.addTab("고객", Customer_Panel);
		Register_Frame.addTab("매출", Sale_Panel);
		Register_Frame.addTab("직원", Staff_Panel);
		Register_Frame.addTab("메뉴", Menu_Panel);
		Register_Frame.setTabPlacement(JTabbedPane.TOP);
		
		Scroll_Sale_Information.setViewportView(Sale_Information);
		Scroll_Sale_Information.setBounds(8, 80, 350, 240);
		Sale_Panel.add(Scroll_Sale_Information);

		this.add(Register_Frame);
		this.setBounds(405, 430, 400, 400);
		this.setBorder(Frame_Name);

	}
}
