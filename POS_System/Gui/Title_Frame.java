
// DataBase - TermProject
// GUI - Title_Frame
// ��ǻ�� ���а�
// �й� : 2012036901
// �̸� : �� �� ��
// 2016.05.23

package Gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import POS_System.POS_Controller;

public class Title_Frame extends JPanel {
	public JLabel Title_Label = new JLabel("�Ĵ� �ֹ� ����", SwingConstants.CENTER);

	public Title_Frame(POS_Controller p) {

		Title_Label.setBounds(85, 35, 645, 60);
		Title_Label.setFont(new Font("SansSerif", 1, 60));
		this.add(Title_Label);

		this.setLayout(null);
		this.setBounds(5, 5, 810, 120);
		this.setBorder(new LineBorder(Color.BLACK, 3));
	}
}
