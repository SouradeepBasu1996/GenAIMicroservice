package com.projectgenerator.ai.aiProjectGenertor.model;

public class MethodParameterModel {

    private String parameterName;
    private String dataType;

    public MethodParameterModel() {
    }

    public MethodParameterModel(String parameterName, String dataType) {
        this.parameterName = parameterName;
        this.dataType = dataType;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
