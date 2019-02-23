package com.github.glo2003.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Response;

public class ResponseHelper {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public final static String EMPTY_RESPONSE = "";

    public static class ResponseError {
        public String error;
        public ResponseError(String error) {
            this.error = error;
        }
    }

    public static Object returnNotNullObjectOrError(Response res, Object object, int statusCode, String errorMessage) {
        if (object == null) {
            res.status(statusCode);
            return new ResponseError(errorMessage);
        }
        return object;
    }

    public static String parseToJson(Object object) throws JsonProcessingException {
        if (object == null || object == "") return "";
        return jsonObjectMapper.writeValueAsString(object);
    }

    public static <T> Object isParameterValid(String parameter, Class<T> validationObjectType){
        Object parsedObject = null;
        try {
            parsedObject = jsonObjectMapper.readValue(parameter, validationObjectType);
        } catch (java.io.IOException e) {

        }
        return parsedObject;
    }
}
