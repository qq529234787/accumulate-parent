package com.wme.axis2.factory;

import com.wme.axis2.WsHelloWorld;
import com.wme.axis2.util.IPUtil;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Wangmingen on 2015/10/29.
 */

@Service(value = "helloWorldWS")
public class HelloWorldWS {

    public OMElement sayHello(OMElement requestSoap) {
        return AbstractProcessFactory.getInstance(HelloWordFactory.class)
                .loopup(WsHelloWorld.class)
                .setRemoteIp(getClientIp()).execute(requestSoap);
    }


    public String getClientIp() {
        MessageContext mc = MessageContext.getCurrentMessageContext();
        HttpServletRequest request = (HttpServletRequest) mc
                .getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
        return IPUtil.getIpAddress(request);
    }
}
