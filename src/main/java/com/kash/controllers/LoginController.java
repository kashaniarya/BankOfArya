package com.kash.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kash.dao.BankDao;
import com.kash.dao.BankDaoImpl;
import com.kash.exceptions.InvalidRoleException;
import com.kash.models.User;

public class LoginController {
	public static void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		//System.out.println("You've reached the login method, inside the LoginController");
		
		if(!request.getMethod().equals("POST")) {
			
			//System.out.println("That's a" + request.getMethod() + " method, invalid method");
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			
		}
		else {
			
			String username = request.getParameter("user");
			String pass = request.getParameter("pass");
			
			BankDao bank = new BankDaoImpl();
			String p = bank.readPasswordFromBankDB(username);
			
			
			if(inputValidHelper(username) && inputValidHelper(pass) && p != null && pass.equals(p)) {
				// log in method
				// read from database and if it works log in else it fails
				// read from db means: check if username is in db and check if password user entered matches db password
				//User u = bank.getUserByUsername(username);
				//String role_str = u.getRole().getRole();
				
				HttpSession session = request.getSession();
				
				session.setAttribute("user", username);
				
				response.setStatus(300);
				
//				if (role_str.equals("admin")) {
//					response.sendRedirect("http://localhost:8080/FinalProjectV1/api/getAdminHomePage");
//				}
//				else {
//					response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
//				}
				response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				
				
			}
			else {
				HomeController.getFailedLoginPage(request, response);
			}
		}
		
	}
	
	
	

	public static void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		
		response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		
	}
	
	private static boolean inputValidHelper(String s) {
		
		if (s == null || s.equals("") || s.equals(" ")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvalidRoleException {
		
		//System.out.println("You've reached the CREATE ACCOUNT method, inside the LoginController");
		
		if(!request.getMethod().equals("POST")) {
			
			//System.out.println("That's a" + request.getMethod() + " method, invalid method");
			
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			
		}
		else {
			// create account 
			// insert account into db
			//System.out.println("in here 1");
			
			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			String firstname = request.getParameter("fname");
			String lastname = request.getParameter("lname");
			String email = request.getParameter("email");
			
			
			
			// deal with failed create account
			if(inputValidHelper(username) && inputValidHelper(password) && inputValidHelper(firstname) && inputValidHelper(lastname) && inputValidHelper(email)) {
				
				
				
				
				User u = new User(username.toLowerCase(), password.toLowerCase(), firstname.toLowerCase(), lastname.toLowerCase(), email.toLowerCase(), "standard");
				BankDao db = new BankDaoImpl();
				Boolean worked = db.insertUser(u);
				
				if (worked) {
			
					HttpSession session = request.getSession();
					
					session.setAttribute("user", username);
					
					response.setStatus(300);
					
					response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				
				}
				else {
					HomeController.getFailedCreateAccountPage(request, response);
				}
				
			}
			else {
				HomeController.getFailedCreateAccountPage(request, response);
			}
			
		}
	}
}













