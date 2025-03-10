package com.projectgenerator.ai.aiProjectGenertor.model;

public class ProjectDetailsModel {

    private String projectName;
    private String groupId;
    private String artifactId;
    private String description;
    private ControllerModel controllerModel;
    private ServiceClassModel serviceClass;
    private EntityModel entity;

    public ProjectDetailsModel() {
    }

    public ProjectDetailsModel(String projectName, String groupId, String artifactId, ControllerModel controllerModel,ServiceClassModel serviceClass,EntityModel entityModel, String description) {
        this.projectName = projectName;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.controllerModel=controllerModel;
        this.serviceClass= serviceClass;
        this.entity = entityModel;
        this.description=description;
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

    public ServiceClassModel getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(ServiceClassModel serviceClass) {
        this.serviceClass = serviceClass;
    }

    public EntityModel getEntity() {
        return entity;
    }

    public void setEntity(EntityModel entity) {
        this.entity = entity;
    }
}
