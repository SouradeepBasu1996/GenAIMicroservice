package com.projectgenerator.ai.genAiMicroservice.service;

import com.projectgenerator.ai.genAiMicroservice.model.ProjectDetailsModel;
import com.projectgenerator.ai.genAiMicroservice.service.aiService.RepositoryPromptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Scanner;

@Service
public class CreateRepositoryService {

    @Value("${app.working-directory}")
    private String workingDirectory;

    private RepositoryPromptService repositoryPromptService;

    public CreateRepositoryService(RepositoryPromptService repositoryPromptService){
        this.repositoryPromptService=repositoryPromptService;
    }

    public void createRepository(ProjectDetailsModel projectDetails,String serviceClassCode)throws IOException {
        String code=repositoryPromptService.getRepositoryCode(projectDetails,serviceClassCode);
        Map<String, String> placeholders = Map.of(
                "packageName", projectDetails.getGroupId(),
                "packageClass",projectDetails.getProjectName(),
                "repository_code",code
        );
        System.out.println("Repository class response : "+code);
        ClassPathResource resource = new ClassPathResource("templates/RepositoryTemplate.java");
        String content;
        try (InputStream inputStream = resource.getInputStream(); Scanner scanner = new Scanner(inputStream)) {
            content = scanner.useDelimiter("\\A").next(); // Read entire file as a string
        }

        // Replace placeholders
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        String packagePath = projectDetails.getGroupId().replace(".", "/")
                +"/"
                +projectDetails.getProjectName();

        Path targetDir = Path.of(workingDirectory,
                projectDetails.getProjectName(),
                "src/main/java",
                packagePath,
                "repository");

        Files.createDirectories(targetDir); // Ensure directory exists

        // Define target file path
        Path targetPath = targetDir.resolve(projectDetails.getEntity().getEntityName() + "Repository.java");

        // Write processed content to the new repository class
        Files.writeString(targetPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Repository Class created at: " + targetPath);
    }
}
