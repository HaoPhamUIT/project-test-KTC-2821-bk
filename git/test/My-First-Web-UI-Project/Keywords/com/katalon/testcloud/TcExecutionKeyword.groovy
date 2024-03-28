package com.katalon.testcloud

import com.katalon.testcloud.*
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

import internal.GlobalVariable

public class TcExecutionKeyword {
	def execute(String action, String params) {
		if ("check-exist-file".equals(action)) {
			(new com.katalon.testcloud.FileExecutor()).exist(params)
		} else if ("get-content-file".equals(action)) {
			(new com.katalon.testcloud.FileExecutor()).getFileContent(params)
		} else if ("get-descriptor-file".equals(action)) {
			(new com.katalon.testcloud.FileExecutor()).exist(params)
		} else if ("inject-image-camera".equals(action)) {
			(new com.katalon.testcloud.CameraImageInjectionExecutor()).injectImage(params)
		} else if ("check-exist-image-camera".equals(action)) {
			(new com.katalon.testcloud.CameraImageInjectionExecutor()).exist(params)
		}
	}
}
