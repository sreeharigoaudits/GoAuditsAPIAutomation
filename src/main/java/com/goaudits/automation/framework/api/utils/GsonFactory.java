package com.goaudits.automation.framework.api.utils;

import com.goaudits.automation.framework.api.API_Client;
import com.google.gson.*;


import java.util.ArrayList;
import java.util.List;

public class GsonFactory {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    /**
     * parse
     *
     * It maps a json object to a class object
     * @param jsonObject
     * @param mClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T parse(JsonObject jsonObject, Class<T> mClass) throws Exception {
        if (jsonObject == null || jsonObject.size() == 0) {
            return null;
        }
        return gson.fromJson(jsonObject.toString(), mClass);
    }

    /**
     * stringParse
     *
     * It maps a json String primitive type to a class object
     * @param jsonPrimitive
     * @param mClass
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T stringParse(String jsonPrimitive, Class<T> mClass) throws Exception {
        if (jsonPrimitive == null || jsonPrimitive.length() == 0) {
            return null;
        }

        return gson.fromJson(jsonPrimitive, mClass);
    }

    public static <T> T parse(String jsonString, Class<T> mClass) throws Exception {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonString).getAsJsonObject();
        return parse(jsonObject, mClass);
    }

    public static <T> List<T> parseList(String jsonString, Class<T> mClass) throws Exception {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(jsonString);
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            return parseList(jsonArray, mClass);
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
            T object = gson.fromJson(jsonElement, mClass);
            objects.add(object);
        }

        return objects;
    }

    public static <T> List<T> parseList(List<JsonObject> jsonObjects, Class<T> mClass) throws Exception {
        List<T> objects = new ArrayList<>();
        for (int i = 0; i < jsonObjects.size(); i++) {
//            objects.add(parse(jsonObjects.get(i), mClass));    It is not working in case of DB stuff so used ModuleFactory
            objects.add(ModuleFactory.parse(jsonObjects.get(i), mClass));
        }
        return objects;
    }

    public static <T> List<T> parseList(JsonObject jsonObject, Class<T> mClass) throws Exception {
        JsonArray jsonArray= null;
        if(jsonObject.get("data") == null) {
            return null;
        } else {
            jsonArray = jsonObject.get("data").getAsJsonArray();
            if (jsonArray == null) {
                return null;
            }
        }

        List<T> objects = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            T object = gson.fromJson(jsonElement.toString(), mClass);
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
                T object = gson.fromJson(jsonElement.toString(), mClass);
                objects.add(object);
            }
        }

        return objects;
    }

    public static <T> T parseData(API_Client client, Class<T> mClass) throws Exception {
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

public static <T> T parse(API_Client client, Class<T> mClass) throws Exception {
        if (!client.validator().isPositiveResponse()) {
            return null;
        } else if (client.validator().isPositiveResponse() && client.jsonResponse == null) {
            return null;
        }

//        JsonElement dataObject;
//        JsonObject response;
//        if(client.jsonResponse.get("data") != null) {
//            dataObject = client.jsonResponse.get("data");
//            response = dataObject.getAsJsonObject();
//        } else {
//            response = client.jsonResponse.getAsJsonObject();
//        }
        return parse(client.jsonResponse.getAsJsonObject(), mClass);
    }


    public static <T> T parse(JsonElement jsonElement, Class<T> mClass) throws Exception {
        return parse(jsonElement.getAsJsonObject(), mClass);
    }
}