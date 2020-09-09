package main.java.com.kash.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//private static String url = "jdbc:postgresql://localhost/postgres";
	private static String url = "jdbc:postgres://qwptitazckhyhv:15dcdb59f1d05fc7e02a12f0bc504873ebfebb5b0d7c643988aaf38b7b7e6023@ec2-3-223-9-166.compute-1.amazonaws.com:5432/dd70aerbgtn2q6";
	private static String username = "qwptitazckhyhv";//"postgres";
	private static String password = "15dcdb59f1d05fc7e02a12f0bc504873ebfebb5b0d7c643988aaf38b7b7e6023";//"p";
	
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
