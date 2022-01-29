import java.nio.file.Path
import java.nio.file.Paths

Double time = CustomKeywords."com.kazurayam.ks.reports.TestSuiteCollectionReportsCollector.execute"()
println time + " seconds"

Path outfile = Paths.get(".").resolve("stats.json")
CustomKeywords."com.kazurayam.ks.reports.TestSuiteCollectionReportsCollector.write"(outfile)
