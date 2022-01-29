package com.kazurayam.ks.reports


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.stream.Collectors

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class TestSuiteCollectionReportsUtil {

	private static DocumentBuilder docBuilder

	static {
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance()
		docBuilder = dbfactory.newDocumentBuilder()
	}

	private TestSuiteCollectionReportsUtil() {}

	public static Optional<Path> findLatestReportCollectionEntity(Path reportsDir) {
		List<Path> dirs = findReportCollectionEntities(reportsDir)
		if (dirs.size() > 0) {
			Path p = dirs.stream()
					.sorted(Comparator.reverseOrder())
					.collect(Collectors.toList())
					.get(0)
			return Optional.of(p)
		} else {
			return Optional.empty()
		}
	}

	public static List<Path> findReportCollectionEntities(Path base) {
		Pattern pattern = Pattern.compile(".+\\.rp\$")
		FileFinder finder = new FileFinder(pattern)
		Files.walkFileTree(base, finder)
		return finder.getResult()
	}


	public static List<Path> findXmlReports(Path latestRCE) {
		Path tscDir = latestRCE.getParent().getParent().getParent()
		assert Files.exists(tscDir)
		Pattern pattern = Pattern.compile("JUnit_Report\\.xml")
		FileFinder finder = new FileFinder(pattern)
		Files.walkFileTree(tscDir, finder)
		return finder.getResult()
	}


	public static List<Document> loadXmlDocuments(List<Path> xmlFiles) {
		List<Document> docs = new ArrayList<>()
		xmlFiles.each { p ->
			FileInputStream fis = new FileInputStream(p.toFile())
			Document doc = docBuilder.parse(fis)
			doc.setDocumentURI(p.toFile().toURI().toURL().toExternalForm())
			docs.add(doc)
		}
		return docs
	}

	
}
