package com.koreait.sboard.model;

import org.apache.ibatis.type.Alias;

@Alias("BoardCmtDomain")
public class BoardCmtDomain extends BoardCmtEntity {
	private String user_nm, user_img;
	private int is_mycmt;

	public int getIs_mycmt() {
		return is_mycmt;
	}

	public void setIs_mycmt(int is_mycmt) {
		this.is_mycmt = is_mycmt;
	}

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
