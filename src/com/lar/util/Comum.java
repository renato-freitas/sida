package com.lar.util;

public class Comum {

	/**Return any file name without extension*/
	public static String cut_extension(String file) {
		int i, l = file.length();
		for(i = 0; i < l; i++) { 
			if(file.charAt(i) == '.')
				break;
		}
		return file.substring(0, i);
	}
}