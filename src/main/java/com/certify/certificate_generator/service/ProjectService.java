
package com.certify.certificate_generator.service;

import com.certify.certificate_generator.dto.request.CreateProjectRequest;
import com.certify.certificate_generator.dto.request.UpdateProjectRequest;
import com.certify.certificate_generator.dto.response.ProjectResponse;
import com.certify.certificate_generator.entity.Project;
import com.certify.certificate_generator.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Create Project
    public ProjectResponse createProject(CreateProjectRequest request) {

        Project project = new Project(
                request.getName(),
                request.getDescription()
        );

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    // Get All Projects
    public List<ProjectResponse> getProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get Project By ID
    public ProjectResponse getProjectById(Long id) {

        Project project = getProjectEntityById(id);

        return mapToResponse(project);
    }

    // Update Project
    public ProjectResponse updateProjectById(
            Long id,
            UpdateProjectRequest request
    ) {

        Project existingProject = getProjectEntityById(id);

        existingProject.setName(request.getName());
        existingProject.setDescription(request.getDescription());

        Project updatedProject = projectRepository.save(existingProject);

        return mapToResponse(updatedProject);
    }

    // Reusable method to find project entity
    private Project getProjectEntityById(Long id) {

        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Project not found with id " + id)
                );
    }

    // Entity to Response DTO conversion
    private ProjectResponse mapToResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

    public void deleteProjectById(Long id) {
        Project project = getProjectEntityById(id);
        projectRepository.delete(project);
    }

}
