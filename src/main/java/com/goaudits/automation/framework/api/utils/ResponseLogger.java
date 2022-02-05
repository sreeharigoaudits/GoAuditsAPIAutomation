package com.goaudits.automation.framework.api.utils;

import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import lombok.extern.slf4j.Slf4j;
import org.testng.Reporter;

import java.util.List;

@Slf4j
public class ResponseLogger {

    public static void logRequest(Method method, String api, RequestSpecification request, Response response) {
        QueryableRequestSpecification queryable = SpecificationQuerier.query(request);

        Reporter.log("--------------------------------------------------------------------------------------------------------------------------------------------------<b>BEGIN</b>----------------------------------------------------------------------------------------------------------------------------------------------------");
        Reporter.log("");
        StringBuilder queryParams = new StringBuilder();
        if (!queryable.getQueryParams().isEmpty()) {
            queryParams.append("?");
            queryable.getQueryParams().forEach((key, value) -> queryParams.append(key).append("=").append(value).append("&"));
        }
        Reporter.log("<b>REQUEST URL : </b>" + method + " " + queryable.getBaseUri() + api + queryParams.toString());
        Reporter.log("");
        Reporter.log("<b>REQUEST HEADERS</b>");
        Reporter.log("");
        List<Header> requestHeaders = queryable.getHeaders().asList();
        for (Header header : requestHeaders) {
            Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + header.getName() + " : " + header.getValue());
        }

        Reporter.log("");
        Reporter.log("<b>REQUEST BODY</b>");
        Reporter.log("");

        if(queryable.getMultiPartParams() != null && !queryable.getMultiPartParams().isEmpty()) {
            for (MultiPartSpecification multiPartSpecification : queryable.getMultiPartParams())
                Reporter.log(multiPartSpecification.toString());
        }

        if (queryable.getBody() != null)
            Reporter.log(queryable.getBody().toString());

        Reporter.log("");
        int code = response.statusCode();
        if (code == 200 || code == 201 || code == 204 || code == 206) {
            Reporter.log("<b>RESPONSE STATUS CODE : " + "<font color=\"#00CC00\">" + code + "</font></b>");
        } else {
            Reporter.log("<b>RESPONSE STATUS CODE : " + "<font color=\"red\">" + code + "</font></b>");
        }

        Reporter.log("");
        Reporter.log("<b>RESPONSE HEADERS</b>");
        Reporter.log("");
        List<Header> responseHeaders = response.getHeaders().asList();
        for (Header header : responseHeaders) {
            Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + header.getName() + " : " + header.getValue());
        }

        Reporter.log("");
        Reporter.log("<b>RESPONSE BODY</b>");
        Reporter.log("");
        Reporter.log(response.asString());
        Reporter.log("");
        Reporter.log("----------------------------------------------------------------------------------------------------------------------------------------------------<b>END</b>----------------------------------------------------------------------------------------------------------------------------------------------------");
        Reporter.log("");
    }
}