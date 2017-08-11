package com.woo.base.io.nio2;

import java.io.IOException;
import java.nio.file.*;

public class WatchServiceDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get("F:","test").toAbsolutePath();
        calculate(path);
    }

    public static void calculate(Path path) throws IOException, InterruptedException {
        WatchService service = FileSystems.getDefault().newWatchService();
        path.register(service, StandardWatchEventKinds.ENTRY_CREATE);
        while (true) {
            WatchKey key = service.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                Path createdPath = (Path) event.context();
                createdPath = path.resolve(createdPath);
                long size = Files.size(createdPath);
                System.out.println(createdPath + " ==> " + size);
            }
            key.reset();

        }
    }
}
