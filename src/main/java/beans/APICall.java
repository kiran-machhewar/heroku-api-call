package beans;

public class APICall {
	public String apiCallRecordId;
	public String request;
	public String response;
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "APICall [apiCallRecordId=" + apiCallRecordId + ", request=" + request + ", response=" + response
				+ ", endpoint=" + endpoint + "]";
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String endpoint;

	public String getApiCallRecordId() {
		return apiCallRecordId;
	}

	public void setApiCallRecordId(String apiCallRecordId) {
		this.apiCallRecordId = apiCallRecordId;
	}
	
}
