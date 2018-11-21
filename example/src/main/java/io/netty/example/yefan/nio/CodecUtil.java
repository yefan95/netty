package io.netty.example.yefan.nio;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author yefan
 * @date 2018/11/21
 * <p>
 * 编解码工具类
 */
public class CodecUtil {
    public static ByteBuffer read(SocketChannel channel) {
        //注意，不考虑拆包的处理
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int count = channel.read(buffer);
            if (count == -1) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void write(SocketChannel channel, String content) {
        //写入buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            buffer.put(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //写入Channel
        buffer.flip();
        try {
            //注意，不考虑写入超过Channel缓存区的问题
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String newString(ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        System.arraycopy(buffer.array(), buffer.position(), bytes, 0, buffer.remaining());
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
