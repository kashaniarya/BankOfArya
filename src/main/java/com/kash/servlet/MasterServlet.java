package main.java.com.kash.servlet;

import main.java.com.kash.controllers.RequestHelper;
import main.java.com.kash.exceptions.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestHelper.process(request,response);
		} catch (IOException | ServletException | InvalidRoleException e) {
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RequestHelper.process(request,response);
		} catch (IOException | ServletException | InvalidRoleException e) {
			e.printStackTrace();
		}
	}
}
