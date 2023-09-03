package com.wadhams.dedup.service

class ScriptingService {
	def buildScript(Map<BigInteger, List<String>> dupMap, String filenamePrefix) {
		File f = new File("out/${filenamePrefix}-removeDuplicateFiles.txt")
		
		f.withPrintWriter {pw ->
			pw.println '@echo off'
			pw.println ''
			
			dupMap.each {k,v ->
				v.each {fn ->
					pw.println ":: del \"$fn\""
				}
				pw.println ''
			}
			
			pw.println ''
			pw.println 'pause'
		}
	}
}
