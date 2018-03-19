package com.wme.axis2.bean.helloword;


import com.wme.axis2.bean.ReturnInfo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Wangmingen on 2015/10/30.
 */

@XmlRootElement(name = "ROOT")
public class HelloWorldResponse implements Serializable {

    private ReturnInfo returnInfo;

    private HelloWorld helloWord;

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }

    public HelloWorld getHelloWord() {
        return helloWord;
    }

    public void setHelloWord(HelloWorld helloWord) {
        this.helloWord = helloWord;
    }
}
