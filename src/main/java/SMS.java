
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@WebServlet("/sms")
public class SMS extends HttpServlet {
	private static final long serialVersionUID = 1L;   
	
	private String parameters = "{"
			+ "\"sid\":\"\","
			+ "\"authToken\":\"\","
			+ "\"toNumber\":\"\","
			+ "\"fromNumber\":\"\","
			+ "\"text\":\"hello my friend\""
			+ "}";
    
	 public static void main(String[] args) {
		 SMS sms = new SMS();
		 JsonObject params = new JsonParser().parse(sms.parameters).getAsJsonObject();
		 System.out.println(sms.process(params, params.get("text").getAsString()));
	 }

	private String process(JsonObject credConfig, String text) {
		String output = "";				
		try {
			String sid = credConfig.get("sid").getAsString();
			String authToken = credConfig.get("authToken").getAsString();
			String fromNumber = credConfig.get("fromNumber").getAsString();
			String toNumber = credConfig.get("toNumber").getAsString();
			Twilio.init(sid,authToken);
		    Message message = Message.creator(
		    		new PhoneNumber(toNumber),
		    		new PhoneNumber(fromNumber),text).create();		    
			output = message.getSid();				
		}catch (Exception e) {
			output = e.getMessage();
		}		
		return output;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sessionId = request.getParameter("sessionId");
		String messageText = request.getParameter("text");
		JsonObject credConfig = Set.settingMap.get(sessionId)==null?new JsonObject():Set.settingMap.get(sessionId);		
		
		
		String output = process(credConfig, messageText);
		
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		out.println(output);		
		out.close();
	}




}
