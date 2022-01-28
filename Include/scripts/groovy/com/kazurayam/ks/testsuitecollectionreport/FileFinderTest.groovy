package com.kazurayam.ks.testsuitecollectionreport

import static org.junit.Assert.*

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import org.junit.Before
import org.junit.Test
import java.util.regex.Pattern
import com.kazurayam.ks.testsuitecollectionreport.ReportsMerger.FileFinder
import com.kms.katalon.core.configuration.RunConfiguration

public class FileFinderTest {

	static final Path projectDir = Paths.get(RunConfiguration.getProjectDir())
	private Path reportsDir
	
	@Before
	void setup() {
		reportsDir = projectDir.resolve("Include/fixtures/Reports")
	}
	
	@Test
	void test_find_rp() {
		Pattern pattern = Pattern.compile(".+\\.rp\$")
		FileFinder finder = new FileFinder(pattern)
		Files.walkFileTree(reportsDir, finder)
		List<Path> result = finder.getResult()
		assertTrue(result.size() > 0)
		//println result.get(0).toString()
	}
}
