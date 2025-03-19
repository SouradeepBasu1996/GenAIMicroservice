package com.projectgenerator.ai.genAiMicroservice.model;

public class ProjectDetailsModel {

    private String projectName;
    private String groupId;
    private String artifactId;
    private String description;
    private ControllerModel controllerModel;
    private String serviceClassName;
    private EntityModel entity;
    private String databaseUname;
    private String databasePword;

    public ProjectDetailsModel() {
    }

    public ProjectDetailsModel(String projectName, String groupId, String artifactId, ControllerModel controllerModel,String serviceClassName,EntityModel entityModel, String description, String databaseUname, String databasePword) {
        this.projectName = projectName;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.controllerModel=controllerModel;
        this.serviceClassName= serviceClassName;
        this.entity = entityModel;
        this.description=description;
        this.databaseUname=databaseUname;
        this.databasePword=databasePword;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ControllerModel getControllerModel() {
        return controllerModel;
    }

    public void setControllerModel(ControllerModel controllerModel) {
        this.controllerModel = controllerModel;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public EntityModel getEntity() {
        return entity;
    }

    public void setEntity(EntityModel entity) {
        this.entity = entity;
    }

    public String getDatabaseUname() {
        return databaseUname;
    }

    public void setDatabaseUname(String databaseUname) {
        this.databaseUname = databaseUname;
    }

    public String getDatabasePword() {
        return databasePword;
    }

    public void setDatabasePword(String databasePword) {
        this.databasePword = databasePword;
    }
}
