package com.koreait.sboard.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.sboard.model.UserEntity;

public class SecurityUtils {
	public static String genSalt() {
		return BCrypt.gensalt();
	}
	
	public static String hashPassword(String pw, String salt) {
		return BCrypt.hashpw(pw, salt);
	}
	
	// true: 로그아웃 상태, false: 로그인 상태
	public static boolean isLogout(HttpServletRequest request) {
		return getLoginUser(request) == null;
	}

	public static UserEntity getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserEntity) hs.getAttribute(Const.LOGINUSER);
	}

	public static int getLoingUserPk(HttpServletRequest request) {
		UserEntity loginUser = getLoginUser(request);
		return loginUser == null ? 0 : loginUser.getI_user();
	}
}
