package com.later.horizon.common.helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

public class JacksonHelper {

    private final static ObjectMapper ObjectMapper = new ObjectMapper();

    public static String convert(Class<?> clazz) {
        try {
            return ObjectMapper.writeValueAsString(clazz);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> T convertBean(String json, Class<T> clazz) {
        try {
            return ObjectMapper.readValue(json, clazz);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <T> List<T> convertListBean(String json, Class<T> clazz) {
        try {
            return ObjectMapper.readValue(json, ObjectMapper.getTypeFactory().constructParametricType(List.class, clazz));
        } catch (Exception ignored) {
        }
        return null;
    }

    public static <E> Set<E> convertSetBean(String json, Class<E> clazz) {
        try {
            return ObjectMapper.readValue(json, ObjectMapper.getTypeFactory().constructCollectionType(Set.class, clazz));
        } catch (Exception ignored) {
        }
        return null;
    }

}
