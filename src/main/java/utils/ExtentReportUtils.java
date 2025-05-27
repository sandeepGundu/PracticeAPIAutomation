package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportUtils
{
    private static ExtentReports extentReports;
    //private static ExtentTest extentTest;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Initializes the Extent Reports instance.
     */
    public static void initializeReport(String reportFilePath) {
        if (extentReports == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports.html");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("API Automation Test Report");
            sparkReporter.config().setEncoding("utf-8");
            sparkReporter.config().setReportName("API Automation Framework");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
        }
    }

    /**
     * Creates a new test in the report.
     *
     * @param testName The name of the test.
     */
    public static void createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTest.set(test); // Set the test in ThreadLocal
        //extentTest = extentReports.createTest(testName);
    }

    /**
     * Logs information to the current test.
     *
     * @param message The message to log.
     */
    public static void logInfo(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.info(message);
        } else {
            throw new IllegalStateException("ExtentTest is not initialized. Call createTest() before logging.");
        }
        //extentTest.info(message);
    }

    /**
     * Logs a pass status to the current test.
     *
     * @param message The message to log.
     */
    public static void logPass(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.pass(message);
        } else {
            throw new IllegalStateException("ExtentTest is not initialized. Call createTest() before logging.");
        }
        //extentTest.pass(message);
    }

    /**
     * Logs a fail status to the current test.
     *
     * @param message The message to log.
     */
    public static void logFail(String message) {
        ExtentTest test = extentTest.get();
        if (test != null) {
            test.fail(message);
        } else {
            throw new IllegalStateException("ExtentTest is not initialized. Call createTest() before logging.");
        }
        //extentTest.fail(message);
    }

    /**
     * Flushes the report to the file.
     */
    public static void flushReport() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
