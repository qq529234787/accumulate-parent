package com.wme.axis2.controller;

import com.wme.axis2.factory.ProcessService;
import com.wme.axis2.util.IPUtil;
import com.wme.axis2.util.ObjBodyWriter;
import org.codehaus.plexus.util.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wangmingen on 2015/10/14.
 */

@Controller
public class HttpParamToWebServiceController {

    @RequestMapping(value = "/json/*" )
    public void toJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String requestURI = request.getRequestURI();
        String packageName = requestURI.replaceFirst("^/json/", "");

		/*
		 * url e.g : ?method=xx&root=info&alias=person:p&p:name=ee&p:age=10&status=10
		 * transform to :
		 * <info>
		 * 		<person>
		 * 			<name>ee</name>
		 * 			<age>ee</age>
		 * 		<person>
		 * 		<status>10</status>
		 * </info>
		 */
        Enumeration<String> parameterNames = request.getParameterNames();
        // 参数名
        String methodName = request.getParameter("method");
        // JSONP
        String callback = request.getParameter("callback");
        // 别名转化
        String[] aliasMapS = request.getParameterValues("alias");
        Map<String, String> alias = new HashMap<>();
        if(aliasMapS != null && aliasMapS.length > 0){
            for (String aliasMap : aliasMapS) {
                String[] token = aliasMap.split(":");
                String nodeName = token[0];
                String aliasName = token[1];
                alias.put(aliasName, nodeName);
            }
        }

        // 创建ROOT 节点
        Element rootE = DocumentHelper.createElement("ROOT");
        Document document = DocumentHelper.createDocument(rootE);

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String param = request.getParameter(paramName);
            if (Arrays.asList(new String[]{"method", "root", "alias", "callback", "_"})
                    .contains(paramName)) {
                continue;
            }
            transNode(rootE, paramName, param, alias);
        }

        String beanName = "ws_"+packageName+"_"+methodName;
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        if (methodName != null && context.containsBean(beanName)) {
            response.setContentType("text/javascript;charset=UTF-8");
            ProcessService wsService = getBean(beanName,context);
            wsService.setRemoteIp(IPUtil.getIpAddress(request));
            StringWriter out = new StringWriter();
            XMLWriter xmlWriter = new XMLWriter(out);
            xmlWriter.write(document);
            xmlWriter.close();
            Object responseObj = wsService.execute(out.toString());
            out.close();
            String result = ObjBodyWriter.convertBeanToXml(responseObj);
            if (StringUtils.isNotEmpty(callback)) {
                result = callback + "("+result+")";
            }

            response.getWriter().write(result);
        }

    }

    private Element transNode(Element element, String paramName, String param, Map<String, String> alias) {
        if (paramName.contains(":")) {
            // 分级 person:name
            String[] tokens = paramName.split(":",2);

            String nodeName = tokens[0];
            String nParamName = tokens[1];

            // 别名转化
            if (alias.containsKey(nodeName))
                nodeName = alias.get(nodeName);

            // 保证节点存在
            Element nodeE = element.element(nodeName);
            if(nodeE == null){
               nodeE = element.addElement(nodeName);
            }
            // 继续下一层
            return transNode(nodeE, nParamName, param, alias);
        }

        Element childElement = element.addElement(paramName);
        childElement.setText(param);
        return childElement;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz, ApplicationContext context) {
        return (T)context.getBeansOfType(clazz).values().iterator().next();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, ApplicationContext context) {
        return (T)context.getBean(beanName);
    }

}
