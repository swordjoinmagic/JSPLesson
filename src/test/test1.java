package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ado.CommonDatabase;
import javabean.Book;
import javabean.Score;

public class test1 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.jdbc.Driver");
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JSP?serverTimezone=UTC","root","09043330");
//		Statement state = conn.createStatement();
//		String sql = "select * from books where name=?";
//		PreparedStatement pre = conn.prepareStatement(sql);
//		pre.setString(1, "java ¥”»Î√≈");
//		ResultSet rs = pre.executeQuery();
//		while(rs.next()) {
//			System.out.println(rs.getString(1));
//		}
//		System.out.println("setAttribute".substring(3));
		CommonDatabase ado = new CommonDatabase();
		System.out.println(ado.commonQuery("select * from score", Score.class).get(0).getScore());
	}
}
