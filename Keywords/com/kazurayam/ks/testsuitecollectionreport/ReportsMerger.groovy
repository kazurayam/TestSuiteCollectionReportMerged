package com.kazurayam.ks.testsuitecollectionreport

import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.stream.Collectors

import com.kms.katalon.core.configuration.RunConfiguration

public class ReportsMerger {

	static final Path projectDir = Paths.get(RunConfiguration.getProjectDir())

	public ReportsMerger() {
		Path reportsDir = projectDir.resolve("Reports")
		Path latestBunchDir = findLatestBunchDir(reportsDir)
	}

	public static Optional<Path> findLatestBunchDir(Path reportsDir) {
		List<Path> dirs =
				Files.list(reportsDir)
				.filter({ p ->
					Files.isDirectory(p)
				})
				.filter({ p ->
					p.getFileName().toString().matches("\\d[6]_\\d[6]")
				})
				.filter({ p ->
					findReportCollectionEntity(p).size() > 0
				})
				//.sorted(Comparator.comparing({ p1, p2 -> Path::getFileName, Comparator.reverseOrder()))
				.collect(Collectors.toList())
		if (dirs.size() > 0) {
			return Optional.of(dirs.get(0))
		} else {
			return Optional.empty()
		}
	}

	public static List<Path> findReportCollectionEntity(Path base) {
		ReportCollectionEntityFileFinder finder = new ReportCollectionEntityFileFinder()
		Files.walkFileTree(base, finder)
		return finder.getFound()
	}

	public void compile(String path) {
		Path outfile = resolveOutfile(path)
	}

	private Path resolveOutfile(String path) {
		Path p = Paths.get(path)
		if (! p.isAbsolute()) {
			p = projectDir.resolve(p)
		}
		Files.createDirectories(p.getParent())
		return p
	}


	/**
	 * 
	 * @author kazuakiurayama
	 *
	 */
	public static class ReportCollectionEntityFileFinder implements FileVisitor<Path> {

		List<Path> found

		ReportCollectionEntityFileFinder() {
			found = new ArrayList<>()
		}

		List<Path> getResult() {
			return found
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
			return FileVisitResult.CONTINUE ;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			if (file.getFileName().toString().endsWith(".rp")) {
				found.add(file)
			}
			return FileVisitResult.CONTINUE ;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			return FileVisitResult.CONTINUE ;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
			return FileVisitResult.CONTINUE ;
		}
	}
}
