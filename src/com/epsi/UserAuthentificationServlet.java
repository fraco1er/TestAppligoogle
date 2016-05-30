package com.epsi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UserAuthentificationServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{ doPost(req,resp); }
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{
		UserService userService = UserServiceFactory.getUserService();

		resp.setContentType("text/html");
		
	    if(req.getUserPrincipal() != null)
	    {
	    	resp.getWriter().println("Bonjour "+req.getUserPrincipal().getName()+" <br/>"
	    			+ "<a href='"+userService.createLogoutURL(req.getRequestURI())+"' >deconnection</a>");
	    }
	    else
	    {
	    	resp.getWriter().println("Vous n'etes pas connecté <br/>"
	    			+ "<a href='"+userService.createLoginURL(req.getRequestURI())+"' >connection</a>");
	    }
	}
}
