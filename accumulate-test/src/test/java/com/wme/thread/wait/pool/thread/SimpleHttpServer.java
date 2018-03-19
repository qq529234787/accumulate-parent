package com.wme.thread.wait.pool.thread;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/22
 * @Version: 1.0
 */
public class SimpleHttpServer {

    private static DefaultThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);

    static ServerSocket serverSocket = null;
    static int port = 8080;

    public static void setPort(int port) {
        SimpleHttpServer.port = port;
    }

    public static void main(String[] args) throws IOException {
        SimpleHttpServer.start();

    }

    public static void start() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            // 接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable{
        Socket socket;

        public HttpRequestHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            if (socket != null) {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    PrintStream printStream = new PrintStream(outputStream);

                    int len = 0;
                    byte[] buff = new byte[1024];
                    FileInputStream in = new FileInputStream(new File("C:/Users/ming/Pictures/1.jpg"));
                    while ((len = in.read(buff)) != -1) {
                        printStream.write(buff, 0, len);
                    }

                    close(in, printStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    // 关闭流或者Socket
    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception ex) {
                }
            }
        }
    }

}
