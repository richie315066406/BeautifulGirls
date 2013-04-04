package org.lansir.beautifulgirls.utils;

import java.io.File;

public class FileUtil {

    /**
     *
     * @param dir Directory or file
     * @return space size of all files in this dir, default value in Bytes; return 0 if incorrect dir.
     */
    public static long getFileSize(File dir) //取得文件夹大小
    {
        long size = 0;
        if (dir == null) return size;
        if (!dir.isDirectory()) return dir.length();
        File flist[] = dir.listFiles();
        if (flist == null) return 0;
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            }
            else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     *
     * @param dir Directory or file
     * @return space size of all files in this dir, in MBytes.
     */
    public static double getFileSizeMB(File dir) {
        double mb = getFileSize(dir) / 1024. / 1024.;
        return Double.parseDouble(NumberUtil.fractionDigits(mb, 2));
    }

    /**
     * delete file or dirs(including all files under that dir)
     * @param file
     */
    public static void deleteFileOrDir(File file) {
        if (file != null && file.exists()) {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                file.delete();
            }
            else {  // 为目录时调用删除目录方法
                deleteDir(file);
            }
        }
    }

    /**
     * delete dir and all its children
     * @param dir
     */
    private static void deleteDir(File dir) {
        if (dir != null && dir.exists() && dir.isDirectory()) {
            // 删除文件夹下的所有文件(包括子目录)
            File[] files = dir.listFiles();
            for (File filedir : files) {
                // 删除子文件
                if (filedir.isFile()) {
                    filedir.delete();
                } // 删除子目录
                else {
                    deleteDir(filedir);
                }
            }
            dir.delete();
        }
    }


}
