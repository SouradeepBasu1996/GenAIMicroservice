package com.projectgenerator.ai.aiProjectGenertor.service;

import com.projectgenerator.ai.aiProjectGenertor.model.ProjectDetailsModel;
import com.projectgenerator.ai.aiProjectGenertor.service.aiService.PromptService;
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

    private final PromptService promptService;

    public CreateServiceClassService(PromptService promptService){
        this.promptService=promptService;
    }


    public void createServiceClass(ProjectDetailsModel projectDetails)throws IOException {

        String repositoryName = projectDetails.getEntity().getEntityName();
        StringBuilder repoBuilder = new StringBuilder();
        StringBuilder importBuilder = new StringBuilder();
        StringBuilder modelImportBuilder = new StringBuilder();
        StringBuilder methodBuilder = new StringBuilder();

        String promptResponse = promptService.generateServiceClassCode(projectDetails);
        System.out.println("Prompt Response : "+promptResponse);


        repoBuilder.append("\tprivate ")
                .append(repositoryName)
                .append("Repository")
                .append(" ")
                .append(createRepositoryObj(repositoryName))
                .append("Repository")
                .append(";\n");

        modelImportBuilder.append("import")
                .append(" ")
                .append(projectDetails.getGroupId())
                .append(".")
                .append(projectDetails.getProjectName())
                .append(".")
                .append("entity")
                .append(".")
                .append(repositoryName)
                .append(";\n");

        importBuilder.append("import ")
                .append(projectDetails.getGroupId())
                .append(".")
                .append(projectDetails.getProjectName())
                .append(".")
                .append("repository.")
                .append(repositoryName)
                .append("Repository")
                .append(";\n");

        methodBuilder.append(promptResponse);

        Map<String, String> placeholders = Map.of(
                "packageName",projectDetails.getGroupId(),
                "packageClass",projectDetails.getArtifactId(),
                "serviceClassName",projectDetails.getServiceClass().getServiceClassName(),
                "imports",importBuilder.toString(),
                "repositories",repoBuilder.toString(),
                "entity_imports",modelImportBuilder.toString(),
                "methods",methodBuilder.toString()
        );
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
        Path targetPath = targetDir.resolve(projectDetails.getServiceClass().getServiceClassName()+ ".java");

        // Write processed content to the new service class
        Files.writeString(targetPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Service Class created at: " + targetPath);
    }

    private String createRepositoryObj(String repository){
        return repository.substring(0,1)
                .toLowerCase()+repository.substring(1);
    }
}
