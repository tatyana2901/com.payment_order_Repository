package com.payment_order.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {
    public static void saveToFile(String fileName, List<String> lines) throws IOException {
        Path file = Paths.get(fileName);
        Files.write(file, lines, StandardCharsets.UTF_8);
    }
}
