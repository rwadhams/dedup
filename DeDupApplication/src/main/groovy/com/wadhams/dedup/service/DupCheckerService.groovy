package com.wadhams.dedup.service

import java.security.MessageDigest

import com.wadhams.dedup.type.Extension

import static groovy.io.FileType.FILES

class DupCheckerService {
	Map<BigInteger, List<String>> findDups(Extension matchingExtension, String folderPath) {
		Map<BigInteger, List<String>> dupMap = [:]	//key=message digest value, value(s)=absolute path to each file
		
		MessageDigest md = MessageDigest.getInstance("MD5")
		int counter = 1
		int printProgress = 50	//used to calculate when to print a progress message
		
		def baseDir = new File(folderPath)
		baseDir.eachFileRecurse(FILES) {f ->
			String filename = f.name
			Extension fileExtension = Extension.findByFileExtension(filename)
			if (fileExtension != matchingExtension) {	//short-circuit
				return
			}
			
			String absolutePath = f.getAbsolutePath()
			byte[] messageDigest = md.digest(f.getBytes())
			BigInteger num = new BigInteger(1, messageDigest)
			List<String> filenameList = dupMap[num]
			if (filenameList == null) {
				filenameList = []
				dupMap[num] = filenameList
			}
			filenameList << absolutePath

			//progress indicator
			if (counter%printProgress == 0) {
				println "Progress: $counter"
				println ''
			}
			counter++
		}
		
		//remove non duplicates (i.e. Lists with size == 1)
		def iterator = dupMap.entrySet().iterator()
		while (iterator.hasNext()) {
			if (iterator.next().value.size() == 1) {
				iterator.remove()
			}
		}

		return dupMap
	}
}
