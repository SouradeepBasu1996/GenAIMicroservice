package com.projectgenerator.ai.genAiMicroservice.service;

import com.projectgenerator.ai.genAiMicroservice.model.ProjectDetailsModel;
import com.projectgenerator.ai.genAiMicroservice.service.aiService.ControllerPromptService;
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
public class CreateControllerService {

    @Value("${app.working-directory}")
    private String workingDirectory;

    private final ControllerPromptService promptService;
    private final CreateServiceClassService createServiceClassService;

    public CreateControllerService(ControllerPromptService promptService,
                                   CreateServiceClassService createServiceClassService){
        this.promptService=promptService;
        this.createServiceClassService=createServiceClassService;
    }

    public void createControllerClass(ProjectDetailsModel projectDetails)throws IOException {
        String code = promptService.generateControllerCode(projectDetails);

        Map<String, String> placeholders = Map.of(
                "packageName",projectDetails.getGroupId(),
                "packageClass",projectDetails.getProjectName(),
                "controller",code);
        System.out.println("Controller class response : "+code);
        createServiceClassService.createServiceClass(projectDetails,code);
        ClassPathResource resource = new ClassPathResource("templates/ControllerTemplate.java");
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
                "controller");

        Files.createDirectories(targetDir); // Ensure directory exists

        // Define target file path
        Path targetPath = targetDir.resolve(projectDetails.getControllerModel().getControllerClassName() + ".java");

        // Write processed content to the new main class
        Files.writeString(targetPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Controller Class created at: " + targetPath);
    }
}
