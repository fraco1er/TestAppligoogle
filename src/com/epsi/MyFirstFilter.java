package com.epsi;

import java.io.Console;
import java.io.IOException;

import javax.servlet.*;

import com.google.appengine.api.NamespaceManager;

public class MyFirstFilter implements Filter {
		
	public void doFilter (ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		if(NamespaceManager.get() == null)
		{
			String namespace="KerbalNamespaceProgram";
			NamespaceManager.set(namespace);
		}
		chain.doFilter(req, resp);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
