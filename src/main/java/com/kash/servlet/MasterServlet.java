package com.kash.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kash.controllers.RequestHelper;
import com.kash.exceptions.InvalidRoleException;


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
