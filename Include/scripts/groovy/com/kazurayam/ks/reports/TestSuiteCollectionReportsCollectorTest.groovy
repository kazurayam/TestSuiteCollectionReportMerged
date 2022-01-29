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

public class TestSuiteCollectionReportsCollectorTest {

	Path projectDir = Paths.get(".")
	Path reportsDir
	Path outputDir

	@Before
	void setup() {
		reportsDir = projectDir.resolve("Include/fixtures/Reports").toAbsolutePath()
		assert Files.exists(reportsDir)
		outputDir = projectDir.resolve("build/tmp/testOutput/TestSuiteCollectionReprtsCollectorTest")
	}


	@Test
	void test_getStats() {
		Optional<Path> rp = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		if (rp.isPresent()) {
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(rp.get())
			List<Document> docs = TestSuiteCollectionReportsUtil.loadXmlDocuments(xmlReports)
			List<TestSuiteStat> stats = TestSuiteCollectionReportsCollector.getStats(docs)
			assert stats != null
			assert stats.size() > 0
			for (TestSuiteStat stat in stats) {
				println stat
			}
		} else {
			fail("rp was empty")
		}
	}

	@Test
	void test_calculateSum() {
		Optional<Path> rp = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		if (rp.isPresent()) {
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(rp.get())
			List<Document> docs = TestSuiteCollectionReportsUtil.loadXmlDocuments(xmlReports)
			List<TestSuiteStat> stats = TestSuiteCollectionReportsCollector.getStats(docs)
			TestSuiteStat sum = TestSuiteCollectionReportsCollector.calculateSum(stats)
			assert sum != null
			println sum.toString()
		} else {
			fail("rp was empty")
		}
	}


	// execute() calls WebUI.comment() keyword, it will fail when the junit was called in the commandline,
	// so will skip this.
	@Ignore
	@Test
	void test_execute() {
		TestSuiteCollectionReportsCollector collector = new TestSuiteCollectionReportsCollector()
		collector.execute(reportsDir)
	}
	
	@Test
	void test_write() {
		Path outFile = outputDir.resolve("stats.json")
		TestSuiteCollectionReportsCollector collector = new TestSuiteCollectionReportsCollector()
		collector.write(outFile, reportsDir)
	}
}
