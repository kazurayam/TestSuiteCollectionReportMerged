package com.kazurayam.ks.reports

import java.nio.file.Path
import java.nio.file.Paths

import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * 
 * @author kazurayam
 */
public class TestSuiteCollectionReportsCollector {

	private static final Path projectDir = Paths.get(".")

	public TestSuiteCollectionReportsCollector() {}

	/**
	 * 1. will look into the projects `Reports` directory,
	 * 2. will identify the latest execution of a Test Suite Collection
	 * 3. will find the `JUnit_Report.xml` files generated by the execution of the Test Suite Collection
	 * 4. will find statistics out of `JUnit_Report.xml`: name, time, tests, failures, errors
	 * 5. will calculate the total time of execution of the Test Suite Collection
	 * 6. will output the total time into the console
	 * 
	 * @return total time of a Test Suite Collection execution, in seconds
	 */
	@Keyword
	public Double execute(Path reportsDir = projectDir.resolve("Reports").toAbsolutePath()) {
		Optional<Path> latestRCE = TestSuiteCollectionReportsUtil.findLatestReportCollectionEntity(reportsDir)
		Double time = 0.0
		latestRCE.ifPresent({ it ->
			List<Path> xmlReports = TestSuiteCollectionReportsUtil.findXmlReports(it)
			List<Document> documents = TestSuiteCollectionReportsUtil.loadXmlDocuments(xmlReports)
			List<TestSuiteStat> stats = getStats(documents)
			Collections.sort(stats)
			for (TestSuiteStat stat in stats) {
				WebUI.comment(stat.toString() + "; " + stat.getXmlReportPathRelativeTo(reportsDir))
			}
			TestSuiteStat sum = calculateSum(stats)
			WebUI.comment(sum.toString())
			time = sum.getTime()
		})
		return time
	}

	//-----------------------------------------------------------------





	/**
	 * returns a List of TestSuiteStat objects
	 * 
	 * e.g ["name": "TS1", "time": 3.717, "tests": 1, "failures": 0, "errors": 0]
	 */
	static List<TestSuiteStat> getStats(List<Document> docs) {
		XPath xpath =  XPathFactory.newInstance().newXPath()
		List<TestSuiteStat> stats = new ArrayList<>()
		for (Document doc in docs) {
			Node node =
					(Node)xpath
					.compile("/testsuites")
					.evaluate(doc, XPathConstants.NODE)
			Element testsuites = (Element)node
			TestSuiteStat stat = new TestSuiteStat(
					testsuites.getAttribute("name"),
					Double.parseDouble(testsuites.getAttribute("time")),
					Integer.parseInt(testsuites.getAttribute("tests")),
					Integer.parseInt(testsuites.getAttribute("failures")),
					Integer.parseInt(testsuites.getAttribute("errors")),
					doc.getDocumentURI()
					)
			stats.add(stat)
		}
		return stats
	}


	static TestSuiteStat calculateSum(List<TestSuiteStat> stats) {
		Double time = 0
		Integer tests = 0
		Integer failures = 0
		Integer errors = 0
		for (TestSuiteStat stat in stats) {
			time += stat.getTime()
			tests += stat.getTests()
			failures = stat.getFailures()
			errors = stat.getErrors()
		}
		TestSuiteStat sum = new TestSuiteStat("sum", time, tests, failures, errors)
		return sum
	}
}
