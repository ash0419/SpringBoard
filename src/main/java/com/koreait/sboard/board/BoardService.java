package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardDto;
import com.koreait.sboard.model.BoardEntity;

@Service
public class BoardService {
	@Autowired
	private BoardMapper mapper;

	public int insBoard(BoardEntity p) {
		return mapper.insBoard(p);
	}
	public List<BoardDomain> selBoardList(BoardDto p) {
		if (p.getTyp() == 0) {
			p.setTyp(1);
		}
		return mapper.selBoardList(p);
	}
}
