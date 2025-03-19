package com.projectgenerator.ai.genAiMicroservice.model;

import java.util.List;

public class MethodDescModel {

    private String methodName;
    private String returnType;
    private List<MethodParameterModel> methodParameters;
    private String methodDescription;

    public MethodDescModel() {
    }

    public MethodDescModel(String methodName, String returnType, List<MethodParameterModel> methodParameters, String methodDescription) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.methodParameters = methodParameters;
        this.methodDescription=methodDescription;
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

    public List<MethodParameterModel> getMethodParameters() {
        return methodParameters;
    }

    public void setMethodParameters(List<MethodParameterModel> methodParameters) {
        this.methodParameters = methodParameters;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }
}
