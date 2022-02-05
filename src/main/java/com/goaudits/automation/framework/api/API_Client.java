package com.goaudits.automation.framework.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goaudits.automation.framework.api.utils.ResponseValidator;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.internal.Utils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.yandex.qatools.allure.annotations.Attachment;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@Slf4j
public class API_Client {

    private final CloseableHttpClient client;
    private final BasicCookieStore cookieStore = new BasicCookieStore();
    public int responseCode;
    public JsonArray jsonResponseArray;
    public String serverURL;
    public JsonObject jsonResponse;
    private Header[] responseHeaders;
    private HttpResponse httpResponse;
    private Document xmlResponse;
    public String stringResponse;
    public JsonElement jsonElement;
    private String responseString = "{}";
    private Map<String, String> requestBodyParameters = new HashMap<>();
    private Map<String, String> requestHeaders = new HashMap<>();
    private Map<String, String> queryParameters = new HashMap<>();
    private ResponseValidator responseValidator;
    private static boolean masterServerURL = false;

    public API_Client(String serverURL) {
        this.serverURL = serverURL;
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        int timeout = 12000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();
        this.client = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config).setRedirectStrategy(new LaxRedirectStrategy()).setDefaultCookieStore(cookieStore).build();
        this.responseValidator = new ResponseValidator(this);
    }

    public HttpResponse getResponse() {
        return httpResponse;
    }

    public void post(String api) throws Exception {
        post(api, getJsonAsStringEntity(convertMapToJSON(this.requestBodyParameters)));
    }

    public void post(String api, JsonElement jsonRequestBody) throws Exception {
        post(api, getJsonAsStringEntity(jsonRequestBody.toString()));
    }

    /**
     * Takes only json object as input
     *
     * @param api pathParameter
     * @param object jsonObject
     * @throws Exception
     */
    public void post(String api, Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        post(api, getJsonAsStringEntity(mapper.writeValueAsString(object)));

    }

    public void post(String api, HttpEntity httpEntity) throws Exception {
        executeRequest(REQUEST_TYPE.POST, api, this.queryParameters, this.requestHeaders, httpEntity);
    }

    public void get(String api) throws Exception {
        executeRequest(REQUEST_TYPE.GET, api, this.queryParameters, this.requestHeaders, null);
    }

    public void put(String api) throws Exception {
        put(api, getJsonAsStringEntity(convertMapToJSON(this.requestBodyParameters)));
    }

    /**
     * Takes only json object as input
     *
     * @param api
     * @param object
     * @throws Exception
     */
    public void put(String api, Object object) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        put(api, getJsonAsStringEntity(mapper.writeValueAsString(object)));
    }

    public void put(String api, JsonElement jsonRequestBody) throws Exception {
        put(api, getJsonAsStringEntity(jsonRequestBody.toString()));
    }

    public void put(String api, HttpEntity httpEntity) throws Exception {
        executeRequest(REQUEST_TYPE.PUT, api, this.queryParameters, this.requestHeaders, httpEntity);
    }

    /**
     * delete
     *
     * @param api
     * @throws Exception
     */
    public void delete(String api) throws Exception {
        delete(api, getJsonAsStringEntity(convertMapToJSON(this.requestBodyParameters)));
    }

    public void delete(String api, JsonElement jsonRequestBody) throws Exception {
        delete(api, getJsonAsStringEntity(jsonRequestBody.toString()));
    }

    public void delete(String api, HttpEntity httpEntity) throws Exception {
        executeRequest(REQUEST_TYPE.DELETE, api, this.queryParameters, this.requestHeaders, httpEntity);
    }

    public void patch(String api) throws Exception {
        patch(api, getJsonAsStringEntity(convertMapToJSON(this.requestBodyParameters)));
    }

    public void patch(String api, JsonElement jsonRequestBody) throws Exception {
        patch(api, getJsonAsStringEntity(jsonRequestBody.toString()));
    }

    public void patch(String api, HttpEntity httpEntity) throws Exception {
        executeRequest(REQUEST_TYPE.PATCH, api, this.queryParameters, this.requestHeaders, httpEntity);
    }

    private void executeRequest(REQUEST_TYPE requestType, String api, Map<String, String> urlParameter, Map<String, String> requestHeader, HttpEntity httpEntity) throws Exception {

        try {
            switch (requestType) {
                case POST:
                    HttpPost postRequest = new HttpPost(serverURL + api + convertMapToURL(urlParameter));
                    if (requestHeader != null) {
                        for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                            if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("ANONYMOUS")) {
                                postRequest.addHeader(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                    postRequest.setEntity(httpEntity);
                    httpResponse = client.execute(postRequest);
                    setupResponseObjects();
                    consoleLog(postRequest, httpResponse);
                    //logRequest(postRequest, httpResponse);
                    break;
                case DELETE:
                  HttpDeleteWithBody deleteRequest = new HttpDeleteWithBody(serverURL + api + convertMapToURL(urlParameter));
                    for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("ANONYMOUS")) {
                            deleteRequest.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    deleteRequest.setEntity(httpEntity);
                    httpResponse = client.execute(deleteRequest);
                    setupResponseObjects();
                    consoleLog(deleteRequest, httpResponse);
                    //logRequest(deleteRequest, httpResponse);
                    break;
                case GET:
                    HttpGet getRequest = new HttpGet(serverURL + api + convertMapToURL(urlParameter));
                    for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("ANONYMOUS")) {
                            getRequest.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    httpResponse = client.execute(getRequest);
                    setupResponseObjects();
                    consoleLog(getRequest, httpResponse);
                    //logRequest(getRequest, httpResponse);
                    break;
                case PATCH:
                    HttpPatch patchRequest = new HttpPatch(serverURL + api + convertMapToURL(urlParameter));
                    for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("ANONYMOUS")) {
                            patchRequest.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    patchRequest.setEntity(httpEntity);
                    httpResponse = client.execute(patchRequest);
                    setupResponseObjects();
                    consoleLog(patchRequest, httpResponse);
                    //logRequest(patchRequest, httpResponse);
                    break;
                case PUT:
                    HttpPut putRequest = new HttpPut(serverURL + api + convertMapToURL(urlParameter));
                    for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("ANONYMOUS")) {
                            putRequest.addHeader(entry.getKey(), entry.getValue());
                        }
                    }
                    putRequest.setEntity(httpEntity);
                    httpResponse = client.execute(putRequest);
                    setupResponseObjects();
                    consoleLog(putRequest, httpResponse);
                    //logRequest(putRequest, httpResponse);
                    break;
            }

            responseCode = httpResponse.getStatusLine().getStatusCode();
            responseHeaders = httpResponse.getAllHeaders();
        } finally {
            requestBodyParameters.clear();
            requestHeaders.clear();
            queryParameters.clear();
            consumeResponse();
        }
    }

    private StringEntity getJsonAsStringEntity(String input) {
        return new StringEntity(input, ContentType.APPLICATION_JSON);
    }

    private void consumeResponse() throws IOException {
        if (httpResponse != null) {
            EntityUtils.consume(httpResponse.getEntity());
        }
    }

    @SuppressWarnings("unused")
    public Map<String, String> getResponseHeaders() {
        Map<String, String> headers = new HashMap<>();
        for (Header header : responseHeaders) {
            headers.put(header.getName(), header.getValue());
        }

        return headers;
    }

    public String getJsonObjectValue(String parameter) {
        JsonElement jElement = jsonResponse.get(parameter);
        if (jElement != null && !(jElement instanceof JsonNull))
            return jElement.getAsString();
        else
            return "";
    }

    public String getJsonArrayObjectValue(String parameter) {
        if(jsonResponse != null) {
            JsonObject jObject = jsonResponse.get("error_fields").getAsJsonObject();
            JsonArray jsonArray = jObject.get(parameter).getAsJsonArray();
            return jsonArray.get(0).getAsString();
        } else {
            return "";
        }
    }

    private String convertMapToJSON(Map<String, String> requestBody) {
        StringBuilder json = new StringBuilder("{");
        if (requestBody != null) {
            for (Map.Entry<String, String> entry : requestBody.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("<NULL>") && !entry.getValue().equalsIgnoreCase("<SKIP>")) {
                    //TODO handle <SKIP> here
                    if (isNumeric(entry.getValue()))
                        json.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue()).append(",");
                    else
                        json.append("\"").append(entry.getKey()).append("\": \"").append(entry.getValue()).append("\"").append(",");
                }
            }
        }
        json = new StringBuilder(json.toString().replaceAll(",$", ""));
        json.append("}");
        return json.toString();
    }

    private String convertMapToURL(Map<String, String> urlParameter) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder();
        if (urlParameter != null) {
            if (urlParameter.size() > 0)
                url = new StringBuilder("?");
            for (Map.Entry<String, String> entry : urlParameter.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().equalsIgnoreCase("<NULL>") && !entry.getValue().equalsIgnoreCase("<SKIP>")) {
                    //url = url + StringEscapeUtils.escapeHtml(entry.getKey()) + "=" + entry.getValue() + "&";
                    url.append(StringEscapeUtils.escapeHtml4(entry.getKey())).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
                }
            }
            url = new StringBuilder(url.toString().replaceAll("&$", ""));
        }
        return url.toString();
    }

    private void logRequest(HttpRequest request, HttpResponse response) throws ParseException, IOException, TransformerException {
        String newLine = "\r\n", tab = "\t", jsonString;
        StringBuilder report = new StringBuilder();

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonElement je;

        Reporter.log("");
        Reporter.log("--------------------------------------------------------------------------------------------------------------------------------------------------<b>BEGIN</b>----------------------------------------------------------------------------------------------------------------------------------------------------");
        Reporter.log("");
        Reporter.log("<b>REQUEST URL : </b>" + request.getRequestLine());
        report.append("REQUEST URL : ");
        report.append(request.getRequestLine());
        report.append(newLine);
        report.append(newLine);
        Reporter.log("");
        Reporter.log("<b>REQUEST HEADERS</b>");
        report.append("REQUEST HEADERS : ");
        report.append(newLine);
        report.append(newLine);
        Reporter.log("");
        HeaderIterator hIterator = request.headerIterator();
        while (hIterator.hasNext()) {
            Header header = hIterator.nextHeader();
            Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + header.getName() + " : " + header.getValue());
            report.append(tab);
            report.append(header.getName());
            report.append(" : ");
            report.append(header.getValue());
            report.append(newLine);
        }

        Reporter.log("");
        Reporter.log("<b>REQUEST BODY</b>");
        report.append(newLine);
        report.append("REQUEST BODY : ");
        report.append(newLine);
        report.append(newLine);
        Reporter.log("");
        if (request instanceof HttpDeleteWithBody) {
           HttpDeleteWithBody deleteRequest = (HttpDeleteWithBody) request;
            je = readEntity(deleteRequest.getEntity());
            jsonString = gson.toJson(je);
            Reporter.log(jsonString.replaceAll("\n", "<br>"));
            report.append(jsonString);
            report.append(newLine);
            report.append(newLine);
        } else if (request instanceof HttpPost) {
            HttpPost postRequest = (HttpPost) request;
            je = readEntity(postRequest.getEntity());
            jsonString = gson.toJson(je);
            Reporter.log(jsonString.replaceAll("\n", "<br>"));
            report.append(jsonString);
            report.append(newLine);
            report.append(newLine);
        } else if (request instanceof HttpPut) {
            HttpPut putRequest = (HttpPut) request;
            je = readEntity(putRequest.getEntity());
            jsonString = gson.toJson(je);
            Reporter.log(jsonString.replaceAll("\n", "<br>"));
            report.append(jsonString);
            report.append(newLine);
            report.append(newLine);
        } else if (request instanceof HttpPatch) {
            HttpPatch patchRequest = (HttpPatch) request;
            je = readEntity(patchRequest.getEntity());
            jsonString = gson.toJson(je);
            Reporter.log(jsonString.replaceAll("\n", "<br>"));
            report.append(jsonString);
            report.append(newLine);
            report.append(newLine);
        }

        Reporter.log("");
        int code = httpResponse.getStatusLine().getStatusCode();
        if (code == 200 || code == 201 || code == 204 || code == 206) {
            Reporter.log("<b>RESPONSE STATUS CODE : " + "<font color=\"#00CC00\">" + httpResponse.getStatusLine().getStatusCode() + "</font></b>");
        } else {
            Reporter.log("<b>RESPONSE STATUS CODE : " + "<font color=\"red\">" + httpResponse.getStatusLine().getStatusCode() + "</font></b>");
        }
        report.append("RESPONSE STATUS CODE : ");
        report.append(httpResponse.getStatusLine().getStatusCode());
        report.append(newLine);
        report.append(newLine);

        Reporter.log("");
        Reporter.log("<b>RESPONSE HEADERS</b>");
        report.append("RESPONSE HEADERS : ");
        report.append(newLine);
        report.append(newLine);
        Reporter.log("");
        hIterator = response.headerIterator();
        while (hIterator.hasNext()) {
            Header header = hIterator.nextHeader();
            Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + header.getName() + " : " + header.getValue());
            report.append(tab);
            report.append(header.getName());
            report.append(" : ");
            report.append(header.getValue());
            report.append(newLine);
        }

        Reporter.log("");
        Reporter.log("<b>COOKIES :</b>");
        Reporter.log("");
        report.append(newLine);
        report.append("COOKIES : ");
        report.append(newLine);
        report.append(newLine);
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + cookie.toString());
            report.append(tab);
            report.append(cookie.toString());
            report.append(newLine);
        }

        Reporter.log("");
        Reporter.log("<b>RESPONSE BODY</b>");
        Reporter.log("");
        report.append(newLine);
        report.append("RESPONSE BODY : ");
        report.append(newLine);
        report.append(newLine);
        if (jsonResponse != null)
            responseString = gson.toJson(jsonResponse);
        else if (jsonResponseArray != null)
            responseString = gson.toJson(jsonResponseArray);
        else if (xmlResponse != null) {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlResponse), new StreamResult(writer));
            responseString = writer.getBuffer().toString().replaceAll("[\n\r]", "");
        } else
            responseString = "INVALID RESPONSE TYPE ! Current responseString object is : " + stringResponse;

        report.append(responseString);
        report.append(newLine);
        report.append(newLine);
        responseString = Utils.escapeHtml(responseString);
        Reporter.log(responseString);
        Reporter.log("");
        Reporter.log("----------------------------------------------------------------------------------------------------------------------------------------------------<b>END</b>----------------------------------------------------------------------------------------------------------------------------------------------------");
        Reporter.log("");
        //log.debug(report.toString());         //commented as we are using #consoleLog for console logging
        //logRequestAllure(report.toString());
    }

    /**
     * THis is to print the curl request and response in console.
     *
     * @param request
     * @param response
     * @throws ParseException
     * @throws IOException
     * @throws TransformerException
     */
    public void consoleLog(HttpRequest request, HttpResponse response) throws ParseException, IOException, TransformerException {
        StringBuilder curlRequest = new StringBuilder();
        curlRequest.append("\n====================== REQUEST ==========================\n");
        curlRequest.append("curl -L -X ");
        curlRequest.append(request.getRequestLine().getMethod() + " '");
        curlRequest.append(request.getRequestLine().getUri() + "' \n");
        HeaderIterator headerIterator = request.headerIterator();
        while (headerIterator.hasNext()) {
            Header header = headerIterator.nextHeader();
            curlRequest.append(" -H '");
            curlRequest.append(header.getName());
            curlRequest.append(" : ");
            curlRequest.append(header.getValue());
            curlRequest.append("' \n");
        }
        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        if (request instanceof HttpDeleteWithBody) {
            HttpDeleteWithBody deleteRequest = (HttpDeleteWithBody) request;
            jsonElement = readEntity(deleteRequest.getEntity());
            String json = gson.toJson(jsonElement);
            Object obj = mapper.readValue(json, Object.class);
            curlRequest.append(" --data-raw '");
            curlRequest.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)+"'");
        } else if (request instanceof HttpPost) {
            HttpPost postRequest = (HttpPost) request;
            jsonElement = readEntity(postRequest.getEntity());
            String json = gson.toJson(jsonElement);
            Object obj = mapper.readValue(json, Object.class);
            curlRequest.append(" --data-raw '");
            curlRequest.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)+"'");
        } else if (request instanceof HttpPut) {
            HttpPut putRequest = (HttpPut) request;
            jsonElement = readEntity(putRequest.getEntity());
            String json = gson.toJson(jsonElement);
            Object obj = mapper.readValue(json, Object.class);
            curlRequest.append(" --data-raw '");
            curlRequest.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)+"'");
        } else if (request instanceof HttpPatch) {
            HttpPatch patchRequest = (HttpPatch) request;
            jsonElement = readEntity(patchRequest.getEntity());
            String json = gson.toJson(jsonElement);
            Object obj = mapper.readValue(json, Object.class);
            curlRequest.append(" --data-raw '");
            curlRequest.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj)+"'");
        }
        curlRequest.append("\n====================== RESPONSE ==========================\n");

        curlRequest.append("Response status code: " + response.getStatusLine().getStatusCode() + "\n");
        curlRequest.append("Response Body: ");

        if (jsonResponse != null)
            responseString = gson.toJson(jsonResponse);
        else if (jsonResponseArray != null)
            responseString = gson.toJson(jsonResponseArray);
        else if (xmlResponse != null) {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlResponse), new StreamResult(writer));
            responseString = writer.getBuffer().toString().replaceAll("[\n\r]", "");
        } else
            responseString = "INVALID RESPONSE TYPE ! Current responseString object is : " + stringResponse;

        Object obj = null;
        if(!responseString.contains("INVALID RESPONSE TYPE ! Current responseString object is : " + stringResponse))
        obj = mapper.readValue(responseString, Object.class);

        curlRequest.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
        log.debug(curlRequest.toString());
        logRequestAllure(curlRequest.toString());
    }

    @Attachment
    public String logRequestAllure(String message) {
        return message;
    }

    private JsonElement readEntity(HttpEntity entity) throws ParseException, IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        String result;
        if (entity != null) {
            String contentType = entity.getContentType().getValue();
            if (contentType.contains("multipart/form-data")) {
                result = "File Body";
            } else {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } else {
            result = "{}";
        }

        if (result.equalsIgnoreCase(""))
            result = "{}";

        JsonReader reader = new JsonReader(new StringReader(result));
        reader.setLenient(true);

        jsonElement = gson.fromJson(reader, JsonElement.class);
        reader.close();
        return jsonElement;
    }

    private void setupResponseObjects() throws IOException, ParserConfigurationException, SAXException {
        String result;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            String line;
            boolean emptyFlag = true;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
                emptyFlag = false;
            }
            if (emptyFlag)
                builder.append("{}");
            else
                builder.setLength(builder.length() - 1);
            result = builder.toString();
        } catch (Exception e) {
            result = "<EMPTY RESPONSE>";
        }

        responseString = "";
        if (httpResponse.getFirstHeader("Content-Type") != null) {
            if (httpResponse.getFirstHeader("Content-Type").getValue().contains("application/xml")) {
                jsonResponse = null;
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource(new StringReader(result));
                jsonResponseArray = null;
                xmlResponse = db.parse(is);
                stringResponse = null;
            } else if (httpResponse.getFirstHeader("Content-Type").getValue().contains("application/json")) {
                Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
                jsonResponse = null;
                jsonResponseArray = null;
                xmlResponse = null;
                stringResponse = null;
                if (isJSONValid(result)) {
                    jsonElement = gson.fromJson(result, JsonElement.class);
                    if (jsonElement != null) {
                        if (jsonElement.isJsonArray()) {
                            jsonResponseArray = jsonElement.getAsJsonArray();
                        } else if (jsonElement.isJsonObject()) {
                            jsonResponse = jsonElement.getAsJsonObject();
                        } else {
                            jsonResponse = null;
                            stringResponse = jsonElement.getAsString();
                        }
                    }
                }
            } else {
                jsonResponse = null;
                jsonResponseArray = null;
                xmlResponse = null;
                stringResponse = result;
                responseString = result;
            }
        } else {
            jsonResponse = null;
            jsonResponseArray = null;
            xmlResponse = null;
            stringResponse = null;
        }
    }

    private boolean isJSONValid(String jsonInString) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            gson.fromJson(jsonInString, Object.class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    public boolean isPositiveResponse() {
        int responseCode = this.responseCode;
        int[] positiveResponseCodes = {200, 201, 204, 206};

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
                throw new Exception("Positive Response {200,201,204,206} expected but got " + this.responseCode);
            }
        }
    }

    private enum REQUEST_TYPE {
        POST, GET, PUT, DELETE, PATCH
    }

    public Map<String, String> body() {
        return requestBodyParameters;
    }

    public Map<String, String> headers() {
        return requestHeaders;
    }

    public Map<String, String> query() {
        return queryParameters;
    }

    public ResponseValidator validator() {
        return responseValidator;
    }
}