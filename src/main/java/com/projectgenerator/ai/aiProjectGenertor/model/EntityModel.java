package com.projectgenerator.ai.aiProjectGenertor.model;

import java.util.List;

public class EntityModel {

    private String entityName;
    private List<EntityFieldModel> entityFields;

    public EntityModel() {
    }

    public EntityModel(List<EntityFieldModel> entityFields, String entityName) {
        this.entityFields = entityFields;
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<EntityFieldModel> getEntityFields() {
        return entityFields;
    }

    public void setEntityFields(List<EntityFieldModel> entityFields) {
        this.entityFields = entityFields;
    }
}
