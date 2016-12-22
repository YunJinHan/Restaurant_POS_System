
// DataBase - TermProject
// GUI - Menu_Frame
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import POS_System.POS_Controller;

public class Menu_Frame extends JPanel {

	public Menu MenuList[] = new Menu[20];

	public class Menu {
		public String Menu_Name;
		public int Menu_Price;
		public JButton Menu_Button;

		public Menu() {
			Menu_Button = new JButton("");
			Menu_Button.setFont(new Font("SansSerif", 1, 15));
		}
		
		public Menu(String name) {
			this.Menu_Name = name;
			Menu_Button = new JButton(Menu_Name);
			Menu_Button.setFont(new Font("SansSerif", 1, 15));
		}
	}

	public Menu_Frame(POS_Controller p) {
		TitledBorder Frame_Name = new TitledBorder("메뉴");
		Frame_Name.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		Frame_Name.setTitleFont(new Font("SansSerif", 1, 20));

		this.setLayout(new GridLayout(10, 2));
		this.setBounds(15, 430, 385, 400);
		this.setBorder(Frame_Name);
	
		try {
			p.DB.CreateMenuList(this, p);
		} catch (SQLException e) {
			// Exception 46
			p.Show_Message("메뉴 추가 도중 에러가 발생하였습니다.", "메뉴 추가 에러", 1);
		} catch (IOException ioe){
			// Exception 47
			p.Show_Message("메뉴 추가 도중 파일 형식 에러가 발생하였습니다.", "메뉴 추가 에러", 1);
		}
	}
}
