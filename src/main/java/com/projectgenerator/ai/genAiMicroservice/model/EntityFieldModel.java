package com.projectgenerator.ai.genAiMicroservice.model;

public class EntityFieldModel {

    private String fieldName;
    private String dataType;

    public EntityFieldModel() {
    }

    public EntityFieldModel(String fieldName, String dataType) {
        this.fieldName = fieldName;
        this.dataType = dataType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
