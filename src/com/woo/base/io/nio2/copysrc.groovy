package com.woo.base.io.nio2

import java.nio.file.*


class Copy {
    public static String destDir="E:\\svn\\tomee-src_t";
    public static void main(String[] args) {

        Path srcDir= Paths.get("E:","svn","tw7-tools\\Tomee_Converter\\tomcat_tomee_src\\tomee-src.zip_converted");
        Path desDir= Paths.get("E:","svn","tomee-src");
        System.out.println("copy.........");
        long t1=System.currentTimeMillis();
        copyDir(srcDir,desDir);
        long t2=System.currentTimeMillis();
        System.out.println("escaped time "+(t2-t1)/1000+" seconds.");
    }




    public static void copyDir(Path srcDir, Path desDir) {
        if(Files.notExists(srcDir)) {
            System.out.println("源目录不存在！");
            return;
        }

        if(Files.notExists(desDir)){
            try {
                Files.createDirectory(desDir);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        int desNameCount=desDir.getNameCount();
        int srcNameCount=srcDir.getNameCount();
        int startIndex=Math.max(desNameCount,srcNameCount);
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(srcDir, "*")

            for (Path entry: stream) {
                int nameCount=entry.getNameCount();
                Path newPath=desDir.resolve(entry.subpath(startIndex,nameCount));
                //System.out.println("-----"+newPath);
                if(Files.isDirectory(entry)){
                    if(Files.notExists(newPath)) {
                        Files.createDirectory(newPath);
                    }else {
                        if(!Files.isDirectory(newPath)){
                            Files.delete(newPath);
                            Files.createDirectory(newPath);
                        }
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