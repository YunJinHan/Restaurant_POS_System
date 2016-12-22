package Gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import POS_System.POS_Controller;

public class Main_Frame {

	public JFrame Main_Frame;
	public Menu_Frame Menu;
	public Table_Frame Table;
	public Order_Frame Order;
	public Register_Frame Register;
	public Tab_Frame Tab;
	public Title_Frame Title;

	public Main_Frame(POS_Controller p) {

		Main_Frame = new JFrame();
		Menu = new Menu_Frame(p);
		Table = new Table_Frame(p);
		Order = new Order_Frame(p);
		Register = new Register_Frame(p);
		Tab = new Tab_Frame(p);
		Title = new Title_Frame(p);

		Main_Frame.add(Menu);
		Main_Frame.add(Table);
		Main_Frame.add(Order);
		Main_Frame.add(Register);
		Main_Frame.setJMenuBar(Tab);
		Main_Frame.add(Title);

		Main_Frame.setLayout(null);
		Main_Frame.setResizable(false);
		Main_Frame.setVisible(false);
		Main_Frame.setTitle("식당 관리 프로그램");
		Main_Frame.setBounds(300, 0, 820, 900);
		Main_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
