package com.woo.base.io.nio2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystemAPIDemo {

    public static void main(String[] args) throws IOException {
        //usePath();
       // listFiles();
       // manipulateFiles();
        long t1=System.currentTimeMillis();
        copyDir(Paths.get("e:","svn\\twnt"),Paths.get("e:","test\\tw6"));
        long t2=System.currentTimeMillis();
        System.out.println("耗时："+(t2-t1)/1000+" 秒");
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
        Path path = Paths.get("e:","svn\\twnt");
        Path path2 = Paths.get("e:","svn\\twnt");
        Path path3 = Paths.get("e:","test\\tw6");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*")) {
            for (Path entry: stream) {
                //使用entry
                System.out.println(entry);

               // System.out.println("----root:"+entry.getRoot());
                //System.out.println("-----parent : "+entry.getParent().toAbsolutePath());
                //System.out.println("-----subpath : "+entry.subpath(0,3));
                //System.out.println("-----relative : "+entry.relativize(path2));
                System.out.println("-----getNameCount : "+path.getNameCount());
                int nameCount=entry.getNameCount();
                int p_nameCount=path2.getNameCount();
                Path newPath=entry.subpath(p_nameCount,nameCount);
                newPath=path3.resolve(newPath);

                System.out.println("------"+newPath);
                File dir=entry.toFile();
                if(dir.isDirectory()) {
                    File des=newPath.toFile();
                    if(!des.exists()){
                        des.mkdir();
                    }
                }else {
                    //Path newFile=Files.createFile(newPath);
                    Files.copy(entry, newPath,StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static void manipulateFiles() throws IOException {
        Path newFile = Files.createFile(Paths.get("new.txt").toAbsolutePath());
        List<String> content = new ArrayList<String>();
        content.add("Hello");
        content.add("World");
        Files.write(newFile, content, Charset.forName("UTF-8"));
        Files.size(newFile);
        byte[] bytes = Files.readAllBytes(newFile);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Files.copy(newFile, output);
        Files.delete(newFile);
    }

    public void addFileToZip2(File zipFile, File fileToAdd) throws IOException {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        try (FileSystem fs = FileSystems.newFileSystem(URI.create("jar:" + zipFile.toURI()), env)) {
            Path pathToAddFile = fileToAdd.toPath();
            Path pathInZipfile = fs.getPath("/" + fileToAdd.getName());
            Files.copy(pathToAddFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
        }
    }


    public static void copyDir(Path srcDir,Path desDir) {

        int desNameCount=desDir.getNameCount();


        try (DirectoryStream<Path> stream = Files.newDirectoryStream(srcDir, "*")) {
            for (Path entry: stream) {
                int nameCount=entry.getNameCount();
                Path newPath=desDir.resolve(entry.subpath(desNameCount,nameCount));

                if(Files.isDirectory(entry)){
                    if(!Files.exists(newPath)) {
                        Files.createDirectory(newPath);
                    }
                    copyDir(entry,newPath);

                }else {
                    Files.copy(entry, newPath,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.COPY_ATTRIBUTES);
                }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
