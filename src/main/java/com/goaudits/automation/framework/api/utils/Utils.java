package com.goaudits.automation.framework.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;
import com.google.gson.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.RandomStringUtils;

import javax.xml.bind.DatatypeConverter;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.StreamSupport;

@Slf4j
public class Utils {
    public static <T> boolean contains(List<T> objects, final String propertyName, final Object value) {
        Predicate<T> predicate = object -> {
            try {
                PropertyDescriptor pd = new PropertyDescriptor(propertyName, object.getClass());
                Method method = pd.getReadMethod();
                Object obj = method.invoke(object);
                if (obj.equals(value)) return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };

        Collection<T> filtered = CollectionUtils.select(objects, predicate);
        return !filtered.isEmpty();
    }

    public static String getStringForParamLength(String param) throws Exception {
        return getStringForParamLength(param, "");
    }



    public static String getStringForParamLength(String param, String extension) throws Exception {
        int length;

        if (param.split("_").length != 3)
            throw new Exception("Invalid key string : " + param);

        String key = param.split("_")[1];
        String value = param.split("_")[2];

        switch (key) {
            case "ABOVE":
                length = Integer.valueOf(value) + 1;
                break;
            case "BELOW":
                length = Integer.valueOf(value) - 1;
                break;
            case "EQUAL":
                length = Integer.valueOf(value);
                break;
            default:
                throw new Exception("Invalid key string : " + param);
        }

        if (length != 1)
            length = length - extension.length();

        return RandomStringUtils.randomAlphanumeric(length) + extension;
    }

    public static String getFileHash(byte[] fileInBytes) throws NoSuchAlgorithmException {
        byte[] fileHash = MessageDigest.getInstance("MD5").digest(fileInBytes);
        return DatatypeConverter.printHexBinary(fileHash).toLowerCase();
    }

    public static void addPropertyToMap(Map<String, String> map, String key, String value) {
        if (value != null && !value.equalsIgnoreCase("<SKIP>")) {
            if (value.equalsIgnoreCase("<NULL>")) {
                map.put(key, null);
            } else {
                map.put(key, value);
            }
        }
    }

    public static void addPropertyToJsonObject(JsonObject jsonObject, String key, String value) {
        if (value != null && !value.equalsIgnoreCase("<SKIP>")) {
            if (value.equalsIgnoreCase("<NULL>")) {
                jsonObject.add(key, JsonNull.INSTANCE);
            } else {
                jsonObject.addProperty(key, value);
            }
        }
    }

    public static void addPropertyToJsonArray(JsonObject json, String field, List<String> arrayOfIds) {
        JsonArray array = new JsonArray();
        if (arrayOfIds != null && arrayOfIds.size() > 0) {
            for (String id : arrayOfIds) {
                array.add(new JsonPrimitive(id));
            }
        }
        json.add(field, array);
    }

    public static void addPropertyToJsonJsonArray(JsonObject json, String field, List<JsonObject> arrayOfIds) {
        JsonArray array = new JsonArray();
        if (arrayOfIds != null && arrayOfIds.size() > 0) {
            for (JsonObject id : arrayOfIds) {
                array.add(id);
            }
            json.add(field, array);
        }
    }

    public static void addPropertyToJsonObject(JsonObject json, String field, Map<String, String> map) {
        JsonArray array = new JsonArray();

        if (map != null) {
            for (String key : map.keySet()) {
                if (key != null) {
                    if (!key.equalsIgnoreCase("<SKIP>")) {
                        JsonObject object = new JsonObject();
                        object.addProperty("id", key);
                        object.addProperty("type", map.get(key));
                        array.add(object);
                    }
                }
            }
            json.add(field, array);
        }
    }

    public static void addPropertyToJsonObject(JsonObject jsonObject, String key, JsonElement jsonElement) {
        if (jsonElement != null) {
            jsonObject.add(key, jsonElement);
        }
    }

    public static void addIfPassed(String field, Object value, JsonObject json) {
        if (value != null) {
            if (value instanceof String) {
                if (!((String) value).equalsIgnoreCase("<NULL>"))
                    json.addProperty(field, (String) value);
            } else if (value instanceof Double) {
                json.addProperty(field, (Double) value);
            } else if (value instanceof Integer) {
                json.addProperty(field, (Integer) value);
            } else if (value instanceof Boolean) {
                json.addProperty(field, (Boolean) value);
            } else if (value instanceof JsonElement) {
                json.add(field, (JsonElement) value);
            } else if (value instanceof ArrayList) {
                JsonArray array = new JsonArray();
                List arrayOfIds = (ArrayList) value;
                for (Object id : arrayOfIds) {
                    array.add(new JsonPrimitive(id.toString()));
                }
                json.add(field, array);
            }
        }
    }

    public static String readTextFile(String path) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        //TODO Fix possible resource leak
        br.close();

        return sb.toString();
    }

    public static String getResourcesPath() {
        String workingDir = getWorkingDirectory();
        String[] pathArray = splitPath(workingDir);
        // workingDir = workingDir.replaceAll(pathArray[pathArray.length - 1], "MPokke_API_Automation_Scripts");
        if (!workingDir.contains("target"))
            workingDir = workingDir + File.separator + "target" + File.separator;
        if (!workingDir.endsWith(File.separator))
            workingDir = workingDir + File.separator;
        return workingDir;
    }

    public static String[] splitPath(String pathString) {
        Path path = Paths.get(pathString);
        return StreamSupport.stream(path.spliterator(), false).map(Path::toString)
                .toArray(String[]::new);
    }


    public static String getWorkingDirectory() {
        String workingDir = System.getProperty("user.dir");
        if (!("JENKINS".equalsIgnoreCase(DataHolder.getExecution_mode()))) {
            workingDir = System.getProperty("user.dir");
            String[] pathArray = splitPath(workingDir);
            workingDir = workingDir.replaceAll(pathArray[pathArray.length - 1], "GoAuditsAPIAutomation");
        }
        if (!workingDir.contains("target"))
            workingDir = workingDir + System.getProperty("file.separator") + "target";
        return workingDir;
    }

    public static String getWorkingDirectory_withoutTarget() {
        String workingDir = System.getProperty("user.dir");
        if (DataHolder.getExecution_mode().equalsIgnoreCase("JENKINS")) {
            return workingDir;
        } else {
            String[] pathArray = splitPath(workingDir);
            workingDir = workingDir.replaceAll(pathArray[pathArray.length - 1], "GoAuditsAPIAutomation");
            return workingDir;
        }
    }

    public static String getTestResourcesPath() {
        return getWorkingDirectory() + File.separator + "test-classes" + File.separator;
    }

    public static String getTestResourceFilesPath() {
        return getWorkingDirectory() + File.separator + "test-classes" + File.separator + "resourceFiles" + File.separator;
    }

    public static String serialize(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object).replace("\\\\", "");
    }

    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(24) + "@mailsac.com";
    }

    public static String setupBaseUrl(String url) {
        StringBuilder urlBuilder = new StringBuilder();

        if (!url.toLowerCase().matches("^\\w+://.*")) {
            urlBuilder.append("http:");
            if (!(url.startsWith("//"))) {
                urlBuilder.append("//");
            }
            urlBuilder.append(url);
        }

        if (!url.endsWith("/")) {
            urlBuilder.append("/");
        }
        return urlBuilder.toString();
    }

    public static void setUpUrlConfigs(String executionMode, PropertyFile propertyFile, String groupName) {
        if (executionMode == null || executionMode.equalsIgnoreCase("LOCAL")) {
            DataHolder.setMasterServerURL_v1(setupBaseUrl(propertyFile.getProperty("test.server.url.v1")));
            DataHolder.setGroupName(propertyFile.getProperty("group.name"));

        } else if (executionMode.equalsIgnoreCase("JENKINS")) {
            String server = System.getenv("ENV");
            DataHolder.setMasterServerURL_v1(setupBaseUrl(server + "-mapi.abc.org"));
            DataHolder.setGroupName(groupName);

        }
    }

    public static String getRandomString(int number) {
        return RandomStringUtils.randomAlphabetic(number);
    }
}