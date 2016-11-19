package com.ibm.sample.jazzbot.app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@WebServlet("/sms")
public class SMS extends HttpServlet {
	private static final long serialVersionUID = 1L;   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String messageText = request.getParameter("text");
		JsonObject cred = Set.settingMap.get(sessionId)==null?new JsonObject():Set.settingMap.get(sessionId);		
		JsonObject output = new JsonObject();		
		try {
			
			String sid = cred.get("sid").getAsString();
			String authToken = cred.get("authToken").getAsString();
			String fromNumber = cred.get("fromNumber").getAsString();
			String toNumber = cred.get("toNumber").getAsString();
			
			Twilio.init(sid,authToken);
		    Message message = Message.creator(
		    		new PhoneNumber(toNumber),
		    		new PhoneNumber(fromNumber),
		    		messageText).create();
			output.addProperty("result",message.getSid());	
		} catch (Exception e) {
			output.addProperty("err", e.getMessage());
		}
		
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
