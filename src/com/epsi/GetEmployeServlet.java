package com.epsi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class GetEmployeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		doPost(req,resp);
	}
	
	private String getKey(HttpServletRequest req)
	{
		String key=req.getRequestURI().toString();
		Map<String, String[]> parameters = req.getParameterMap();
		for(String parameter : parameters.keySet()) {
	        String[] values = parameters.get(parameter);
	        if(values[0]!=null && values[0]!="")
	        { key+="&"+parameter+"="+values[0]; }        
		}
		
		return key;
	}
	
	private ArrayList<Entity> tryUseMemcache(HttpServletRequest req)
	{
		ArrayList<Entity> ListEmployees;
		
		String key = getKey(req);
	    MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	    Object value=cache.get(key);
	    if(value==null)
	    { return null; }
	    
	    return (ArrayList<Entity>) value;
	}
	
	private void saveInMemcache(HttpServletRequest req, ArrayList<Entity> ListEmployees)
	{
		String key = getKey(req);
	    MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
	    cache.put(key, ListEmployees, Expiration.byDeltaSeconds(60));		
	}
	
	private ArrayList<Entity> tryDb(HttpServletRequest req)
	{
		Query query=new Query("Employee");
		
		Map<String, String[]> parameters = req.getParameterMap();
		for(String parameter : parameters.keySet()) {
	        String[] values = parameters.get(parameter);
	        if(values[0]!=null && values[0]!="")
	        {
	        	query.setFilter(new FilterPredicate(parameter,FilterOperator.EQUAL, values[0]));
		    }        
		}
		
		DatastoreService datastore= DatastoreServiceFactory.getDatastoreService();
		
		
		PreparedQuery pq = datastore.prepare(query);
		ArrayList<Entity> ListEmployees=new ArrayList<Entity>();
		
		for(Entity result : pq.asIterable() )
		{ ListEmployees.add(result); }
	
		
		return ListEmployees;
	}
	
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		ArrayList<Entity> ListEmployees;
		boolean fromcache=true;
		
		ListEmployees=tryUseMemcache(req);
		if(ListEmployees==null)
		{
			ListEmployees=tryDb(req);
			saveInMemcache(req,ListEmployees);
			fromcache=false;
		}
		

		resp.setContentType("text/html");
		resp.getWriter().println("from cache: "+fromcache+"<br/>");
		for(Entity result : ListEmployees )
		{
			String lastName=(String)result.getProperty("FirstName");
			String firstName=(String)result.getProperty("FirstName");
			Key key=result.getKey();
			resp.getWriter().println(firstName+" "+lastName+" / "+key+"<br/>");
		}

		RequestDispatcher view = req.getRequestDispatcher("SearchEmployee.html");
		view.forward(req, resp);
	}
}
