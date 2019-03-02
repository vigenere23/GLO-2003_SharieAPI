package com.github.glo2003.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseHelper {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public final static String EMPTY_RESPONSE = "";

    public static String errorAsJson(String message) {
        return "{\"error\":\"" + message + "\"}";
    }

    public static String parseToJson(Object object) throws JsonProcessingException {
        if (object == null) return "";
        else if (object instanceof String) return (String)object;
        else return jsonObjectMapper.writeValueAsString(object);
    }

    public static <T> T isParameterValid(String parameters, Class<T> validationObjectType) throws IOException {
        return jsonObjectMapper.readValue(parameters, validationObjectType);
    }
}
