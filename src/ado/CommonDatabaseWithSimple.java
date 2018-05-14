package ado;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javabean.Book;

public class CommonDatabaseWithSimple {
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}
	
	private String user = "root";
	private String password = "09043330";
	
	public Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/JSP";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,user,password);
		}catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
	public List<Book> getBookList(){
		// ��һ������������
		Connection conn = getConnection();
		Statement state = null;
		ResultSet rs = null;
		
		// �ڶ�������дsql���
		String sql = "select * from bookes";
		
		// �����ݴ����ݿ���ȡ��������ŵ���һ��List��
		List<Book>list = new ArrayList<Book>();
		// �������������ݴ����ݿ���ȡ����������һ�����ݽṹ��
		try {
			state = conn.createStatement();
			// ִ��sql���
			rs = state.executeQuery(sql);			
			
			while(rs.next()) {
				Book book = new Book();
				String name = rs.getString("name");
				String author = rs.getString("author");
				book.setAuthor(author);
				book.setName(name);
				list.add(book);
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}finally {
			// ����������ע��Ҫ�����ݿ����ӣ�statement��resultset���йرղ���
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
