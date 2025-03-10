package com.projectgenerator.ai.aiProjectGenertor.service.aiService;

import com.projectgenerator.ai.aiProjectGenertor.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PromptService {

    private final RestTemplate restTemplate;
    private final String FASTAPI_URL = "http://localhost:8000/generate";

    public PromptService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public String generateServiceClassCode(ProjectDetailsModel projectDetails) {
        StringBuilder prompt = new StringBuilder();
        String repositoryName = projectDetails.getEntity().getEntityName()+"Repository";
        for(MethodDescModel model:projectDetails.getServiceClass().getMethodDescriptions()){
            prompt.append("Respond with code only. No explanation required.")
                    .append("Create a method in java having method name ")
                    .append(model.getMethodName())
                    .append("having return type ")
                    .append(model.getReturnType())
                    .append(" and parameters, ");
            model.getMethodParameters()
                    .forEach(methodParameterModel ->{prompt.append(methodParameterModel.getParameterName())
                            .append("(")
                            .append(methodParameterModel.getDataType())
                            .append("), ");});
            prompt.append("and the function of the method is the following - ")
                    .append(model.getMethodDescription())
                    .append(". Use the Repository class of name, ")
                    .append(repositoryName)
                    .append(" and its object is of the same name but in camel characters,")
                    .append("to write code inside the method to achieve what is mentioned in the method description.")
                    .append("Use the entity definition to for getter ans setter methods - ")
                    .append(projectDetails.getEntity().getEntityName())
                    .append(" having fields ");
        }
        for(EntityFieldModel entityField:projectDetails.getEntity().getEntityFields()){
            prompt.append(entityField)
                    .append(", ");
        }
        prompt.append(" Assume that the Repository class is already created and extends the JpaRepository. And assume that the Entity class is also created")
                .append("So only focus on the method to be created and do not create any extra classes or interfaces like Repository class or Entity classes");
        System.out.println("Prompt : "+prompt.toString());


        // Prepare JSON request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL, requestEntity, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("response")) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");

            if (responseBody.containsKey("content")) {
                String code = responseBody.get("content").toString();
                code = code.replaceAll("^```\\w*\\n?", "").replaceAll("\\n```$", "");
                return code.trim();
            }
        }
        return "Failed to get response";
    }

    public String generateControllerClassCode(ProjectDetailsModel projectDetailsModel){
        StringBuilder prompt = new StringBuilder();

        prompt.append("Please respond with code only. No explanation or other String lines should be present.")
                .append("Create a Rest Controller class for java spring boot with name- ")
                .append(projectDetailsModel.getControllerModel().getControllerClassName())
                .append(" and api endpoint- ")
                .append(projectDetailsModel.getControllerModel().getRestControllerEndpoint())
                .append(". Inside the Controller class create the following methods. ");
        for(ControllerMethod method:projectDetailsModel.getControllerModel().getControllerMethods()){
            prompt.append("Create method- ")
                    .append(method.getMethodName())
                    .append(" with arguments, ");
            for(ControllerMethodArgument argument:method.getMethodArguments()){
                prompt.append(argument)
                        .append("(")
                        .append(argument.getDataType())
                        .append(")")
                        .append(" and parameter annotation, ")
                        .append(argument.getParameterAnnotation())
                        .append(". ");
            }
            prompt.append("The method should return a ResponseEntity of suitable type depending on the return type ")
                    .append(method.getReturnType())
                    .append(" and http method- ")
                    .append(method.getHttpMethod())
                    .append(". Please put proper annotations over the method and arguments. The endpoint of the method is -")
                    .append(method.getEndPoint())
                    .append(". Inside the method, write the code to achieve the functionality of the method as given in the method description as- ")
                    .append(method.getDescription())
                    .append(". Use the reference of the Service class and model class names to write the code inside the method.")
                    .append("Service class name- ").append(projectDetailsModel.getServiceClass().getServiceClassName())
                    .append(". Model class names- ").append(projectDetailsModel.getEntity().getEntityName())
                    .append(" having fields- ");
            for(EntityFieldModel entityField: projectDetailsModel.getEntity().getEntityFields()){
                prompt.append(entityField.getFieldName())
                        .append("(").append(entityField.getDataType()).append("), ");
            }
            prompt.append("Assume that the Service classes and Entity classes are already created and hence do not need to create them again. ");
        }
        prompt.append("Use proper Spring Boot annotations and dependency injections in the controller and the methods. ")
                        .append("Also write all the import statements for the different imports like- import org.springframework.web.bind.annotation.RestController.")
                .append(", for @RestController annotation.");


        System.out.println("Prompt : "+prompt.toString());


        // Prepare JSON request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(FASTAPI_URL, requestEntity, Map.class);

        if (response.getBody() != null && response.getBody().containsKey("response")) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody().get("response");

            if (responseBody.containsKey("content")) {
                String code = responseBody.get("content").toString();
                code = code.replaceAll("^```\\w*\\n?", "").replaceAll("\\n```$", "");
                return code.trim();
            }
        }
        return "Failed to get response";
    }
}
