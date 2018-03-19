package com.wme.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangmingne on 2015/5/13.
 */
public class DateUtils {

    public static String dateToString(Date time) {
        return dateToString(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToString(Date time, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String ctime = formatter.format(time);
        return ctime;
    }
}
