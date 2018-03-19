package com.wme.axis2;

import com.wme.axis2.sender.WebServiceSender;
import com.wme.axis2.util.ObjBodyWriter;
import org.apache.axiom.om.OMElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Wangmingen on 2015/10/22.
 */
public class WsSendTest {

    private static final Logger logger = LoggerFactory.getLogger(WsSendTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("<ROOT>" +
                "<appAccess>" +
                "<userName>t3_iphone</userName>" +
                "<password>t3_iphone</password>" +
                "<sign>69649baa95a93e36195f0f6b8971b701</sign>" +
                "<timestamp>20121105120423</timestamp>" +
                "</appAccess>" +
                "<name>hello world</name>" +
                "</ROOT>");

        OMElement requestSoap = ObjBodyWriter.toOMElement(buffer.toString(), "UTF-8");
        String url = "http://localhost:8080/services/helloWorldWS";
        OMElement sayHello = WebServiceSender.sender(url, "sayHello", requestSoap);
        System.err.print(sayHello.toString());
    }

}
