package com.mutithread.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOEchoServer {

    public static void echo() {
        try {
            // 服务器端 绑定端口
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("server start...");

            // 循环监听 客户端的连接
            while (true) {
                // 阻塞式接收客户端
                Socket socket = serverSocket.accept();
                System.out.println("one client link... " + socket);

                // 每连接每线程  由于是阻塞式 所以开启线程对应每个连接
                new Thread(() -> {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String msg = null;
                        // read 阻塞读
                        while ((msg = bufferedReader.readLine()) != null) {
                            System.out.println("receive message: " + msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
