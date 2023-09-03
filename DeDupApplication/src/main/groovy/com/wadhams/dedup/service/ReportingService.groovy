package com.wadhams.dedup.service

class ReportingService {
	def buildReport(Map<BigInteger, List<String>> dupMap, String filenamePrefix) {
		File f = new File("out/${filenamePrefix}-duplicate-file-report.txt")
		
		f.withPrintWriter {pw ->
			pw.println 'DUPLICATE FILE REPORT'
			pw.println '---------------------'
			pw.println ''
			
			dupMap.each {k,v ->
				v.each {fn ->
					pw.println "$fn"
				}
				pw.println ''
			}
		}
	}
}
