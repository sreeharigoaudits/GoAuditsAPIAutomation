package com.goaudits.automation.framework.api.reporter;

import lombok.extern.slf4j.Slf4j;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.List;

@Slf4j
public class ThreadCountHandler implements IAlterSuiteListener {
    @Override
    public void alter(List<XmlSuite> suites) {
        log.info("Entering ThreadCountHandler");
        String countStr = System.getenv("THREAD_COUNT");
        if (countStr == null) {
            countStr = "-1";
        }
        int count = Integer.parseInt(countStr);

        if (count <= 0) {
            return;
        }
        log.info("Setting THREAD_COUNT : {}", count);
        for (XmlSuite suite : suites) {
            for (XmlTest test : suite.getTests()) {
                if (test.getName().contains("Regression")) {
                    test.setThreadCount(count);
                    log.info("Set THREAD_COUNT : {}", count);
                }
            }
        }
    }
}