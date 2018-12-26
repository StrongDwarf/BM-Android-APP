package com.app.bm.bm.utils;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *专门用于将输入流转换成一个字节数组的utils类
 */
public class StreamTool {

    public static byte[] read(InputStream inputStream) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buf))!=-1){
            baos.write(buf, 0 ,len);
        }
        baos.close();
        return buf;
    }

}