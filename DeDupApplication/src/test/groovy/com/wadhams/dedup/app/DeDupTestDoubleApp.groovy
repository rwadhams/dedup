package com.wadhams.dedup.app

import com.wadhams.dedup.controller.DeDupController
import com.wadhams.dedup.type.Extension
import com.wadhams.dedup.type.Mode

class DeDupTestDoubleApp {
	static main(args) {
		println 'DeDupTestDoubleApp started...'
		println ''

		DeDupTestDoubleApp app = new DeDupTestDoubleApp()
		app.execute()
		
		println 'DeDupTestDoubleApp ended.'
	}
	
	def execute() {
		Extension extension = Extension.findByName('txt')
		Mode mode = Mode.findByName('double')
		
		String userDir = System.properties['user.dir']
		String folderPath1 = "${userDir}\\src\\test\\resources\\TestFolder1"
		String folderPath2 = "${userDir}\\src\\test\\resources\\TestFolder2"
		
		DeDupController controller = new DeDupController()
		controller.execute(mode, extension, folderPath1, folderPath2)
	}
}
