package com.koreait.sboard.model;

import org.apache.ibatis.type.Alias;

@Alias("BoardCmtEntity")
public class BoardCmtEntity {
	private int i_cmt, i_board, i_user;
	private String ctnt, r_dt;

	public int getI_cmt() {
		return i_cmt;
	}

	public void setI_cmt(int i_cmt) {
		this.i_cmt = i_cmt;
	}

	public int getI_board() {
		return i_board;
	}

	public void setI_board(int i_board) {
		this.i_board = i_board;
	}

	public int getI_user() {
		return i_user;
	}

	public void setI_user(int i_user) {
		this.i_user = i_user;
	}

	public String getCtnt() {
		return ctnt;
	}

	public void setCtnt(String ctnt) {
		this.ctnt = ctnt;
	}

	public String getR_dt() {
		return r_dt;
	}

	public void setR_dt(String r_dt) {
		this.r_dt = r_dt;
	}

}
