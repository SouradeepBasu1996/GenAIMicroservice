package com.projectgenerator.ai.aiProjectGenertor.service.aiService;

import com.projectgenerator.ai.aiProjectGenertor.model.ProjectDetailsModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceClassPromptService {
    private final RestTemplate restTemplate;
    private final String FASTAPI_URL = "http://localhost:8000/generate";

    public ServiceClassPromptService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public String getServiceClassCode(ProjectDetailsModel projectDetailsModel, String controllerClassCode){
        StringBuilder prompt = getPrompt(projectDetailsModel,controllerClassCode);
        String extractedCode="";
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
                String startTag = "<java>";
                String endTag = "</java>";

                int startIndex = code.indexOf(startTag);
                int endIndex = code.indexOf(endTag);

                if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    extractedCode = code.substring(startIndex + startTag.length(), endIndex).trim();
                } else {
                    System.out.println("No Java code found!");
                }
                return extractedCode;
            }
        }
        return "Failed to get response";
    }
    private StringBuilder getPrompt(ProjectDetailsModel projectDetailsModel, String controllerClassCode){
        StringBuilder prompt = new StringBuilder();

        prompt.append("Respond with code only, no explanation required.")
                .append(" Create a Service class for a Spring Boot Application, with name- ")
                .append(projectDetailsModel.getServiceClass())
                .append("\n The methods inside the service class should be the written according to the requirements of the controller class code as given below- ")
                .append("\n")
                .append(controllerClassCode)
                .append("\n")
                .append("Assume that the controller class code already exist, no need to write that again.")
                .append("Enclose the java code with in <java>,</java> tags");

        System.out.println("Service class prompt : "+prompt.toString());

        return prompt;
    }
}
