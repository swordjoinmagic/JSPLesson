package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test1 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JSP?serverTimezone=UTC","root","09043330");
		Statement state = conn.createStatement();
		ResultSet rs = state.executeQuery("select * from books");
		while(rs.next()) {
			System.out.println(rs.getString(1));
		}
	}
}
