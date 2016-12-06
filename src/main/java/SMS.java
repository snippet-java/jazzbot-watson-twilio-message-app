
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
	
	
	private static final String SID = "sid";
	private static final String AUTH_TOKEN = "authtoken";
	private static final String TO_NUMBER = "tonumber";
	private static final String FROM_NUMBER = "fromnumber";
	
	private static final long serialVersionUID = 1L;   
	
	
 private String parameters = "{"
            + "\""+SID+"\":\"AC84e49acd218452ef869aa44b7ddb316e\","
            + "\""+AUTH_TOKEN+"\":\"f7d66a3ab7aa59bc84af694828861df4\","
            + "\""+TO_NUMBER+"\":\"+17045267752\","
            + "\""+FROM_NUMBER+"\":\"+19804306890\","
            + "\"text\":\"hello my friend\""
            + "}";
    
	 public static void main(String[] args) {
		 SMS sms = new SMS();
		 JsonObject params = new JsonParser().parse(sms.parameters).getAsJsonObject();
		 String result = sms.process(params, params.get("text").getAsString());
		 System.out.println(result);
	 }

	private String process(JsonObject credConfig, String text) {
		String output = "";				
		try {
			String sid = credConfig.get(SID).getAsString();
			String authtoken = credConfig.get(AUTH_TOKEN).getAsString();
			String fromnumber = credConfig.get(FROM_NUMBER).getAsString();
			String tonumber = credConfig.get(TO_NUMBER).getAsString();
			Twilio.init(sid,authtoken);
		    Message message = Message.creator(
		    		new PhoneNumber(tonumber),
		    		new PhoneNumber(fromnumber),text).create();		    
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
