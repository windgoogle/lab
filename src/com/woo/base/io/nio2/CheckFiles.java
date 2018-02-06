package com.woo.base.io.nio2;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CheckFiles extends SimpleFileVisitor<Path> {

    private Path destDir;
    private Path srcDir;
    private int rootIndex;
    private long missedCount=0;
    private long errorCount=0;
    private long fileCount=0;

    public CheckFiles(String src,String dest) {
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
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path dest=getDestFilePath(file,destDir);
        //System.out.println("----"+dest);
        if(Files.notExists(dest)) {
            System.out.println("[file] " + dest + ".........missed !");
            missedCount++;
        }else if(Files.isDirectory(dest)) {
                System.out.println(dest+" is a directory!");
                errorCount++;

        }else {
            long srcSize=Files.size(file);
            long destSize=Files.size(dest);
            if(srcSize!=destSize) {
                System.out.println("[file] " + dest + ".........size ["+destSize+"] not matched source file size["+srcSize+"] !");
                errorCount++;
            }else {
                fileCount++;
            }


        }
        return FileVisitResult.CONTINUE;
    }


    public static void main(String[] args) throws IOException {
        CheckFiles visitor=new CheckFiles (args[0],args[1]);

        System.out.println("check begin .....");
        long t1=System.currentTimeMillis();
        Files.walkFileTree(visitor.srcDir,visitor);
        long t2=System.currentTimeMillis();
        String msg=" ";
        if(visitor.errorCount>0||visitor.missedCount>0) {
            msg = "copy failed[success:"+visitor.fileCount+",error:"+visitor.errorCount+",miss:"+visitor.missedCount+"]!";
        }else {
            msg = "copy success [success:"+visitor.fileCount+"] !";
        }

        System.out.println("check is  complete ,escaped time " + (t2 - t1) / 1000 + " seconds......."+ msg);
    }
}
