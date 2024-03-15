package com.example.DbUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	private DBUtil() {
	}

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getDBConection() throws IOException, SQLException {
		String url = "jdbc:mysql://localhost:3306/student";
		String user = "root";
		String password = "shrikant@123";
		return DriverManager.getConnection(url, user, password);
	}

	public static void cleanUpResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		// 5. Close the resources
		if (resultSet != null) {
			try {
				// Closing ResultSet
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				// Closing Statement
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				// Closing Connection
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
