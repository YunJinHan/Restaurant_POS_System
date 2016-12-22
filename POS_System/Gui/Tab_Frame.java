
// DataBase - TermProject
// GUI - Tab_Frame
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package Gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import POS_System.POS_Controller;

public class Tab_Frame extends JMenuBar {

	public JMenu Tab_Menu = new JMenu("Menu");
	public JMenuItem File_Open = new JMenuItem("Open");
	public JMenuItem Login = new JMenuItem("Login / Logout");

	public Tab_Frame(POS_Controller p) {

		File_Open.addActionListener(p);
		Login.addActionListener(p);

		Tab_Menu.add(File_Open);
		Tab_Menu.add(Login);
		this.add(Tab_Menu);

	}
}
