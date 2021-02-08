package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardDto;
import com.koreait.sboard.model.BoardEntity;
import com.koreait.sboard.model.BoardParentDomain;

@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;

	public int insBoard(BoardEntity p) {
		return mapper.insBoard(p);
	}

	public BoardDomain selBoard(BoardDto p) {
		// 조회수
		p.setHits(1);
		mapper.updBoardHits(p);
		return mapper.selBoard(p);
	}

	public BoardParentDomain selBoardList(BoardDto p) {
		if (p.getTyp() == 0) {
			p.setTyp(1);
		}
		if (p.getRecordCntPerPage() == 0) {
			p.setRecordCntPerPage(5);
		}
		if (p.getPage() == 0) {
			p.setPage(1);
		}

		int sIdx = (p.getPage() - 1) * p.getRecordCntPerPage();
		p.setsIdx(sIdx);
		
		BoardParentDomain bpd = new BoardParentDomain();
		bpd.setMaxPageNum(mapper.selMaxPageNum(p));
		bpd.setList(mapper.selBoardList(p));
		bpd.setPage(p.getPage());
		bpd.setRecordCntPerPage(p.getRecordCntPerPage());
		return bpd;
	}

	public int delBoard(BoardDto p) {
		return mapper.delBoard(p);
	}

	public int updBoard(BoardEntity p) {
		return mapper.updBoard(p);
	}

	// -------------------------------CMT------------------------------------
	public int insCmt(BoardCmtEntity p) {
		return mapper.insCmt(p);
	}

	public List<BoardCmtDomain> selCmtList(BoardCmtEntity p) {
		return mapper.selCmtList(p);
	}

	public int delCmt(BoardCmtEntity p) {
		return mapper.delCmt(p);
	}

	public int updCmt(BoardCmtEntity p) {
		return mapper.updCmt(p);
	}
}
