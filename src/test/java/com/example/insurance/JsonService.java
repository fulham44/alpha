package com.example.insurance;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 01.09.18.
 */
@Service
@AllArgsConstructor
public class JsonService {

    private ObjectMapper mapper;

    @SneakyThrows
    public <T> T toObject(String json, Class<T> clazz) {
        return (json == null) ? null : mapper.readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T toObject(Map data, Class<T> clazz) {
        return mapper.convertValue(data, clazz);
    }

    @SneakyThrows
    public String toJson(Object object) {
        return mapper.writeValueAsString(object);
    }

    @Nullable
    public <T> T fromJson(@Nullable String json, Class<T> clazz) {
        return (json == null) ? null : doFromJson(json, clazz, mapper);
    }

    @Nullable
    public <T> List<T> fromJsonList(@Nullable String json, Class<T> clazz) {
        return (json == null) ? null : doFromJson(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz), mapper);
    }

    @Nullable
    public <T> Set<T> fromJsonSet(@Nullable String json, Class<T> clazz) {
        return (json == null) ? null : doFromJson(json, mapper.getTypeFactory().constructCollectionType(Set.class, clazz), mapper);
    }


    private  <T> T doFromJson(String json, Class<T> clazz, ObjectMapper mapper) {
        return doFromJson(json, clazz, mapper.reader());
    }

    private  <T> T doFromJson(String json, Class<T> clazz, ObjectReader reader) {
        try {
            return reader.withType(clazz).readValue(json);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private  <T> T doFromJson(String json, JavaType obj, ObjectMapper reader) {
        try {
            return reader.readValue(json, obj);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
