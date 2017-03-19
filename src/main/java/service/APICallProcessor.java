package service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import beans.APICall;
import beans.OutboundMessage;

public class APICallProcessor implements Runnable {
	
	public OutboundMessage outboundMessage;
	public APICall apiCall;
	
	public static void main(String[] args) {
				
		OutboundMessage outboundMessage = new OutboundMessage();
		APICall apiCall  = new APICall();
		apiCall.setEndpoint("http://headers.jsontest.com/");
		new APICallProcessor(outboundMessage,apiCall);
	}		
	
	public APICallProcessor(OutboundMessage outboundMessage, APICall apiCall) {
		super();
		this.outboundMessage = outboundMessage;
		this.apiCall = apiCall;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
  	  
  	  GetMethod getMethod = new GetMethod(apiCall.getEndpoint());

  	    //getMethod.setRequestHeader("Authorization", "OAuth " + apiCall.getSessionId());
  	  getMethod.setRequestHeader("Content-Type", "application/json");    	    	
  	  
  	  HttpClient httpClient = new HttpClient();
  	  int sc;
	  try {
			sc = httpClient.executeMethod(getMethod);
			System.out.println("PATCH call returned a status code of " + sc);
			System.out.println("Response Body-->"+getMethod.getResponseBodyAsString());
			apiCall.setResponse(getMethod.getResponseBodyAsString());
			System.out.println("Updating response-->"+getMethod.getResponseBodyAsString());
			updateResponseToSalesforce();
	  } catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
		
    }
	
	public void updateResponseToSalesforce() throws HttpException, IOException{
	  String json = "{\"Is_Acknowledged__c\":\"true\",\"Response__c\":\""+apiCall.getResponse().replace("\"", "\\\"").replace("\n", "").replace("\r", "")+"\"}";
	  
	  System.out.println("Updating salesforce with Request-->"+json);
  	  PostMethod postRequest = new PostMethod("https://"+outboundMessage.getSalesforceInstance()+".salesforce.com/services/data/v20.0/sobjects/API_Call__c/"+apiCall.getApiCallRecordId()) {
  	    @Override public String getName() { return "PATCH"; }
  	  };
  	  postRequest.setRequestHeader("Authorization", "OAuth " + outboundMessage.getSessionId());
  	  postRequest.setRequestHeader("Content-Type", "application/json");    	  
  	  postRequest.setRequestBody(json);
  	  
  	  HttpClient httpClient = new HttpClient();
  	  int sc = httpClient.executeMethod(postRequest);  	  
  	  System.out.println("Response Body-->"+postRequest.getResponseBodyAsString()); 
	}				
	
}
