
// DataBase - TermProject
// POS_System - DB_Controller
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package POS_System;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.plaf.synth.SynthSeparatorUI;

import Gui.Menu_Frame;

public class DB_Controller {

	protected Connection DB_Connection;
	private String DB_ID;
	private String DB_PassWord;
	private int Customer_ID = 1000;
	private int Staff_ID = 5000;

	public DB_Controller(String DB_ID, String DB_PawwWord, POS_Controller p) {
		// TODO Auto-generated constructor stub
		this.DB_ID = DB_ID;
		this.DB_PassWord = DB_PawwWord;
		Connect_DataBase(p);
	}

	// DB_Controller 생성자.
	public void Connect_DataBase(POS_Controller p) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			DB_Connection = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", DB_ID, DB_PassWord);
			DB_Connection.commit();
			p.Show_Message("데이터베이스 연결을 성공하였습니다.", "데이터베이스 연결", 0);
		} catch (SQLException e) {
			// Exception 39 - 데이터베이스 연결 실패
			p.Show_Message("데이터베이스 연결을 실패하였습니다.\n다시 시작해주세요.", "데이터베이스 연결 오류", 1);
			System.exit(0);
		} catch (Exception e) {
			// Exception 40 - 데이터베이스 연결 실패
			p.Show_Message("데이터베이스 연결을 실패하였습니다.\n다시 시작해주세요.", "데이터베이스 연결 오류", 1);
			System.exit(0);
		}
	}

	// 데이터 베이스에 연결해주는 메소드.
	public boolean Is_Staff_Table() throws SQLException {
		String sqlStr = "select count(*) from staff";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		int cnt = 0;
		while (rs.next()) {
			cnt = Integer.parseInt(rs.getString("count(*)"));
		}
		stmt.close();
		rs.close();
		if (cnt == 0) {
			return false;
		}
		return true;
	}

	// 해당 데이터 베이스에 직원 테이블이 있는지 없는지 혹은 직원 정보가 있는지 없는지 판별해줌.
	public void DropTable() throws SQLException {
		PreparedStatement stmt;
		String sqlStr = "drop table sale";
		stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		sqlStr = "drop table menu";
		stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		sqlStr = "drop table customer";
		stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		sqlStr = "drop table staff";
		stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		Customer_ID = 1000;
		Staff_ID = 5000;
		DB_Connection.commit();
	}

	// 해당 데이터 베이스에서 사용하고 있는 4개의 테이블을 드랍해줌.
	public void CreateTable(File file, POS_Controller p) throws SQLException, IOException {
		PreparedStatement stmt;
		String Staff_Table = "create table staff (staff_name varchar2(20),staff_ID number(4),"
				+ "staff_rank varchar2(10), staff_record number(20), primary key (staff_ID))";
		stmt = DB_Connection.prepareStatement(Staff_Table);
		stmt.executeQuery();
		stmt.close();
		String Customer_Table = "create table customer (customer_name varchar2(20),customer_ID number(4),"
				+ "customer_birth number(4),customer_tel	number(20),customer_class varchar2(10),customer_total_pay number(20),primary key (customer_ID))";
		stmt = DB_Connection.prepareStatement(Customer_Table);
		stmt.executeQuery();
		stmt.close();
		String Menu_Table = "create table menu (menu_number number(2),menu_name varchar2(25),menu_price number(6),"
				+ "menu_sale_count number(10),primary key (menu_name))";
		stmt = DB_Connection.prepareStatement(Menu_Table);
		stmt.executeQuery();
		stmt.close();
		String Sale_Table = "create table sale (menu_name varchar2(25),menu_price number(6),"
				+ "sale_day varchar2(10),customer_ID	number(4),staff_ID number(4),"
				+ "foreign key (staff_ID) references staff, foreign key (customer_ID) references customer,foreign key (menu_name) references menu)";
		stmt = DB_Connection.prepareStatement(Sale_Table);
		stmt.executeQuery();
		stmt.close();
		DB_Connection.commit();

		File Table_File = new File(file.getAbsolutePath());
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(Table_File));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			// Exception 41 - 데이터 파일 형식 오류
			p.Show_Message("데이터 파일 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
			System.exit(0);
		} catch (Exception e) {
			// Exception 42 - 데이터 파일 형식 오류
			p.Show_Message("테이터 파일 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
			System.exit(0);
		}

		String sqlStr = null;
		String Data_Text = sb.toString();
		StringTokenizer token = new StringTokenizer(Data_Text, "\t\n");
		int Next_token_Length = Integer.parseInt(token.nextToken());
		for (int i = 0; i < Next_token_Length; i++, Customer_ID++) {
			sqlStr = "insert into customer values ('" + token.nextToken() + "','" + Customer_ID + "','"
					+ token.nextToken() + "','" + token.nextToken() + "',";
			String Rank_Check = token.nextToken();
			if (Rank_Check.equals("Normal")) {
				sqlStr += "'Normal','0')";
			} else if (Rank_Check.equals("Bronze")) {
				sqlStr += "'Bronze','300000')";
			} else if (Rank_Check.equals("Silver")) {
				sqlStr += "'Silver','500000')";
			} else if (Rank_Check.equals("Gold")) {
				sqlStr += "'Gold','1000000')";
			}
			stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
			DB_Connection.commit();
		}

		Next_token_Length = Integer.parseInt(token.nextToken());
		for (int i = 0; i < Next_token_Length; i++, Staff_ID++) {
			sqlStr = "insert into staff values ('" + token.nextToken() + "','" + Staff_ID + "','" + token.nextToken()
					+ "','0')";
			stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
			DB_Connection.commit();
		}

		Next_token_Length = Integer.parseInt(token.nextToken());
		for (int i = 0; i < Next_token_Length; i++) {
			sqlStr = "insert into menu values ('" + i + "','" + token.nextToken() + "','" + token.nextToken()
					+ "','0')";
			stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
			DB_Connection.commit();
		}
		p.Show_Message("데이터 파일 저장 성공!", "데이터 불러오기 성공", 0);
	}

	// 새롭게 테이블을 만든후 파일에서 텍스트를 불러와 해당 정보를 각각 테이블에 insert 시켜줌.
	public void CreateMenuList(Menu_Frame mf, POS_Controller p) throws SQLException, IOException {
		mf.removeAll();
		String sqlStr = "select menu_name,menu_price from menu";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			mf.MenuList[i] = mf.new Menu(rs.getString("menu_name"));
			mf.MenuList[i].Menu_Price = Integer.parseInt(rs.getString("menu_price"));
			mf.MenuList[i].Menu_Button.addActionListener(p);
			mf.add(mf.MenuList[i].Menu_Button);
			i++;
		}
		p.setMenu_Count(i);
		stmt.close();
		rs.close();
		for (; i < 20; i++) {
			mf.MenuList[i] = mf.new Menu();
			mf.add(mf.MenuList[i].Menu_Button);
		}
	}

	// 메뉴 버튼 생성 메소드.
	public void Login(String name, String ID, POS_Controller p) throws SQLException {
		String sqlStr = "select staff_rank from staff where staff_name = '" + name + "' and staff_ID =" + ID;
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		String rank = "";
		while (rs.next()) {
			rank = rs.getString("staff_rank");
		}
		stmt.close();
		rs.close();
		if (rank.equals("Staff") || rank.equals("Supervisor")) {
			p.Show_Message("로그인 성공!", "로그인 성공", 0);
			p.setStaff_ID(Integer.parseInt(ID));
			p.setStaff_Name(name);
			if (rank.equals("Supervisor")) {
				p.setSupervisor_Login_Judge(1);
			} else {
				p.setSupervisor_Login_Judge(0);
			}
		} else {
			// Exception 43
			p.Show_Message("직원정보에 없거나 올바르지 않은 정보를 입력하셨습니다.", "로그인 에러", 1);
			p.setStaff_ID(0);
			p.setStaff_Name(null);
		}
	}

	// 로그인 메소드.
	public String Find_Cutomer_Class(String Customer_Name) throws SQLException {
		String sqlStr = "select customer_class from customer where customer_name = '" + Customer_Name + "'";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		String Class = null;
		while (rs.next()) {
			Class = rs.getString("customer_class");
		}
		if (!Customer_Name.equals("") && Class == null) {
			// Exception 19-1 - 해당 고객 이름 존재하지 않음
			throw new SQLException();
		}
		return Class;
	}

	// 해당 이름을 가진 고객의 등급을 리턴해줌.
	public void Order_Payment(String date, int staff_ID, String Customer_Name, int Payment, int[] Count_Menu,
			int Menu_Count) throws SQLException {
		PreparedStatement stmt;
		ResultSet rs;
		String sqlStr = "update staff set staff_record = staff_record + " + Payment + "where staff_ID = " + staff_ID;
		stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		// staff_recore update

		int Customer_id = 0;
		String Customer_class = null;
		if (!Customer_Name.equals("")) {
			sqlStr = "update customer set customer_total_pay = customer_total_pay + " + Payment
					+ "where customer_name ='" + Customer_Name + "'";
			stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
			// customer_total_pay update

			sqlStr = "select customer_ID from customer where customer_name = '" + Customer_Name + "'";
			stmt = DB_Connection.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Customer_id = Integer.parseInt(rs.getString("customer_ID"));
			}
			stmt.close();
			rs.close();
			// Find customer_id

			sqlStr = "select customer_class from customer where customer_name = '" + Customer_Name + "'";
			stmt = DB_Connection.prepareStatement(sqlStr);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Customer_class = rs.getString("customer_class");
			}
			stmt.close();
			rs.close();
			// Find customer_class
		} else if (Customer_Name.equals("")) {
			sqlStr = "update customer set customer_total_pay = customer_total_pay + " + Payment
					+ "where customer_name ='비회원'";
			stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
			// customer_total_pay update
		}
		for (int i = 0; i < Menu_Count; i++) {
			while (Count_Menu[i] != 0) {
				sqlStr = "update menu set menu_sale_count = menu_sale_count + 1 where menu_number = " + i;
				stmt = DB_Connection.prepareStatement(sqlStr);
				stmt.executeQuery();
				stmt.close();
				// menu_count update

				sqlStr = "select menu_name,menu_price from menu where menu_number =" + i;
				stmt = DB_Connection.prepareStatement(sqlStr);
				rs = stmt.executeQuery();
				String menu_name = null;
				int menu_price = 0;
				while (rs.next()) {
					menu_name = rs.getString("menu_name");
					menu_price = rs.getShort("menu_price");
				}
				stmt.close();
				rs.close();

				double DiscountPer = 1;
				if (Customer_class != null) {
					if (Customer_class.equals("Gold")) {
						DiscountPer = 0.7;
					} else if (Customer_class.equals("Sliver")) {
						DiscountPer = 0.8;
					} else if (Customer_class.equals("Bronze")) {
						DiscountPer = 0.9;
					}
				}
				menu_price *= DiscountPer;
				if (Customer_id != 0) {
					sqlStr = "insert into sale values ('" + menu_name + "'," + menu_price + ",'" + date + "',"
							+ Customer_id + "," + staff_ID + ")";
				} else {
					sqlStr = "insert into sale values ('" + menu_name + "'," + menu_price + ",'" + date + "',1005,"
							+ staff_ID + ")";
				}
				stmt = DB_Connection.prepareStatement(sqlStr);
				stmt.executeQuery();
				stmt.close();
				// insert sale information
				Count_Menu[i]--;
			}
		}
		if (Customer_id != 0) {
			String sqlStr1 = "update customer set customer_class = 'Bronze' where customer_name ='" + Customer_Name
					+ "' and customer_total_pay >= 300000";
			PreparedStatement stmt1 = DB_Connection.prepareStatement(sqlStr1);
			stmt1.executeQuery();
			stmt1.close();
			String sqlStr2 = "update customer set customer_class = 'Sliver' where customer_name ='" + Customer_Name
					+ "' and customer_total_pay >= 500000";
			PreparedStatement stmt2 = DB_Connection.prepareStatement(sqlStr2);
			stmt2.executeQuery();
			stmt2.close();
			String sqlStr3 = "update customer set customer_class = 'Gold' where customer_name ='" + Customer_Name
					+ "' and customer_total_pay >= 1000000";
			PreparedStatement stmt3 = DB_Connection.prepareStatement(sqlStr3);
			stmt3.executeQuery();
			stmt3.close();
			// customer_class update
		}
	}

	// 결제를 하였을때 필요한 정보들을 받아 데이터 베이스 상에 해당 필요 정보들을 insert 해줌.
	public Vector<String> Sale_Period(POS_Controller p) throws SQLException {
		Vector<String> Sale_Period = new Vector<String>();
		String sqlStr = "select sale_day from sale group by sale_day order by sale_day";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Sale_Period.add((String) rs.getString("sale_day"));
		}
		stmt.close();
		rs.close();
		return Sale_Period;
	}

	// 매출 조회 콤보 박스를 생성해줌.
	public String SearchCustomer(String Customer_Name) throws SQLException {
		String result = null;
		String sqlStr = "select customer_name,customer_ID,customer_birth,customer_tel,customer_class,customer_total_pay from customer where customer_name = '"
				+ Customer_Name + "'";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = " 고 객 명 : " + Customer_Name + "\n";
			result += " 고 객 ID : " + rs.getString("customer_ID") + "\n";
			result += " 생    일 : " + rs.getString("customer_birth") + "\n";
			result += " 전화번호 : " + rs.getString("customer_tel") + "\n";
			result += " 고객등급 : " + rs.getString("customer_class") + "\n";
			result += " 총 구매금액 : " + rs.getString("customer_total_pay") + "\n";
		}
		stmt.close();
		rs.close();
		return result;
	}

	// 고객 조회 메소드.
	public String SearchStaff(String Staff_Name) throws SQLException {
		String result = null;
		String sqlStr = "select staff_rank,staff_record from staff where staff_name ='" + Staff_Name + "'";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = " 직원명 : " + Staff_Name + "\n";
			result += " 직  급 : " + rs.getString("staff_rank") + "\n";
			result += " 총실적 : " + rs.getString("staff_record") + "\n";
		}
		stmt.close();
		rs.close();
		return result;
	}

	// 직원 조회 메소드.
	public String SearchMenu(String Menu_Name) throws SQLException {
		String result = null;
		String sqlStr = "select menu_name,menu_price from menu where menu_name ='" + Menu_Name + "'";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = " 메뉴명 : " + rs.getString("menu_name") + "\n";
			result += " 가  격 : " + rs.getString("menu_price") + "\n";
		}
		stmt.close();
		rs.close();
		return result;
	}

	// 메뉴 조회 메소드.
	public String SearchSale(String Sale_Date) throws SQLException {
		String result = null;
		String sqlStr = "select sum(menu_price) from sale where sale_day ='" + Sale_Date + "'";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			result = "  일 매출 : " + rs.getString("sum(menu_price)") + "\n";
		}
		stmt.close();
		rs.close();
		if (result == null) {
			result = " 일 매출 : 0\n";
		}
		result += "----------------------------------------------\n";
		sqlStr = "select menu_name from menu where menu_sale_count >= all(select menu_sale_count from menu)";
		stmt = DB_Connection.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		while (rs.next()) {
			result += " 가장 많이 팔린 메뉴 : " + rs.getString("menu_name") + "\n";
		}
		stmt.close();
		rs.close();
		sqlStr = "select menu_name from (select * from menu where menu_sale_count <= all (select menu_sale_count from menu where menu_sale_count > 0)) where menu_sale_count > 0";
		stmt = DB_Connection.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		while (rs.next()) {
			result += " 가장 적게 팔린 메뉴 : " + rs.getString("menu_name") + "\n";
		}
		result += "----------------------------------------------\n";
		stmt.close();
		rs.close();
		sqlStr = "select sum(menu_price) from sale where sale_day <= '" + Sale_Date + "'";
		stmt = DB_Connection.prepareStatement(sqlStr);
		rs = stmt.executeQuery();
		while (rs.next()) {
			result += " 누적 매출 : " + rs.getString("sum(menu_price)") + "\n";
		}
		stmt.close();
		rs.close();
		return result;
	}

	// 매출 조회 메소드.
	public void Insert_Customer(String Customer_Name, String Customer_Birth, String Customer_Tel) throws SQLException {
		String ci = null;
		String sqlStr1 = "select customer_id from customer where customer_name ='" + Customer_Name + "'";
		PreparedStatement stmt1 = DB_Connection.prepareStatement(sqlStr1);
		ResultSet rs = stmt1.executeQuery();
		while (rs.next()) {
			ci = rs.getString("customer_id");
		}
		stmt1.close();
		rs.close();
		if (ci != null) {
			// Exception 30 - 1 - 이미 등록되어 있는 고객을 등록할때.
			throw new SQLException();
		} else {
			String sqlStr = "insert into customer values ('" + Customer_Name + "'," + Customer_ID + ",'"
					+ Customer_Birth + "','" + Customer_Tel + "','Normal'," + 0 + ")";
			Customer_ID++;
			PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
		}
	}

	// 고객 등록 메소드.
	public void Insert_Staff(String Staff_Name, String Staff_Rank) throws SQLException {
		String si = null;
		String sqlStr1 = "select staff_id from staff where staff_name ='" + Staff_Name + "'";
		PreparedStatement stmt1 = DB_Connection.prepareStatement(sqlStr1);
		ResultSet rs = stmt1.executeQuery();
		while (rs.next()) {
			si = rs.getString("staff_id");
		}
		stmt1.close();
		rs.close();
		if (si != null) {
			// Exception 32 - 1 - 이미 등록되어 있는 직원을 등록할때.
			throw new SQLException();
		} else {
			String sqlStr = "insert into staff values ('" + Staff_Name + "'," + Staff_ID + ",'" + Staff_Rank + "'," + 0
					+ ")";
			Staff_ID++;
			PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
			stmt.executeQuery();
			stmt.close();
		}
	}

	// 직원 등록 메소드.
	public void Insert_Menu(String Menu_Name, String Menu_Price, POS_Controller p) throws SQLException {
		String sqlStr = "insert into menu values (" + p.getMenu_Count() + ",'" + Menu_Name + "','" + Menu_Price + "',"
				+ 0 + ")";
		PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
		stmt.executeQuery();
		stmt.close();
		p.setMenu_Count(p.getMenu_Count() + 1);
	}

	// 메뉴 등록 메소드.
	public void Get_Customer_Max_ID(POS_Controller p) {
		try {
			String sqlStr = "select max(customer_id) from customer";
			PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Customer_ID = Integer.parseInt(rs.getString("max(customer_id)")) + 1;
			}
			stmt.close();
			rs.close();
		} catch (SQLException sql) {
			// Exception 44
			p.Show_Message("고객 ID 데이터 처리 중 에러가 발생하였습니다.", "데이터 처리 에러", 1);
		}
	}

	// 현재 가장 큰 고객 id 를 가져와 그 다음 부여될 고객 id 값을 정해줌.
	public void Get_Staff_Max_ID(POS_Controller p) {
		try {
			String sqlStr = "select max(staff_id) from staff";
			PreparedStatement stmt = DB_Connection.prepareStatement(sqlStr);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Staff_ID = Integer.parseInt(rs.getString("max(staff_id)")) + 1;
			}
			stmt.close();
			rs.close();
		} catch (SQLException sql) {
			// Exception 45
			p.Show_Message("직원 ID 데이터 처리 중 에러가 발생하였습니다. ", "데이터 처리 에러", 1);
		}
	}
	// 현재 가장 큰 직원 id 를 가져와 그 다음 부여될 직원 id 값을 정해줌.
}
