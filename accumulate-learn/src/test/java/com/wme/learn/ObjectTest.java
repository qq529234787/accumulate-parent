package com.wme.learn;

import org.junit.Test;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2018/2/12
 * @Version: 1.0
 */
public class ObjectTest {

    @Test
    public void testA(){
        ClassLoader classLoader = ObjectTest.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
    }

}
