package com.mutithread.nio.reactormodel.singlethread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;


public class Handler implements Runnable {

    final SocketChannel socketChannel;
    final SelectionKey selectionKey;

    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;


    public Handler(Selector selector, SocketChannel channel) throws IOException {
        this.socketChannel = channel;
        // 非阻塞
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        // 将 handler 作为callback对象
        selectionKey.attach(this);

        // 注册 read就绪事件
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete() {
        /* ... */
        return false;
    }

    boolean outputIsComplete() {
        /* ... */
        return false;
    }

    void process() {
        /* ... */
        return;
    }

    void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComplete()) {
            process();

            state = SENDING;

            // 接收写就绪事件
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    void send() throws IOException {
        socketChannel.write(output);
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

    @Override
    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
