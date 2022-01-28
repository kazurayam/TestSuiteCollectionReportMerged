package com.kazurayam.ks.testsuitecollectionreport

import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Before
import org.junit.Test
import org.w3c.dom.Document

import com.kms.katalon.core.configuration.RunConfiguration

public class ReportsMergerTest {

	Path projectDir = Paths.get(RunConfiguration.getProjectDir())
	Path reportsDir

	@Before
	void setup() {
		reportsDir = projectDir.resolve("Include/fixtures/Reports")
		assert Files.exists(reportsDir)
	}

	@Test
	void test_findReportCollectionEntities() {
		List<Path> results = ReportsMerger.findReportCollectionEntities(reportsDir)
		assertTrue(results.size() > 0)
	}

	@Test
	void test_findLatestReportCollectionEntity() {
		Optional<Path> latest = ReportsMerger.findLatestReportCollectionEntity(reportsDir)
		assertTrue (latest.isPresent())
	}

	@Test
	void test_findXMlReports() {
		Optional<Path> latest = ReportsMerger.findLatestReportCollectionEntity(reportsDir)
		if (latest.isPresent()) {
			Path p = latest.get()
			List<Path> xmlReports = ReportsMerger.findXmlReports(p)
			assertTrue(xmlReports.size() > 0)
			assertEquals(2, xmlReports.size())
			//println xmlReports.get(0).toString()
			//println xmlReports.get(1).toString()
		} else {
			fail("latest ReportCollectionEntity not found")
		}
	}

	@Test
	void test_loadXmlDocuments() {
		Optional<Path> rp = ReportsMerger.findLatestReportCollectionEntity(reportsDir)
		if (rp.isPresent()) {
			List<Path> xmlReports = ReportsMerger.findXmlReports(rp.get())
			assert xmlReports.size() > 0
			List<Document> docs = ReportsMerger.loadXmlDocuments(xmlReports)
			assert docs.size() > 0
		} else {
			fail("rp was empty")
		}
		
	}
}
