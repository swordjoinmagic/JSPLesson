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
			// ��ʼ�����������ռ�
			Context ctx = new InitialContext();
			// ����java:/comp/env�ǹ̶�·��
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			// ����jdbc/myDataBase������Դ��JNDI�󶨵�����
			DataSource ds = (DataSource)envContext.lookup("jdbc/myDataBase");
			// �������
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
		
		// �������ݿ�����
		Connection conn = getConnection();
		PreparedStatement pre = null;
		
		try {
			// ����sqlԤ����Statememnt
			pre = conn.prepareStatement(sql);
			
			// ΪԤ����������ò���
			setParms(pre, objects);
			
			// ���в�ѯ����
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
	
	// ͨ�ò�ѯ
	/**
	 * @param sql SQL���
	 * @param javabean	���ɵ�JavaBean����
	 * @param objects	ΪSQL������õĲ���
	 * @return
	 */
	public <T> List<T> commonQuery(String sql,Class<T>JavaBeanClass,Object...objects) {
		List<T> list = new ArrayList<>();
		
		// ��������
		Connection conn = getConnection();
		PreparedStatement pre = null;
		ResultSet rs = null;
		try {
			// ����Ԥ������
			pre = conn.prepareStatement(sql);
			// ΪԤ����������ò���
			setParms(pre, objects);
			
			// ���в�ѯ
			rs = pre.executeQuery();
			
			// ͨ�����佫���ݿ��е�����ע��javabean��
			while(rs.next()) {
				// ����javabean
				T data = JavaBeanClass.newInstance();
				
				// ����set���������ݿ��е�����ע��javabean��
				for(Method method:JavaBeanClass.getMethods()) { 
					if(method.getName().indexOf("set")!=-1) {
						// Ҫ��ע���������
						String attribute = method.getName().substring(3);
																		
						// ע������
						method.invoke(data, rs.getObject(attribute));
					}
				}
				// �����ɵ�JavaBean����List��
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
