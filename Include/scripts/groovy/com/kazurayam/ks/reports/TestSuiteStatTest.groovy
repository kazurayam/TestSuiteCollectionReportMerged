package com.kazurayam.ks.reports

import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Test
import org.w3c.dom.Document

public class TestSuiteStatTest {

	Path reportsDir = Paths.get(".").resolve("Include/fixtures/Reports").toAbsolutePath()

	@Test
	void test_getXmlReportPathRelativeTo() {
		Optional<Path> rp = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		if (rp.isPresent()) {
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(rp.get())
			List<Document> docs = TestSuiteCollectionReportsUtil.loadXmlDocuments(xmlReports)
			List<TestSuiteStat> stats = TestSuiteCollectionReportsCollector.getStats(docs)
			Collections.sort(stats)
			for (TestSuiteStat stat in stats) {
				Path p = stat.getXmlReportPathRelativeTo(reportsDir).normalize()
				println p.toString()
				assert ! p.toString().contains("file:")
			}
		}
	}
}
