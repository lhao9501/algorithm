package com.mutithread.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIOEchoServer {

    public static void echo() {

        ServerSocketChannel serverSocketChannel = null;
        try {
            Selector selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8090));
            // 设置成非阻塞
            serverSocketChannel.configureBlocking(false);
            // 首先把服务器端的监听事件注册到 selector中
            SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            register.attach(new Object());
            Object attachment = register.attachment();

            // System.out.println("server start...");
            log.info("server start...");

            while (true) {
                // 把所有注册的连接 都传到内核中  在内核中进行遍历  返回有用的连接信息
                // 第一次调用 是把服务器端socket传到内核
                // 阻塞调用  知道至少有一个通道发生了注册的IO事件
                selector.select();
                // 获取就绪的channel信息
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                if (null != selectionKeys) {
                    // 遍历就绪channel
                    Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey selectionKey = keyIterator.next();

                        if (selectionKey.isAcceptable()) {
                            // 监听是否有客户端连接  一定是服务器channel
                            ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                            // 得到客户端channel
                            SocketChannel socketChannel = serverChannel.accept();
                            // 客户端设置非阻塞
                            socketChannel.configureBlocking(false);
                            // System.out.println("one client link...");
                            log.info("one client link...");
                            // 把得到的客户端channel的读事件 注册到selector中
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            // 可读的客户端channel
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            // 把传过来的信息 通过channel写到buffer中
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            while (socketChannel.read(buffer) != -1) {
                                buffer.flip();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                buffer.clear();
                                String msg = new String(bytes, "UTF-8").replace("\r\n", "");
                                // System.out.println("receive message: " + msg);
                                log.info("receive message: {}", msg);
                            }
                            socketChannel.close();
                        }
                        // 当前的channel完毕后 要剔除 防止下一次循环被重复处理
                        keyIterator.remove();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
