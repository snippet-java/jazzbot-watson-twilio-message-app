
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
	
	private static final String SID = "sid";
	private static final String AUTH_TOKEN = "authtoken";
	private static final String TO_NUMBER = "totumber";
	private static final String FROM_NUMBER = "fromnumber";
	private static final long serialVersionUID = 1L;
    protected static Map<String, JsonObject> settingMap = new HashMap<String, JsonObject>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = request.getParameter("sessionId");		
		String sid = request.getParameter(SID);
		String authtoken = request.getParameter(AUTH_TOKEN);
		String tonumber = request.getParameter(TO_NUMBER);
		String fromnumber = request.getParameter(FROM_NUMBER);
		
		JsonObject configCred = settingMap.get(sessionId) == null?new JsonObject():settingMap.get(sessionId);
		
		if(sid != null)
			configCred.addProperty(SID, sid);
		if(authtoken != null)
			configCred.addProperty(AUTH_TOKEN, authtoken);
		if(fromnumber != null)
			configCred.addProperty(FROM_NUMBER, fromnumber);
		if(tonumber != null)
			configCred.addProperty(TO_NUMBER, tonumber);

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
