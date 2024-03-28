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

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import static org.apache.commons.io.IOUtils.toByteArray;
import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import internal.GlobalVariable;

public class CameraImageInjectionExecutor {
	final double MAX_FILE_SIZE_MB = 4.5;

	boolean isSupportedFileType(File file) {
		String fileName = file.getName()
		String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()
		return ['png', 'jpg', 'jpeg'].contains(fileExtension)
	}

	void checkValidFile(File file) {
		if (!file.exists()) {
			throw new Exception(String.format("File %s is not existed.", file.name))
		}

		long fileSizeInByte = file.length()

		if (fileSizeInByte > MAX_FILE_SIZE_MB * (1024 * 1024)) {
			throw new Exception(String.format("File size is exceeding %sMB.", MAX_FILE_SIZE_MB))
		}

		// Check file type (supports png, jpg, and jpeg)
		if (!isSupportedFileType(file)) {
			throw new Exception("File type is not supported. Only png, jpg, and jpeg are supported.")
		}
	}


	File getFileByName(String fileName) {
		try {
			Path userHome = Paths.get(System.getProperty("user.dir"));
			Path dataFileDirectory = userHome.resolve("Data Files");
			Path testCloudDirectory = dataFileDirectory.resolve("TestCloud");
			Path filePath = testCloudDirectory.resolve(fileName);
			File file = filePath.toFile();
			return file;
		} catch (Exception e) {
			throw new Exception(String.format("Error when get file %s.", fileName))
		}
	}


	/**
	 * Injects an image from "Data Files/Test Cloud" into the camera for testing purposes.
	 *
	 * @param fileName The name of the file to inject.
	 * @return true if the injection was successful, false if there was an error.
	 */
	@Keyword
	def injectImage(String fileName) {
		File file = getFileByName(fileName);
		checkValidFile(file);
		try {
			AppiumDriver<?> driver = MobileDriverFactory.getDriver()
			FileInputStream fileStream = new FileInputStream(file);
			String encodedImage = Base64.getEncoder().encodeToString(toByteArray(fileStream));
			driver.executeScript("sauce:inject-image=" + encodedImage);
			fileStream.close();
			return true;
		} catch (Exception e) {
			throw new Exception("Failed to execute TestCloud Keyword: "
			+ "CameraImageInjectionExecutor.injectImage - Error Code: TCKW-201")
		}
	}

	/**
	 * Checks whether a file with the given name exist in "Data Files/Test Cloud".
	 *
	 * @param fileName The name of the file to check.
	 * @return true if the file exists, false otherwise.
	 */
	@Keyword
	def exist(String fileName) {
		try {
			File file = getFileByName(fileName);
			if (file == null) {
				return false;
			}
			return file.exists();
		} catch (Exception e) {
			throw new Exception("Failed to execute TestCloud Keyword: "
			+ "CameraImageInjectionExecutor.fileExists - Error Code: TCKW-202")
		}
	}
}
