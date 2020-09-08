package main.java.com.kash.controllers;

import main.java.com.kash.dao.*;
import main.java.com.kash.models.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AdminController {
	
	public static void getAdminHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			
			HttpSession session = request.getSession();
			String username = (String) session.getAttribute("user");
			
			BankDao bank = new BankDaoImpl();
			User u = bank.getUserByUsername(username);
			String role_str = u.getRole().getRole();
			
			if (role_str.equals("admin") || role_str.equals("employee")) {
				request.getRequestDispatcher("/AdminHomePage.html").forward(request, response);
			}
			else {
				//System.out.println("Inside getHomePage method " + request.getSession().getAttribute("user"));
				request.getRequestDispatcher("/Home.html").forward(request, response);
			}
		}
	}
	
	public static void showAllUsersAndAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			BankDao bank = new BankDaoImpl();
			List<User> users = bank.getAllUsers();
			
			//tring html_string = "<div>";
			
			String html_string = "<head>" + 
					"<title>BoA Show All</title>" + 
					"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
					"</head>" + 
					"<body>"
					+ "<div>";
			
			html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
					+ "<input type=\"submit\" value=\"Back\">"
					+ "</form><br>";
			
			
			for (User u : users) {
				
				int user_id = u.getID();
				String username = u.getUsername();
				String firstName = u.getFirstname();
				String lastName = u.getLastname();
				String email = u.getEmail();
				String role = u.getRole().getRole();
				List<Account> users_accounts = bank.readAllUsersAccounts(username);
			
				html_string = html_string + "<form name=\"user\" action=\"/FinalProjectV1/api/adminUpdateUser\" method\"post\">"
						+ "<input type=\"text\" value=\"USER: \" readonly>"
						+ "<input type=\"text\" name = \"user_id\" value=\""+ user_id + "\" readonly>"
						+ "<input type=\"text\" name = \"username\" value=\""+username+"\">"
						+ "<input type=\"text\" name = \"firstName\" value=\""+firstName+"\">"
						+ "<input type=\"text\" name = \"lastName\" value=\""+lastName+"\">"
						+ "<input type=\"text\" name = \"email\" value=\""+email+"\">"
						+ "<input type=\"text\" name = \"role\" value=\""+role+"\">"
						+ "<input type=\"submit\" value=\"Update User\">"
						+ "</form>";
				
				for (Account ua : users_accounts) {
					int account_id = ua.getID();
					Double balance = ua.getBalance();
					String acc_status = ua.getStatus().getStatus();
					String acc_type = ua.getType().getType();
					
					html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
							+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
							+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
							+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
							+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
							+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
							+ "<input type=\"submit\" value=\"Update Account\">"
							+ "</form>";
				}
			
			}
			
			html_string = html_string + "</div></body>";
			response.getWriter().write(html_string);
		}
	}
	
	
	
	public static void adminUpdateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			HttpSession session = request.getSession();
			String ad = (String) session.getAttribute("user");
			
			BankDao bank = new BankDaoImpl();
			
			User admin = bank.getUserByUsername(ad);
			String r_ = admin.getRole().getRole();
			
			if (r_.equals("admin")) {
			
			
				try {
					String id = request.getParameter("user_id");
					int user_id = Integer.parseInt(id);
					String username = request.getParameter("username").toLowerCase();
					String firstName = request.getParameter("firstName").toLowerCase();
					String lastName = request.getParameter("lastName").toLowerCase();
					String email = request.getParameter("email").toLowerCase();
					String role = request.getParameter("role").toLowerCase();
					
					if( !(role.equals("admin") || role.equals("employee") || role.equals("premium") || role.equals("standard"))) {
						throw new Exception("doesn't matter");
					}
					
					//BankDao bank = new BankDaoImpl();
					boolean worked = bank.updateUser(user_id, username, firstName, lastName, email, role);
					
					if (worked) {
						AccountController.errorPage(request, response, "Updating User Successful!");
					}
					else {
						AccountController.errorPage(request, response, "Updating User Failed.");
					}
				}
				catch (Exception e) {
					AccountController.errorPage(request, response, "Updating User Failed.");
				}
			}
			else {
				AccountController.errorPage(request, response, "You do not have permission to update");
			}
		}
	}
	
	public static void adminUpdateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			HttpSession session = request.getSession();
			String ad = (String) session.getAttribute("user");
			
			BankDao bank = new BankDaoImpl();
			
			User admin = bank.getUserByUsername(ad);
			String r_ = admin.getRole().getRole();
			
			if (r_.equals("admin")) {
				
			
				try {
					String id = request.getParameter("account_id");
					int account_id = Integer.parseInt(id);
					String balance = request.getParameter("balance");
					Double bal = Double.parseDouble(balance);
					
					String acc_status = request.getParameter("acc_status").toLowerCase();
					String acc_type = request.getParameter("acc_type").toLowerCase();
					
					if (!(acc_status.equals("open") || acc_status.equals("pending") || acc_status.equals("closed") || acc_status.equals("denied"))) {
						throw new Exception("doesn't matter");
					}
					if (!(acc_type.equals("checking") || acc_type.equals("savings"))) {
						throw new Exception("doesn't matter");
					}
	
					
					//BankDao bank = new BankDaoImpl();
					boolean worked = bank.updateAccount(account_id, bal, acc_status, acc_type);
					
					if (worked) {
						AccountController.errorPage(request, response, "Updating Account Successful!");
					}
					else {
						AccountController.errorPage(request, response, "Updating Account Failed.");
					}
				}
				catch (Exception e) {
					AccountController.errorPage(request, response, "Updating Account Failed.");
				}
			}
			else {
				AccountController.errorPage(request, response, "You do not have permission to update");
			}
		}
	}
	
	public static void showAllAccounts(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			BankDao bank = new BankDaoImpl();
			List<User> users = bank.getAllUsers();
			
			//String html_string = "<div>";
			
			String html_string = "<head>" + 
					"<title>BoA All Accounts</title>" + 
					"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
					"</head>" + 
					"<body>"
					+ "<div>";
			
			html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
					+ "<input type=\"submit\" value=\"Back\">"
					+ "</form><br>";
			
			
			for (User u : users) {
				
				int user_id = u.getID();
				String username = u.getUsername();

				List<Account> users_accounts = bank.readAllUsersAccounts(username);
			

				
				for (Account ua : users_accounts) {
					int account_id = ua.getID();
					Double balance = ua.getBalance();
					String acc_status = ua.getStatus().getStatus();
					String acc_type = ua.getType().getType();
					
					html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
							+ "<input type=\"text\" value=\"User id #: "+user_id+"\" readonly>"
							+ "<input type=\"text\" value=\""+username+"\" readonly>"
							+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
							+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
							+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
							+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
							+ "<input type=\"submit\" value=\"Update Account\">"
							+ "</form>";
				}
			
			}
			
			html_string = html_string + "</div></body>";
			response.getWriter().write(html_string);
		}
			
		
	}
	
	public static void showAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			BankDao bank = new BankDaoImpl();
			List<User> users = bank.getAllUsers();
			
			//String html_string = "<div>";
			
			String html_string = "<head>" + 
					"<title>BoA All Users</title>" + 
					"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
					"</head>" + 
					"<body>"
					+ "<div>";
			
			html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
					+ "<input type=\"submit\" value=\"Back\">"
					+ "</form><br>";
			
			
			for (User u : users) {
				
				int user_id = u.getID();
				String username = u.getUsername();
				String firstName = u.getFirstname();
				String lastName = u.getLastname();
				String email = u.getEmail();
				String role = u.getRole().getRole();
			
				html_string = html_string + "<form name=\"user\" action=\"/FinalProjectV1/api/adminUpdateUser\" method\"post\">"
						+ "<input type=\"text\" value=\"USER: \" readonly>"
						+ "<input type=\"text\" name = \"user_id\" value=\""+ user_id + "\" readonly>"
						+ "<input type=\"text\" name = \"username\" value=\""+username+"\">"
						+ "<input type=\"text\" name = \"firstName\" value=\""+firstName+"\">"
						+ "<input type=\"text\" name = \"lastName\" value=\""+lastName+"\">"
						+ "<input type=\"text\" name = \"email\" value=\""+email+"\">"
						+ "<input type=\"text\" name = \"role\" value=\""+role+"\">"
						+ "<input type=\"submit\" value=\"Update User\">"
						+ "</form>";
				
				
			}
			
			html_string = html_string + "</div></body>";
			response.getWriter().write(html_string);
			
		}
	}
	
	
	public static void showUserById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String id = request.getParameter("uid");
				int uid = Integer.parseInt(id);
				
				
				BankDao bank = new BankDaoImpl();
				User u = bank.getUserById(uid);
				
				//String html_string = "<div>";
				
				String html_string = "<head>" + 
						"<title>BoA User by ID</title>" + 
						"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
						"</head>" + 
						"<body>"
						+ "<div>";
				
				html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
						+ "<input type=\"submit\" value=\"Back\">"
						+ "</form><br>";
				
				
				
					
				int user_id = u.getID();
				String username = u.getUsername();
				String firstName = u.getFirstname();
				String lastName = u.getLastname();
				String email = u.getEmail();
				String role = u.getRole().getRole();
				
				List<Account> users_accounts = bank.readAllUsersAccounts(username);
			
				html_string = html_string + "<form name=\"user\" action=\"/FinalProjectV1/api/adminUpdateUser\" method\"post\">"
						+ "<input type=\"text\" value=\"USER: \" readonly>"
						+ "<input type=\"text\" name = \"user_id\" value=\""+ user_id + "\" readonly>"
						+ "<input type=\"text\" name = \"username\" value=\""+username+"\">"
						+ "<input type=\"text\" name = \"firstName\" value=\""+firstName+"\">"
						+ "<input type=\"text\" name = \"lastName\" value=\""+lastName+"\">"
						+ "<input type=\"text\" name = \"email\" value=\""+email+"\">"
						+ "<input type=\"text\" name = \"role\" value=\""+role+"\">"
						+ "<input type=\"submit\" value=\"Update User\">"
						+ "</form>";
					
				for (Account ua : users_accounts) {
					int account_id = ua.getID();
					Double balance = ua.getBalance();
					String acc_status = ua.getStatus().getStatus();
					String acc_type = ua.getType().getType();
					
					html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
							+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
							+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
							+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
							+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
							+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
							+ "<input type=\"submit\" value=\"Update Account\">"
							+ "</form>";
				}
				
				
				html_string = html_string + "</div></body>";
				response.getWriter().write(html_string);
				
				
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get User by id");
			}
		}
	}
	
	public static void showAccountById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String aid = request.getParameter("account_id");
				int account_id = Integer.parseInt(aid);
				
				
				BankDao bank = new BankDaoImpl();
				Account a = bank.getAccountById(account_id);
				
				Double balance = a.getBalance();
				String status = a.getStatus().getStatus();
				String type = a.getType().getType();
				
				//String html_string = "<div>";
				
				String html_string = "<head>" + 
						"<title>BoA Account by ID</title>" + 
						"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
						"</head>" + 
						"<body>"
						+ "<div>";
				
				html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
						+ "<input type=\"submit\" value=\"Back\">"
						+ "</form><br>";
				
				html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
						+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
						+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
						+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
						+ "<input type=\"text\" name = \"acc_status\" value=\""+status+"\">"
						+ "<input type=\"text\" name = \"acc_type\" value=\""+type+"\">"
						+ "<input type=\"submit\" value=\"Update Account\">"
						+ "</form>";
				
				html_string = html_string + "</div></body>";
				response.getWriter().write(html_string);
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get Account by id");
			}
		}
	}
	
	
	public static void showAccountByUsername(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String username = request.getParameter("username");
				
				
				
				BankDao bank = new BankDaoImpl();
				//User u = bank.getUserById(uid);
				
				//String html_string = "<div>";
				
				String html_string = "<head>" + 
						"<title>BoA Account by Username</title>" + 
						"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
						"</head>" + 
						"<body>"
						+ "<div>";
				
				html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
						+ "<input type=\"submit\" value=\"Back\">"
						+ "</form><br>";
				
				
				
					
//				int user_id = u.getID();
//				String username = u.getUsername();
//				String firstName = u.getFirstname();
//				String lastName = u.getLastname();
//				String email = u.getEmail();
//				String role = u.getRole().getRole();
				
				List<Account> users_accounts = bank.readAllUsersAccounts(username);
			
//				html_string = html_string + "<form action=\"/FinalProjectV1/api/adminUpdateUser\" method\"post\">"
//						+ "<input type=\"text\" value=\"USER: \" readonly>"
//						+ "<input type=\"text\" name = \"user_id\" value=\""+ user_id + "\" readonly>"
//						+ "<input type=\"text\" name = \"username\" value=\""+username+"\">"
//						+ "<input type=\"text\" name = \"firstName\" value=\""+firstName+"\">"
//						+ "<input type=\"text\" name = \"lastName\" value=\""+lastName+"\">"
//						+ "<input type=\"text\" name = \"email\" value=\""+email+"\">"
//						+ "<input type=\"text\" name = \"role\" value=\""+role+"\">"
//						+ "<input type=\"submit\" value=\"Update User\">"
//						+ "</form>";
					
				for (Account ua : users_accounts) {
					int account_id = ua.getID();
					Double balance = ua.getBalance();
					String acc_status = ua.getStatus().getStatus();
					String acc_type = ua.getType().getType();
					
					html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
							+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
							+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
							+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
							+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
							+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
							+ "<input type=\"submit\" value=\"Update Account\">"
							+ "</form>";
				}
				
				
				html_string = html_string + "</div></body>";
				response.getWriter().write(html_string);
				
				
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get User by id");
			}
		}
	}
	
	
	public static void showAccountByType(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String type = request.getParameter("type");
				BankDao bank = new BankDaoImpl();
				List<Account> la = bank.getAccountByType(type);
				
				if (la.size() == 0) {
					AccountController.errorPage(request, response, "No accounts to show");
				}
				else {
					//String html_string = "<div>";
					
					String html_string = "<head>" + 
							"<title>BoA Account By Type</title>" + 
							"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
							"</head>" + 
							"<body>"
							+ "<div>";
					
					
					html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
							+ "<input type=\"submit\" value=\"Back\">"
							+ "</form><br>";
					
					for (Account ua : la) {
						int account_id = ua.getID();
						Double balance = ua.getBalance();
						String acc_status = ua.getStatus().getStatus();
						String acc_type = ua.getType().getType();
						
						html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
								+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
								+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
								+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
								+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
								+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
								+ "<input type=\"submit\" value=\"Update Account\">"
								+ "</form>";
					}
					
					
					html_string = html_string + "</div></body>";
					response.getWriter().write(html_string);
				}
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get Accounts by Type");
			}
		}
	}
	
	public static void showAccountByStatus(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String status = request.getParameter("status");
				BankDao bank = new BankDaoImpl();
				List<Account> la = bank.getAccountByStatus(status);
				
				if (la.size() == 0) {
					AccountController.errorPage(request, response, "No accounts to show");
				}
				else {
					//String html_string = "<div>";
					
					String html_string = "<head>" + 
							"<title>BoA Account By Status</title>" + 
							"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
							"</head>" + 
							"<body>"
							+ "<div>";
					
					html_string = html_string + "<form action=\"/FinalProjectV1/api/getAdminHomePage\" method=\"get\">"
							+ "<input type=\"submit\" value=\"Back\">"
							+ "</form><br>";
					
					for (Account ua : la) {
						int account_id = ua.getID();
						Double balance = ua.getBalance();
						String acc_status = ua.getStatus().getStatus();
						String acc_type = ua.getType().getType();
						
						html_string = html_string + "<form name=\"acc\" action=\"/FinalProjectV1/api/adminUpdateAccount\" method\"post\">"
								+ "<input type=\"text\" value=\"ACCOUNT: \" readonly>"
								+ "<input type=\"text\" name = \"account_id\" value=\""+ account_id + "\" readonly>"
								+ "<input type=\"text\" name = \"balance\" value=\""+balance+"\">"
								+ "<input type=\"text\" name = \"acc_status\" value=\""+acc_status+"\">"
								+ "<input type=\"text\" name = \"acc_type\" value=\""+acc_type+"\">"
								+ "<input type=\"submit\" value=\"Update Account\">"
								+ "</form>";
					}
					
					
					html_string = html_string + "</div></body>";
					response.getWriter().write(html_string);
				}
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get Accounts by Status");
			}
		}
	}
	
}






