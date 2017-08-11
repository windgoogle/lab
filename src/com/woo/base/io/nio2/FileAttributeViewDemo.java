package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileAttributeViewDemo {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("F:","test","monitor.schema");
        useFileAttributeView(path);
        System.out.println("++++++++++++++++++++++++++++++++++++");
        checkUpdatesRequired(path);
    }


    public static void useFileAttributeView(Path path) throws IOException {

        DosFileAttributeView view = Files.getFileAttributeView(path, DosFileAttributeView.class);
        if (view != null) {
            DosFileAttributes attrs = view.readAttributes();
            System.out.println("readonly: "+attrs.isReadOnly());
            System.out.println("system: "+attrs.isSystem());
            System.out.println("airchive: "+attrs.isArchive());
            System.out.println("hidden: "+attrs.isHidden());
            System.out.println("directory: "+attrs.isDirectory());
            System.out.println("createTime: "+attrs.creationTime());
            System.out.println("size: "+attrs.size());
            System.out.println("lastAccessTime: "+attrs.lastAccessTime());
            System.out.println("isSymbolicLink: "+attrs.isSymbolicLink());
            System.out.println("fileKey: "+attrs.fileKey());

        }
    }

    public static void checkUpdatesRequired(Path path) throws IOException {

        FileTime lastModifiedTime = (FileTime) Files.getAttribute(path, "lastModifiedTime");
        System.out.println("lastModifiedTime:"+lastModifiedTime);
        System.out.println("owner:"+Files.getOwner(path));
        System.out.println("filestroe:"+Files.getFileStore(path));
        System.out.println("---lastModifiedTime:"+Files.getLastModifiedTime(path));
        System.out.println("---isSymbolicLink:"+Files.isSymbolicLink(path));
        System.out.println("---directory:"+Files.isDirectory(path));
        System.out.println("---readable:"+Files.isReadable(path));
        System.out.println("---writeable:"+Files.isWritable(path));
        System.out.println("---hidden:"+Files.isHidden(path));
        System.out.println("---executable:"+Files.isExecutable(path)); //windows/Linux上执行不同
        System.out.println("---size:"+Files.size(path));
        System.out.println("---probeContentType:"+Files.probeContentType(path)); //windows/Linux上执行不同

    }
}
