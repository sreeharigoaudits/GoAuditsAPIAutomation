package com.goaudits.automation.framework.api.utils;

import com.google.common.collect.ImmutableMap;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

class AllureUtils {
    static void createAllureEnvironmentFile() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Group_Name", DataHolder.getGroupName())
                        .put("Test_Server_URLs_V1", DataHolder.getMasterServerURL_v1())
                        .build());
    }
}