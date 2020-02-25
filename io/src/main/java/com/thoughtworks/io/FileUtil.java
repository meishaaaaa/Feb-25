package com.thoughtworks.io;

import java.io.*;

public class FileUtil {

    /**
     * 完成复制文件夹方法:
     * 1. 把给定文件夹from下的所有文件(包括子文件夹)复制到to文件夹下
     * 2. 保证to文件夹为空文件夹，如果to文件夹不存在则自动创建
     * <p>
     * 例如把a文件夹(a文件夹下有1.txt和一个空文件夹c)复制到b文件夹，复制完成以后b文件夹下也有一个1.txt和空文件夹c
     */
    public static void copyDirectory(File from, File to) throws IOException {
        //判断源目录是不是一个目录
        if (!from.isDirectory()) {
            return;
        }
        //如果原目标目录存在，删除目标目录原有文件和文件夹
        if (to.exists()) {
            deleteFile(to);
        }
        //创建目的目录
        to.mkdir();

        //获取源目录下的File
        File[] files = from.listFiles();
        for (File file : files) {
            //取得源文件和目标文件的绝对路径，以String的形式
            String strFrom = from.getAbsolutePath() + File.separator + file.getName();
            String strTo = to.getAbsolutePath() + File.separator + file.getName();

            //如果file是目录，进行递归
            if (file.isDirectory()) {
                File strFrom1 = new File(strFrom);
                File strTo1 = new File(strTo);
                copyDirectory(strFrom1, strTo1);
            }
            //如果file是文件，调用复制文件的方法
            if (file.isFile()) {
                File fromFile = new File(strFrom);
                File toFile = new File(strTo);
                copyFile(fromFile, toFile);
            }
        }
    }


    public static void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream fis = new FileInputStream(fromFile);
        FileOutputStream fos = new FileOutputStream(toFile);

        byte[] bytes = new byte[1024];
        int n = 0;
        while ((n = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, n);
        }
        fis.close();
        fos.flush();
        fos.close();
    }

    public static void deleteFile(File toFile) throws IOException {
        File[] files = toFile.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            } else {
                deleteFile(file);
            }
        }
        toFile.delete();
    }
}


