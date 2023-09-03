package com.wadhams.dedup.type

enum Mode {
	SingleFolderMode('SINGLE'),
	DoubleFolderMode('DOUBLE'),
	Unknown('Unknown');
	
	private static EnumSet<Mode> allEnums = EnumSet.allOf(Mode.class)
	
	private final String name

	Mode(String name) {
		this.name = name
	}
	
	public static Mode findByName(String text) {
		if (text) {
			text = text.toUpperCase()
			for (Mode e : allEnums) {
				if (e.name.equals(text)) {
					return e
				}
			}
		}
		else {
			return Mode.Unknown
		}
		return Mode.Unknown
	}

}
