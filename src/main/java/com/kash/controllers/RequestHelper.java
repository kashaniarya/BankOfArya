package main.java.com.kash.controllers;

import main.java.com.kash.exceptions.InvalidRoleException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class RequestHelper {
public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvalidRoleException {
		
	
		String s = request.getRequestURI();
		//System.out.println(s);
		
		
		/*
		System.out.println("asg: ");
		String[] str = s.split("/");
		for (String i : str) {
			System.out.println(i);
		}
		*/
		//System.out.println("s: "+ s);
		switch(s) {
			case "/api/login":
				LoginController.login(request,response);
				break;
				
			case "/api/logout":
				LoginController.logout(request,response);
				break;
				
			case "/api/home":
				HomeController.getHomePage(request,response);				
				break;
				
			case "/api/failedlogin":
				HomeController.getFailedLoginPage(request, response);
				break;
				
			case "/api/createAccountPage":
				HomeController.getCreateAccountPage(request,response);
				break;
				
			case "/api/create":
				LoginController.create(request,response);
				break;
				
			case "/api/failedCreateAcc":
				HomeController.getFailedCreateAccountPage(request,response);
				break;
			
			case "/api/openAccountPage":
				HomeController.getOpenAccountPage(request,response);
				break;
				
			case "/api/checkAccountsPage":
				HomeController.getCheckAccountsPage(request,response);
				break;
				
			case "/api/createNewAccountChecking":
				HomeController.createNewAccountChecking(request,response);
				break;
				
			case "/api/createNewAccountSavings":
				HomeController.createNewAccountSavings(request,response);
				break;
				
			case "/api/selectAccount":
				HomeController.selectAccount(request,response);
				break;
			
			case "/api/deposit":
				AccountController.deposit(request, response);
				break;
			
			case "/api/withdraw":
				AccountController.withdraw(request, response);
				break;
				
			case "/api/transfer":
				AccountController.transfer(request, response);
				break;
				
			case "/api/close":
				AccountController.close(request, response);
				break;
				
			case "/api/getAdminHomePage":
				AdminController.getAdminHomePage(request,response); 
				break;
				
			case "/api/showAllUsersAndAccounts":
				AdminController.showAllUsersAndAccounts(request, response);
				break;
			
			case "/api/showAllUsers":
				//System.out.println("DOES IT EVER HIT HERE");
				AdminController.showAllUsers(request, response);
				break;
			
			case "/api/showAllAccounts":
				AdminController.showAllAccounts(request, response);
				break;
				
			case "/api/adminUpdateUser":
				AdminController.adminUpdateUser(request, response);
				break;
				
			case "/api/adminUpdateAccount":
				AdminController.adminUpdateAccount(request, response);
				break;
			
			case "/api/showUserById":
				AdminController.showUserById(request,response);
				break;
				
			case "/api/showAccountById":
				AdminController.showAccountById(request,response);
				break;
				
			case "/api/showAccountByUsername":
				AdminController.showAccountByUsername(request,response);
				break;
				
			case "/api/showAccountByType":
				AdminController.showAccountByType(request,response);
				break;
				
			case "/api/showAccountByStatus":
				AdminController.showAccountByStatus(request,response);
				break;
				
			case "/api/editInfoPage":
				HomeController.getEditInfoPage(request,response);
				break;
				
			case "/api/editInfo":
				HomeController.editInfo(request,response);
				break;
				
			case "/api":
				HomeController.getLoginPage(request,response);
				break;
				
			case "/api/":
				HomeController.getLoginPage(request,response);
				break;
	
			default:
				//HomeController.getLoginPage(request,response);
				//request.getRequestDispatcher("/Main.html").forward(request, response);
				HomeController.getMainPage(request,response);
				break;
			
		}
		
		
	}
}
