package android.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConnDB {

	
	public static Connection openConn() {
		
		Connection conn = null;
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
		
			String url = "jdbc:mysql://bj-cynosdbmysql-grp-gp4x36vw.sql.tencentcdb.com:23772/myapp?useUnicode=true&characterEncoding=UTF-8";  			
			String mysql_name = "root";
			String mysql_password = "Huyanjiao2000831";
			
			conn = DriverManager.getConnection(url, mysql_name, mysql_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		return conn;
	}

	
	public static void closeConn(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}