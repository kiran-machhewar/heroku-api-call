package beans;

import java.util.ArrayList;

public class OutboundMessage {
	public String salesforceInstance;
	public String sessionId;
	public String apiVersion;
	public ArrayList<APICall> apiCalls = new ArrayList<APICall>();
	
	public ArrayList<APICall> getApiCalls() {
		return apiCalls;
	}

	public void setApiCalls(ArrayList<APICall> apiCalls) {
		this.apiCalls = apiCalls;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
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
	

	@Override
	public String toString() {
		return "APICall [ salesforceInstance="
				+ salesforceInstance + ", sessionId=" + sessionId + ", apiVersion=" + apiVersion;
	}

}
