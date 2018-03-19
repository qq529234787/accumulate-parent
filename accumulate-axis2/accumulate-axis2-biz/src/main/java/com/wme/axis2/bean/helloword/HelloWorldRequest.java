package com.wme.axis2.bean.helloword;



import com.wme.axis2.bean.AppAccess;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Wangmingen on 2015/10/30.
 */

@XmlRootElement(name = "ROOT")
public class HelloWorldRequest implements Serializable {

    private AppAccess appAccess;

    private String name;

    public AppAccess getAppAccess() {
        return appAccess;
    }

    public void setAppAccess(AppAccess appAccess) {
        this.appAccess = appAccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
