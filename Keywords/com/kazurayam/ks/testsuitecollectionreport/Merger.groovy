package com.kazurayam.ks.testsuitecollectionreport

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
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration

public class Merger {
	
	static final Path projectDir = Paths.get(RunConfiguration.getProjectDir())
	
	public Merger() {
		Path reportsDir = projectDir.resolve("Reports")
		Path latestBunchDir = findLatestBunchDir(reportsDir)
	}
	
	private Optional<Path> findLatestBunchDir(Path reportsDir) {
		List<Path> dirs = 
			Files.list(reportsDir)
				.filter({ p -> Files.isDirectory(p) })
				.filter({ p -> p.getFileName().toString().matches("\\d[6]_\\d[6]") })
				.filter({ p -> findReportCollectionEntity(p).size() > 0 })
				.sorted(Comparator.comparing(Path::getFileName, Comparator.reverseOrder()))
				.collect(Collectors.toList())
		if (dirs.size() > 0) {
			return Optional.of(dirs.get(0)
		} else {
			return Optional.empty()
		}
	}
	
	private List<Path> findReportCollectionEntity(Path base) {
		ReportCollectionEntityFileFinder finder = new ReportCollectionEntityFileFinder()
		Files.walkFileTree(p, finder)
		return finder.getFound()
	}
	
	public void compile(String path) {
		Path outfile = resolveOutfile(path)
	}
	
	private Path resolveOutfile(String path) {
		Path p = Paths.get(path)
		if (! p.isAbsolute()) {
			p = projectDir.resolve(p)
		}
		Files.createDirectories(p.getParent())
		return p
	}
	
	
}
