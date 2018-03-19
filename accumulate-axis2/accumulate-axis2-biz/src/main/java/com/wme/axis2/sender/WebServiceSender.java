package com.wme.axis2.sender;

import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebServiceSender {

	private static final Logger logger = LoggerFactory.getLogger(WebServiceSender.class);

	private WebServiceSender(){}
	
	public static OMElement sender(String wsAddress,String actionName,OMElement requestSoapMessage){
		logger.info("requestSoap: \n"+requestSoapMessage);
		long s  = System.currentTimeMillis();
		Options options = new Options();
		options.setTo(new EndpointReference(wsAddress));
		options.setAction(actionName);
		options.setProperty(HTTPConstants.CHUNKED, false);
		ServiceClient sender = null;
		OMElement result = null;
		try {
			sender = new ServiceClient();
			sender.setOptions(options);
			result = sender.sendReceive(requestSoapMessage);
			long e = System.currentTimeMillis();
			logger.info("elapsed time: " + (e - s) + "ms   response: \n" + result.toString());
		} catch (Exception axisFault) {
			logger.error(axisFault.getMessage(), axisFault);
		}finally{
			try {
				sender.cleanup();
			} catch (AxisFault e) {
				logger.error(e.getMessage(),e);
			}
		}
		return result;
	}

}
