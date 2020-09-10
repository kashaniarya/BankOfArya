package main.java.com.kash.controllers;

import main.java.com.kash.dao.*; 


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.com.kash.models.*;

public class HomeController {

	public static void getHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		if(request.getSession(false) == null) {
			response.setStatus(401);
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			request.getRequestDispatcher("/Login.html").forward(request, response);
		} 
		
		else {
			response.setStatus(200);
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

	public static void getLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
			getHomePage(request,response);
		}
		else {
			request.getRequestDispatcher("/Login.html").forward(request, response);
		}
		
	}
	
	

	public static void getFailedLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
			getHomePage(request,response);
		}
		else {
			response.setStatus(400);
			request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
		}
	}
	
	public static void getCreateAccountPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
			getHomePage(request,response);
		}
		else {
			request.getRequestDispatcher("/CreateAccount.html").forward(request, response);
		}
	}
	
	public static void getFailedCreateAccountPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
			getHomePage(request,response);
		}
		else {
			response.setStatus(400);
			request.getRequestDispatcher("/FailedCreateAccount.html").forward(request, response);
		}
	}
	
	public static void getOpenAccountPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else {
			request.getRequestDispatcher("/OpenAccountPage.html").forward(request, response);
		}
	}
	
	public static void getCheckAccountsPage(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else {
			//request.getRequestDispatcher("/CheckAccountsPage.html").forward(request, response);
			/*
			 * from request you'll get: user
			 * read user's accounts from db
			 * for each account: id, balance, status, type
			 * 
			 */
			//String html_string = "<div>";
			
			String html_string = "<head>" + 
					"<title>BoA Check Accounts</title>" + 
					AdminController.styleCss() +
					"</head>" + 
					"<body>"
					+ "<div>";
			
			//String username = request.getParameter("user");
			
			String username = (String) request.getSession().getAttribute("user");
			
			//System.out.println("username: " + username);
			
			BankDao bank = new BankDaoImpl();
			
			List<Account> la = bank.readAllUsersAccounts(username);
			
			html_string = html_string + "<form action=\"/api/home\" method=\"post\">"
					+ "<input type=\"submit\" value=\"Back\">"
					+ "</form>";
			
			html_string = html_string + "<h1>All Accounts for User, " + username + "</h1>";
			
			if (la.size() == 0) {
				html_string = html_string + "<h3>No accounts opened yet</h3>";
			}
			else {
				for (Account a : la) {
					int id = a.getID();
					double balance = a.getBalance();
					String acc_type = a.getType().getType();
					String acc_status = a.getStatus().getStatus();
					//html_string = html_string + "<p>Account #: " + id + "; Balance: " + balance + "; Account Type: " + acc_type + "; Account Status: " + acc_status + "</p>";
					
					html_string = html_string + "<form name=\"acc\" action=\"/api/selectAccount\" method=\"post\">"
							+ "<label for=\"accnum\">Account #: </label>"
							+ "<input type=\"text\" name=\"accnum\" value=\"" + id +"\" readonly>"
							+ "<br>"  
							+ "<label for=\"balanc\">Balance: </label>"
							+ "<input type=\"text\" name=\"balanc\" value=\"" + balance +"\" readonly>"
							+ "<br>" 
							+  "<label for=\"acctype\">Account Type: </label>"
							+ "<input type=\"text\" name=\"acctype\" value=\"" + acc_type +"\" readonly>"
							+ "<br>"  
							+ "<label for=\"accstatus\">Account Status: </label>"
							+ "<input type=\"text\" name=\"accstatus\" value=\"" + acc_status +"\" readonly>"
							+ "<br>" 
							+ "<input type=\"submit\" value=\"Select\">"
							+ "</form><br><br><br>";

					// i can add a button here to select a specific account and go to a new page
					// options there will be to: Deposit, Withdraw, Transfer, Close
					
				}
			}

			html_string = html_string + "</div></body>";
			
			response.getWriter().write(html_string);
		}
	}
	
	
	public static void createNewAccountChecking(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request, response);
		}
		else {
			String b = request.getParameter("balance");
			try {
				double balance = Double.parseDouble(b);
				
				String u = (String) request.getSession().getAttribute("user");
				
				Account a = new Account(balance, new AccountStatus("pending"), new AccountType("checking"));
				
				BankDao bank = new BankDaoImpl();
				bank.addAccount(u, a);
				//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				AccountController.errorPage(request,response,"Created Checking Account Successfully");
			}
			catch (Exception e) {
				//System.out.println("error in creating checking account");
				AccountController.errorPage(request,response, "Creating Checking Account Error. Try again.");
			}
			//System.out.println("create new checking account | end of func | should never hit this");
			//AccountController.errorPage(request,response, "Creating Checking Account Error. Try again.");
		}
	}
	
	public static void createNewAccountSavings(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else {
			String b = request.getParameter("balance");
			try {
				double balance = Double.parseDouble(b);
				
				String u = (String) request.getSession().getAttribute("user");
				
				Account a = new Account(balance, new AccountStatus("pending"), new AccountType("savings"));
				
				BankDao bank = new BankDaoImpl();
				bank.addAccount(u, a);
				//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				AccountController.errorPage(request,response, "Creating Saving Account Successfully");
			}
			catch (Exception e) {
				//System.out.println("error in creating savings account");
				AccountController.errorPage(request,response, "Creating Savings Account Error. Try again.");
			}
			//AccountController.errorPage(request,response, "Creating Savings Account Error. Try again.");
		}
	}
	
	public static void selectAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else if(!((String)request.getParameter("accstatus")).equals("open")){
//			response.getWriter().write("<div>"
//					+ "<h1>Can't select account if it is pending, closed, or denied</h1>"
//					+ "<h2>Can only select Open accounts to: Withdraw, Deposit, Transfer, or Close</h2>"
//					+ "<form action=\"/FinalProjectV1/api/home\" method=\"post\"><input type=\"submit\" value=\"Back\"></form>"
//					+ "</div>");
			String errorMsg = "Can only Select Open Accounts to: deposit, withdraw, transfer, or close. Can not select if it is pending, closed, or denied.";
			AccountController.errorPage(request, response, errorMsg);
		}
		else {
			// show the account info
			// show options to withdraw, deposit, transfer, close
			
			
			String acc_id = request.getParameter("accnum");
			String balance = request.getParameter("balanc");
			//String acc_type = request.getParameter("acctype");
			//String acc_status = request.getParameter("accstatus");
			
			HttpSession session = request.getSession();
			
			session.setAttribute("account_id", acc_id);
			session.setAttribute("balance", balance);
			
			
			String html_string = "<head>" + 
					"<title>BoA Select Account</title>" + 
					AdminController.styleCss() +
					"</head>" + 
					"<body>"
					+ "<div>";
			
			//String html_string = "<div>";
			
			html_string = html_string + "<h1>Account Number: "+ acc_id + "</h1>";
			
			html_string = html_string + "<h2>Balance: " + balance + "</h2>";
			
			// Deposit, Withdraw, Transfer, Close, Back

			
			html_string = html_string + "<form action=\"/api/deposit\" method=\"post\"> <input type=\"submit\" value=\"Deposit\"> <input type=\"text\" name=\"amount\" placeholder=\"amount\"> </form>"
					+ "<form action=\"/FinalProjectV1/api/withdraw\" method=\"post\"><input type=\"submit\" value=\"Withdraw\"><input type=\"text\" name=\"amount\" placeholder=\"amount\"></form>"
					+ "<form action=\"/FinalProjectV1/api/transfer\" method=\"post\"><input type=\"submit\" value=\"Transfer\"><input type=\"text\" name=\"amount\" placeholder=\"amount\"><input type=\"text\" name=\"otherAccount\" placeholder=\"other account #\"></form>"
					+ "<form action=\"/FinalProjectV1/api/close\" method=\"post\"><input type=\"submit\" value=\"Close\"></form>"
					+ "<form action=\"/FinalProjectV1/api/home\" method=\"post\"><input type=\"submit\" value=\"Back\"></form>";
			

			
			html_string = html_string + "</div></body>";
			response.getWriter().write(html_string);
		}
	}
	
	public static void getEditInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else {
			try {
				HttpSession session = request.getSession();
				String username = (String) session.getAttribute("user");
				
				
				BankDao bank = new BankDaoImpl();
				User u = bank.getUserByUsername(username);
				
				//String html_string = "<div>";
				
				String html_string = "<head>" + 
						"<title>BoA Edit Profile</title>" 
						+ AdminController.styleCss() +
						"</head>" + 
						"<body>"
						+ "<div>";
				
				html_string = html_string + "<form action=\"/api/home\" method=\"get\">"
						+ "<input type=\"submit\" value=\"Back\">"
						+ "</form><br>";
				
				
				
					
				int user_id = u.getID();
				//String username = u.getUsername();
				String firstName = u.getFirstname();
				String lastName = u.getLastname();
				String email = u.getEmail();
				String role = u.getRole().getRole();
				
				
			
				html_string = html_string + "<form name=\"user\" action=\"/api/editInfo\" method\"post\">"
						+ "<input type=\"text\" value=\"USER: \" readonly>"
						+ "<input type=\"text\" name = \"user_id\" value=\""+ user_id + "\" readonly>"
						+ "<input type=\"text\" name = \"username\" value=\""+username+"\" readonly>"
						+ "<input type=\"text\" name = \"firstName\" value=\""+firstName+"\">"
						+ "<input type=\"text\" name = \"lastName\" value=\""+lastName+"\">"
						+ "<input type=\"text\" name = \"email\" value=\""+email+"\">"
						+ "<input type=\"text\" name = \"role\" value=\""+role+"\" readonly>"
						+ "<input type=\"submit\" value=\"Update User\">"
						+ "</form>";
					
				
				
				
				html_string = html_string + "</div></body>";
				response.getWriter().write(html_string);
				
				
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to get User by id");
			}
		}
	}
	
	public static void editInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(request.getSession(false) == null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
			getLoginPage(request,response);
		}
		else {
			try {
				BankDao bank = new BankDaoImpl();
				
				String id_  = request.getParameter("user_id");
				int id = Integer.parseInt(id_);
				String username = request.getParameter("username");
				String fname = request.getParameter("firstName").toLowerCase();
				String lname = request.getParameter("lastName").toLowerCase();
				String email = request.getParameter("email").toLowerCase();
				String r = request.getParameter("role").toLowerCase();
				
				boolean worked = bank.updateUser(id, username, fname, lname, email, r);
				
				if(worked) {
					AccountController.errorPage(request, response, "Updated Profile Successfully!");
				}
				else {
					AccountController.errorPage(request, response, "Failed to Update Profile");
				}
			}
			catch (Exception e) {
				AccountController.errorPage(request, response, "Failed to Update Profile");
			}
		}
	}
	
}


