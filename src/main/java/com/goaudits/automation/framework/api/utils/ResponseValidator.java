package com.goaudits.automation.framework.api.utils;


import com.goaudits.automation.framework.api.API_Client;
import org.testng.Assert;

public class ResponseValidator {
    private final API_Client client;

    public ResponseValidator(API_Client client) {
        this.client = client;
    }

    public boolean isPositiveResponse() {
        int responseCode = client.responseCode;
        int[] positiveResponseCodes = {200, 201, 202, 204, 206, 304};

        for (int positiveResponseCode : positiveResponseCodes) {
            if (positiveResponseCode == responseCode)
                return true;
        }

        return false;
    }

    public void assertPositiveResponse(boolean flag) throws Exception {
        if (flag) {
            try {
                Assert.assertTrue(this.isPositiveResponse());
            } catch (AssertionError e) {
                StackTraceElement[] ste = e.getStackTrace();
                throw new Exception("Test blocked : Expected Positive Response {200,201} but got " +
                        client.responseCode + " while attempting {" + ste[6].getMethodName() + " in " + ste[6].getFileName() + "}");
            }
        }
    }

    public void assertNegativeResponse(boolean flag) throws Exception {
        if (flag) {
            try {
                Assert.assertFalse(this.isPositiveResponse());
            } catch (AssertionError e) {
                StackTraceElement[] ste = e.getStackTrace();
                throw new Exception("Test blocked : Expected Negative Response {other than 200,201,204,206} but " +
                        "got " + client.responseCode + " while attempting {" + ste[6].getMethodName() + " in " + ste[6].getFileName() + "}");
            }
        }
    }
    public void assertResponseCode(String responseCode) {
        Assert.assertEquals(client.responseCode, Integer.parseInt(responseCode), "Response code mis-match");
    }

}