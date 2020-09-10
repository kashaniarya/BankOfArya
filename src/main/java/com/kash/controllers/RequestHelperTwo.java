package main.java.com.kash.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.kash.exceptions.InvalidRoleException;

public class RequestHelperTwo {
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvalidRoleException {
		
		
		String s = request.getRequestURI();
		
		

		switch(s) {
//			case "/main":
//				MainController.getMainPage(request,response);
//				break;
				
			case "/main/experience":
				MainController.getExperiencePage(request,response);
				break;
				
			case "/main/techskills":
				MainController.getTechSkillsPage(request,response);
				break;
				
			case "/main/coursework":
				MainController.getCourseworkPage(request,response);
				break;
		
			default:
				MainController.getMainPage(request,response);
				break;
				
		}
		
	}
}
