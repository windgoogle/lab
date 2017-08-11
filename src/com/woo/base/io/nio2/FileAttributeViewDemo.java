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
        useFileAttributeView();
        System.out.println("++++++++++++++++++++++++++++++++++++");
        checkUpdatesRequired();
    }


    public static void useFileAttributeView() throws IOException {
        Path path = Paths.get("F:","test","monitor.schema");
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

    public static void checkUpdatesRequired() throws IOException {
        Path path = Paths.get("F:","test","monitor.schema");
        FileTime lastModifiedTime = (FileTime) Files.getAttribute(path, "lastModifiedTime");
        System.out.println("lastModifiedTime:"+lastModifiedTime);
        System.out.println("owner:"+Files.getOwner(path));
    }
}
