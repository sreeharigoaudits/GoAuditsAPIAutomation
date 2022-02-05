package com.goaudits.automation.framework.api.reporter;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class ExceptionReporter implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) { }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod() && ITestResult.FAILURE == result.getStatus()) {
            Throwable throwable = result.getThrowable();
            if (throwable instanceof UnrecognizedPropertyException) {
                String newMessage = throwable.getMessage().split(",")[0];
                try {
                    FieldUtils.writeField(throwable, "detailMessage", newMessage, true);
                    FieldUtils.writeField(throwable, "_propertiesAsString", "", true);
                    FieldUtils.writeField(throwable, "_location", new JsonLocation(null, -1L, -1L, -1, -1), true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}