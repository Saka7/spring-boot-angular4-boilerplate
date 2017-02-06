package com.app.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMapper {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static String toJsonIgnoreAnnotations(Object object) throws JsonProcessingException {
        ObjectMapper mapperWithoutAnnotations = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);
        return mapperWithoutAnnotations.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, Class<T> className)
            throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(json, className);
    }
}
