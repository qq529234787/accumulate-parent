package com.wme.axis2;

import com.wme.axis2.bean.helloword.HelloWorld;
import com.wme.axis2.bean.helloword.HelloWorldRequest;
import com.wme.axis2.bean.helloword.HelloWorldResponse;
import com.wme.axis2.factory.ProcessService;
import com.wme.axis2.bean.ReturnInfo;
import com.wme.axis2.util.ObjBodyWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Wangmingen on 2015/10/29.
 */

@Component(value = "ws_helloWorld_sayHello")
public class WsHelloWorld extends ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(WsHelloWorld.class);

    @Override
    public Object execute(String xml) {
        logger.info("requestXml: "+xml);
        HelloWorldRequest request = ObjBodyWriter.readFromXMLString(xml, HelloWorldRequest.class);

        HelloWorldResponse response = new HelloWorldResponse();
        ReturnInfo returnInfo = new ReturnInfo();
        returnInfo.setCode(200);
        returnInfo.setMsg("success");
        response.setReturnInfo(returnInfo);

        HelloWorld helloWord = new HelloWorld();
        helloWord.setName(request.getName());
        response.setHelloWord(helloWord);
        return response;
    }


}
