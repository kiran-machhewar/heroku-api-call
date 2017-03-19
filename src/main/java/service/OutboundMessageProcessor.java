package service;

import beans.APICall;
import beans.OutboundMessage;

public class OutboundMessageProcessor {
	public static void processOutboundMessage(OutboundMessage outboundMessage){
		for(APICall apiCall : outboundMessage.getApiCalls()){
			APICallProcessor apiCallProcessor = new APICallProcessor(outboundMessage,apiCall);						
		}
		
	}
}
