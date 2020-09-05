package com.kash.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kash.exceptions.InvalidRoleException;



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
			case "/FinalProjectV1/api/login":
				LoginController.login(request,response);
				break;
				
			case "/FinalProjectV1/api/logout":
				LoginController.logout(request,response);
				break;
				
			case "/FinalProjectV1/api/home":
				HomeController.getHomePage(request,response);				
				break;
				
			case "/FinalProjectV1/api/failedlogin":
				HomeController.getFailedLoginPage(request, response);
				break;
				
			case "/FinalProjectV1/api/createAccountPage":
				HomeController.getCreateAccountPage(request,response);
				break;
				
			case "/FinalProjectV1/api/create":
				LoginController.create(request,response);
				break;
				
			case "/FinalProjectV1/api/failedCreateAcc":
				HomeController.getFailedCreateAccountPage(request,response);
				break;
			
			case "/FinalProjectV1/api/openAccountPage":
				HomeController.getOpenAccountPage(request,response);
				break;
				
			case "/FinalProjectV1/api/checkAccountsPage":
				HomeController.getCheckAccountsPage(request,response);
				break;
				
			case "/FinalProjectV1/api/createNewAccountChecking":
				HomeController.createNewAccountChecking(request,response);
				break;
				
			case "/FinalProjectV1/api/createNewAccountSavings":
				HomeController.createNewAccountSavings(request,response);
				break;
				
			case "/FinalProjectV1/api/selectAccount":
				HomeController.selectAccount(request,response);
				break;
			
			case "/FinalProjectV1/api/deposit":
				AccountController.deposit(request, response);
				break;
			
			case "/FinalProjectV1/api/withdraw":
				AccountController.withdraw(request, response);
				break;
				
			case "/FinalProjectV1/api/transfer":
				AccountController.transfer(request, response);
				break;
				
			case "/FinalProjectV1/api/close":
				AccountController.close(request, response);
				break;
				
			case "/FinalProjectV1/api/getAdminHomePage":
				AdminController.getAdminHomePage(request,response); 
				break;
				
			case "/FinalProjectV1/api/showAllUsersAndAccounts":
				AdminController.showAllUsersAndAccounts(request, response);
				break;
			
			case "/FinalProjectV1/api/showAllUsers":
				//System.out.println("DOES IT EVER HIT HERE");
				AdminController.showAllUsers(request, response);
				break;
			
			case "/FinalProjectV1/api/showAllAccounts":
				AdminController.showAllAccounts(request, response);
				break;
				
			case "/FinalProjectV1/api/adminUpdateUser":
				AdminController.adminUpdateUser(request, response);
				break;
				
			case "/FinalProjectV1/api/adminUpdateAccount":
				AdminController.adminUpdateAccount(request, response);
				break;
			
			case "/FinalProjectV1/api/showUserById":
				AdminController.showUserById(request,response);
				break;
				
			case "/FinalProjectV1/api/showAccountById":
				AdminController.showAccountById(request,response);
				break;
				
			case "/FinalProjectV1/api/showAccountByUsername":
				AdminController.showAccountByUsername(request,response);
				break;
				
			case "/FinalProjectV1/api/showAccountByType":
				AdminController.showAccountByType(request,response);
				break;
				
			case "/FinalProjectV1/api/showAccountByStatus":
				AdminController.showAccountByStatus(request,response);
				break;
				
			case "/FinalProjectV1/api/editInfoPage":
				HomeController.getEditInfoPage(request,response);
				break;
				
			case "/FinalProjectV1/api/editInfo":
				HomeController.editInfo(request,response);
				break;
	
			default:
				//System.out.println("NO I BET IT HITS HERE");
				HomeController.getLoginPage(request,response);
				break;
			
		}
		
		
	}
}
