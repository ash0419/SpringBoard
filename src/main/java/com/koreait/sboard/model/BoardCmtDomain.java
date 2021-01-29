package com.koreait.sboard.model;

import org.apache.ibatis.type.Alias;

@Alias("BoardCmtDomain")
public class BoardCmtDomain extends BoardCmtEntity {
	private String user_nm, user_img;

	public String getUser_nm() {
		return user_nm;
	}

	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}

	public String getUser_img() {
		return user_img;
	}

	public void setUser_img(String user_img) {
		this.user_img = user_img;
	}
}
