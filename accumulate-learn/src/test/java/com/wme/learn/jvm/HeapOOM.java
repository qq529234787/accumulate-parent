package com.wme.learn.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: OutOfMemoryError,简称OOM
 *      VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @Auther: ming
 * @Date: 2018/2/26
 * @Version: 1.0
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }

    }

}
