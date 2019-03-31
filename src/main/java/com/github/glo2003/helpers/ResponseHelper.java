package com.github.glo2003.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.glo2003.exceptions.JsonDeserializingException;
import com.github.glo2003.exceptions.JsonSerializingException;

public class ResponseHelper {

    private static ObjectMapper jsonObjectMapper = new ObjectMapper();

    public static final String EMPTY_RESPONSE = "";

    public static class Error {
        public String error;

        public Error(String message) {
            error = message;
        }
    }

    public static String serializeObjectToJson(Object object) throws JsonSerializingException {
        try {
            if (object == null) {
                return "";
            } else if (object instanceof String) {
                return (String) object;
            } else {
                return jsonObjectMapper.writeValueAsString(object);
            }
        } catch (Exception e) {
            throw new JsonSerializingException(object.getClass().getName());
        }
    }

    public static <T> T deserializeJsonToObject(String json, Class<T> validationObjectType) throws JsonDeserializingException {
        try {
            return jsonObjectMapper.readValue(json, validationObjectType);
        } catch (Exception e) {
            throw new JsonDeserializingException(validationObjectType.getName());
        }
    }
}
