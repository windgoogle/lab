package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDirectory extends SimpleFileVisitor<Path> {

    private Path destDir=Paths.get("E:\\test\\tc");
    private Path srcDir=Paths.get("E:","svn","TOMCAT_7_0_82");
    private int destRootIndex=getDestRootIndex();

    private int getDestRootIndex() {
        int srcNameCount=srcDir.getNameCount();
        return srcNameCount;
    }

    private Path getDestFilePath(Path srcDir,Path destDir) {
      if(destRootIndex>=srcDir.getNameCount())
          return destDir;
      return destDir.resolve(srcDir.subpath(destRootIndex,srcDir.getNameCount()));

    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("----preVisistDirectory : "+dir);
        Path dest=getDestFilePath(dir,destDir);
        if(Files.notExists(dest))
            Files.createDirectory(dest);
        else{
            if(!Files.isDirectory(dest)) {
                Files.delete(dest);
                Files.createDirectory(dest);
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println("----visitFile : "+file);
        Files.copy(file,getDestFilePath(file,destDir),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("----visitFileFailed : "+file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        System.out.println("----postVisitDirectory : "+dir);
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        CopyDirectory visitor=new CopyDirectory ();
        Path path= Paths.get("E:","svn","TOMCAT_7_0_82");
        System.out.println("copy begin .....");
        long t1=System.currentTimeMillis();
        Files.walkFileTree(path,visitor);
        long t2=System.currentTimeMillis();
        System.out.println("copy end , escaped time "+(t2-t1)/1000+" seconds .");
    }
}
