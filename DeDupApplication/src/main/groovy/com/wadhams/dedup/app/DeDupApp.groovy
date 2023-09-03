package com.wadhams.dedup.app

import com.wadhams.dedup.controller.DeDupController
import com.wadhams.dedup.type.Extension
import com.wadhams.dedup.type.Mode

class DeDupApp {
	static main(args) {
		println 'DeDupApp started...'
		println ''
		println 'Usage: DeDupApp <extension> <mode> <folderPath1> <folderPath2>'
		println '<extension> = mp3 | wma | txt | docx'
		println '<mode> = SINGLE | single | DOUBLE | double'
		println '<folderPath1> = Required for both SINGLE and DOUBLE mode.'
		println '<folderPath2> = Required for DOUBLE mode.'
		println ''
		
		if (args.size() == 3 || args.size() == 4) {
			Extension extension = Extension.findByName(args[0])
			Mode mode = Mode.findByName(args[1])
			DeDupController controller = new DeDupController()
			
			if (extension == Extension.Unknown) {
				println "Unknown \'extension\' parameter: ${args[0]}"
				println ''
				println 'See \'Usage\' above. Application did not run.'
				println ''
			}
			else if (mode == Extension.Unknown) {
				println "Unknown \'mode\' parameter: ${args[1]}"
				println ''
				println 'See \'Usage\' above. Application did not run.'
				println ''
			}
			else if (mode == Mode.SingleFolderMode) {
				controller.execute(mode, extension, args[2], null)
			}
			else if (mode == Mode.DoubleFolderMode) {
				controller.execute(mode, extension, args[2], args[3])
			}
			else {
				println "ERROR: Should never happen."
				println ''
				println 'See \'Usage\' above. Application did not run.'
				println ''
				throw new RuntimeException()
			}
		}
		else {
			println "Invalid number of arguments. args.size(): ${args.size()}"
			println 'See \'Usage\' above. Application did not run.'
			println ''
		}
		
		println 'DeDupApp ended.'
	}
}
