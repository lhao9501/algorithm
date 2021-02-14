package com.mutithread.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    /**
     * 复制文件
     * @param src
     * @param dest
     */
    public static void copyFileUseNio(String src, String dest) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(dest);
            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();

            // 分配 buffer  默认为写模式
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 从通道读入数据 并写入buffer中
            while (inChannel.read(buffer) != -1) {
                // 切换成读模式
                buffer.flip();
                // 从buffer中读取数据并写入到通道中
                outChannel.write(buffer);
                // 清空buffer 并切换成写模式  为下一次while循环写入做准备
                buffer.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
                outChannel.close();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
