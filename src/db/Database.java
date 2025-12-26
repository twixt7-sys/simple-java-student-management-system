package src.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	// Use environment variables or replace with your values
	private static final String URL = System.getenv().getOrDefault("DB_URL",
		"jdbc:mysql://localhost:3306/student_management_system?useSSL=false&serverTimezone=UTC");
	private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
	private static final String PASS = System.getenv().getOrDefault("DB_PASS", "");

	public static Connection getConnection() throws SQLException {
		try {
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException ex) {
			// Clear, actionable message for callers and logs
			System.err.println("Database.getConnection() failed: " + ex.getMessage());
			throw ex;
		}
	}
}