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
			default:
				HomeController.getMainPage(request,response);
				break;
				
		}
		
	}
}
