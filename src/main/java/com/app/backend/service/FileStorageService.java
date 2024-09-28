package com.app.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads");
    public void store(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file.");
        }
        Path destinationFile = this.rootLocation.resolve(
                Paths.get(file.getOriginalFilename()))
                .normalize().toAbsolutePath();
        if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
            // This is a security check
            throw new IOException("Cannot store file outside current directory.");
        }
        try (var inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile);
        }
    }
}