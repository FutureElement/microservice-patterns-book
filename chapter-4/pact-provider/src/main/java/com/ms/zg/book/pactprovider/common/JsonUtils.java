package com.ms.zg.book.pactprovider.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {
    private JsonUtils() {
    }

    public static String toJson(Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T parseJson(String json, Class<T> classType) {
        try {
            return new ObjectMapper().readValue(json, classType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

}
