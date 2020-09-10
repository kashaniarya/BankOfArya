package main.java.com.kash.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController {
	
	
	public static void getMainPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			//response.sendRedirect("http://localhost:8080/FinalProjectV1/api/home");
			HomeController.getHomePage(request,response);
		}
		else {
			//request.getRequestDispatcher("/Login.html").forward(request, response);
			request.getRequestDispatcher("/Main.html").forward(request, response);
		}
	}
	
	
	public static void getExperiencePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			HomeController.getHomePage(request, response);
		}
		else {
			//request.getRequestDispatcher("/Login.html").forward(request, response);
			request.getRequestDispatcher("/Experience.html").forward(request, response);
		}
	}
	
	public static void getTechSkillsPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			HomeController.getHomePage(request, response);
		}
		else {
			//request.getRequestDispatcher("/Login.html").forward(request, response);
			request.getRequestDispatcher("/Techskills.html").forward(request, response);
		}
	}
	
	public static void getCourseworkPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(false) != null) {
			HomeController.getHomePage(request, response);
		}
		else {
			//request.getRequestDispatcher("/Login.html").forward(request, response);
			request.getRequestDispatcher("/Coursework.html").forward(request, response);
		}
	}
	
	
	
	
}
