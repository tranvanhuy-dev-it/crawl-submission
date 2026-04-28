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
        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy MySQL Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi: Kết nối Database thất bại! Hãy kiểm tra lại User/Pass.");
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
		} catch (SQLException e) {
            System.err.println("Lỗi thực thi SQL: " + e.getMessage());
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
        } catch (SQLException e) {
            System.err.println("Lỗi truy vấn SQL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
