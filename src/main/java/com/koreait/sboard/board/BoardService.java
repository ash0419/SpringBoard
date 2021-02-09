package com.koreait.sboard.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.common.Const;
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
		
		final int SIDE_NUM = Const.PAGE_SIDE_NUM;
		int pageLen = SIDE_NUM * 2 + 1;
		int page = p.getPage();
		int maxPage = bpd.getMaxPageNum();
		int sPage = page- SIDE_NUM; // 나타내는 곳의 왼쪽, 시작
		int ePage = page + SIDE_NUM; //나타내는 곳의 오른쪽, 끝
		
		if(pageLen < maxPage) {	// 나타내는 양쪽의 길이가 총 길이보다 작을 때
			if(sPage < 1) { // 나타내는 한곳의 길이가 현재 페이지보다 작을 때
				sPage = 1; //나타내는 곳의 왼쪽에는  현재 페이지 - 나타내는 한곳의 길이를 넣어준다.
			} 
			if(sPage > maxPage - pageLen) { // 전체길이 - 나타내는 양쪽의 길이가 나타내는 페이지 왼쪽보다 클 때
				sPage = maxPage - pageLen + 1; // 나타내는 곳의 왼쪽에는 총 페이지 - 나타내는 양쪽 길이 + 1을 넣어준다.
			}
			
			if(ePage > maxPage) { // 나타내는 쪽의 오른쪽이 총 페이지보다 클 때
				ePage = maxPage; // 오른쪽에는 총 페이지의 값을 대입
			} else if(ePage < pageLen) { // 나타내는 오른쪽이 나타내는 페이지의 수보다 작을 때
				ePage = pageLen; // 나타내는 오른쪽에는 나타내는 양쪽의 페이지값을 넣어준다.
			}
		} else { // 나타내는 양쪽의 길이가 총 길이보다 클 때
			sPage = 1;
			ePage = maxPage; // 나타내는 오른쪽에는 총 페이지의 길이를 넣어준다.
		}
		
		bpd.setsPage(sPage);
		bpd.setePage(ePage);		
		
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
