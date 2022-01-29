package com.kazurayam.ks.reports

import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.w3c.dom.Document

import com.kms.katalon.core.configuration.RunConfiguration

public class TestSuiteCollectionReportsUtilTest {

	Path projectDir = Paths.get(".")
	Path reportsDir

	@Before
	void setup() {
		reportsDir = projectDir.resolve("Include/fixtures/Reports").toAbsolutePath()
		assert Files.exists(reportsDir)
	}


	@Test
	void test_findReportCollectionEntities() {
		List<Path> results = TestSuiteCollectionReportsUtil.findReportCollectionEntities(reportsDir)
		assertTrue(results.size() > 0)
	}

	@Test
	void test_findLatestReportCollectionEntity() {
		Optional<Path> latest = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		assertTrue (latest.isPresent())
	}


	@Test
	void test_findXMlReports() {
		Optional<Path> latest = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		if (latest.isPresent()) {
			Path p = latest.get()
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(p)
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
		Optional<Path> rp = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		if (rp.isPresent()) {
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(rp.get())
			assert xmlReports.size() > 0
			List<Document> docs = TestSuiteCollectionReportsUtil.loadXmlDocuments(xmlReports)
			assert docs.size() > 0
		} else {
			fail("rp was empty")
		}
	}

	
}
