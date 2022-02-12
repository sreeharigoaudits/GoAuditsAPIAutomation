package com.goaudits.automation.framework.api.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.goaudits.automation.framework.api.API_Client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ModuleFactory {
    private static final ObjectMapper mapper;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        mapper = new ObjectMapper();
        mapper.setDateFormat(simpleDateFormat).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true).configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static <T> List<T> parseList(API_Client client, Class<T> mClass) throws Exception {
        if (!client.validator().isPositiveResponse()) {
            return null;
        }
        JsonArray jsonArray = client.jsonResponseArray;
        return parseList(jsonArray, mClass);
    }

    public static <T> List<T> parseList(API_Client client, Class<? extends T> mClass, Class<T> iClass) throws Exception {
        if (!client.validator().isPositiveResponse()) {
            return null;
        }

        JsonArray jsonArray = client.jsonResponseArray;
        return parseList(jsonArray, mClass, iClass);
    }

    public static <T> List<T> parseList(JsonArray jsonArray, Class<T> mClass) throws Exception {
        if (jsonArray == null) {
            return null;
        }

        List<T> objects = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            T object = mapper.readValue(jsonElement.toString(), mClass);
            objects.add(object);
        }

        return objects;
    }

    public static <T> List<T> parseList(JsonObject jsonObject, Class<T> mClass) throws Exception {
        JsonArray jsonArray= null;
        if(jsonObject == null) {
            return null;
        } else {
            jsonArray = jsonObject.getAsJsonArray();
            if (jsonArray == null) {
                return null;
            }
        }

        List<T> objects = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            T object = mapper.readValue(jsonElement.toString(), mClass);
            objects.add(object);
        }

        return objects;
    }

    public static <T> T parseDataSet(API_Client client, Class<T> mClass) throws Exception {
        if(client.jsonResponse.get("raws") != null) {
            JsonObject jsonObject = client.jsonResponse.getAsJsonObject("raws");
            JsonObject dataSet = jsonObject.getAsJsonObject("dataset");
            return parse(dataSet, mClass);
        } else
            return null;
    }

    public static <T> List<T> parseList(JsonArray jsonArray, Class<? extends T> mClass, Class<T> iClass) throws Exception {
        if (jsonArray == null) {
            return null;
        }

        List<T> objects = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            if (iClass.isAssignableFrom(mClass)) {
                T object = mapper.readValue(jsonElement.toString(), mClass);
                objects.add(object);
            }
        }

        return objects;
    }

    public static <T> T parse(API_Client client, Class<T> mClass) throws Exception {
        if (!client.validator().isPositiveResponse()) {
            return null;
        } else if (client.validator().isPositiveResponse() && client.jsonResponse == null) {
            return null;
        }

        JsonElement dataObject;
        JsonObject response;
        if(client.jsonResponse.get("data") != null) {
            dataObject = client.jsonResponse.get("data");
            response = dataObject.getAsJsonObject();
        } else {
            response = client.jsonResponse.getAsJsonObject();
        }
        return parse(response, mClass);
    }

    public static <T> T parse(JsonElement jsonElement, Class<T> mClass) throws Exception {
        return parse(jsonElement.getAsJsonObject(), mClass);
    }

    public static <T> T parse(JsonObject jsonObject, Class<T> mClass) throws Exception {
        if (jsonObject == null || jsonObject.size() == 0) {
            return null;
        }


        return mapper.readValue(jsonObject.toString(), mClass);
    }

    public static <T> T stringParse(String jsonPrimitive, Class<T> mClass) throws Exception {
        if (jsonPrimitive == null || jsonPrimitive.length() == 0) {
            return null;
        }

        return mapper.readValue(jsonPrimitive, mClass);
    }

    public static <T> T parse(String jsonString, Class<T> mClass) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return parse(jsonObject, mClass);
    }
}