package com.katalon.testcloud

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

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

public class FileExecutor {

	/**
	 * Download file content using base64 encoding.
	 *
	 * @param the given file name
	 * @return the file content in base64
	 */
	@Keyword
	def getFileContent(String fileName) {
		try {
			WebDriver driver  = DriverFactory.getWebDriver()
			String scriptCommand = String.format('lambda-file-content=%s', fileName)
			String base64EncodedFile = ((JavascriptExecutor) driver).executeScript(scriptCommand).toString()
			return base64EncodedFile
		} catch (Exception e) {
			throw new Exception('Failed to execute TestCloud Keyword: FileExecutor.getFileContent - Error Code: TCKW-104');
		}
	}

	/**
	 * Retrieve file metadata.
	 *
	 * @param the given file name
	 * @return file metadata
	 */
	@Keyword
	def getFileDescriptor(String fileName) {
		try {
			WebDriver driver  = DriverFactory.getWebDriver()
			String scriptCommand = String.format('lambda-file-stats=%s', fileName)
			return ((JavascriptExecutor) driver).executeScript(scriptCommand)
		} catch (Exception e) {
			throw new Exception('Failed to execute TestCloud Keyword: FileExecutor.getFileDescriptor - Error Code: TCKW-105');
		}
	}

	/**
	 * Check if file name exists.
	 *
	 * @param the given file name
	 * @return true if the file exists
	 */
	@Keyword
	def exist(String fileName) {
		try {
			WebDriver driver  = DriverFactory.getWebDriver()
			String scriptCommand = String.format('lambda-file-exists=%s', fileName)
			return ((JavascriptExecutor) driver).executeScript(scriptCommand)
		} catch (Exception e) {
			throw new Exception('Failed to execute TestCloud Keyword: FileExecutor.exist - Error Code: TCKW-106');
		}
	}
}