package com.wadhams.dedup.controller

import com.wadhams.dedup.service.DupCheckerService
import com.wadhams.dedup.service.ReportingService
import com.wadhams.dedup.service.ScriptingService
import com.wadhams.dedup.type.Extension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SingleController {
	ReportingService reportingService = new ReportingService()
	ScriptingService scriptingService = new ScriptingService()
	
	Extension extension
	String folderPath
	
	def SingleController(Extension extension, String folderPath) {
		this.extension = extension
		this.folderPath = folderPath
	}
	
	def execute() {
		println 'Running in Single Folder Mode'
		println "extension....: $extension"
		println "folderPath...: $folderPath"
		println ''
		
		DupCheckerService dupCheckerService = new DupCheckerService()
		Map<BigInteger, List<String>> dupMap = dupCheckerService.findDups(extension, folderPath)
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
