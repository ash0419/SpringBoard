package com.koreait.sboard.model;

public class AuthDto extends AuthEntity {
	private String user_pw;

	public String getUser_pw() {
		return user_pw;
	}

	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
}
