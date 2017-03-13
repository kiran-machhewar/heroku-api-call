package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myjarpackage.MyJarService;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/api-call"}
    )
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	handleRequest(req,resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 handleRequest(req,resp);
    }
    
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException{
    	 String line = null;
    	 StringBuffer jb = new StringBuffer();    	 
    	 BufferedReader reader = req.getReader();
    	    while ((line = reader.readLine()) != null)
    	      jb.append(line);    	 
    	 ServletOutputStream out = resp.getOutputStream();
    	// Build a response
    	 String msg;
    	     msg = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">";
    	     msg += "<soapenv:Body>";
    	     msg += "<notificationsResponse xmlns=\"http://soap.sforce.com/2005/09/outbound\">";
    	     msg += "<Ack>true</Ack>";
    	     msg += "</notificationsResponse>";
    	     msg += "</soapenv:Body>";
    	     msg += "</soapenv:Envelope>";    	 
         out.write((msg).getBytes());
         out.flush();  
         out.close();
    }
    
}
