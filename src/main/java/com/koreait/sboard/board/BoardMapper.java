package com.koreait.sboard.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDomain;
import com.koreait.sboard.model.BoardDto;
import com.koreait.sboard.model.BoardEntity;

@Mapper
public interface BoardMapper {
	int insBoard(BoardEntity p);
	int selMaxPageNum(BoardDto p);
	List<BoardDomain> selBoardList(BoardDto p);
	BoardDomain selBoard(BoardDto p);
	int updBoardHits(BoardDto p);
	int updBoard(BoardEntity p);
	int delBoard(BoardDto p);
	
	//-------------------------------CMT------------------------------------
	int insCmt(BoardCmtEntity p);
	List<BoardCmtDomain> selCmtList(BoardCmtEntity p);
	int delCmt(BoardCmtEntity p);
	int updCmt(BoardCmtEntity p);
}
