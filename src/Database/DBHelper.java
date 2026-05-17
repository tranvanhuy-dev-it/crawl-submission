package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
	private static DBHelper instance;
	private Connection conn;
	
	public static DBHelper getInstance() {
		if (instance == null) {
			instance = new DBHelper();
		}
		return instance;
	}
	
	private DBHelper() {
		String url = "jdbc:mysql://localhost:3306/btl_java";
		String user = "root"; 
		String password = "123456"; 
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } 
		catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
		catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void ExecuteDB(String sql, Object... parameters) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}
			}
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public ResultSet GetRecords(String sql, Object... parameters) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setObject(i + 1, parameters[i]);
                }
            }
            return pstmt.executeQuery();     
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
