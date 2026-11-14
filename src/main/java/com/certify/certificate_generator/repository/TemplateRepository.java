package com.certify.certificate_generator.repository;

import com.certify.certificate_generator.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findByProjectId(Long projectId);

}