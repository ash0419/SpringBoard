package com.koreait.sboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.sboard.model.UserEntity;

public class SecurityUtils {
	public static String getSalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
	
	// true: 로그인, false: 로그아웃 상태
	public static boolean isLogin(HttpSession hs) {
		return getLoginUser(hs) != null;
	}

	public static UserEntity getLoginUser(HttpSession hs) {
		return (UserEntity) hs.getAttribute(Const.KEY_LOGINUSER);
	}

	public static int getLoingUserPk(HttpSession hs) {
		UserEntity loginUser = getLoginUser(hs);
		return loginUser == null ? -1 : loginUser.getI_user();
	}
	
	public static String getPrivateCode(int len) {
		String str = "";
		for(int i=0; i<len; i++) {
			str += (int)(Math.random() * 10);
		}
		
		return str;
	}
}
