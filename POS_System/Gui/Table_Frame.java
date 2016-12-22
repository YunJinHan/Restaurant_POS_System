
// DataBase - TermProject
// GUI - Table_Frame
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import POS_System.POS_Controller;

public class Table_Frame extends JPanel {

	public JPanel Table_List;
	public Table[] Table_Box = new Table[20];

	public class Table {
		public JButton Table_Button;
		public int Table_Number;
		public int Total_Price;
		public String Order_List;
		public int[] Count_Menu = new int[20];
		public int Color; // 0 white 1 yellow

		Table(int Table_Number) {
			this.Table_Number = Table_Number;
			Table_Button = new JButton(Integer.toString(Table_Number));
			Table_Button.setFont(new Font("SansSerif", 1, 15));
			Order_List = " ============ 현재 주문 리스트 ============\n";
			for (int i = 0; i < 20; i++) {
				Count_Menu[i] = 0;
			}
			Change_Color_To_White();
		}
		
		public void Initialize(){
			Order_List = " ============ 현재 주문 리스트 ============\n";
			for (int i = 0; i < 20; i++) {
				Count_Menu[i] = 0;
			}
			Total_Price = 0;
			Change_Color_To_White();
		}

		public void Change_Color_To_White() {
			this.Table_Button.setBackground(java.awt.Color.WHITE);
			this.Color = 0;
		}

		public void Change_Color_To_Yellow() {
			this.Table_Button.setBackground(java.awt.Color.YELLOW);
			this.Color = 1;
		}
	}

	public Table_Frame(POS_Controller p) {
		TitledBorder Frame_Name = new TitledBorder("테이블 현황");
		Frame_Name.setBorder(new LineBorder(Color.DARK_GRAY, 2));
		Frame_Name.setTitleFont(new Font("SansSerif", 1, 20));

		Table_List = new JPanel();
		Table_List.setBounds(17, 33, 350, 250);
		Table_List.setLayout(new GridLayout(4, 5));
		for (int i = 0; i < 20; i++) {
			Table_Box[i] = new Table(i + 1);
			Table_Box[i].Table_Button.addActionListener(p);
			Table_List.add(Table_Box[i].Table_Button);
		}
		this.add(Table_List);
		this.setLayout(null);
		this.setBounds(15, 130, 385, 300);
		this.setBorder(Frame_Name);
	}
}
