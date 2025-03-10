package com.projectgenerator.ai.aiProjectGenertor.service;

import com.projectgenerator.ai.aiProjectGenertor.model.ProjectDetailsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProjectGeneratorService {

    @Value("${app.working-directory}")
    private String workingDirectory;

    private final ZipService zipService;
    private final CreatePomService createPomService;
    private final CreateMainClassService createMainClassService;
    private final CreateRepositoryService createRepositoryService;
    private final CreateServiceClassService createServiceClassService;
    private final CreateControllerService createControllerService;
    private final CreateEntityService createEntityService;

    public ProjectGeneratorService(CreatePomService createPomService,
                                   CreateMainClassService createMainClassService,
                                   CreateRepositoryService createRepositoryService,
                                   CreateServiceClassService createServiceClassService,
                                   CreateControllerService createControllerService,
                                   CreateEntityService createEntityService,
                                   ZipService zipService){
        this.createPomService=createPomService;
        this.createMainClassService=createMainClassService;
        this.createRepositoryService=createRepositoryService;
        this.createServiceClassService=createServiceClassService;
        this.createControllerService=createControllerService;
        this.createEntityService=createEntityService;
        this.zipService=zipService;
    }

    public Path createProject(ProjectDetailsModel projectDetails)throws IOException {

        Path projectDir = getProjectRootPath(projectDetails.getProjectName());

        createPomService.generatePom(projectDetails);
        createRepositoryService.createRepository(projectDetails);
        createControllerService.createControllerClass(projectDetails);
        createMainClassService.createMainClass(projectDetails);
        createEntityService.createEntityClass(projectDetails);
        createServiceClassService.createServiceClass(projectDetails);

        Files.createDirectories(projectDir);
        createBasicStructure(projectDetails);
        return zipService.zipProject(projectDir);
    }

    private void createBasicStructure(ProjectDetailsModel projectDetails)throws IOException {
        Path projectDir = getProjectRootPath(projectDetails.getProjectName());

        List<Path> directories = List.of(
                projectDir.resolve("src/main/java/"
                        + projectDetails.getGroupId().replace(".", "/")
                        +"/"
                        +projectDetails.getProjectName().replace(".","/")),
                projectDir.resolve("src/test/java/"
                        +projectDetails.getGroupId().replace(".", "/")
                        +"/"
                        +projectDetails.getProjectName().replace(".","/")),
                projectDir.resolve("src/main/resources")
        );

        for (Path dir : directories) {
            try{
                Files.createDirectories(dir);
                System.out.println("Directory created successfully :"+dir);
            }catch (IOException ioException){
                System.out.println("Failed to create directory : "+dir);
            }
        }
        createFileIfNotExists(projectDir.resolve("pom.xml"));
        createFileIfNotExists(projectDir.resolve("README.md"));
        createFileIfNotExists(projectDir.resolve(".gitignore"));

        System.out.println("Project Structure created successfully : CreateProjectService.createBasicStructure");
    }
    private Path getProjectRootPath(String projectName) {
        return Paths.get(workingDirectory, projectName);
    }
    private void createFileIfNotExists(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }
}
