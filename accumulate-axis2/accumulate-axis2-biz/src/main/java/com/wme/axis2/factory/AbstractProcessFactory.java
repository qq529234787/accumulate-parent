package com.wme.axis2.factory;

import org.springframework.context.ApplicationContext;


public abstract class AbstractProcessFactory {
	
	/**
	 * 不允许外部创建
	 */
	protected AbstractProcessFactory() {
		
	}
	
	public abstract ProcessService loopup(Class<? extends ProcessService> clazz);

	public abstract ProcessService loopup(Class<? extends ProcessService> clazz, ApplicationContext context);

	public static AbstractProcessFactory getInstance(Class<? extends AbstractProcessFactory> clazz) {
		AbstractProcessFactory inter = null;
		try {
			inter = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inter;
	}

}
