package com.wadhams.dedup.app

import com.wadhams.dedup.controller.DeDupController
import com.wadhams.dedup.type.Extension
import com.wadhams.dedup.type.Mode

class DeDupTestSingleApp {
	static main(args) {
		println 'DeDupTestSingleApp started...'
		println ''

		DeDupTestSingleApp app = new DeDupTestSingleApp()
		app.execute()
		
		println 'DeDupTestSingleApp ended.'
	}
	
	def execute() {
		Extension extension = Extension.findByName('docx')
		Mode mode = Mode.findByName('single')
		
		String userDir = System.properties['user.dir']
		String folderPath1 = "${userDir}\\src\\test\\resources\\TestFolder1"
		
		DeDupController controller = new DeDupController()
		controller.execute(mode, extension, folderPath1, null)
	}
}
