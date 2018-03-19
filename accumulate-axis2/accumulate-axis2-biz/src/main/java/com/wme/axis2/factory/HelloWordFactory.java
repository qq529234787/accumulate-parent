package com.wme.axis2.factory;

import org.springframework.context.ApplicationContext;

/**
 * Created by Wangmingen on 2015/10/29.
 */
public class HelloWordFactory extends AbstractProcessFactory {

    @Override
    public ProcessService loopup(Class<? extends ProcessService> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProcessService loopup(Class<? extends ProcessService> clazz, ApplicationContext context) {
        try {
            return clazz.getConstructor(ApplicationContext.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
