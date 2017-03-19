package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import beans.OutboundMessage;
import service.OutboundMessageParser;
import service.OutboundMessageProcessor;


@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/api-call"}
    )
public class APICallServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	 ServletOutputStream out = resp.getOutputStream();
         out.write(("OK Kiran Change 1\n ").getBytes());
         out.flush();
         out.close();
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
    	 try {
			handleRequest(req,resp);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
           
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException, ParserConfigurationException, SAXException{
    	 String line = null;
    	 StringBuffer jb = new StringBuffer();    	 
    	 BufferedReader reader = req.getReader();
    	    while ((line = reader.readLine()) != null)
    	      jb.append(line);    	 
    	 System.out.println("Request -->"+ jb);
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
         
         OutboundMessage outboundMessage = OutboundMessageParser.parseXML(jb.toString()); 
         OutboundMessageProcessor.processOutboundMessage(outboundMessage);
         out.flush();  
         out.close();         
                           
    }
 
    
}