package com.DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

	private JdbcUtils() {}

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cne) {
			cne.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException, IOException {
		FileInputStream fis = new FileInputStream("D:\\Pratik\\Servlet-Projects-Workspace\\BookShopRepo\\BookShopApp\\src\\main\\java\\com\\properties\\applications.properties");
		Properties properties = new Properties();
		properties.load(fis);

		Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userName"), properties.getProperty("password"));
		return connection;
	}

	public static void clearConnections(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		if (connection != null) {
			connection.close();
		}

		if (preparedStatement != null) {
			preparedStatement.close();
		}

		if (resultSet != null) {
			resultSet.close();
		}
	}

}
