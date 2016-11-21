
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;

@WebServlet("/help")
public class Help extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonArray output = new JsonArray();
		
		output.add("set sid <SID> - set sid for Twilio service");
		output.add("set authToken <AUTHTOKEN> - set authToken for Twilio");
		output.add("set toNumber <TO_NUMBER> - set toNumber with country code");
		output.add("set fromNumber <FROM_NUMBER> - set fromNumber with country code");
	
		
		output.add("sms <BODY_OF_SMS> - The SMS will be send with this body to the mentioned toNUmber");
    	
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.println(output);
		
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
