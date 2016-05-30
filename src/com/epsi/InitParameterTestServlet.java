package com.epsi;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class InitParameterTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		Enumeration paramsName=getServletConfig().getInitParameterNames();
		
		resp.setContentType("text/plain");
		
		
		while(paramsName.hasMoreElements())
		{
			String parameterName=(String) paramsName.nextElement();
			resp.getWriter().println("parameter: "+parameterName
					+" / value: "+getServletConfig().getInitParameter(parameterName));
		}
		
		
	}
}
