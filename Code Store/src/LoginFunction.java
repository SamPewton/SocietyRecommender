import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginFunction {

	private Connection c = null;
	
	String userName = "";
	String password = "";

	public LoginFunction() {
		try {
			c = DriverManager.getConnection("jdbc:sqlite:UniSocietyFinderDatabase.db");
			
		}

		catch (SQLException se) {
			se.printStackTrace();
		}
		
		askLogin(userName, password);
	}
	
	
	public void checkLogin() {
		try {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery("SELECT * FROM 'User';");
			boolean loggedIn = false;
			
			while (rs.next()) {
				if(rs.getString("username").compareTo(userName) == 0 && rs.getString("password").compareTo(password) == 0) {
					System.out.println("Welcome");
					loggedIn = true;
					break;
				}
			}
			
			if(!loggedIn) {
				System.out.println("Login Failed");
				askLogin(userName, password);
				checkLogin();
			}
		}
		
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	
	
	public void askLogin(String userName, String password) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username");
		this.userName = scanner.nextLine();  // Read user input
		
		System.out.println("Enter password");
		this.password = scanner.nextLine();  // Read user input
	}
}
