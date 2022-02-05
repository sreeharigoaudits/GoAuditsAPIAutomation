package com.goaudits.automation.framework.api.utils;

import org.apache.commons.codec.binary.Base64;

public class AuthUtil {

    /**
     * This will convert the userName and password to Authorisation using Base64
     *
     * @param userName
     * @param password
     * @return
     */
    public static String getAuthFromUserNamePassword(String userName, String password){
        String authToConvert = userName+":"+password;
        byte[] encodedBytes = Base64.encodeBase64(authToConvert.getBytes());
        String encodedAuth = new String(encodedBytes);
        String finalAuth = "Basic "+encodedAuth;
        return finalAuth;
    }
}
