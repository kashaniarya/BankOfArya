package main.java.com.kash.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static String url = "jdbc:postgresql://localhost/postgres";
	//private static String url = "jdbc:postgresql:postgres";
	//private static String url = "jdbc:postgresql://localhost:8080/postgres";
	private static String username = "postgres";
	private static String password = "Plk8qas$";
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url,username,password);
			
		} catch (SQLException e) {
			//System.out.println("problem in conn fact");
			e.printStackTrace();
		}
		
		return conn;
		
	}
}
