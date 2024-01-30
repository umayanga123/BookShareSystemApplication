package db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class will create DB connection with SQL
 */
public class Database {

	/**
	 * Load SQL Connetor and connet to PHPMyadmin SQL db
	 * Create db Connection
	 * 
	 * @return Connection
	 */
	public static Connection connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/marcoData", "root", "");
			return connect;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
