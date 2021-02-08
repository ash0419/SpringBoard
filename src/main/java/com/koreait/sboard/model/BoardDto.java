package com.koreait.sboard.model;

import org.apache.ibatis.type.Alias;

@Alias("BoardDto")
public class BoardDto extends BoardEntity {
	private int recordCntPerPage;
	private int sIdx;
	private int page;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRecordCntPerPage() {
		return recordCntPerPage;
	}

	public void setRecordCntPerPage(int recordCntPerPage) {
		this.recordCntPerPage = recordCntPerPage;
	}

	public int getsIdx() {
		return sIdx;
	}

	public void setsIdx(int sIdx) {
		this.sIdx = sIdx;
	}
}
