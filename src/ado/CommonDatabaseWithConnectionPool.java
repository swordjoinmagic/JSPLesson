package ado;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.*;
import javax.sql.DataSource;

import javabean.Book;

public class CommonDatabaseWithConnectionPool {
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
		Connection connection = null;
		try {
			// 初始化查找命名空间
			Context ctx = new InitialContext();
			// 参数java:/comp/env是固定路径
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			// 参数jdbc/myDataBase是数据源和JNDI绑定的名字
			DataSource ds = (DataSource)envContext.lookup("jdbc/myDataBase");
			// 获得连接
			connection = ds.getConnection();
		}catch(NamingException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void setParms(PreparedStatement pre,Object...objects) throws SQLException {
		for(int i=0;i<objects.length;i++) {
			pre.setObject(i+1, objects[i]);
		}
	}
	
	public ResultSet getQuery(String sql,Object...objects) {
		
		// 建立数据库连接
		Connection conn = getConnection();
		PreparedStatement pre = null;
		
		try {
			// 建立sql预处理Statememnt
			pre = conn.prepareStatement(sql);
			
			// 为预处理语句设置参数
			setParms(pre, objects);
			
			// 进行查询操作
			ResultSet rs = pre.executeQuery();
			
			return rs;
			
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				pre.close();
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
		return null;
	}
	
	// 通用查询
	/**
	 * @param sql SQL语句
	 * @param javabean	生成的JavaBean类型
	 * @param objects	为SQL语句设置的参数
	 * @return
	 */
	public <T> List<T> commonQuery(String sql,Class<T>JavaBeanClass,Object...objects) {
		List<T> list = new ArrayList<>();
		
		// 建立连接
		Connection conn = getConnection();
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// 建立预处理环境
			pre = conn.prepareStatement(sql);
			// 为预处理语句设置参数
			setParms(pre, objects);
			
			// 进行查询
			rs = pre.executeQuery();
			
			// 通过反射将数据库中的数据注入javabean中
			while(rs.next()) {
				// 生成javabean
				T data = JavaBeanClass.newInstance();
				
				// 利用set方法将数据库中的数据注入javabean中
				for(Method method:JavaBeanClass.getMethods()) { 
					if(method.getName().indexOf("set")!=-1) {
						// 要被注入的属性名
						String attribute = method.getName().substring(3);
																		
						// 注入属性
						method.invoke(data, rs.getObject(attribute));
					}
				}
				// 将生成的JavaBean放入List中
				list.add(data);
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				pre.close();
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
	public static void main(String[] args) {
		CommonDatabaseWithConnectionPool ado = new CommonDatabaseWithConnectionPool();
	}
}
