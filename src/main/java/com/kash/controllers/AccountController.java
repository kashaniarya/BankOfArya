package com.kash.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kash.dao.BankDao;
import com.kash.dao.BankDaoImpl;
import com.kash.models.Account;

public class AccountController {
	

	public static void deposit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String amt = request.getParameter("amount");
				Double amount = Double.parseDouble(amt);
				HttpSession session = request.getSession();
				
				String account_id = (String) session.getAttribute("account_id");
				int id = Integer.parseInt(account_id);
				
				String bal = (String) session.getAttribute("balance");
				Double balance = Double.parseDouble(bal);
	//			System.out.println(amount);
	//			System.out.println(balance);
	//			System.out.println(account_id);
				BankDao bank = new BankDaoImpl();
				// create func in bank dao impl to update bank_account
				boolean worked = bank.updateAccountBalance(balance + amount, id);
				// redirect at end
				if(worked) {
					//System.out.println("withdraw worked!");
					//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
					errorPage(request,response, "Deposit Successful.");
				}
				else {
					//System.out.println("withdraw failed. need to create failed redirect");
					//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
					errorPage(request,response, "Deposit Failed.");
				}
			}
			catch (Exception e) {
				//System.out.println("withdraw fell into exception; redirected to home");
				//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				errorPage(request,response, "Deposit Failed. Try again.");
			}
		}
	}
	
	public static void withdraw(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				String amt = request.getParameter("amount");
				Double amount = Double.parseDouble(amt);
				HttpSession session = request.getSession();
				
				String account_id = (String) session.getAttribute("account_id");
				int id = Integer.parseInt(account_id);
				
				String bal = (String) session.getAttribute("balance");
				Double balance = Double.parseDouble(bal);
	//			System.out.println(amount);
	//			System.out.println(balance);
	//			System.out.println(account_id);
				BankDao bank = new BankDaoImpl();
				// create func in bank dao impl to update bank_account
				boolean worked = bank.updateAccountBalance(balance - amount, id);
				// redirect at end
				if(worked) {
					//System.out.println("withdraw worked!");
					//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
					errorPage(request,response, "Withdraw Successful!");
				}
				else {
					//System.out.println("withdraw failed. need to create failed redirect");
					//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
					errorPage(request,response, "Withdraw Failed.");
				}
			}
			catch (Exception e) {
//				System.out.println("withdraw fell into exception; redirected to home");
//				response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				errorPage(request,response, "Withdraw Failed. Try again.");
			}
		}
	}
	
	public static void transfer(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			try {
				HttpSession session = request.getSession();
				
				String account_id = (String) session.getAttribute("account_id");
				int id = Integer.parseInt(account_id);
				
				String bal = (String) session.getAttribute("balance");
				Double balance = Double.parseDouble(bal);
				
				String amt = request.getParameter("amount");
				Double amount = Double.parseDouble(amt);
				String other_account = request.getParameter("otherAccount");
				int other_id = Integer.parseInt(other_account);
				BankDao bank = new BankDaoImpl();
				Account other_acc = bank.getAccountById(other_id);
				
				String other_status = other_acc.getStatus().getStatus();
				Double other_balance = other_acc.getBalance();
				
				if (other_status.equals("open")) {
				
					boolean worked = bank.updateAccountBalance(balance - amount, id);
					boolean worked2 = bank.updateAccountBalance(other_balance + amount, other_id);
					
					if (worked && worked2) {
						//System.out.println("transfer worked!");
						//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
						errorPage(request,response, "Transfer successful.");
					}
					else {
						System.out.println("transfer failed or half failed. need to create failed redirect");
						System.out.println("worked; "+worked);
						System.out.println("worked2: "+worked2);
						//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
						errorPage(request,response, "Transfer Failed. Try again.");
					}
				}
				else {
					//System.out.println("Status of other Account is Not Open");
					//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
					errorPage(request,response, "Transfer Failed. Other Account Status is Not Open.");
				}
				
			}
			catch (Exception e) {
//				System.out.println("transfer fell into exception; redirected to home");
//				response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
				errorPage(request,response, "Transfer Failed. Try again.");
			}
		}
	}
	
	public static void close(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
//			System.out.println(request.getParameter("accnum"));
			//errorPage(request,response, "test close button");
			try {
				HttpSession session = request.getSession();
				String account_id = (String) session.getAttribute("account_id");
				int id = Integer.parseInt(account_id);
				
				String bal = (String) session.getAttribute("balance");
				//Double balance = Double.parseDouble(bal);
				
				BankDao bank = new BankDaoImpl();
				boolean worked = bank.updateAccountToClosed(id);
				
				if (worked ) {
					errorPage(request,response,"Closed Account Successfully. Here is all the money left in your account: "+bal);
				}
				else {
					errorPage(request,response, "Closed Account Failed.");
				}
			}
			catch (Exception e) {
				errorPage(request,response, "Failed to close account.");
			}
			
		}
	}
	
	public static void errorPage(HttpServletRequest request, HttpServletResponse response, String errorMsg) throws IOException {
		if(request.getSession(false) == null) {
			response.sendRedirect("http://localhost:8080/FinalProjectV1/api/");
		}
		else {
			//String html_string = "<div>";
			
			String html_string = "<head>" + 
					"<title>BoA e-Page</title>" + 
					"<link rel=\"stylesheet\" href=\"file:///Users/MasterKashani/Documents/FinalProjectV1/src/main/webapp/first.css\" type=\"text/css\"/>" + 
					"</head>" + 
					"<body>"
					+ "<div>";
			
			html_string = html_string + "<form action=\"/FinalProjectV1/api/home\" method=\"post\">"
					+ "<input type=\"submit\" value=\"Home\">"
					+ "</form>";
			
			html_string = html_string + "<h1>" + errorMsg + "</h1>";
			
			
			html_string = html_string + "</div></body>";
			response.getWriter().write(html_string);
		}
	}
	
}
