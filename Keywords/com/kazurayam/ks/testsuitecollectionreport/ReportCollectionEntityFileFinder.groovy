package com.kazurayam.ks.testsuitecollectionreport

import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

public class ReportCollectionEntityFileFinder implements FileVisitor<Path> {
	
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
