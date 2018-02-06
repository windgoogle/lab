package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyDirectory extends SimpleFileVisitor<Path> {

    private Path destDir;
    private Path srcDir;
    private int rootIndex;


    public CopyDirectory(String src,String dest) {
        this.srcDir=Paths.get(src);
        this.destDir=Paths.get(dest);
        rootIndex=getDestRootIndex();
    }

    private int getDestRootIndex() {
        return srcDir.getNameCount();
    }

    private Path getDestFilePath(Path srcDir,Path destDir) {
      if(rootIndex>=srcDir.getNameCount())
          return destDir;
      return destDir.resolve(srcDir.subpath(rootIndex,srcDir.getNameCount()));

    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
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
        System.out.print("----copy [file] "+file);
        Files.copy(file,getDestFilePath(file,destDir),StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
        System.out.println(".........ok !");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("----visitFileFailed : "+file);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        CopyDirectory visitor=new CopyDirectory (args[0],args[1]);

        System.out.println("copy begin .....");
        long t1=System.currentTimeMillis();
        Files.walkFileTree(visitor.srcDir,visitor);
        long t2=System.currentTimeMillis();
        System.out.println("copy is complete , escaped time "+(t2-t1)/1000+" seconds .");
    }
}
