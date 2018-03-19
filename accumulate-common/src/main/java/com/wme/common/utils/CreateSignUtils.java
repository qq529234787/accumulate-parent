package com.wme.common.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/17
 * @Version: 1.0
 */
public class CreateSignUtils {

    /**
     * 生成sign
     * @param paramMap
     * @param appKey
     * @return
     */
    public static String createHttpApiSign(Map<String, String> paramMap, String appKey) {
        try {
            Set<String> keySet = paramMap.keySet();
            ArrayList<String> keys = new ArrayList<>(keySet);
            Collections.sort(keys);
            StringBuilder signString = new StringBuilder(appKey);
            for (String k : keys) {
                if (k.equals("_sign")) {
                    continue;
                }
                signString.append(k).append(paramMap.get(k));
            }
            signString.append(appKey);
            String sign = getSign(signString.toString());
            return sign.toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSign(String signString) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signString.getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
