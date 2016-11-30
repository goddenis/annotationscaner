package com.xrm;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by goddenis on 28.11.2016.
 */
public class MemoryClassLoader extends ClassLoader {
    private final byte[] array;

    public MemoryClassLoader(ClassLoader parent, InputStream inputStream) {
        super(parent);
        ByteBuffer bb = ByteBuffer.allocate(4*1024);
        byte[] buf = new byte[1024];
        int readedBytes = -1;

        try {
            while ((readedBytes = inputStream.read(buf)) != -1) {
                bb.put(buf, 0, readedBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        array = bb.array();
    }

    public Class findClass(String name){
        try {
            return defineClass(name, array, 0, array.length);
        } catch (Throwable e){
            return null;
        }

    }
}
