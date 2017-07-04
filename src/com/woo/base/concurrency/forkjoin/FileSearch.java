package com.woo.base.concurrency.forkjoin;

import java.io.File;
import java.io.IOException;

/**
 * Created by huangfeng on 2017/3/17.
 */
public class FileSearch {

    public String path;

    public FileSearch(String path) {
        this.path=path;
    }

   public  File[] listFiles() {
       File currentDir=new File(path);
       if(currentDir.isDirectory())
          return currentDir.listFiles();
       else
           return null;
   }

    public static void main(String[] args) throws IOException {
        String testpath="F:/";
        FileSearch fs=new FileSearch(testpath);
        File [] files=fs.listFiles();
        for(File file :files)
            System.out.println(file.getCanonicalPath()+" is directory : "+file.isDirectory());
    }

}
