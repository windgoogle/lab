package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class SvnInfoCleanVisitor extends SimpleFileVisitor<Path> {
    private boolean cleanMark = false;

    private boolean isSvnFolder(Path dir) {
        return ".svn".equals(dir.getFileName().toString());
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if (isSvnFolder(dir)) {
            cleanMark = true;
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
        if (e == null && cleanMark) {
            Files.delete(dir);
            if (isSvnFolder(dir)) {
                cleanMark = false;
            }
        }
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (cleanMark) {
            Files.setAttribute(file, "dos:readonly", false);
            Files.delete(file);
        }
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) throws IOException {
        SvnInfoCleanVisitor visitor=new SvnInfoCleanVisitor();
        Path path= Paths.get("E:","svn","TOMCAT_7_0_82");
        Files.walkFileTree(path,visitor);
    }
}
