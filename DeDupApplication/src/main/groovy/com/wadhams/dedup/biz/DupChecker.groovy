package com.wadhams.dedup.biz

import java.security.DigestInputStream
import java.security.MessageDigest
import com.wadhams.dedup.type.Extension

import static groovy.io.FileType.FILES

class DupChecker {
	Map<BigInteger, List<String>> dupMap = [:]	//key=message digest value, value(s)=absolute path to each file
	
	int counter = 1
	int printProgress = 50	//used to calculate when to print a progress message
		
	Map<BigInteger, List<String>> findDups(Extension matchingExtension, String... folderPath) {
		folderPath.each {fp ->
			findDupsInternal(matchingExtension, fp)
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
	
	def findDupsInternal(Extension matchingExtension, String folderPath) {
		MessageDigest md = MessageDigest.getInstance("MD5")
		
		def baseDir = new File(folderPath)
		baseDir.eachFileRecurse(FILES) {f ->
			String filename = f.name
			Extension fileExtension = Extension.findByFileExtension(filename)
			if (fileExtension != matchingExtension) {	//short-circuit
				return
			}
			
			String absolutePath = f.getAbsolutePath()
			//println absolutePath
			
			BigInteger num = new BigInteger(1, digest(f, md))
			//println num
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
	}
	
	def byte[] digest(File f, MessageDigest md) throws IOException {
		md.reset()
		InputStream is = f.newInputStream()
		DigestInputStream dis = new DigestInputStream(is, md)
		while (dis.read() != -1) {
			//loop until all bytes read from stream
		}
		return md.digest()
	}
}
