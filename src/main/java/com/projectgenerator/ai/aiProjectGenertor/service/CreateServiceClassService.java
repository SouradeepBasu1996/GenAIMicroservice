package com.projectgenerator.ai.aiProjectGenertor.service;

import com.projectgenerator.ai.aiProjectGenertor.model.ProjectDetailsModel;
import com.projectgenerator.ai.aiProjectGenertor.service.aiService.ServiceClassPromptService;
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
public class CreateServiceClassService {

    @Value("${app.working-directory}")
    private String workingDirectory;

    private final ServiceClassPromptService promptService;
    private final CreateRepositoryService createRepositoryService;

    public CreateServiceClassService(ServiceClassPromptService promptService,
                                     CreateRepositoryService createRepositoryService){
        this.promptService=promptService;
        this.createRepositoryService=createRepositoryService;
    }


    public void createServiceClass(ProjectDetailsModel projectDetails,String controllerClassCode)throws IOException {

        String code = promptService.getServiceClassCode(projectDetails,controllerClassCode);
        System.out.println("Prompt Response : "+code);

        Map<String, String> placeholders = Map.of(
                "packageName",projectDetails.getGroupId(),
                "packageClass",projectDetails.getArtifactId(),
                "service_class_code",code
        );

        createRepositoryService.createRepository(projectDetails,code);

        ClassPathResource resource = new ClassPathResource("templates/ServiceClassTemplate.java");
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
                "service");

        Files.createDirectories(targetDir); // Ensure directory exists

        // Define target file path
        Path targetPath = targetDir.resolve(projectDetails.getServiceClass()+ ".java");

        // Write processed content to the new service class
        Files.writeString(targetPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Service Class created at: " + targetPath);
    }

    private String createRepositoryObj(String repository){
        return repository.substring(0,1)
                .toLowerCase()+repository.substring(1);
    }
}
