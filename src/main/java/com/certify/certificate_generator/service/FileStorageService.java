package com.certify.certificate_generator.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String upload(MultipartFile file, Long projectId) throws IOException;

    Resource download(String filePath);

    void delete(String filePath) throws IOException;
}