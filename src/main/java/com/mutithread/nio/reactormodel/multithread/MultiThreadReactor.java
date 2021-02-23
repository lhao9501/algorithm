package com.mutithread.nio.reactormodel.multithread;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.sun.corba.se.pept.transport.Acceptor;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiThreadReactor implements Runnable {

    // subReactors集合  一个selector代表一个subReactor
    Selector[] selectors = new Selector[2];
    final ServerSocketChannel serverSocketChannel;
    int next = 0;

    public MultiThreadReactor(int port) throws Exception {
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
        selectionKey.attach(new Acceptor());
    }

    void dispatch(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (null != attachment) {
            attachment.run();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < selectors.length; i++) {
                        selectors[i].select();
                        Set<SelectionKey> selectionKeys = selectors[i].selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
                        while (keyIterator.hasNext()) {
                            dispatch(keyIterator.next());
                        }

                        selectionKeys.clear();
                    }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    class Acceptor {
        public synchronized void run() throws Exception {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel) {
                new MultiThreadHandler(selectors[next], socketChannel);
            }

            if (++next == selectors.length) {
                next = 0;
            }
        }
    }
}
