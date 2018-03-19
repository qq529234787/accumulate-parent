package com.wme.axis2.factory;

import com.wme.axis2.util.ObjBodyWriter;
import org.apache.axiom.om.OMElement;
import org.springframework.context.ApplicationContext;

public abstract class ProcessService {

	protected final static String DEFAULT_CHARSET = "UTF-8";
	protected String remoteIp;
	private ApplicationContext context;

	public ProcessService() {

	}
	
	public ProcessService(ApplicationContext context) {
		super();
		this.context = context;
	}

	public abstract Object execute(String xml);

	public OMElement execute(OMElement omElement) {
		Object response = execute(omElement.toString());
		String toXml = ObjBodyWriter.convertBeanToXml(response);
		OMElement element = ObjBodyWriter.toOMElement(toXml, DEFAULT_CHARSET);
		//OMElement element = ObjBodyWriter.convertBeanToOMElement(response, "ROOT");
		return element;
	}

	public ProcessService setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
		return this;
	}

	protected <T> T getBeansOfType(Class<T> clazz) {
		return (T) context.getBeansOfType(clazz).values().iterator().next();

	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

}
