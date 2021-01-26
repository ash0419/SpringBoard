package com.koreait.sboard.common;

import org.mindrot.jbcrypt.BCrypt;

public class Utils {
	public static String myViewResolver(String fileNm) {
		return "/WEB-INF/views/" + fileNm + ".jsp";
	}
}
