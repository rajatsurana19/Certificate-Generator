
package com.certify.certificate_generator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path rootLocation;

    public LocalFileStorageService(
            @Value("${app.storage.root}") String storageRoot) {

        this.rootLocation = Paths.get(storageRoot)
                .toAbsolutePath()
                .normalize();
    }

    @Override
    public String upload(MultipartFile file, Long projectId)
            throws IOException {

        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException("Invalid filename");
        }

        String extension = originalName.substring(
                originalName.lastIndexOf(".")
        ).toLowerCase();

        if (!extension.matches("\\.(pdf|png|jpg|jpeg)")) {
            throw new IllegalArgumentException("Unsupported file extension");
        }

        Path projectDirectory = rootLocation
                .resolve("templates")
                .resolve("project-" + projectId)
                .normalize();

        Files.createDirectories(projectDirectory);

        String storedName = UUID.randomUUID() + extension;

        Path destination = projectDirectory
                .resolve(storedName)
                .normalize();

        if (!destination.startsWith(projectDirectory)) {
            throw new IOException("Invalid file path");
        }

        Files.copy(file.getInputStream(), destination);

        return rootLocation.relativize(destination).toString();
    }

    @Override
    public Resource download(String filePath) {
        try {
            Path file = rootLocation.resolve(filePath)
                    .normalize();

            if (!file.startsWith(rootLocation)) {
                throw new IllegalArgumentException("Invalid file path");
            }

            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            }

            throw new RuntimeException("File not found");

        } catch (IOException exception) {
            throw new RuntimeException("Could not read file", exception);
        }
    }

    @Override
    public void delete(String filePath) throws IOException {

        Path file = rootLocation.resolve(filePath).normalize();

        if (!file.startsWith(rootLocation)) {
            throw new IllegalArgumentException("Invalid file path");
        }

        Files.deleteIfExists(file);
    }
}
