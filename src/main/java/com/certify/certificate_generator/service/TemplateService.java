
package com.certify.certificate_generator.service;

import com.certify.certificate_generator.dto.response.TemplateResponse;
import com.certify.certificate_generator.entity.Project;
import com.certify.certificate_generator.entity.Template;
import com.certify.certificate_generator.exception.ResourceNotFoundException;
import com.certify.certificate_generator.repository.ProjectRepository;
import com.certify.certificate_generator.repository.TemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Locale;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final ProjectRepository projectRepository;
    private final FileStorageService fileStorageService;

    public TemplateService(
            TemplateRepository templateRepository,
            ProjectRepository projectRepository,
            FileStorageService fileStorageService) {

        this.templateRepository = templateRepository;
        this.projectRepository = projectRepository;
        this.fileStorageService = fileStorageService;
    }

    public TemplateResponse uploadTemplate(
            Long projectId,
            String name,
            MultipartFile file
    ) throws IOException {

        // 1. Validate template name
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Template name is required"
            );
        }

        // 2. Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload a file"
            );
        }

        // 3. Check file size (25 MB)
        if (file.getSize() > 25L * 1024 * 1024) {
            throw new IllegalArgumentException(
                    "File size cannot exceed 25 MB"
            );
        }

        // 4. Get original filename
        String originalName = file.getOriginalFilename();

        if (originalName == null || !originalName.contains(".")) {
            throw new IllegalArgumentException(
                    "Invalid filename"
            );
        }

        // 5. Extract extension
        String extension = originalName.substring(
                originalName.lastIndexOf(".") + 1
        ).toLowerCase(Locale.ROOT);

        if (!extension.matches("pdf|png|jpg|jpeg")) {
            throw new IllegalArgumentException(
                    "Only PDF, PNG and JPG files are allowed"
            );
        }

        // 6. Find associated project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Project not found with id " + projectId
                        )
                );

        // 7. Store physical file
        String filePath = fileStorageService.upload(file, projectId);

        try {

            // 8. Create Template entity
            Template template = new Template();

            template.setName(name.trim());
            template.setFileName(originalName);
            template.setFilePath(filePath);
            template.setFileType(extension.toUpperCase(Locale.ROOT));
            template.setProject(project);

            // 9. Save metadata in database
            Template savedTemplate = templateRepository.save(template);

            // 10. Convert entity into response DTO
            return new TemplateResponse(
                    savedTemplate.getId(),
                    savedTemplate.getName(),
                    savedTemplate.getFileName(),
                    savedTemplate.getFileType(),
                    savedTemplate.getWidth(),
                    savedTemplate.getHeight(),
                    savedTemplate.getProject().getId(),
                    savedTemplate.getCreatedAt()
            );

        } catch (RuntimeException exception) {

            // Remove uploaded file if database saving fails
            try {
                fileStorageService.delete(filePath);
            } catch (IOException cleanupException) {
                exception.addSuppressed(cleanupException);
            }

            throw exception;
        }
    }
}
