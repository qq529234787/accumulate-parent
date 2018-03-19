package com.wme.rms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wangmingen on 2015/9/14.
 */
public class DualEntity implements Serializable {

    private Date sysdate;

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }
}
