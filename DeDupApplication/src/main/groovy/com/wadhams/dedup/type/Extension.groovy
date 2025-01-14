package com.wadhams.dedup.type

import java.util.regex.Pattern

enum Extension {
	Docx('DOCX', ['DOCX']),
	EPub('EPUB', ['EPUB']),
	HEIC('HEIC', ['HEIC']),
	JPG('JPG', ['JPG','JPEG']),
	M4A('M4A', ['M4A']),
	MOV('MOV', ['MOV']),
	MP3('MP3', ['MP3']),
	MP4('MP4', ['MP4']),
	PDF('PDF', ['PDF']),
	Txt('TXT', ['TXT']),
	WMA('WMA', ['WMA']),
	Unknown('', ['Unknown']);
	
	private static Pattern extensionPattern = ~/.*\.(\w{3,4})$/
	
	private static EnumSet<Extension> allEnums = EnumSet.allOf(Extension.class)
	
	private final String name
	private final List<String> matchingExtensions
	
	Extension(String name, List<String> matchingExtensions) {
		this.name = name
		this.matchingExtensions = matchingExtensions
	}
	
	public static Extension findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (Extension e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return Extension.Unknown
		}
		return Extension.Unknown
	}

	public static Extension findByFileExtension(String filename) {
		String fileExtension = ''
		def m = filename =~ extensionPattern
		if (m) {
			//println m[0]
			//println m[0][1]
			fileExtension = m[0][1]
		}
		
		if (fileExtension) {
			fileExtension = fileExtension.toUpperCase()
			for (Extension e : allEnums) {
				if (e.matchingExtensions.contains(fileExtension)) {
					return e
				}
			}
		}
		else {
			println 'findByFileExtension() was passed a file without an extension'
			println ''
			return Extension.Unknown
		}
		
		//println "Unknown extension: $fileExtension"
		//println ''
		return Extension.Unknown
	}
}
