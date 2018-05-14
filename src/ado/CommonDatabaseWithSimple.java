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
		// 第一步，建立连接
		Connection conn = getConnection();
		Statement state = null;
		ResultSet rs = null;
		
		// 第二步，编写sql语句
		String sql = "select * from bookes";
		
		// 将数据从数据库中取出，并存放到到一个List中
		List<Book>list = new ArrayList<Book>();
		// 第三步，将数据从数据库中取出，并放入一个数据结构中
		try {
			state = conn.createStatement();
			// 执行sql语句
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
			// 在这里密切注意要对数据库连接，statement，resultset进行关闭操作
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
