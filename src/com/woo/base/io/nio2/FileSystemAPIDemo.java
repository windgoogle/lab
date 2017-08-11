package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemAPIDemo {

    public static void main(String[] args) throws IOException {
        //usePath();
        listFiles();
    }


    public static  void usePath() {
        Path path1 = Paths.get("folder1", "sub1");
        Path path2 = Paths.get("folder2", "sub2");
        path1.resolve(path2); //folder1\sub1\folder2\sub2
        path1.resolveSibling(path2); //folder1\folder2\sub2
        path1.relativize(path2); //..\..\folder2\sub2
        path1.subpath(0, 1); //folder1
        path1.startsWith(path2); //false
        path1.endsWith(path2); //false
        Paths.get("folder1/./../folder2/my.text").normalize(); //folder2\my.text
    }

    public static void listFiles() throws IOException {
        Path path = Paths.get("d:","svn\\tw6","ejb","openejb-core\\src\\main\\java\\com\\tongweb\\tongejb\\");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.java")) {
            for (Path entry: stream) {
                //使用entry
                System.out.println(entry);
            }
        }
    }
}
