package com.wme.web.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.wme.common.utils.XSSReplaceUtils;
import com.wme.common.utils.http.HttpClientUtil;
import com.wme.web.entity.GeneralMapQuery;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: accumulate-master
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
@Controller
@RequestMapping("/http/")
public class HttpDataController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 访问uri白名单
     */
    private static List<String> uriList = new ArrayList<String>(){{
        add("tags/getAllTags");
    }};

    /**
     * 需要传入内置变量_host标示需要请求哪个url，其他变量照常传就行，最终结果会直接返回（json格式）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/data",produces = "text/html;charset=UTF-8")
    public Object index(GeneralMapQuery generalMapQuery, HttpServletRequest request, HttpServletResponse response) {
        String result ="";
        String nocache = request.getParameter("_nocache");
        if(generalMapQuery!=null){
            String methodType = request.getMethod();
            Map<String, String> data = generalMapQuery.getData();
            if(data!=null){
				/* Xss过滤 */
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    data.put(entry.getKey(), XSSReplaceUtils.antiXSS(entry.getValue()));
                }

                //logger.Info("get generalMapQuery data [" + data + "]");
                String host = data.get("_host");
                String domain= "";
                try{
                    domain = new URL(host).getHost();
                }catch (Exception e){
                    logger.error("host error:"+host);
                }
                String uriString = host.replace("http://", "");
                uriString = uriString.substring(uriString.indexOf("/")+1);
                if(!uriList.contains(uriString) || (host.indexOf("#") > -1) || host.contains("@")
                        || host.contains("[") || host.contains("]")|| host.contains("%")){
                    result="{'code':'403'}";

                }else{
                    /*if(uriDecryptpcpopclub.contains(uriString)){  //解密cookie
                        String userId = getLoginUserId(request,response);
                        if(StringUtils.isBlank(userId)){
                            return "{\"returncode\":403,\"message\":\"登录状态异常\"}";
                        }
                        data.put("userId",userId);
                    }*/
                    if(StringUtils.isBlank(result)){
                        try {
                            result = executeFormHttpRequest(result, methodType, data, host);
                        } catch (Exception e) {
                            logger.error("mesage:["+e.getMessage()+"];error host  [" + host + "];error data:["+data+"]");
                        }
                    }
                }
            }
        }
        return result ;
    }

    private String executeFormHttpRequest(String result, String methodType, Map<String, String> data, String host) throws Exception {
        Boolean isJsonContentType = isJsonContentType(data);
        if(isJsonContentType && methodType.equals("POST")){
            String json = data.get("data");
            result = HttpClientUtil.postJSON(host, json);
        }else{
            URI uri = new URI(host);
            List<NameValuePair> params= new ArrayList<>();

            for (Map.Entry<String, String> entry : data.entrySet()) {
                NameValuePair e = new BasicNameValuePair(entry.getKey(), entry.getValue());
                params.add(e);
            }

            if(methodType.equals("POST")) {
                result = HttpClientUtil.post(host, data);
            }else{
                String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
                result = HttpClientUtil.get(uri.toString() + "?" + str);
            }
        }
        return result;
    }

    private Boolean isJsonContentType(Map<String, String> data) {
        if(data == null || data.isEmpty()){
            return false;
        }
        String dataObject = data.get("data");
        if(dataObject == null){
            return false;
        }
        try {
            Map jsonObject = JSON.parse(dataObject,Map.class);
            if(jsonObject == null){
                return false;
            }
            Object isJsonObj = jsonObject.get("isJsonContentType");
            if(isJsonObj == null){
                return false;
            }
            return BooleanUtils.toBoolean(isJsonObj.toString());
        } catch (Exception e) {
            return false;
        }
    }

}
