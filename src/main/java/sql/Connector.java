package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Connector {
	Connection conn;
	Statement stat;

	static String url, database, username, password, hostname, port, driver;

	public Connector(Properties pros, String pass) {

		database = pros.getProperty("Database");
		/*
		 * Find Users in PostgreSQL SELECT usename FROM pg_user;
		 */
		username = pros.getProperty("User_Name");

		password = pass;

		/*
		 * SELECT boot_val,reset_val FROM pg_settings WHERE name='listen_addresses';
		 */
		hostname = pros.getProperty("Host_Name");
		/*
		 * SELECT * FROM pg_settings WHERE name = 'port';
		 */
		port = pros.getProperty("Port");
		driver = "org.postgresql.Driver";
		url = "jdbc:postgresql://" + hostname + ":" + port + "/" + database;

	}

	public boolean open() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, password);
			stat = conn.createStatement();

		} catch (Exception e) {
			System.out.println("The connection failed");
			e.printStackTrace();
			if (conn == null)
				return false;
		}
		System.out.println("Connection successful");
		return true;
	}

	public ResultSet excuteQury(String s) throws SQLException {
		// TODO Auto-generated method stub
		return stat.executeQuery(s);
	}

	public void executeUpdate(String s) throws SQLException {
		stat.executeUpdate(s);

	}

}
