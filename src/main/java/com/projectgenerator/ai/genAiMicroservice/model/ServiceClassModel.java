package com.projectgenerator.ai.genAiMicroservice.model;

import java.util.List;

public class ServiceClassModel {

    private String serviceClassName;
    private List<MethodDescModel> methodDescriptions;

    public ServiceClassModel() {
    }

    public ServiceClassModel(String serviceClassName, List<MethodDescModel> methodDescriptions) {
        this.serviceClassName = serviceClassName;
        this.methodDescriptions = methodDescriptions;
    }

    public String getServiceClassName() {
        return serviceClassName;
    }

    public void setServiceClassName(String serviceClassName) {
        this.serviceClassName = serviceClassName;
    }

    public List<MethodDescModel> getMethodDescriptions() {
        return methodDescriptions;
    }

    public void setMethodDescriptions(List<MethodDescModel> methodDescriptions) {
        this.methodDescriptions = methodDescriptions;
    }
}
