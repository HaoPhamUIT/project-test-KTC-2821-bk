package com.katalon.testcloud

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.configuration.RunConfiguration;
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.WebDriver
import internal.GlobalVariable

public class ExecutionDynamicKeyword {
	String browserType = RunConfiguration.getDriverSystemProperty(RunConfiguration.REMOTE_DRIVER_PROPERTY, "browserType");
	Map<String, Object> runTestCloudFromTestOps = RunConfiguration.getReportProperties();
	@Keyword
	def execute(String action,String params) {
		println("runTestCloudFromTestOps value : ${runTestCloudFromTestOps}.")
		println("browserType value : ${browserType}.")
		if (runTestCloudFromTestOps) {
			try {
				def tcExecutionEnvClass = this.class.classLoader.loadClass('com.katalon.testcloud.TcExecutionKeyword')
				def tcExecutionEnv = tcExecutionEnvClass.newInstance()
				return tcExecutionEnv.execute(action, params)
			} catch (ClassNotFoundException e) {
				throw new Exception('ExcutionDynamicKeyword is not defined yet. Adding it to the project...', e)
			}
		}
		//runTestCloudFromKatalonStudio
		if ("TestCloud" == browserType) {
			try {
				WebDriver driver = DriverFactory.getWebDriver()
				String scriptCommand = String.format('execute-action=%s execute-param=%s', action, params)
				return ((JavascriptExecutor) driver).executeScript(scriptCommand)
			} catch (Exception e) {
				throw new Exception('Failed to execute TestCloud Keyword:', e)
			}
		}
	}
	
}
