package com.wadhams.dedup.controller

class DoubleController {
	String folderPath1
	String folderPath2
	
	def DoubleController(String folderPath1, String folderPath2) {
		this.folderPath1 = folderPath1
		this.folderPath2 = folderPath2
	}
	
	def execute() {
		println folderPath1
		println folderPath2
		println ''
	}
}
