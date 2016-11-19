package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

@WebServlet("/set")
public class Set extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    protected static Map<String, JsonObject> settingMap = new HashMap<String, JsonObject>();



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = request.getParameter("sessionId");
		
		String sid = request.getParameter("sid");
		String fromNumber = request.getParameter("fromNumber");
		String toNumber = request.getParameter("toNumber");
		String authToken = request.getParameter("authToken");

		
		JsonObject configCred = settingMap.get(sessionId) == null?new JsonObject():settingMap.get(sessionId);
		
		if(sid != null)
			configCred.addProperty("sid", sid);
		if(authToken != null)
			configCred.addProperty("authToken", authToken);
		if(fromNumber != null)
			configCred.addProperty("fromNumber", fromNumber);
		if(toNumber != null)
			configCred.addProperty("toNumber", toNumber);

		settingMap.put(sessionId, configCred);
		String output = "SET operation successful";		
    	response.setContentType("text/html");
		PrintWriter out = response.getWriter();		
		out.println(output);		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
