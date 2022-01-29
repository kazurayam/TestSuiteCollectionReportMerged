package com.kazurayam.ks.reports

import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author kazurayam
 */
public class FileFinder implements FileVisitor<Path> {

	private final Pattern pattern
	private final List<Path> found

	FileFinder(Pattern pattern) {
		this.pattern = pattern
		this.found = new ArrayList<>()
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
		Matcher m = pattern.matcher(file.getFileName().toString())
		if (m.matches()) {
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

