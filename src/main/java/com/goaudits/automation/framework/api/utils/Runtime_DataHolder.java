package com.goaudits.automation.framework.api.utils;

import com.google.gson.JsonArray;

import java.util.List;

public class Runtime_DataHolder {



    private static String GUID;
    private static String AuthToken;
    private static String UserName;
    private static String UID;
    private static Object object;
    private static List<Object> objectList;
    private static JsonArray jsonArrayObject;


    static {
        init();
    }

    public static void init() {
    }



    public static String getGUID() {
        return GUID;
    }

    public static void setGUID(String user_GUID) {
      GUID = user_GUID;
    }
    public static String getUID() {
        return UID;
    }

    public static void setUID(String UID) {
        Runtime_DataHolder.UID = UID;
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String userName) {
        UserName = userName;
    }
    public static String getAuthToken() {
        return AuthToken;
    }

    public static void setAuthToken(String authToken) {
        AuthToken = authToken;
    }
    public static Object getObject() {
        return object;
    }

    public static void setObject(Object object) {
        Runtime_DataHolder.object = object;
    }

    public static List<Object> getObjectList() {
        return objectList;
    }

    public static void setObjectList(List<Object> objectList) {
        Runtime_DataHolder.objectList = objectList;
    }



    public static void setJsonArrayObject(JsonArray jsonArray) { jsonArrayObject = jsonArray; }

    public static JsonArray getJsonArrayObject() { return jsonArrayObject; }




}
