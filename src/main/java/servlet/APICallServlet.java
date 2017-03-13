package servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import myjarpackage.MyJarService;

@WebServlet(
        name = "MyServlet", 
        urlPatterns = {"/api-call"}
    )
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
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
    
    public static class APICall{
    	public String request;
    	public String response;
    	public String salesforceInstance;
    	public String sessionId;
    	public String apiVersion;
    	public String getApiVersion() {
			return apiVersion;
		}

		public void setApiVersion(String apiVersion) {
			this.apiVersion = apiVersion;
		}

		public String getResponse() {
			return response;
		}

		public void setResponse(String response) {
			this.response = response;
		}

		public String getSalesforceInstance() {
			return salesforceInstance;
		}

		public void setSalesforceInstance(String salesforceInstance) {
			this.salesforceInstance = salesforceInstance;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getApiCallRecordId() {
			return apiCallRecordId;
		}

		public void setApiCallRecordId(String apiCallRecordId) {
			this.apiCallRecordId = apiCallRecordId;
		}

		public String apiCallRecordId;

		public String getRequest() {
			return request;
		}

		public void setRequest(String request) {
			this.request = request;
		}

		@Override
		public String toString() {
			return "APICall [request=" + request + ", response=" + response + ", salesforceInstance="
					+ salesforceInstance + ", sessionId=" + sessionId + ", apiVersion=" + apiVersion
					+ ", apiCallRecordId=" + apiCallRecordId + "]";
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
         out.flush();  
         out.close();
         APICall apiCallDetails = this.parseRequestXML(jb.toString());
         this.ackowledgeSalesforce(apiCallDetails);                  
    }
    public static void main(String[] args) throws Exception {
    	
    	//new HelloServlet().ackowledgeSalesforce("https://ap2.salesforce.com/services/data/v20.0/sobjects/API_Call__c/a0O28000006pq3QEAQ","00D28000001i5TN!ARoAQMf2Umi5xM3xnmdaspxI0ZK3HBaYwWBP6pL6HBMSXIQEnDs5tBiqB3dgR.Vrv0znkq0zhcw10IkH57k9P3U5eZAhs2ZF");
    	 
    	 APICall apiCall = new HelloServlet().parseRequestXML("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <soapenv:Body>  <notifications xmlns=\"http://soap.sforce.com/2005/09/outbound\">   <OrganizationId>00D28000001i5TNEAY</OrganizationId>   <ActionId>04k28000000XZXsAAO</ActionId>   <SessionId>00D28000001i5TN!ARoAQMf2Umi5xM3xnmdaspxI0ZK3HBaYwWBP6pL6HBMSXIQEnDs5tBiqB3dgR.Vrv0znkq0zhcw10IkH57k9P3U5eZAhs2ZF</SessionId>   <EnterpriseUrl>https://ap2.salesforce.com/services/Soap/c/39.0/00D28000001i5TN</EnterpriseUrl>   <PartnerUrl>https://ap2.salesforce.com/services/Soap/u/39.0/00D28000001i5TN</PartnerUrl>   <Notification>    <Id>04l28000007DCRXAA4</Id>    <sObject xsi:type=\"sf:API_Call__c\" xmlns:sf=\"urn:sobject.enterprise.soap.sforce.com\">     <sf:Id>a0O28000006pq3QEAQ</sf:Id>     <sf:Request__c>This is going to be the request Salesforce</sf:Request__c>    </sObject>   </Notification>  </notifications> </soapenv:Body></soapenv:Envelope>");
    	System.out.println(apiCall);
	}
    
    public APICall parseRequestXML(String xml) throws ParserConfigurationException, SAXException, IOException{
    	APICall apiCall = new APICall();    	
    	DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringBuilder xmlStringBuilder = new StringBuilder();
		xmlStringBuilder.append(xml);
		ByteArrayInputStream input =  new ByteArrayInputStream(
		   xmlStringBuilder.toString().getBytes("UTF-8"));
		Document doc = builder.parse(input);
		apiCall.setSessionId(doc.getElementsByTagName("SessionId").item(0).getTextContent());
		String partnerEndpoint = doc.getElementsByTagName("PartnerUrl").item(0).getTextContent();
		apiCall.setSalesforceInstance(partnerEndpoint.split("//")[1].split("\\.")[0]);
		apiCall.setApiCallRecordId(doc.getElementsByTagName("sf:Id").item(0).getTextContent());
		apiCall.setApiVersion(partnerEndpoint.split("/u/")[1].split("/00D")[0]);
		return apiCall;
    }
    private void ackowledgeSalesforce(APICall apiCall) throws ParserConfigurationException, SAXException, IOException{
    	String json = "{\"Is_Acknowledged__c\":\"true\"}";
    	  PostMethod postRequest = new PostMethod("https://"+apiCall.getSalesforceInstance()+".salesforce.com/services/data/v20.0/sobjects/API_Call__c/"+apiCall.getApiCallRecordId()) {
    	    @Override public String getName() { return "PATCH"; }
    	  };

    	  postRequest.setRequestHeader("Authorization", "OAuth " + apiCall.getSessionId());
    	  postRequest.setRequestHeader("Content-Type", "application/json");    	  
    	  postRequest.setRequestBody(json);
    	  
    	  HttpClient httpClient = new HttpClient();
    	  int sc = httpClient.executeMethod(postRequest);
    	  System.out.println("PATCH call returned a status code of " + sc);
    	  System.out.println("Response Body-->"+postRequest.getResponseBodyAsString());        	    	
    }
    
}
