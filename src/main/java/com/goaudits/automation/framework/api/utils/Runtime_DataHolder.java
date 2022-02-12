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
    private static String CompanyID;
    private static String CompanyName;
    private static String LocationName;
    private static String LocationID;
    private static String AuditNameID;
    private static String AuditName;
    private static String SeqNo;

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
    public static String getCompanyID() {
        return CompanyID;
    }

    public static void setCompanyID(String companyID) {
        CompanyID = companyID;
    }
    public static String getCompanyName() {
        return CompanyName;
    }

    public static void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public static String getLocationName() {
        return LocationName;
    }

    public static void setLocationName(String locationName) {
        LocationName = locationName;
    }
    public static String getAuditName() {
        return AuditName;
    }

    public static void setAuditName(String auditName) {
        AuditName = auditName;
    }
    public static String getAuditNameID() {
        return AuditNameID;
    }

    public static void setAuditNameID(String auditNameID) {
        AuditNameID = auditNameID;
    }
    public static String getLocationID() {
        return LocationID;
    }
    public static String getSeqNo() {
        return SeqNo;
    }

    public static void setSeqNo(String seqNo) {
        SeqNo = seqNo;
    }
    public static void setLocationID(String locationID) {
        LocationID = locationID;
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
