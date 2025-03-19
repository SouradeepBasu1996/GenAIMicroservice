package com.projectgenerator.ai.genAiMicroservice.controller;

import com.projectgenerator.ai.genAiMicroservice.model.ProjectDetailsModel;
import com.projectgenerator.ai.genAiMicroservice.service.ProjectGeneratorService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProjectController {

    private ProjectGeneratorService projectGeneratorService;

    public ProjectController(ProjectGeneratorService projectGeneratorService){
        this.projectGeneratorService=projectGeneratorService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Resource> createProject(@RequestBody ProjectDetailsModel projectDetails) throws IOException {
        Path zipFilePath = projectGeneratorService.createProject(projectDetails);

        if (zipFilePath == null) {
            return ResponseEntity.internalServerError().build();
        }

        Resource resource = new UrlResource(zipFilePath.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFilePath.getFileName() + "\"")
                .body(resource);
    }
}
