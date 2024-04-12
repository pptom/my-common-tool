package com.example.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ptomjie
 * @since 2024-01-25 22:16
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File files = new File("D:\\MySamllP\\2024\\2021");
        File[] files1 = files.listFiles();
        for (File file : files1) {
            System.out.println(file.getName());
            if (file.isDirectory()) {
                extracted(file);
            }
            System.out.println("-------------------");
        }
    }

    private static void extracted(File file) throws IOException {
        for (File listFile : file.listFiles()) {
            System.out.println(listFile.getName());
            System.out.println(listFile.getParent());
            Path sourcePath = Paths.get(listFile.getPath());
            for (int i = 2; i <= 12; i++) {
                String[] split = listFile.getName().split("-");
                String newName = i + "-" + split[1];
                System.out.println(listFile.getParent() + File.separator + newName);
                Path targetPath = Paths.get(listFile.getParent() + File.separator + newName);
                Files.copy(sourcePath, targetPath);
            }
        }
    }
}
