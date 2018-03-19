package com.wme.learn.jvm;

/**
 * @Title: accumulate-parent
 *      VM Args：-Xss128k
 * @Auther: ming
 * @Date: 2018/2/26
 * @Version: 1.0
 */
public class JavaVMStackSOF {
    private int stackLength = 1;
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }
    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" +
                    oom.stackLength);
            throw e;
        }
    }
}
