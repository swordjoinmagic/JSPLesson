package ado;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public void setParms(PreparedStatement pre,Object...objects) throws NumberFormatException, SQLException {
		for(int i=0;i<objects.length;i++) {
			if(objects[i] instanceof Integer) {
				pre.setInt(i+1, Integer.parseInt(objects[i].toString()));
			}else if(objects[i] instanceof String) {
				pre.setString(i+1, objects[i].toString());
			}else if(objects[i] instanceof Float) {
				pre.setFloat(i+1, Float.parseFloat(objects[i].toString()));
			}else if(objects[i] instanceof Double) {
				pre.setDouble(i+1, Double.parseDouble(objects[i].toString()));
			}
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
		
		try {
			// ����Ԥ������
			PreparedStatement pre = conn.prepareStatement(sql);
			// ΪԤ����������ò���
			setParms(pre, objects);
			
			// ���в�ѯ
			ResultSet rs = pre.executeQuery();
			
			// ͨ�����佫���ݿ��е�����ע��javabean��
			while(rs.next()) {
				// ����javabean
				T data = JavaBeanClass.newInstance();
				
				// ����set���������ݿ��е�����ע��javabean��
				for(Method method:JavaBeanClass.getMethods()) { 
					if(method.toString().indexOf("set")!=-1) {
						// Ҫ��ע���������
						String attribute = method.toString().substring(3);
						
						// ע������
						method.invoke(data, rs.getString(attribute));
						
					}
				}
				
				// �����ɵ�JavaBean����List��
				list.add(data);
				
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
		return list;
	}

}
