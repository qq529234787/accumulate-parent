package com.wme.web.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: accumulate-master
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
public class GeneralMapQuery {

    private Map<String,String> data = new HashMap<String,String>();

    public Map<String,String> getData() {
        return data;
    }

    public void setData(Map<String,String> data) {
        this.data = data;
    }
}
