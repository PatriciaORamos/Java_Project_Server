package ca.nscc.gamefrogger.model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {
	
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/mydb";
	private final static String USER = "root";
	private final static String PASS = "";
 
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);			
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Connection error", ex);
		} catch (SQLException ex) {
			throw new RuntimeException("Connection error", ex);
		}	
	}
	
	public static void closeConnection(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException ex) {
			Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}