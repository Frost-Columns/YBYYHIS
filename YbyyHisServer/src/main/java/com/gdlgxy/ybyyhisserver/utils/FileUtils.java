package com.gdlgxy.ybyyhisserver.utils;

import java.io.*;
import java.text.SimpleDateFormat;

public class FileUtils {
    public static boolean delFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static void createDirector(String path) {
        File file = new File(path);
        if(!file.exists()) {
            file.mkdir();
        }
    }

    public static String sysTimeName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssms");
        return format.format(System.currentTimeMillis());
    }

    public static String getSuffix(String name) {
        int index = name.lastIndexOf(".");
        return name.substring(index);
    }

    public static String parentName(String name) {
        File file = new File(name);
        return file.getParent();
    }

    public static void writeByte(String name,byte[] b) throws IOException {
        OutputStream out =new FileOutputStream(name);
        out.write(b);
        out.flush();
        out.close();
    }

    public static byte[] readByte(String name) throws IOException{
        InputStream in  = new FileInputStream(name);
        byte[] b = new byte[in.available()];
        in.read(b);
        in.close();
        return b;
    }

}
