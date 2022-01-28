package com.kazurayam.ks.testsuitecollectionreport

import static org.junit.Assert.*

import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Test

import com.kazurayam.ks.testsuitecollectionreport.ReportsMerger.ReportCollectionEntityFileFinder
import com.kms.katalon.core.configuration.RunConfiguration

public class ReportCollectionEntityFileFinderTest {
	
	static final Path projectDir = Paths.get(RunConfiguration.getProjectDir())
	
	@Test
	void test_findReportCollectionEntity() {
		Path reportsDir = projectDir.resolve("Reports")
		List<Path> result = ReportCollectionEntityFileFinder.findReportCollectionEntity(reportsDir)
		assertTrue(result.size() > 0)
		//println result.get(0).toString()
	}
}
