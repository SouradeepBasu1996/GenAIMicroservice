Here is the JSON payload example for this.

{
    "projectName": "helloAiProject",
    "groupId": "com.online.market",
    "artifactId": "helloAiProject",
    "description": "Demo project on AI created app",
    "controllerModel":{
        "controllerClassName":"CustomerController",
        "restControllerEndpoint":"/api",
        "controllerMethods":[
            {
                "methodName":"createNewCustomer",
                "returnType":"Customer",
                "methodArguments":[
                    {
                        "argumentName":"newCustomer",
                        "dataType":"Customer",
                        "parameterAnnotation":"@ResponseBody"
                    }

                ],
                "httpMethod":"POST",
                "endPoint":"/newcustomer",
                "description":"create a new customer"
            }
        ]
    },
    "serviceClassName":"CustomerService",
    "entity": {
        "entityName": "Customer",
        "entityFields":[
            {
                "fieldName":"customerId",
                "dataType":"Long"
            },
            {
                "fieldName":"customerName",
                "dataType":"String"
            },
            {
                "fieldName":"customerAge",
                "dataType":"Integer"
            }
        ]
    }
}
