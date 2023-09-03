package com.wadhams.dedup.controller

import com.wadhams.dedup.biz.DupChecker
import com.wadhams.dedup.service.DupCheckerService
import com.wadhams.dedup.service.ReportingService
import com.wadhams.dedup.service.ScriptingService
import com.wadhams.dedup.type.Extension
import com.wadhams.dedup.type.Mode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DeDupController {
	ReportingService reportingService = new ReportingService()
	ScriptingService scriptingService = new ScriptingService()
	
	def execute(Mode mode, Extension extension, String folderPath1, String folderPath2) {
		if (mode == Mode.SingleFolderMode) {
			println 'Running in Single Folder Mode'
			println "extension....: $extension"
			println "folderPath...: $folderPath1"
			println ''
		}
		else if (mode == Mode.DoubleFolderMode) {
			println 'Running in Double Folder Mode'
			println "extension.....: $extension"
			println "folderPath1...: $folderPath1"
			println "folderPath2...: $folderPath2"
			println ''
		}
		else {
			println 'ERROR. Should never happen.'
			println ''
			throw new RuntimeException()
		}
		
		DupChecker dupChecker = new DupChecker()
		Map<BigInteger, List<String>> dupMap
		if (mode == Mode.SingleFolderMode) {
			dupMap = dupChecker.findDups(extension, folderPath1)
		}
		else {
			dupMap = dupChecker.findDups(extension, folderPath1, folderPath2)
		}
//		DupCheckerService dupCheckerService = new DupCheckerService()
//		Map<BigInteger, List<String>> dupMap
//		if (mode == Mode.SingleFolderMode) {
//			dupMap = dupCheckerService.findDups(extension, folderPath1)
//		}
//		else {
//			dupMap = dupCheckerService.findDups(extension, folderPath1, folderPath2)
//		}
//		dupMap.each {k,v ->
//			println k
//			v.each {fn ->
//				println "\t$fn"
//			}
//		}
		
		if (dupMap.size() == 0) {
			println 'No duplicate files found. Yeah!'
			println ''
		}
		else {
			//filenamePrefix = datetimestamp + file extension
			DateTimeFormatter prefixDTF = DateTimeFormatter.ofPattern('yyyy-MM-dd-HH-mm-ss')
			LocalDateTime ldt = LocalDateTime.now()
			String filenamePrefix = "${ldt.format(prefixDTF)}-${extension.name}"
					
			reportingService.buildReport(dupMap, filenamePrefix)
			scriptingService.buildScript(dupMap, filenamePrefix)
		}
	}
}
