package net.guardiandev.terrabuild.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileUtil {
    public static File newFileWithContent(String parentFolder, String fileName, String extension, String content) throws IOException {
        return newFileWithContent(parentFolder, String.format("%s.%s", fileName, extension), content);
    }

    public static File newFileWithContent(String parentFolder, String fileName, String content) throws IOException {
        return newFileWithContent(String.format("%s/%s", parentFolder, fileName), content);
    }

    public static File newFileWithContent(String path, String content) throws IOException {
        File file = newFile(path);
        Files.write(file.toPath(), List.of(content.split("\\r?\\n")), StandardCharsets.UTF_8);
        return file;
    }



    public static File newFile(String parentFolder, String fileName, String extension) throws IOException {
        return newFile(parentFolder, String.format("%s.%s", fileName, extension));
    }

    public static File newFolder(String parentFolder, String folderName) throws IOException {
        File file = getFolder(parentFolder, folderName);
        file.mkdir();
        return file;
    }

    public static File newFile(String parentFolder, String fileName) throws IOException {
        return newFile(String.format("%s/%s", parentFolder, fileName));
    }

    public static File newFile(String path) throws IOException {
        File file = getFile(path);
        file.createNewFile();
        return file;
    }



    public static File getFile(String parentFolder, String fileName, String extension) {
        return getFile(parentFolder, String.format("%s.%s", fileName, extension));
    }

    public static File getFolder(String parentFolder, String folderName) {
        return getFile(String.format("%s/%s", parentFolder, folderName));
    }

    public static File getFile(String parentFolder, String fileName) {
        return getFile(String.format("%s/%s", parentFolder, fileName));
    }

    public static File getFile(String path) {
        return new File(path);
    }
}
