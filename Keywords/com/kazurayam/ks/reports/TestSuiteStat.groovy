package com.kazurayam.ks.reports

import java.nio.file.Path

import org.apache.commons.io.FileUtils

/**
 * @author kazurayam
 */
public class TestSuiteStat implements Comparable {

	private final Map stat

	TestSuiteStat(String name, Double time, Integer tests, Integer failures, Integer errors) {
		this(name, time, tests, failures, errors, null)
	}

	TestSuiteStat(String name, Double time, Integer tests, Integer failures, Integer errors, String xmlReportURI) {
		Objects.requireNonNull(name)
		Objects.requireNonNull(time)
		Objects.requireNonNull(tests)
		Objects.requireNonNull(failures)
		Objects.requireNonNull(errors)
		// xmlReport may be null
		this.stat = ["name": name, "time": time, "tests": tests, "failures": failures, "errors": errors, "xmlReportURI": xmlReportURI]
	}

	String getName() {
		return stat.get("name")
	}

	Double getTime() {
		return stat.get("time")
	}

	Integer getTests() {
		return stat.get("tests")
	}

	Integer getFailures() {
		return stat.get("failures")
	}

	Integer getErrors() {
		return stat.get("errors")
	}

	String getXmlReportURI() {
		return stat.get("xmlReportURI")
	}

	Path getXmlReportPathRelativeTo(Path reportsDir) {
		File file = FileUtils.toFile(new URL(this.getXmlReportURI()));
		Path path = file.toPath()
		println "reportsDir: " + reportsDir
		println "path      : " + path
		return reportsDir.getParent().relativize(path)
	}

	@Override
	String toString() {
		StringBuilder sb = new StringBuilder()
		sb.append("name: ")
		sb.append(this.getName())
		sb.append(", time: ")
		sb.append(String.format("%.3f", this.getTime()))
		sb.append(", tests: ")
		sb.append(this.getTests())
		sb.append(", failures: ")
		sb.append(this.getFailures())
		sb.append(", errors: ")
		sb.append(this.getErrors())
		return sb.toString()
	}

	@Override
	int compareTo(Object obj) {
		TestSuiteStat other = (TestSuiteStat)obj
		return this.getName().compareTo(other.getName())
	}
}
