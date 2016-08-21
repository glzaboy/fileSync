package com.crudelion;

import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by guliuzhong on 2016/8/21.
 */
public class FileSync {
    public void copydir() {

    }

    public void sync(String Source, String Target) {
        sync(new File(Source), new File(Target), new HashSet<String>(), new HashSet<String>(), true);
    }

    public void sync(String Source, String Target, Set<String> sourceExclude) {
        sync(new File(Source), new File(Target), sourceExclude, new HashSet<String>(), true);
    }

    public void sync(String Source, String Target, Set<String> sourceExclude, Set<String> targetExclude) {
        sync(new File(Source), new File(Target), sourceExclude, targetExclude, true);
    }

    public void sync(String Source, String Target, Set<String> sourceExclude, Set<String> targetExclude, Boolean targetClean) {
        sync(new File(Source), new File(Target), sourceExclude, targetExclude, targetClean);
    }

    public void sync(File fileSource, File fileTarget, Set<String> sourceExclude, Set<String> targetExclude, Boolean targetClean) {
        if (sourceExclude.contains(fileSource.getAbsolutePath())) {
            System.out.println("排队源文件" + fileSource.getAbsoluteFile());
            deldir(fileTarget);
            return;
        }
        if (sourceExclude.contains(fileSource.getName())) {
            System.out.println("排队源文件" + fileSource.getName());
            deldir(fileTarget);
            return;
        }
        if (fileTarget.isDirectory() && fileSource.isFile()) {
            System.out.println("目标文件和源文件类型不一致删除目标文件" + fileTarget.getAbsolutePath());
            deldir(fileTarget);
        }
        if (fileTarget.isFile() && fileSource.isDirectory()) {
            System.out.println("目标文件和源文件类型不一致删除目标文件" + fileTarget.getAbsolutePath());
            deldir(fileTarget);
        }
        if (!fileTarget.exists()) {
            if (fileSource.isDirectory()) {
                System.out.println("创建目录" + fileTarget.getAbsolutePath());
                fileTarget.mkdir();
            } else {
                System.out.println("创建文件" + fileTarget.getAbsolutePath());
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = new FileInputStream(fileSource);
                    outputStream = new FileOutputStream(fileTarget);
                    byte[] by = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(by, 0, 1024)) != -1) {
                        outputStream.write(by, 0, len);
                    }
                    inputStream.close();
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileTarget.setLastModified(fileSource.lastModified());
                return;
            }
        }
        if (fileSource.isFile()) {
            try {
                //open 文件开始写
                System.out.println("替换文件" + fileTarget.getAbsolutePath());
                InputStream inputStream = new FileInputStream(fileSource);
                OutputStream outputStream = new FileOutputStream(fileTarget);
                byte[] by = new byte[102400];
                int len = 0;
                while ((len = inputStream.read(by, 0, 102400)) != -1) {
                    outputStream.write(by, 0, len);
                }
                inputStream.close();
                outputStream.close();
                fileTarget.setLastModified(fileSource.lastModified());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileSource.isDirectory()) {
            //目录处理,包含创建目录下面的文件和子目录,删除不需要的文件。
            File[] listFiles = fileSource.listFiles();
            ArrayList<String> SourceFileList = new ArrayList<>();
            for (File file2 : listFiles) {
                if (file2.getName().equals(".") || file2.equals("..")) {
                    continue;
                }
                SourceFileList.add(file2.getName());
                File file2tart = new File(fileTarget.getAbsolutePath() + "/" + file2.getName());
                sync(file2, file2tart, sourceExclude, targetExclude, targetClean);
            }
            //
            File[] listFilesTarget = fileTarget.listFiles();
            for (File fileT : listFilesTarget) {
                if (!SourceFileList.contains(fileT.getName())) {
                    if (!targetExclude.contains(fileT.getAbsolutePath()) && !targetExclude.contains(fileT.getName())) {
                        System.out.println("清理目标文件" + fileTarget.getAbsoluteFile());
                        deldir(fileT);
                    }
                }
            }
        }
        fileTarget.setLastModified(fileSource.lastModified());
    }

    public void deldir(String dir) {
        System.out.println("清理目录" + dir);
        File file = new File(dir);
        if (!file.exists()) {
            System.out.println("目标不存在");
            return;
        }
        deldir(file);
    }

    private void deldir(File file) {
        if (file.isFile()) {
            file.delete();
            System.out.println("删除文件:" + file.getAbsoluteFile());
            return;
        } else if (file.isDirectory()) {
            System.out.println(file.getName() + "是目录进入目录删除");
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                if (file2.getName().equals(".") || file2.equals("..")) {
                    continue;
                }
                deldir(file2);
            }
            file.delete();
            return;
        }
    }
}
