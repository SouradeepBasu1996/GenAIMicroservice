package com.projectgenerator.ai.aiProjectGenertor.model;

import java.util.List;

public class ControllerModel {

    private String controllerClassName;
    private String restControllerEndpoint;
    private List<ControllerMethod> controllerMethods;

    public ControllerModel() {
    }

    public ControllerModel(String controllerClassName, String restControllerEndpoint, List<ControllerMethod> controllerMethods) {
        this.controllerClassName = controllerClassName;
        this.restControllerEndpoint = restControllerEndpoint;
        this.controllerMethods = controllerMethods;
    }

    public String getControllerClassName() {
        return controllerClassName;
    }

    public void setControllerClassName(String controllerClassName) {
        this.controllerClassName = controllerClassName;
    }

    public String getRestControllerEndpoint() {
        return restControllerEndpoint;
    }

    public void setRestControllerEndpoint(String restControllerEndpoint) {
        this.restControllerEndpoint = restControllerEndpoint;
    }

    public List<ControllerMethod> getControllerMethods() {
        return controllerMethods;
    }

    public void setControllerMethods(List<ControllerMethod> controllerMethods) {
        this.controllerMethods = controllerMethods;
    }
}
