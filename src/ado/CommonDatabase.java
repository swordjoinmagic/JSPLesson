package ado;

import java.sql.Connection;
import java.sql.DriverManager;

public class CommonDatabase {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	public Connection getConnection(String user,String password) {
		String url = "jdbc:mysql://localhost:3306/JSP";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,user,password);
		}catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
