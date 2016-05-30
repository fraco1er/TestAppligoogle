package com.epsi;

import java.io.IOException;

import javax.servlet.http.*;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskQueuePb.TaskQueueFetchQueuesResponse.Queue;

@SuppressWarnings("serial")
public class SetEmployeServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		com.google.appengine.api.taskqueue.Queue queue=QueueFactory.getDefaultQueue();
		queue.add(
			TaskOptions
			.Builder.withUrl("/SetEmployeWorker")
			.param("FirstName", req.getParameter("FirstName"))
			.param("LastName", req.getParameter("LastName"))
		);
		
	}
}
