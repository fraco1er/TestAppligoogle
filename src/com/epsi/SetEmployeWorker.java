package com.epsi;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class SetEmployeWorker extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity employee = new Entity("Employee");
		
		String FirstName=req.getParameter("FirstName");
		String LastName=req.getParameter("LastName");
				
		employee.setProperty("FirstName", FirstName);
		employee.setProperty("LastName", LastName);
		datastore.put(employee);
		
		resp.setContentType("text/plain");
		resp.getWriter().println("employee set");
	}
}
