package com.goaudits.automation.framework.api.reporter;


import com.goaudits.automation.framework.api.fileHandlers.PropertyFile;
import com.goaudits.automation.framework.api.utils.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.collections.Lists;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class HTMLReporter implements IReporter {
    private final Map<String, Integer> linkMap = new HashMap<>();
    private final List<SuiteResult> suiteResults = Lists.newArrayList();
    private final StringBuilder buffer = new StringBuilder();
    private final int sNoColumn = 0;
    private final int descriptionColumn = 1;
    private final int columnsCount = 3;
    private PrintWriter writer;
    private PropertyFile environmentFile;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        try {
            writer = createWriter(outputDirectory);
            environmentFile = new PropertyFile(PathUtils.getAllureResourcesPath() + "environment.properties");
        } catch (IOException e) {
            log.error("Unable to create output data.files", e);
            return;
        }
        for (ISuite suite : suites) {
            suiteResults.add(new SuiteResult(suite));
        }

        writeDocumentStart();
        writeHead();
        try {
            writeBody();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDocumentEnd();

        writer.close();
    }

    private PrintWriter createWriter(String outdir) throws IOException {
        if(System.getenv("REPORT_PATH") != null) {
            String reportPath = System.getenv("REPORT_PATH");
            new File(reportPath).mkdirs();
            return new PrintWriter(new BufferedWriter(new FileWriter(new File(reportPath, "API_Automation_Results.html"))));
        } else {
            String workingDir = this.getClass().getClassLoader().getResource("").getPath();
            workingDir = workingDir.replaceAll("/target/classes", "");
            new File(workingDir + "/" + outdir).mkdirs();
            return new PrintWriter(new BufferedWriter(new FileWriter(new File(workingDir + "/" + outdir, "API_Automation_Results.html"))));
        }
    }

    private void writeDocumentStart() {
        writer.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
        writer.print("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    }

    private void writeHead() {
        writer.print("<head>");
        writeChartScripts();
        writer.print("<title>Automation Report</title>");
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        writer.print("<h3 align=center id=\"summary\">API Automation Test Results - (" + dateFormat.format(new Date()) + ")</h3>");
        writeStylesheet();
        writer.print("</head>");
    }

    private void writeChartScripts() {
        //https://developers.google.com/chart/interactive/docs/gallery/piechart
        String script = "<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>" +
                "<script type=\"text/javascript\">" +
                "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});" +
                "google.setOnLoadCallback(drawChart);" +
                "function drawChart() {" +
                "var data0 = google.visualization.arrayToDataTable([" +
                "['Task Status', 'Count']," +
                "['Passed', " + getTestResultPriorityCount("PASS", "Blocker") + "]," +
                "['Failed', " + getTestResultPriorityCount("FAIL", "Blocker") + "]," +
                "['Skipped', " + getTestResultPriorityCount("SKIP", "Blocker") + "]" +
                "]);" +

                "var options0 = {" +
                "title: 'Blocker Priority Scenarios'," +
                "is3D: true," +
                "colors:['#109618','#DC3912','#FF9900']," +
                "pieSliceText: 'value'," +
                "};" +

                "var data1 = google.visualization.arrayToDataTable([" +
                "['Task Status', 'Count']," +
                "['Passed', " + getTestResultPriorityCount("PASS", "Critical") + "]," +
                "['Failed', " + getTestResultPriorityCount("FAIL", "Critical") + "]," +
                "['Skipped', " + getTestResultPriorityCount("SKIP", "Critical") + "]" +
                "]);" +

                "var options1 = {" +
                "title: 'Critical Priority Scenarios'," +
                "is3D: true," +
                "colors:['#109618','#DC3912','#FF9900']," +
                "pieSliceText: 'value'," +
                "};" +

                "var data2 = google.visualization.arrayToDataTable([" +
                "['Task Status', 'Count']," +
                "['Passed', " + getTestResultPriorityCount("PASS", "Normal") + "]," +
                "['Failed', " + getTestResultPriorityCount("FAIL", "Normal") + "]," +
                "['Skipped', " + getTestResultPriorityCount("SKIP", "Normal") + "]" +
                "]);" +

                "var options2 = {" +
                "title: 'Normal Priority Scenarios'," +
                "is3D: true," +
                "colors:['#109618','#DC3912','#FF9900']," +
                "pieSliceText: 'value'," +
                "};" +

                "var data3 = google.visualization.arrayToDataTable([" +
                "['Task Status', 'Count']," +
                "['Passed', " + getTestResultPriorityCount("PASS", "Minor") + "]," +
                "['Failed', " + getTestResultPriorityCount("FAIL", "Minor") + "]," +
                "['Skipped', " + getTestResultPriorityCount("SKIP", "Minor") + "]" +
                "]);" +

                "var options3 = {" +
                "title: 'Minor Priority Scenarios'," +
                "is3D: true," +
                "colors:['#109618','#DC3912','#FF9900']," +
                "pieSliceText: 'value'," +
                "};" +

                "var data4 = google.visualization.arrayToDataTable([" +
                "['Task Status', 'Count']," +
                "['Passed', " + getTestResultPriorityCount("PASS", "Trivial") + "]," +
                "['Failed', " + getTestResultPriorityCount("FAIL", "Trivial") + "]," +
                "['Skipped', " + getTestResultPriorityCount("SKIP", "Trivial") + "]" +
                "]);" +

                "var options4 = {" +
                "title: 'Trivial Priority Scenarios'," +
                "is3D: true," +
                "colors:['#109618','#DC3912','#FF9900']," +
                "pieSliceText: 'value'," +
                "};" +

                "var chart0 = new google.visualization.PieChart(document.getElementById('piechart0'));" +
                "var chart1 = new google.visualization.PieChart(document.getElementById('piechart1'));" +
                "var chart2 = new google.visualization.PieChart(document.getElementById('piechart2'));" +
                "var chart3 = new google.visualization.PieChart(document.getElementById('piechart3'));" +
                "var chart4 = new google.visualization.PieChart(document.getElementById('piechart4'));" +

                "chart0.draw(data0, options0);" +
                "chart1.draw(data1, options1);" +
                "chart2.draw(data2, options2);" +
                "chart3.draw(data3, options3);" +
                "chart4.draw(data4, options4);" +
                "}" +
                "</script>";
        writer.print(script);
    }

    private int getTestResultPriorityCount(String type, String priority) {
        List<ClassResult> tempResults = new ArrayList<>();
        int count = 0;

        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                if (type.equalsIgnoreCase("FAIL")) {
                    tempResults = testResult.getFailedTestResults();
                } else if (type.equalsIgnoreCase("SKIP")) {
                    tempResults = testResult.getSkippedTestResults();
                } else if (type.equalsIgnoreCase("PASS")) {
                    tempResults = testResult.getPassedTestResults();
                }

                if (!tempResults.isEmpty()) {
                    for (ClassResult classResult : tempResults) {
                        for (MethodResult methodResult : classResult.getMethodResults()) {
                            List<ITestResult> results = methodResult.getResults();
                            int resultsCount = results.size();
                            assert resultsCount > 0;
                            for (ITestResult result : results) {
                                if (validatePriorityParam(result, priority)) {
                                    count++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    private void writeStylesheet() {
        String workingDir = this.getClass().getClassLoader().getResource("").getPath();
        workingDir = workingDir.replaceAll("/target/classes", "");
        workingDir += "/test-output/WaterMark.jpg";

        writer.print("<style type=\"text/css\">");
        writer.print("body {background: url(" + workingDir + ") no-repeat center center fixed; -webkit-background-size: cover; -moz-background-size: cover; -o-background-size: cover;background-size: cover;}");
        writer.print("h1 {font-family:Calibri}");
        writer.print("h2 {font-family:Calibri}");
        writer.print("h3 {font-family:Calibri}");
        writer.print("h4 {font-family:Calibri}");

        writer.print("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
        writer.print("th,td {border:1px solid #009;padding:.25em .5em}");
        writer.print("th {vertical-align:bottom;background-color: #36648B;font-family:Calibri;color: #FFFFFF}");
        writer.print("td {vertical-align:top}");
        writer.print("table a {font-weight:bold}");
        writer.print(".stripe td {background-color: #E6EBF9}");
        writer.print(".num {text-align:right}");
        writer.print(".passedodd td, .color1 {background-color: #7FFFD4;font-family:Calibri;color: #000000}");
        writer.print(".passedeven td {background-color: #7FFFD4;font-family:Calibri;color: #000000}");
        writer.print(".skippedodd td, .color2 {background-color: #FFEC8B;font-family:Calibri;color: #000000}");
        writer.print(".skippedeven td {background-color: #FFEC8B;font-family:Calibri;color: #000000}");
        writer.print(".failedodd td {background-color: #FFB5C5;font-family:Calibri;color: #000000}");
        writer.print(".failedeven td, .color3 {background-color: #FFB5C5;font-family:Calibri;color: #000000}");
        writer.print(".warningodd td {background-color: #FFA500;font-family:Calibri;color: #000000}");
        writer.print(".warningeven td, .color4 {background-color: #FFA500;font-family:Calibri;color: #000000}");

        writer.print(".stacktrace {white-space:pre;font-family:Calibri}");
        writer.print(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
        writer.print("img.WaterMark {min-height: 100%;opacity:0.4;filter:alpha(opacity=40);min-width: 1024px;width: 100%;height: auto;position: fixed;top: 0;left: 0;}");
        writer.print("</style>");

    }

    private void writeBody() throws IOException {
        writer.print("<body>");
        writeChartBody();
        writeParamSummary();
        writeSuiteSummary();
        writeScenarioSummary();
        writeScenarioDetails();
        writer.print("</body>");
    }

    private void writeChartBody() {
        String code = "<div id=\"container\">" +
                "<div id=\"piechart0\" style=\"width: 20%; height: 25%; float: left;\"></div>" +
                "<div id=\"piechart1\" style=\"width: 20%; height: 25%; float: left;\"></div>" +
                "<div id=\"piechart2\" style=\"width: 20%; height: 25%; float: left;\"></div>" +
                "<div id=\"piechart3\" style=\"width: 20%; height: 25%; float: left;\"></div>" +
                "<div id=\"piechart4\" style=\"width: 20%; height: 25%; float: left;\"></div>" +
                "</div>";
        writer.print(code);
    }

    private void writeDocumentEnd() {
        writer.print("</html>");
    }

    private void writeParamSummary() throws IOException {
        StringBuilder code = new StringBuilder("<table>" +
                "<thead><tr><th>Parameter(s)</th><th>Value</th></tr></thead>" +
                "<tbody>");

        code.append("<tr>" + "<td>TestNG Suite File</td><td>").append(org.testng.internal.Utils.escapeHtml(suiteResults.get(0).getSuiteName())).append("</td></tr>");

        if (environmentFile != null) {
            Enumeration<?> e = environmentFile.getAllProperties();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                String value = environmentFile.getProperty(key);
                code.append("<tr>" + "<td>").append(key).append("</td><td>").append(value).append("</td></tr>");
            }
        }
        code.append("</tbody></table>");
        writer.print(code);
    }

    private void writeSuiteSummary() {
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        NumberFormat decimalFormat = NumberFormat.getNumberInstance();

        int totalPassedTests = 0;
        int totalSkippedTests = 0;
        int totalFailedTests = 0;
        long totalDuration = 0;

        writer.print("<table align=center width=\"100%\">");
        writer.print("<tr>");
        writer.print("<th align=center>Test</th>");
        writer.print("<th align=center>Passed</th>");
        writer.print("<th align=center>Skipped</th>");
        writer.print("<th align=center>Failed</th>");
        writer.print("<th align=center>Time (seconds)</th>");
        //writer.print("<th>Included Groups</th>");
        //writer.print("<th>Excluded Groups</th>");
        writer.print("</tr>");

        int testIndex = 0;
        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                int passedTests = testResult.getPassedTestCount();
                int skippedTests = testResult.getSkippedTestCount();
                int failedTests = testResult.getFailedTestCount();
                long duration = testResult.getDuration() / 1000; //Converting milli seconds into seconds

                writer.print("<tr");
                if ((testIndex % 2) == 1) {
                    writer.print(" class=\"stripe\"");
                }
                writer.print(">");

                buffer.setLength(0);
                writeTableData(buffer.append("<a href=\"#t").append(testIndex)
                        .append("\">")
                        .append(org.testng.internal.Utils.escapeHtml(testResult.getTestName()))
                        .append("</a>").toString());
                writeTableData(integerFormat.format(passedTests), (passedTests > 0 ? "num color1" : "num"));
                writeTableData(integerFormat.format(skippedTests),
                        (skippedTests > 0 ? "num color2" : "num"));
                writeTableData(integerFormat.format(failedTests),
                        (failedTests > 0 ? "num color3" : "num"));
                writeTableData(decimalFormat.format(duration), "num");
                //writeTableData(testResult.getIncludedGroups());
                //writeTableData(testResult.getExcludedGroups());

                writer.print("</tr>");

                totalPassedTests += passedTests;
                totalSkippedTests += skippedTests;
                totalFailedTests += failedTests;
                totalDuration += duration;

                testIndex++;
            }
        }

        // Print totals if there was more than one test
        if (testIndex > 1) {
            writer.print("<tr>");
            writer.print("<th>Total</th>");
            writeTableHeader(integerFormat.format(totalPassedTests),
                    (totalPassedTests > 0 ? "num color1" : "num"));
            writeTableHeader(integerFormat.format(totalSkippedTests),
                    (totalSkippedTests > 0 ? "num color2" : "num"));
            writeTableHeader(integerFormat.format(totalFailedTests),
                    (totalFailedTests > 0 ? "num color3" : "num"));
            writeTableHeader(decimalFormat.format(totalDuration), "num");
            writer.print("<th colspan=\"2\"></th>");
            writer.print("</tr>");
        }

        writer.print("</table>");
    }

    private void writeScenarioSummary() {
        writer.print("<table align=center width=\"100%\">");
        writer.print("<thead>");
        writer.print("<tr>");
        writer.print("<th align=center>Test Suite</th>");
        writer.print("<th align=center>Test Case</th>");
        writer.print("<th align=center>Test Case Description</th>");
        writer.print("<th align=center>Time (ms)</th>");
        writer.print("<th align=center>Exception Message</th>");
        writer.print("<th align=center>Warning Message</th>");
        writer.print("</tr>");
        writer.print("</thead>");

        int testIndex = 0;
        int scenarioIndex = 0;
        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                writer.print("<tbody id=\"t");
                writer.print(testIndex);
                writer.print("\">");

                scenarioIndex += writeScenarioSummary("Failed TestCases (configuration methods)", testResult.getFailedConfigurationResults(), "failed", scenarioIndex, "");
                scenarioIndex += writeScenarioSummary("Failed Blocker Priority TestCases", testResult.getFailedTestResults(), "failed", scenarioIndex, "Blocker");
                scenarioIndex += writeScenarioSummary("Failed Critical Priority TestCases", testResult.getFailedTestResults(), "failed", scenarioIndex, "Critical");
                scenarioIndex += writeScenarioSummary("Failed Normal Priority TestCases", testResult.getFailedTestResults(), "failed", scenarioIndex, "Normal");
                scenarioIndex += writeScenarioSummary("Failed Minor Priority TestCases", testResult.getFailedTestResults(), "failed", scenarioIndex, "Minor");
                scenarioIndex += writeScenarioSummary("Failed Trivial Priority TestCases", testResult.getFailedTestResults(), "failed", scenarioIndex, "Trivial");

//				scenarioIndex += writeScenarioSummary("Skipped TestCases (configuration methods)", testResult.getSkippedConfigurationResults(), "skipped", scenarioIndex,"");
                scenarioIndex += writeScenarioSummary("Skipped Blocker Priority TestCases", testResult.getSkippedTestResults(), "skipped", scenarioIndex, "Blocker");
                scenarioIndex += writeScenarioSummary("Skipped Critical Priority TestCases", testResult.getSkippedTestResults(), "skipped", scenarioIndex, "Critical");
                scenarioIndex += writeScenarioSummary("Skipped Normal Priority TestCases", testResult.getSkippedTestResults(), "skipped", scenarioIndex, "Normal");
                scenarioIndex += writeScenarioSummary("Skipped Minor Priority TestCases", testResult.getSkippedTestResults(), "skipped", scenarioIndex, "Minor");
                scenarioIndex += writeScenarioSummary("Skipped Trivial Priority TestCases", testResult.getSkippedTestResults(), "skipped", scenarioIndex, "Trivial");

                scenarioIndex += writeScenarioSummary("Passed TestCases", testResult.getPassedTestResults(), "passed", scenarioIndex, "");

                writer.print("</tbody>");

                testIndex++;
            }
        }

        writer.print("</table>");
    }

    private int writeScenarioSummary(String description, List<ClassResult> classResults, String cssClassPrefix, int startingScenarioIndex, String priority) {
        int scenarioCount = 0;
        Object[] parameters;
        boolean globalScenarioFlag = false;
        List<String> classList = new ArrayList<>();

        if (priority.equalsIgnoreCase("")) {
            globalScenarioFlag = true;
        } else {
            for (ClassResult classResult : classResults) {
                for (MethodResult methodResult : classResult.getMethodResults()) {
                    List<ITestResult> results = methodResult.getResults();
                    for (ITestResult result : results) {
                        if (validatePriorityParam(result, priority)) {
                            globalScenarioFlag = true;
                            if (!classList.contains(classResult.getClassName())) {
                                classList.add(classResult.getClassName());
                            }
                        }
                    }
                }
            }
        }

        if (!classResults.isEmpty() && globalScenarioFlag) {
            writer.print("<tr><th colspan=\"6\">");
            writer.print(description);
            writer.print("</th></tr>");

            int scenarioIndex = startingScenarioIndex;
            int classIndex = 0;
            for (ClassResult classResult : classResults) {

                String cssClass = cssClassPrefix + ((classIndex % 2) == 0 ? "even" : "odd");

                buffer.setLength(0);

                int scenariosPerClass = 0;
                int methodIndex = 0;

                String testCaseDescription;
                String className = classResult.getClassName();
                boolean classScenarioFlag = false;

                if (classList.contains(className) || priority.equalsIgnoreCase("")) {
                    for (MethodResult methodResult : classResult.getMethodResults()) {
                        List<ITestResult> results = methodResult.getResults();
                        for (ITestResult result : results) {
                            if (validatePriorityParam(result, priority)) {
                                classScenarioFlag = true;
                                break;
                            }
                        }
                    }

                    if (classScenarioFlag) {
                        for (MethodResult methodResult : classResult.getMethodResults()) {
                            List<ITestResult> results = methodResult.getResults();
                            int resultsCount = results.size();
                            results.sort(new TestResultSorter());
                            assert resultsCount > 0;

                            ITestResult firstResult = results.iterator().next();
                            String methodName = org.testng.internal.Utils.escapeHtml(firstResult.getMethod().getMethodName());
                            long start = firstResult.getStartMillis();
                            long duration = (firstResult.getEndMillis() - start);

                            parameters = firstResult.getParameters();
							/*if(cssClassPrefix.equalsIgnoreCase("failed") && firstResult.getThrowable().getMessage() != null && firstResult.getThrowable().getMessage().contains("Error message mis-match")) {
								cssClassPrefix = "warning" ;
							}*/
                            //cssClass = cssClassPrefix + ((classIndex % 2) == 0 ? "even" : "odd");
                            //AVG Change to check 'ArrayIndexOutOfBounds' Exception
                            if (parameters.length > columnsCount - 1) {
                                testCaseDescription = "C" + validateParams(parameters[sNoColumn]) + " - " + validateParams(parameters[descriptionColumn]);
                            } else {
                                testCaseDescription = "";
                            }

                            // The first method per class shares a row with the class
                            // header
                            if (methodIndex > 0) {
                                cssClass = cssClassPrefix
                                        + ((classIndex % 2) == 0 ? "even" : "odd");
                                buffer.append("<tr class=\"").append(cssClass)
                                        .append("\">");
                            }

                            // Write the timing information with the first scenario per method
                            if (validatePriorityParam(firstResult, priority) || priority.equalsIgnoreCase("")) {
                                buffer.append("<td><a href=\"#m").append(scenarioIndex)
                                        .append("\">").append(methodName)
                                        .append("</a></td>")
                                        .append("<td>")
                                        .append(testCaseDescription)
                                        .append("</td>")
                                        .append("<td>")
                                        .append(duration).append("</td>");
                                if (firstResult.getStatus() == ITestResult.FAILURE && firstResult.getThrowable().getMessage() != null && !firstResult.getThrowable().getMessage().contains("Error message mis-match")) {
                                    buffer.append("<td>")
                                            .append(firstResult.getThrowable().getMessage())
                                            .append("</td><td></td></tr>");
                                } else if (firstResult.getStatus() == ITestResult.FAILURE && firstResult.getThrowable().getMessage() != null && firstResult.getThrowable().getMessage().contains("Error message mis-match")) {
                                    buffer.append("<td></td>");
                                    buffer.append("<td>")
                                            .append(firstResult.getThrowable().getMessage())
                                            .append("</td></tr>");
                                } else {
                                    buffer.append("<td></td><td></td></tr>");
                                }
                                linkMap.put(methodName + testCaseDescription, scenarioIndex);
                                scenarioIndex++;
                            }

                            int testcasePriorityCount = 0;
                            // Write the remaining scenarios for the method
                            for (int i = 1; i < resultsCount; i++) {

                                cssClass = cssClassPrefix
                                        + ((classIndex % 2) == 0 ? "even" : "odd");

                                firstResult = results.get(i);//iterator().next();
                                methodName = org.testng.internal.Utils.escapeHtml(firstResult.getMethod().getMethodName());
                                parameters = firstResult.getParameters();
                                duration = (firstResult.getEndMillis() - firstResult.getStartMillis());
                                if (parameters.length > columnsCount - 1) {
                                    testCaseDescription = "C" + validateParams(parameters[sNoColumn]) + " - " + validateParams(parameters[descriptionColumn]);
                                } else {
                                    testCaseDescription = "";
                                }

                                if (validatePriorityParam(firstResult, priority) || priority.equalsIgnoreCase("")) {
                                    buffer.append("<tr class=\"").append(cssClass)
                                            .append("\">").append("<td><a href=\"#m")
                                            .append(scenarioIndex).append("\">")
                                            .append(methodName)
                                            .append("</a></td>")
                                            .append("<td>")
                                            .append(testCaseDescription)
                                            .append("</td>")
                                            .append("<td>")
                                            .append(duration).append("</td>");
                                    if (firstResult.getStatus() == ITestResult.FAILURE && firstResult.getThrowable().getMessage() != null && !firstResult.getThrowable().getMessage().contains("Error message mis-match")) {
                                        buffer.append("<td>")
                                                .append(firstResult.getThrowable().getMessage())
                                                .append("</td><td></td></tr>");
                                    } else if (firstResult.getStatus() == ITestResult.FAILURE && firstResult.getThrowable().getMessage() != null && firstResult.getThrowable().getMessage().contains("Error message mis-match")) {
                                        buffer.append("<td></td>");
                                        buffer.append("<td>")
                                                .append(firstResult.getThrowable().getMessage())
                                                .append("</td></tr>");
                                    } else {
                                        buffer.append("<td></td><td></td></tr>");
                                    }
                                    linkMap.put(methodName + testCaseDescription, scenarioIndex);
                                    testcasePriorityCount++;
                                    scenarioIndex++;
                                }
                            }
                            scenariosPerClass += testcasePriorityCount + 1;
                            methodIndex++;
                        }

                        // Write the test results for the class
                        writer.print("<tr class=\"");
                        writer.print(cssClass);
                        writer.print("\">");
                        writer.print("<td rowspan=\"");
                        writer.print(scenariosPerClass);
                        writer.print("\">");
                        writer.print(org.testng.internal.Utils.escapeHtml(className));
                        writer.print("</td>");
                        writer.print(buffer);
                    }
                    classIndex++;
                }
            }
            scenarioCount = scenarioIndex - startingScenarioIndex;
        }
        return scenarioCount;
    }

    /**
     * Writes the details for all test scenarios.
     */
    private void writeScenarioDetails() {
        int scenarioIndex = 0;
        for (SuiteResult suiteResult : suiteResults) {
            for (TestResult testResult : suiteResult.getTestResults()) {
                writer.print("<h3 align=center>");
                writer.print(org.testng.internal.Utils.escapeHtml(testResult.getTestName()));
                writer.print("</h3>");

                scenarioIndex += writeScenarioDetails(testResult.getFailedConfigurationResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(testResult.getFailedTestResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(testResult.getSkippedConfigurationResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(testResult.getSkippedTestResults(), scenarioIndex);
                scenarioIndex += writeScenarioDetails(testResult.getPassedTestResults(), scenarioIndex);
            }
        }
    }

    /**
     * Writes the scenario details for the results of a given state for a single
     * test.
     */
    private int writeScenarioDetails(List<ClassResult> classResults,
                                     int startingScenarioIndex) {
        int scenarioIndex = startingScenarioIndex;
        for (ClassResult classResult : classResults) {
            String className = classResult.getClassName();

            for (MethodResult methodResult : classResult.getMethodResults()) {
                List<ITestResult> results = methodResult.getResults();
                assert !results.isEmpty();

                String label = org.testng.internal.Utils.escapeHtml(className + "-->" + results.iterator().next().getMethod().getMethodName());
                for (ITestResult result : results) {
                    writeScenario(scenarioIndex, label, result);
                    scenarioIndex++;
                }
            }
        }

        return scenarioIndex - startingScenarioIndex;
    }

    /**
     * Writes the details for an individual test scenario.
     */
    private void writeScenario(int scenarioIndex, String label, ITestResult result) {
        String testCaseDescription = "";
        Object[] parameters = result.getParameters();
        String methodName = org.testng.internal.Utils.escapeHtml(result.getMethod().getMethodName());

        if (parameters.length > columnsCount - 1) {
            testCaseDescription = "C" + validateParams(parameters[sNoColumn]) + " - " + validateParams(parameters[descriptionColumn]);
        }

        writer.print("<h4 align=center id=\"m");
        writer.print(linkMap.get(methodName + testCaseDescription));
        writer.print("\">");
        writer.print(label);
        writer.print("</h4>");

        writer.print("<table align=center class=\"result\" width=\"100%\">");

        // Write test parameters (if any)
        int parameterCount = parameters.length;
        if (parameterCount > 0) {
            writer.print("<tr class=\"param\">");
            for (int i = 1; i <= parameterCount - 1; i++) {//AVG - To not print the S.No.
                writer.print("<th>Parameter ");
                writer.print(i);
                writer.print("</th>");
            }
            writer.print("</tr><tr class=\"param stripe\">");
            boolean firstLoop = true, secondLoop = true;//AVG - To not print the S.No.
            String tempSno = "", tempParam;
            for (Object parameter : parameters) {
                if (firstLoop) {
                    tempSno = "C" + org.testng.internal.Utils.escapeHtml(org.testng.internal.Utils.toString(parameter, null)) + " - ";
                    firstLoop = false;
                } else {
                    tempParam = org.testng.internal.Utils.escapeHtml(org.testng.internal.Utils.toString(parameter, null));
                    if (secondLoop) {
                        tempParam = tempSno + tempParam;
                        secondLoop = false;
                    }
                    writer.print("<td>");
                    writer.print(tempParam);
                    writer.print("</td>");
                }
            }
            writer.print("</tr>");
        }

        // Write reporter messages (if any)
        List<String> reporterMessages = Reporter.getOutput(result);

        if (!reporterMessages.isEmpty()) {
            writer.print("<tr><th");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }
            writer.print(">Logger Details</th></tr>");

            writer.print("<tr><td font-family=\"Calibri\"");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }
            writer.print(">");
            writeReporterMessages(reporterMessages);
            writer.print("</td></tr>");
        }

        // Write exception (if any)
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            writer.print("<tr><th");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }
            writer.print(">");
            writer.print((result.getStatus() == ITestResult.SUCCESS ? "Expected Exception"
                    : "Exception"));
            writer.print("</th></tr>");

            writer.print("<tr><td font-family=\"Calibri\"");
            if (parameterCount > 1) {
                writer.print(" colspan=\"");
                writer.print(parameterCount);
                writer.print("\"");
            }
            writer.print(">");
            writeStackTrace(throwable);
            writer.print("</td></tr>");
        }

        writer.print("</table>");
        writer.print("<p class=\"totop\"><a href=\"#summary\">back to top</a></p>");
    }

    private void writeReporterMessages(List<String> reporterMessages) {
        writer.print("<div class=\"messages\">");
        Iterator<String> iterator = reporterMessages.iterator();
        assert iterator.hasNext();
        writer.print(iterator.next());
        //        writer.print(PathUtils.escapeHtml(iterator.next()));
        while (iterator.hasNext()) {
            writer.print("<br/>");
            //            writer.print(PathUtils.escapeHtml(iterator.next()));
            writer.print(iterator.next());
        }
        writer.print("</div>");
    }

    private void writeStackTrace(Throwable throwable) {
        writer.print("<div class=\"stacktrace\">");
        writer.print(org.testng.internal.Utils.shortStackTrace(throwable, true));
        writer.print("</div>");
    }

    /**
     * Writes a TH element with the specified contents and CSS class names.
     *
     * @param html       the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no
     *                   classes to apply
     */
    private void writeTableHeader(String html, String cssClasses) {
        writeTag("th", html, cssClasses);
    }

    /**
     * Writes a TD element with the specified contents.
     *
     * @param html the HTML contents
     */
    private void writeTableData(String html) {
        writeTableData(html, null);
    }

    /**
     * Writes a TD element with the specified contents and CSS class names.
     *
     * @param html       the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no
     *                   classes to apply
     */
    private void writeTableData(String html, String cssClasses) {
        writeTag("td", html, cssClasses);
    }

    /**
     * Writes an arbitrary HTML element with the specified contents and CSS
     * class names.
     *
     * @param tag        the tag name
     * @param html       the HTML contents
     * @param cssClasses the space-delimited CSS classes or null if there are no
     *                   classes to apply
     */
    private void writeTag(String tag, String html, String cssClasses) {
        writer.print("<");
        writer.print(tag);
        if (cssClasses != null) {
            writer.print(" class=\"");
            writer.print(cssClasses);
            writer.print("\"");
        }
        writer.print(">");
        writer.print(html);
        writer.print("</");
        writer.print(tag);
        writer.print(">");
    }

    private String validateParams(Object obj) {
        if (obj != null) {
            return obj.toString();
        } else {
            return "null";
        }
    }

    private boolean validatePriorityParam(ITestResult result, String priority) {
        Object[] parameters = result.getParameters();
        boolean parametersSizeSafe = parameters.length > columnsCount - 1;
        boolean normalPriorityFlag = priority.equalsIgnoreCase("Normal");
        if (parametersSizeSafe || normalPriorityFlag) {
            int priorityColumn = 2;
            if (normalPriorityFlag) {
                if (!parametersSizeSafe) {
                    return true;
                } else {
                    if (parameters[priorityColumn] instanceof String) {
                        String param = parameters[priorityColumn].toString();
                        switch (param) {
                            case "BLOCKER":
                            case "CRITICAL":
                            case "MINOR":
                            case "TRIVIAL":
                                return false;
                            default:
                                return true;
                        }
                    } else {
                        return true;
                    }
                }
            } else {
                return "".equalsIgnoreCase(priority) || (parameters[priorityColumn] != null && parameters[priorityColumn].toString().equalsIgnoreCase(priority));
            }
        } else {
            return "".equalsIgnoreCase(priority);
        }
    }

    /**
     * Groups {@link TestResult}s by suite.
     */
    static class SuiteResult {
        private final String suiteName;
        private final List<TestResult> testResults = Lists.newArrayList();

        SuiteResult(ISuite suite) {
            suiteName = suite.getName();
            for (ISuiteResult suiteResult : suite.getResults().values()) {
                testResults.add(new TestResult(suiteResult.getTestContext()));
            }
        }

        String getSuiteName() {
            return suiteName;
        }

        /**
         * @return the test results (possibly empty)
         */
        List<TestResult> getTestResults() {
            return testResults;
        }
    }

    /**
     * Groups {@link ClassResult}s by test, type (configuration or test), and
     * status.
     */
    static class TestResult {
        //            @Override
        /**
         * Orders test results by class name and then by method name (in
         * lexicographic order).
         */
        static final Comparator<ITestResult> RESULT_COMPARATOR = Comparator.comparing((ITestResult o) -> o.getTestClass().getName()).thenComparing(o -> o.getMethod().getMethodName());

        private final String testName;
        private final List<ClassResult> failedConfigurationResults;
        private final List<ClassResult> failedTestResults;
        private final List<ClassResult> skippedConfigurationResults;
        private final List<ClassResult> skippedTestResults;
        private final List<ClassResult> passedTestResults;
        private final int failedTestCount;
        private final int skippedTestCount;
        private final int passedTestCount;
        private final long duration;
        private final String includedGroups;
        private final String excludedGroups;

        TestResult(ITestContext context) {
            testName = context.getName();

            Set<ITestResult> failedConfigurations = context
                    .getFailedConfigurations().getAllResults();
            Set<ITestResult> failedTests = context.getFailedTests()
                    .getAllResults();
            Set<ITestResult> skippedConfigurations = context
                    .getSkippedConfigurations().getAllResults();
            Set<ITestResult> skippedTests = context.getSkippedTests()
                    .getAllResults();
            Set<ITestResult> passedTests = context.getPassedTests()
                    .getAllResults();

            failedConfigurationResults = groupResults(failedConfigurations);
            failedTestResults = groupResults(failedTests);
            skippedConfigurationResults = groupResults(skippedConfigurations);
            skippedTestResults = groupResults(skippedTests);
            passedTestResults = groupResults(passedTests);

            failedTestCount = failedTests.size();
            skippedTestCount = skippedTests.size();
            passedTestCount = passedTests.size();

            duration = (context.getEndDate().getTime() - context.getStartDate().getTime());

            includedGroups = formatGroups(context.getIncludedGroups());
            excludedGroups = formatGroups(context.getExcludedGroups());

        }

        /**
         * Groups test results by method and then by class.
         */
        List<ClassResult> groupResults(Set<ITestResult> results) {
            List<ClassResult> classResults = Lists.newArrayList();
            if (!results.isEmpty()) {
                List<MethodResult> resultsPerClass = Lists.newArrayList();
                List<ITestResult> resultsPerMethod = Lists.newArrayList();

                List<ITestResult> resultsList = Lists.newArrayList(results);
                resultsList.sort(RESULT_COMPARATOR);
                Iterator<ITestResult> resultsIterator = resultsList.iterator();
                assert resultsIterator.hasNext();

                ITestResult result = resultsIterator.next();
                resultsPerMethod.add(result);

                String previousClassName = result.getTestClass().getName();
                String previousMethodName = result.getMethod().getMethodName();
                while (resultsIterator.hasNext()) {
                    result = resultsIterator.next();

                    String className = result.getTestClass().getName();
                    if (!previousClassName.equals(className)) {
                        // Different class implies different method
                        assert !resultsPerMethod.isEmpty();
                        resultsPerClass.add(new MethodResult(resultsPerMethod));
                        resultsPerMethod = Lists.newArrayList();

                        assert !resultsPerClass.isEmpty();
                        classResults.add(new ClassResult(previousClassName,
                                resultsPerClass));
                        resultsPerClass = Lists.newArrayList();

                        previousClassName = className;
                        previousMethodName = result.getMethod().getMethodName();
                    } else {
                        String methodName = result.getMethod().getMethodName();
                        if (!previousMethodName.equals(methodName)) {
                            assert !resultsPerMethod.isEmpty();
                            resultsPerClass.add(new MethodResult(resultsPerMethod));
                            resultsPerMethod = Lists.newArrayList();

                            previousMethodName = methodName;
                        }
                    }
                    resultsPerMethod.add(result);
                }
                assert !resultsPerMethod.isEmpty();
                resultsPerClass.add(new MethodResult(resultsPerMethod));
                assert !resultsPerClass.isEmpty();
                classResults.add(new ClassResult(previousClassName,
                        resultsPerClass));
            }
            return classResults;
        }

        String getTestName() {
            return testName;
        }

        /**
         * @return the results for failed configurations (possibly empty)
         */
        List<ClassResult> getFailedConfigurationResults() {
            return failedConfigurationResults;
        }

        /**
         * @return the results for failed tests (possibly empty)
         */
        List<ClassResult> getFailedTestResults() {
            return failedTestResults;
        }

        /**
         * @return the results for skipped configurations (possibly empty)
         */
        List<ClassResult> getSkippedConfigurationResults() {
            return skippedConfigurationResults;
        }

        /**
         * @return the results for skipped tests (possibly empty)
         */
        List<ClassResult> getSkippedTestResults() {
            return skippedTestResults;
        }

        /**
         * @return the results for passed tests (possibly empty)
         */
        List<ClassResult> getPassedTestResults() {
            return passedTestResults;
        }

        int getFailedTestCount() {
            return failedTestCount;
        }

        int getSkippedTestCount() {
            return skippedTestCount;
        }

        int getPassedTestCount() {
            return passedTestCount;
        }

        long getDuration() {
            return duration;
        }

        public String getIncludedGroups() {
            return includedGroups;
        }

        public String getExcludedGroups() {
            return excludedGroups;
        }


        /**
         * Formats an array of groups for display.
         */
        String formatGroups(String[] groups) {
            if (groups.length == 0) {
                return "";
            }

            StringBuilder builder = new StringBuilder();
            builder.append(groups[0]);
            for (int i = 1; i < groups.length; i++) {
                builder.append(", ").append(groups[i]);
            }
            return builder.toString();
        }


    }

    /**
     * Groups {@link MethodResult}s by class.
     */
    static class ClassResult {
        private final String className;
        private final List<MethodResult> methodResults;

        /**
         * @param className     the class name
         * @param methodResults the non-null, non-empty {@link MethodResult} list
         */
        ClassResult(String className, List<MethodResult> methodResults) {
            this.className = className;
            this.methodResults = methodResults;
        }

        String getClassName() {
            return className;
        }

        /**
         * @return the non-null, non-empty {@link MethodResult} list
         */
        List<MethodResult> getMethodResults() {
            return methodResults;
        }
    }

    /**
     * Groups test results by method.
     */
    static class MethodResult {
        private final List<ITestResult> results;

        /**
         * @param results the non-null, non-empty result list
         */
        MethodResult(List<ITestResult> results) {
            this.results = results;
        }

        /**
         * @return the non-null, non-empty result list
         */
        List<ITestResult> getResults() {
            return results;
        }
    }

    class TestResultSorter implements Comparator<ITestResult> {
        @Override
        public int compare(ITestResult r1, ITestResult r2) {
            return Long.compare(r1.getStartMillis(), r2.getStartMillis());
        }
    }
}