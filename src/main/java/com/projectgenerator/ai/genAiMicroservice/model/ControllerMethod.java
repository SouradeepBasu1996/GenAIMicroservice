package com.projectgenerator.ai.genAiMicroservice.model;

import java.util.List;

public class ControllerMethod {

    private String methodName;
    private String returnType;
    private List<ControllerMethodArgument> methodArguments;
    private String httpMethod;
    private String endPoint;
    private String description;

    public ControllerMethod() {
    }

    public ControllerMethod(String methodName, String returnType, List<ControllerMethodArgument> methodArguments, String httpMethod, String endPoint, String description) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.methodArguments = methodArguments;
        this.httpMethod = httpMethod;
        this.endPoint = endPoint;
        this.description = description;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<ControllerMethodArgument> getMethodArguments() {
        return methodArguments;
    }

    public void setMethodArguments(List<ControllerMethodArgument> methodArguments) {
        this.methodArguments = methodArguments;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
