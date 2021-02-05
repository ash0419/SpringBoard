package com.koreait.sboard.model;

public class AuthEntity {
	private String user_id, cd;
	private int rest_sec;

	public int getRest_sec() {
		return rest_sec;
	}

	public void setRest_sec(int rest_sec) {
		this.rest_sec = rest_sec;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

}
