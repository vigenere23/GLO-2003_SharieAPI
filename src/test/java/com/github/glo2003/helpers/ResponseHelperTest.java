package com.github.glo2003.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.glo2003.exceptions.JsonDeserializingException;
import com.github.glo2003.exceptions.JsonSerializingException;
import com.github.glo2003.stubs.NestedObject;
import com.github.glo2003.stubs.SimpleObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class ResponseHelperTest {

    private final String exampleString = "Lorem Ispum Eusdi Correcta";
    private final int exampleInteger = 200;
    private final double exampleDecimal = 152.648819;

    private String validJsonSimpleObject;
    private String validJsonNestedObject;
    public String validJsonError;

    private SimpleObject simpleObject;
    private NestedObject nestedObject;

    @Before
    public void setup() {
        simpleObject = new SimpleObject(exampleString, exampleInteger, exampleDecimal);
        nestedObject = new NestedObject(exampleString, exampleInteger, exampleDecimal, simpleObject);
        validJsonSimpleObject = String.format(
                "{\"string\":\"%s\",\"integer\":%d,\"decimal\":%f}",
                exampleString, exampleInteger, exampleDecimal);
        validJsonNestedObject = String.format(
                "{\"string\":\"%s\",\"integer\":%d,\"decimal\":%f,\"simpleObject\":%s}",
                exampleString, exampleInteger, exampleDecimal, validJsonSimpleObject);
        validJsonError = String.format("{\"error\":\"%s\"}", exampleString);
    }

    @Test
    public void jsonError_shouldParseToValidJsonError() {
        assertThat(ResponseHelper.errorAsJson(exampleString)).isEqualTo(validJsonError);
    }

    @Test
    public void givenNull_parseToJson_shouldGiveEmptyString() throws JsonSerializingException {
        assertThat(ResponseHelper.serializeObjectToJson(null)).isEqualTo("");
    }

    @Test
    public void givenString_parseToJson_shouldGiveSameString() throws JsonSerializingException {
        assertThat(ResponseHelper.serializeObjectToJson(exampleString)).isEqualTo(exampleString);
    }

    @Test
    public void givenSimpleObject_parseToJsonCorrectly() throws JsonSerializingException {
        String jsonSimpleObject = ResponseHelper.serializeObjectToJson(simpleObject);
        assertThat(jsonSimpleObject).isEqualTo(validJsonSimpleObject);
    }

    @Test
    public void givenNestedObjects_parseToJsonCorrectly() throws JsonSerializingException {
        String jsonNestedObject = ResponseHelper.serializeObjectToJson(nestedObject);
        assertThat(jsonNestedObject).isEqualTo(validJsonNestedObject);
    }

    @Test
    public void givenJsonSimpleObject_deserializeToSimpleObjectNotNull() throws JsonDeserializingException {
        SimpleObject deserializedSimpleObject = ResponseHelper.deserializeJsonToObject(validJsonSimpleObject, SimpleObject.class);
        assertThat(deserializedSimpleObject).isNotNull();
    }

    @Test
    public void givenJsonSimpleObject_deserializeToNestedObjectNotNull() throws JsonDeserializingException {
        NestedObject deserializedNestedObject = ResponseHelper.deserializeJsonToObject(validJsonNestedObject, NestedObject.class);
        assertThat(deserializedNestedObject).isNotNull();
    }

    @Test
    public void givenJsonSimpleObject_deserializeToSimpleObjectWithCorrectParams() throws JsonDeserializingException {
        SimpleObject deserializedSimpleObject = ResponseHelper.deserializeJsonToObject(validJsonSimpleObject, SimpleObject.class);
        assertThat(deserializedSimpleObject.getString()).isEqualTo(exampleString);
        assertThat(deserializedSimpleObject.getInteger()).isEqualTo(exampleInteger);
        assertThat(deserializedSimpleObject.getDecimal()).isEqualTo(exampleDecimal);
    }

    @Test
    public void givenJsonSimpleObject_deserializeToNestedObjectWithCorrectParams() throws JsonDeserializingException {
        NestedObject deserializedNestedObject = ResponseHelper.deserializeJsonToObject(validJsonNestedObject, NestedObject.class);
        assertThat(deserializedNestedObject.getString()).isEqualTo(exampleString);
        assertThat(deserializedNestedObject.getInteger()).isEqualTo(exampleInteger);
        assertThat(deserializedNestedObject.getDecimal()).isEqualTo(exampleDecimal);
    }

    @Test
    public void givenJsonSimpleObject_deserializeToNestedObjectWithNestedObjectNotNull() throws JsonDeserializingException {
        NestedObject deserializedNestedObject = ResponseHelper.deserializeJsonToObject(validJsonNestedObject, NestedObject.class);
        assertThat(deserializedNestedObject.getSimpleObject()).isNotNull();
    }

    @Test
    public void givenJsonSimpleObject_deserializeToNestedObjectWithCorrectNestedObjectParams() throws JsonDeserializingException {
        NestedObject deserializedNestedObject = ResponseHelper.deserializeJsonToObject(validJsonNestedObject, NestedObject.class);
        SimpleObject deserializedSimpleObject = deserializedNestedObject.getSimpleObject();
        assertThat(deserializedSimpleObject.getString()).isEqualTo(exampleString);
        assertThat(deserializedSimpleObject.getInteger()).isEqualTo(exampleInteger);
        assertThat(deserializedSimpleObject.getDecimal()).isEqualTo(exampleDecimal);
    }
}
