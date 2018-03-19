package com.wme.thread.wait.pool.connection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/20
 * @Version: 1.0
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize){
        if(initialSize>0){
            for (int i=0; i<initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                // 连接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个连接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    // 在mills内无法获取到连接，将会返回null
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            if (mills <= 0) {
                while (pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();

            }else {
                long future = System.currentTimeMillis() + mills;
                long waitTime = mills;
                while (pool.isEmpty() && waitTime>0){
                    pool.wait(mills);
                    waitTime = future - System.currentTimeMillis();
                }
                if(!pool.isEmpty()){
                    return pool.removeFirst();
                }
            }
        }

        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        ConnectionPool pool = new ConnectionPool(1);
        Connection connection1 = pool.fetchConnection(0);
        Connection connection2 = pool.fetchConnection(0);
        System.out.print(connection2);
        pool.releaseConnection(connection1);
        Connection connection3 = pool.fetchConnection(0);

    }

}


class ConnectionDriver {
    static class ConnectionHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }
    // 创建一个Connection的代理，在commit时休眠100毫秒
    public static final Connection createConnection() {
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                new Class[] { Connection.class }, new ConnectionHandler());
    }
}