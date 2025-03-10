package com.projectgenerator.ai.aiProjectGenertor.model;

public class ControllerMethodArgument {

    private String argumentName;
    private String dataType;
    private String parameterAnnotation;

    public ControllerMethodArgument() {
    }

    public ControllerMethodArgument(String argumentName, String dataType, String parameterAnnotation) {
        this.argumentName = argumentName;
        this.dataType = dataType;
        this.parameterAnnotation = parameterAnnotation;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getParameterAnnotation() {
        return parameterAnnotation;
    }

    public void setParameterAnnotation(String parameterAnnotation) {
        this.parameterAnnotation = parameterAnnotation;
    }
}
