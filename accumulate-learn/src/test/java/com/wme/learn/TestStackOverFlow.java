package com.wme.learn;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import static org.junit.Assert.assertNotNull;

/**
 * @Title: 栈的大小是有一定的限制，这个可能出现StackOverFlow问题
 * @Auther: ming
 * @Date: 2018/2/12
 * @Version: 1.0
 */
public class TestStackOverFlow {

    /*public static void main(String [] args){
        Recursive recursive = new Recursive();
        int doit = recursive.doit(10000);
        System.out.println(doit);
    }*/

    @Test
    public void testSoftReference() throws InterruptedException {
        String str = "test";
        WeakReference<String> softreference = new WeakReference<String>(str);
        str = null;
        System.gc();
        assertNotNull(softreference.get());
        System.out.print(softreference.get());
    }

}

class Recursive{
    public int doit(int t){
        if(t<=1)
            return 1;
        else {
            return t + doit(t-1);
        }
    }
}
