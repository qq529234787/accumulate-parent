package com.wme.thread.wait.pool.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/22
 * @Version: 1.0
 */
public class DefaultThreadPoolTimeTest {

    private static AtomicInteger poolNum = new AtomicInteger();
    private static AtomicInteger noPoolNum = new AtomicInteger();
    private static long count = 10000;

    public static void main(String[] args) throws InterruptedException {
        DefaultThreadPool pool = new DefaultThreadPool(5);
        long start = System.currentTimeMillis();
        for (int i=0;  i< count; i++) {
            MyJob myJob = new MyJob();
            pool.execute(myJob);
        }
        while (poolNum.longValue( ) < count) {
            //System.out.println("pool time :"+num.doubleValue());
        }
        long end = System.currentTimeMillis();
        System.out.println("pool time :" + (end-start));



        // 未使用线程池的
        long start2 = System.currentTimeMillis();
        for (int i=0;  i< count; i++) {
            int i1 = noPoolNum.incrementAndGet();
            System.out.println("MyJob name :" + Thread.currentThread().getName() + " random=="+i1);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end2 = System.currentTimeMillis();
        System.out.println("pool time :" + (end2-start2));

    }


    static class MyJob implements Runnable{

        @Override
        public void run() {
            int i = poolNum.incrementAndGet();
            System.out.println("MyJob name :" + Thread.currentThread().getName() + " random=="+i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
