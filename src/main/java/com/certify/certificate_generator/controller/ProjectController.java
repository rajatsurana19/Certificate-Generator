
package com.certify.certificate_generator.controller;

import com.certify.certificate_generator.dto.request.CreateProjectRequest;
import com.certify.certificate_generator.dto.request.UpdateProjectRequest;
import com.certify.certificate_generator.dto.response.ProjectResponse;
import com.certify.certificate_generator.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // Create Project
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody CreateProjectRequest request) {

        ProjectResponse response = projectService.createProject(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get All Projects
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }


    // Update Project
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProjectById(
            @PathVariable Long id,
            @RequestBody UpdateProjectRequest request) {

        return ResponseEntity.ok(
                projectService.updateProjectById(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectById(@PathVariable Long id) {
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    
}
