
// DataBase - TermProject
// POS_System - POS_Controller
// 컴퓨터 공학과
// 학번 : 2012036901
// 이름 : 윤 진 한
// 2016.05.23

package POS_System;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import Gui.Customer_Enroll_Window;
import Gui.Login_Window;
import Gui.Main_Frame;
import Gui.Menu_Enroll_Window;
import Gui.Staff_Enroll_Window;
import Gui.Table_Frame.Table;

public class POS_Controller implements ActionListener {

	private String DB_ID;
	private String DB_PassWord;
	private String Staff_Name = null;
	private int Staff_ID = 0;
	private int Supervisor_Login_Judge = 0;
	private int Menu_Count = 0;

	private String line = "  -----------------------------------------------------------------  \n\n  총 합계 ";
	private String Current_Order_Content;
	private Table Current_Table;
	private int[] Current_Total_Menu_Count = new int[20];
	private int Current_Total_Pay;

	private String Customer_Name = "";
	private String Customer_Class = "";
	private int Final_Payment = 0;

	private Main_Frame Main;
	private Login_Window Login;
	private Customer_Enroll_Window Enroll_Customer;
	private Menu_Enroll_Window Enroll_Menu;
	private Staff_Enroll_Window Enroll_Staff;

	public DB_Controller DB;

	public String getStaff_Name() {
		return Staff_Name;
	}

	// 현재 로그인 중인 직원의 이름을 리턴해줌.
	public void setStaff_Name(String staff_Name) {
		Staff_Name = staff_Name;
	}
	// 현재 로그인 중인 직원의 이름을 Staff_Name 변수에 저장해줌.

	public int getStaff_ID() {
		return Staff_ID;
	}
	// 현재 로그인 중인 직원의 아이디를 리턴해줌.

	public void setStaff_ID(int staff_ID) {
		Staff_ID = staff_ID;
	}
	// 현재 로그인 중인 직원의 아이디를 Staff_ID 변수에 저장해줌.

	public int getSupervisor_Login_Judge() {
		return Supervisor_Login_Judge;
	}
	// 현재 로그인 중인 직원이 관리자인지 아닌지 판별 해주는 변수를 리턴해줌.

	public void setSupervisor_Login_Judge(int supervisor_Login_Judge) {
		Supervisor_Login_Judge = supervisor_Login_Judge;
	}
	// 현재 로그인 중인 직원이 관리자인지 아닌지 판별 해주는 변수의 값을 정해줌.

	public int getMenu_Count() {
		return Menu_Count;
	}
	// 현재 메뉴의 갯수를 리턴해줌.

	public void setMenu_Count(int menu_Count) {
		Menu_Count = menu_Count;
	}
	// 현재 메뉴의 갯수를 정해줌.

	public void Initialize_Current_Order_Information(int k) {
		Current_Order_Content = "";
		Current_Total_Pay = 0;
		for (int i = 0; i < 20; i++) {
			Current_Total_Menu_Count[i] = 0;
		}
		if (k==1 || k==2){
			Main.Order.Order_Field.setText("\n\n\n\n\n\n  ========== 테이블을 선택하세요 ==========");
			if (k==2){
				Current_Table = null;
			}
		}
	}
	// 현재 선택한 테이블의 정보들을 가진 변수들의 값을 초기화 시켜줌.

	public void Initialize_Register() {
		Main.Register.Customer_Information.setText("");
		Main.Register.Customer_Name_Input.setText("");
		Main.Register.Sale_Information.setText("");
		Main.Register.Staff_Information.setText("");
		Main.Register.Staff_Name_Input.setText("");
		Main.Register.Menu_Information.setText("");
		Main.Register.Menu_Name_Input.setText("");
	}
	// 등록 / 조회 프레임에서 해당 텍스트 입력 필드를 초기화 시켜줌.

	public POS_Controller(String DB_ID, String DB_Password) {
		this.DB_ID = DB_ID;
		this.DB_PassWord = DB_Password;
		this.DB = new DB_Controller(this.DB_ID, this.DB_PassWord, this);
		int check = 0;
		try {
			if (!DB.Is_Staff_Table()) {
				// Staff 테이블이 있으나 등록된 직원이 없을 경우.
				check = 1;
			}
		} catch (SQLException sql) {
			// Exception 1
			// Staff 테이블이 없을 경우.
			check = 1;
		} finally {
			if (check == 1) {
				Show_Message("데이터베이스 Staff 정보가 없습니다.\n데이터베이스 파일을 선택해주세요.", "데이터베이스 테이블", 1);
				File file = File_Open();
				if (file == null) {
					// Exception 2
					Show_Message("파일을 선택하지 않으셨습니다. 다시 시작해 주세요.", "파일 선택 취소", 1);
					System.exit(0);
				} else {
					try {
						DB.DropTable();
					} catch (SQLException sql) {
						// Exception 3 - 초기에 Staff 테이블만 없을 경우 에러가 날수 있지만 그 경우 모든
						// 테이블을
						// 드랍하여 문제 없다.
					}
					try {
						DB.CreateTable(file, this);
					} catch (SQLException sql) {
						// Exception 4
						Show_Message("테이블 생성 중 에러로 다시 시작해주세요. ( 파일 형식 에러 )", "데이터베이스 처리 에러", 1);
						System.exit(0);
					} catch (IOException ioe) {
						// Exception 5
						Show_Message("테이블 생성 중 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
						System.exit(0);
					} catch (NumberFormatException nfe) {
						// Exception 6
						Show_Message("테이블 생성 중 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
						System.exit(0);
					} catch (NullPointerException npe) {
						// Exception 7
						Show_Message("테이블 생성 중 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
						System.exit(0);
					} catch (NoSuchElementException nsee) {
						// Exception 8
						Show_Message("테이블 생성 중 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
						System.exit(0);
					}
				}
			}
			Login = new Login_Window(this);
			Main = new Main_Frame(this);
			Enroll_Customer = new Customer_Enroll_Window(this);
			Enroll_Menu = new Menu_Enroll_Window(this);
			Enroll_Staff = new Staff_Enroll_Window(this);
			Initialize_Current_Order_Information(1);
			Initialize_Register();
			Main.Register.Create_Sale_Box();
			DB.Get_Customer_Max_ID(this);
			DB.Get_Staff_Max_ID(this);
		}
	}
	// POS_Controller 생성자.

	public void Show_Message(String message1, String message2, int warning) {
		if (warning == 1) {
			JOptionPane.showMessageDialog(null, message1, message2, JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, message1, message2, JOptionPane.DEFAULT_OPTION);
		}
	}
	// 메세지 창 생성 메소드.

	public void NewStart() {
		Main.Main_Frame.setVisible(false);
		Show_Message(Staff_Name + " 님이 로그아웃 되었습니다.", "로그아웃", 0);
		this.DB = new DB_Controller(DB_ID, DB_PassWord, this);
		Login = new Login_Window(this);
		Main = new Main_Frame(this);
		Enroll_Customer = new Customer_Enroll_Window(this);
		Enroll_Menu = new Menu_Enroll_Window(this);
		Enroll_Staff = new Staff_Enroll_Window(this);
		Initialize_Current_Order_Information(2);
		Initialize_Register();
		Main.Register.Create_Sale_Box();
		DB.Get_Customer_Max_ID(this);
		DB.Get_Staff_Max_ID(this);
	}
	// 새로운 시작 시 프레임 초기화 및 변수 재 설정.

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == Main.Tab.Login) {
			NewStart();
			Login.Login_Frame.setVisible(true);
			Main.Main_Frame.setVisible(false);
		} // Tab -> Login

		else if (e.getSource() == Main.Tab.File_Open) {
			File file = File_Open();
			if (file == null) {
				// Exception 2 - 1
				Show_Message("파일을 선택하지 않으셨습니다.", "파일 선택 취소", 1);
			} else {
				try {
					DB.DropTable();
				} catch (SQLException sql) {
					// Exception 3 - 1
					Show_Message("테이블 제거 중 에러 발생.", "데이터베이스 처리 에러", 1);
				}
				try {
					DB.CreateTable(file, this);
				} catch (SQLException sql) {
					// Exception 4 - 1
					Show_Message("테이블 생성 중 에러 발생 ( 파일 형식 에러 )\n다시 시작해주세요.", "데이터베이스 처리 에러", 1);
					System.exit(0);
				} catch (IOException ioe) {
					// Exception 5 - 1
					Show_Message("테이블 생성 중 에러 발생\n다시 시작해주세요.", "데이터베이스 처리 에러", 1);
					System.exit(0);
				} catch (NumberFormatException nfe) {
					// Exception 6 - 1
					Show_Message("테이블 생성 중 에러 발생\n다시 시작해주세요.", "데이터베이스 처리 에러", 1);
					System.exit(0);
				} catch (NullPointerException npe) {
					// Exception 7 - 1
					Show_Message("테이블 생성 중 에러 발생\n다시 시작해주세요.", "데이터베이스 처리 에러", 1);
					System.exit(0);
				} catch (NoSuchElementException nsee) {
					// Exception 8 - 1
					Show_Message("테이블 생성 중 에러로 다시 시작해주세요.", "데이터베이스 처리 에러", 1);
					System.exit(0);
				}
				NewStart();
			}
		} // Tab -> File_Open

		else if (e.getSource() == Login.Login_Button) {
			String Staff_Name = Login.Name_Input.getText();
			String Staff_ID = new String(Login.ID_Input.getPassword());
			Login.Name_Input.setText("");
			Login.ID_Input.setText("");
			try {
				DB.Login(Staff_Name, Staff_ID, this);
			} catch (SQLException sql) {
				// Exception 9
				Show_Message("직원정보에 없거나 올바르지 않은 정보를 입력하셨습니다.", "로그인 에러", 1);
				this.setStaff_Name(null);
				this.setStaff_ID(0);
			} catch (Exception e1) {
				// Exception 10
				Show_Message("직원정보에 없거나 올바르지 않은 정보를 입력하셨습니다.", "로그인 에러", 1);
				this.setStaff_Name(null);
				this.setStaff_ID(0);
			} finally {
				if (this.Staff_Name != null && this.Staff_ID != 0) {
					Login.Login_Frame.setVisible(false);
					Main.Main_Frame.setVisible(true);
				}
			}
			Initialize_Register();
		} // Login -> Login Button

		else if (e.getSource() == Main.Order.Order) {
			if (Current_Table == null) {
				// Exception 11
				Show_Message("테이블을 선택하세요.", "테이블 오류", 1);
			} else if (Current_Total_Pay != 0) {
				try {
					Current_Table.Total_Price += Current_Total_Pay;
					Current_Table.Order_List += Current_Order_Content;
					for (int i = 0; i < Menu_Count; i++) {
						Current_Table.Count_Menu[i] += Current_Total_Menu_Count[i];
					}
					Current_Table.Change_Color_To_Yellow();
					Main.Order.Order_Field.setText(Current_Table.Order_List + line + Current_Table.Total_Price);
					Main.Order.Customer_Name_Input.setText("");
					Initialize_Current_Order_Information(0);
					Show_Message("주문이 완료되었습니다.", "주문 완료", 0);
				} catch (NullPointerException npe) {
					// Exception 12
					Show_Message("선택된 테이블이 없습니다.", "주문 에러", 1);
				}
			} else {
				// Exception 13
				Show_Message("선택하신 메뉴 내역이 없습니다.", "주문 오류", 1);
			}
		} // Order -> Order Button

		else if (e.getSource() == Main.Order.Cancel) {
			if (Current_Table == null) {
				// Exception 14
				Show_Message("테이블을 선택하세요.", "테이블 오류", 1);
			} else if (Current_Table.Total_Price != 0) {
				try {
					Current_Table.Total_Price = 0;
					Current_Table.Order_List = " ============ 현재 주문 리스트 ============\n";
					for (int i = 0; i < Menu_Count; i++) {
						Current_Table.Count_Menu[i] = 0;
					}
					Current_Table.Change_Color_To_White();
					Main.Order.Order_Field.setText(Current_Table.Order_List + line + Current_Table.Total_Price);
					Main.Order.Customer_Name_Input.setText("");
					Initialize_Current_Order_Information(0);
					Show_Message("현재 테이블의 모든 주문이 취소되었습니다.", "주문 취소", 0);
				} catch (NullPointerException npe) {
					// Exception 15
					Show_Message("선택된 테이블이 없습니다.", "취소 에러", 1);
				}
			} else {
				// Exception 16
				Show_Message("주문하신 내역이 없습니다.", "취소 오류", 1);
			}
		} // Order -> Cancel Button

		else if (e.getSource() == Main.Order.Payment) {
			if (Current_Table == null) {
				// Exception 17
				Show_Message("테이블을 선택하세요.", "테이블 오류", 1);
			} else if (Current_Table.Color == 1) {
				SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
				String date = today.format(new Date());
				Customer_Name = Main.Order.Customer_Name_Input.getText();
				Customer_Name = Customer_Name.trim();
				try {
					Customer_Class = DB.Find_Cutomer_Class(Customer_Name);
					if (Customer_Class == null) {
						Final_Payment = Current_Table.Total_Price;
					} else if (Customer_Class.equals("Gold")) {
						Final_Payment = (int) (Current_Table.Total_Price * 0.7);
					} else if (Customer_Class.equals("Sliver")) {
						Final_Payment = (int) (Current_Table.Total_Price * 0.8);
					} else if (Customer_Class.equals("Bronze")) {
						Final_Payment = (int) (Current_Table.Total_Price * 0.9);
					} else {
						Final_Payment = Current_Table.Total_Price;
					}
					try {
						DB.Order_Payment(date, Staff_ID, Customer_Name, Final_Payment, Current_Table.Count_Menu,
								Menu_Count);
						Current_Table.Initialize();
						Current_Table.Total_Price = 0;
						Current_Table.Order_List = " ============ 현재 주문 리스트 ============\n";
						for (int i = 0; i < Menu_Count; i++) {
							Current_Table.Count_Menu[i] = 0;
						}
						Current_Table.Change_Color_To_White();
						Main.Order.Order_Field.setText(Current_Table.Order_List + line + Current_Table.Total_Price);
						Main.Order.Customer_Name_Input.setText("");
						Initialize_Current_Order_Information(0);
						Show_Message("결제가 완료되었습니다.", "결제 완료", 0);
						Main.Register.Create_Sale_Box();
					} catch (SQLException sql1) {
						// Exception 18
						Show_Message("결제 중 에러가 발생하였습니다.", "결제 에러", 1);
					}
				} catch (SQLException sql) {
					// Exception 19
					Show_Message("해당 고객 이름이 존재하지 않습니다.", "고객검색 에러", 1);
				}
			} else {
				// Exception 20
				Show_Message("주문을 하신 후 결제하세요.", "결제 에러", 1);
			}
		} // Order -> Payment Button

		else if (e.getSource() == Main.Order.Table_Number_Box) {
			Initialize_Current_Order_Information(0);
			String Selected_Table_Number = (String) Main.Order.Table_Number_Box.getSelectedItem();
			Current_Table = Main.Table.Table_Box[Integer.parseInt(Selected_Table_Number) - 1];
			Main.Order.Customer_Name_Input.setText("");
			Main.Order.Order_Field.setText(Current_Table.Order_List + line + Current_Table.Total_Price);
		} // Order -> Table_Number Box

		else if (e.getSource() == Main.Register.Customer_Enroll) {
			if (Supervisor_Login_Judge == 1) {
				Enroll_Customer.Customer_Enroll_Frame.setVisible(true);
			} else {
				// Exception 21
				Show_Message("일반 직원은 고객등록을 할 수 없습니다.", "관리 제한", 1);
			}
		} // Register -> Customer_Enroll

		else if (e.getSource() == Main.Register.Customer_Search) {
			String Customer_Name = Main.Register.Customer_Name_Input.getText();
			try {
				Main.Register.Customer_Information.setText(DB.SearchCustomer(Customer_Name));
				if (DB.SearchCustomer(Customer_Name) == null) {
					Main.Register.Customer_Information
							.setText("\n\n\n\n\n\n =================   검색 결과 없음   ================= ");
				}
			} catch (SQLException sql) {
				// Exception 22
				Show_Message("고객 조회 중 에러가 발생하였습니다.", "고객조회 에러", 1);
			}
		} // Register -> Customer_Search

		else if (e.getSource() == Main.Register.Sales_Date_List) {
			if (Supervisor_Login_Judge == 1) {
				Main.Register.Sale_Information.setText("");
				String SelectedItem = (String) Main.Register.Sales_Date_List.getSelectedItem();
				try {
					Main.Register.Sale_Information.setText(DB.SearchSale(SelectedItem));
				} catch (SQLException sql) {
					// Exception 23
					Show_Message("매출 조회 중 에러가 발생하였습니다.", "매출조회 에러", 1);
				}
			} else {
				// Exception 24
				Main.Register.Sale_Information
						.setText("\n\n\n\n\n\n =================   매출 조회 제한   ================= ");
			}
		} // Register -> Sale_Date_List

		else if (e.getSource() == Main.Register.Staff_Enroll) {
			if (Supervisor_Login_Judge == 1) {
				Enroll_Staff.Staff_Enroll_Frame.setVisible(true);
			} else {
				// Exception 25
				Show_Message("일반 직원은 직원 등록을 할 수 없습니다.", "관리 제한", 1);
			}
		} // Register -> Staff_Enroll

		else if (e.getSource() == Main.Register.Staff_Search) {
			String Staff_Name = Main.Register.Staff_Name_Input.getText();
			try {
				Main.Register.Staff_Information.setText(DB.SearchStaff(Staff_Name));
				if (DB.SearchStaff(Staff_Name) == null) {
					Main.Register.Staff_Information
							.setText("\n\n\n\n\n\n =================   검색 결과 없음   ================= ");
				}
			} catch (SQLException sql) {
				// Exception 26
				Show_Message("직원 조회 중 에러가 발생하였습니다.", "직원조회 에러", 1);
			}
		} // Register -> Staff_Search

		else if (e.getSource() == Main.Register.Menu_Enroll) {
			if (Supervisor_Login_Judge == 1) {
				Enroll_Menu.Menu_Enroll_Frame.setVisible(true);
			} else {
				// Exception 27
				Show_Message("일반 직원은 메뉴등록을 할 수 없습니다.", "관리 제한", 1);
			}
		} // Register -> Menu_Enroll

		else if (e.getSource() == Main.Register.Menu_Search) {
			String Menu_Name = Main.Register.Menu_Name_Input.getText();
			try {
				Main.Register.Menu_Information.setText(DB.SearchMenu(Menu_Name));
				if (DB.SearchMenu(Menu_Name) == null) {
					Main.Register.Menu_Information
							.setText("\n\n\n\n\n\n =================   검색 결과 없음   ================= ");
				}
			} catch (SQLException sql) {
				// Exception 28
				Show_Message("메뉴 조회 중 에러가 발생하였습니다.", "메뉴조회 에러", 1);
			}
		} // Register -> Menu_Search

		else if (e.getSource() == Enroll_Customer.Register_Button) {
			String Customer_Name = Enroll_Customer.Customer_Name_Input.getText();
			String Customer_Birth = Enroll_Customer.Customer_Brith_Input.getText();
			String Customer_Tel = Enroll_Customer.Customer_Tel_Input.getText();
			Customer_Name = Customer_Name.trim();
			Customer_Birth = Customer_Birth.trim();
			Customer_Tel = Customer_Tel.trim();
			if (Customer_Name.equals("") || Customer_Birth.equals("") || Customer_Tel.equals("")) {
				// Exception 29
				Show_Message("빈칸을 채워주세요.", "입력오류", 1);
			}
			else if (!isNumber(Customer_Birth) || !isNumber(Customer_Tel)){
				Show_Message("생일과 전화번호는 숫자로 입력해주세요.", "입력오류", 1);
			}
			else {
				try {
					DB.Insert_Customer(Customer_Name, Customer_Birth, Customer_Tel);
					Show_Message("고객 등록이 완료되었습니다.", "등록 완료", 0);
				} catch (SQLException sql) {
					// Exception 30
					Show_Message("이미 등록되어 있는 고객입니다.", "등록 실패", 1);
				} finally {
					Enroll_Customer.Customer_Brith_Input.setText("");
					Enroll_Customer.Customer_Name_Input.setText("");
					Enroll_Customer.Customer_Tel_Input.setText("");
					Enroll_Customer.Customer_Enroll_Frame.setVisible(false);
				}
			}
		} // Customer_Enroll_Window -> Register Button

		else if (e.getSource() == Enroll_Customer.Cancel_Button) {
			Enroll_Customer.Customer_Brith_Input.setText("");
			Enroll_Customer.Customer_Name_Input.setText("");
			Enroll_Customer.Customer_Tel_Input.setText("");
			Enroll_Customer.Customer_Enroll_Frame.setVisible(false);
		} // Customer_Enroll_Window -> Cancel Button

		else if (e.getSource() == Enroll_Staff.Register_Button) {
			String Staff_Name = Enroll_Staff.Staff_Name_Input.getText();
			String Staff_Rank = (String) Enroll_Staff.Staff_Rank_Box.getSelectedItem();
			Staff_Name = Staff_Name.trim();
			if (Staff_Name.equals("")) {
				// Exception 31
				Show_Message("빈칸을 채워주세요.", "입력오류", 1);
			}
			else {
				try {
					DB.Insert_Staff(Staff_Name, Staff_Rank);
					Show_Message("직원 등록이 완료되었습니다.", "등록 완료", 0);
				} catch (SQLException sql) {
					// Exception 32
					Show_Message("이미 등록되어있는 직원입니다.", "등록 실패", 1);
				} finally {
					Enroll_Staff.Staff_Name_Input.setText("");
					Enroll_Staff.Staff_Enroll_Frame.setVisible(false);
				}
			}
		} // Staff_Enroll_Window -> Register Button

		else if (e.getSource() == Enroll_Staff.Cancel_Button) {
			Enroll_Staff.Staff_Name_Input.setText("");
			Enroll_Staff.Staff_Enroll_Frame.setVisible(false);
		} // Staff_Enroll_Window -> Cancel Button

		else if (e.getSource() == Enroll_Menu.Register_Button) {
			String Menu_Name = Enroll_Menu.Menu_Name_Input.getText();
			String Menu_Price = Enroll_Menu.Menu_Price_Input.getText();
			Menu_Name = Menu_Name.trim();
			Menu_Price = Menu_Price.trim();
			if (Menu_Name.equals("") || Menu_Price.equals("")) {
				// Exception 33
				Show_Message("빈칸을 채워주세요.", "입력오류", 1);
			} else if (!isNumber(Menu_Price)) {
				Show_Message("메뉴 가격은 숫자로 입력해주세요.", "입력오류", 1);
			}
			else if (Menu_Name.trim().length() == 0 || Menu_Price.trim().length() == 0){
				Show_Message("공백입력을 하셨습니다.","입력오류",1);
			}
			else if (Menu_Count == 20) {
				// Exception 34
				Show_Message("메뉴는 20개까지 등록이 가능합니다.", "메뉴갯수초과", 1);
			} else {
				try {
					DB.Insert_Menu(Menu_Name, Menu_Price, this);
					try {
						DB.CreateMenuList(Main.Menu, this);
						SwingUtilities.updateComponentTreeUI(Main.Menu);
						Show_Message("메뉴 등록이 완료되었습니다.", "등록 완료", 0);
					} catch (SQLException sql) {
						// Exception 35
						Show_Message("메뉴 추가 도중 에러가 발생하였습니다.", "메뉴 추가 에러", 1);
					} catch (IOException ioe) {
						// Exception 36
						Show_Message("메뉴 추가 도중 파일 형식 에러가 발생하였습니다.", "메뉴 추가 에러", 1);
					}
				} catch (SQLException sql) {
					// Exception 37
					Show_Message("이미 등록되어있는 메뉴 입니다.", "등록 실패", 1);
				} finally {
					Enroll_Menu.Menu_Name_Input.setText("");
					Enroll_Menu.Menu_Price_Input.setText("");
					Enroll_Menu.Menu_Enroll_Frame.setVisible(false);
				}
			}
		} // Menu_Enroll_Window -> Register Button

		else if (e.getSource() == Enroll_Menu.Cancel_Button) {
			Initialize_Register();
			Enroll_Menu.Menu_Name_Input.setText("");
			Enroll_Menu.Menu_Price_Input.setText("");
			Enroll_Menu.Menu_Enroll_Frame.setVisible(false);
		} // Menu_Enroll_Window -> Cancel Button

		for (int i = 0; i < 20; i++) {
			if (e.getSource() == Main.Table.Table_Box[i].Table_Button) {
				Main.Order.Table_Number_Box.setSelectedIndex(i);
				// order Frame 에서 table_nubmer_box 숫자를 선택한 테이블 번호로 바꿔줌
				Current_Order_Content = "";
				Current_Table = Main.Table.Table_Box[i];
				Current_Total_Pay = 0;
				for (int j = 0; j < 20; j++) {
					Current_Total_Menu_Count[i] = 0;
				}
				// 현재 선택한 테이블 초기화
				Main.Order.Customer_Name_Input.setText("");
				Main.Order.Order_Field.setText(Current_Table.Order_List + line + Current_Table.Total_Price);
			}
		} // Table -> TableBox Button

		for (int i = 0; i < Menu_Count; i++) {
			if (e.getSource() == Main.Menu.MenuList[i].Menu_Button) {
				try {
					Current_Order_Content += "  " + Main.Menu.MenuList[i].Menu_Name + "\t"
							+ Main.Menu.MenuList[i].Menu_Price + "\n";
					Current_Total_Pay += Main.Menu.MenuList[i].Menu_Price;
					Current_Total_Menu_Count[i]++;
					Main.Order.Order_Field.setText(" ============ 현재 선택 리스트 ============\n" + Current_Order_Content
							+ Current_Table.Order_List + line + Current_Table.Total_Price);
				} catch (NullPointerException npe) {
					// Exception 38
					Show_Message("테이블을 먼저 선택해주세요.", "메뉴 선택 에러", 1);
				}
			}
		} // Menu -> MenuList Button
	}

	private boolean isNumber(String str) {
		boolean result = false;
		try {
			Double.parseDouble(str);
			result = true;
		} catch (Exception e) {
			return false;
		}
		return result;
	}
	// 주어진 문자열이 숫자인지 판별해줌.

	private File File_Open() {
		File file = null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("File Open", "txt", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		return file;
	}
	// 파일 오픈 메소드.

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new POS_Controller("system", "system");

	}
}
