package com.koreait.sboard.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.BoardCmtDomain;
import com.koreait.sboard.model.BoardCmtEntity;
import com.koreait.sboard.model.BoardDto;
import com.koreait.sboard.model.BoardEntity;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService service;

	@GetMapping("/home")
	public void home() {
	}

	@GetMapping("/list")
	// @RequestParam(required = false, defaultValue = "1") int typ
	public void list(Model model, BoardDto p) {
		model.addAttribute(Const.KEY_LIST, service.selBoardList(p));
	}

	@GetMapping("/reg")
	public String reg() {
		return "board/regmod";
	}

	@PostMapping("/reg")
	public String reg(BoardEntity p, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		service.insBoard(p);
		return "redirect:/board/detail?i_board=" + p.getI_board();
	}

	@GetMapping("/detail")
	public void detail(BoardDto p, Model model, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		model.addAttribute(Const.KEY_DATA, service.selBoard(p));
	}

//	@GetMapping("/del/{i_board}")
//	public @ResponseBody String del(@PathVariable int i_board) {
//		BoardDto p = new BoardDto();
//		p.setI_board(i_board);
//		System.out.println("i_board : " + p.getI_board());
//		return "1";
//	}

	@ResponseBody
	@DeleteMapping("/del/{i_board}")
	public Map<String, Object> del(BoardDto p, HttpSession hs) {
		System.out.println("i_board : " + p.getI_board());

		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", service.delBoard(p)); // result = 0 or 1
		return map;
	}

	@GetMapping("/mod")
	public String mod(Model model, BoardDto p, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		model.addAttribute(Const.KEY_DATA, service.selBoard(p));
		return "board/regmod";
	}

	@PostMapping("/mod")
	public String mod(BoardEntity p, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		service.updBoard(p);
		return "redirect:/board/detail?i_board=" + p.getI_board();
	}

	// -----------------------------CMT-----------------------------------------
	@ResponseBody
	@PostMapping("/insCmt")
	public Map<String, Object> insCmt(@RequestBody BoardCmtEntity p, HttpSession hs) { // @RequestBody : 받는 문자열이 JSON 형태라는거를 알려줘서 알아서 문자열을 해석함
		System.out.println("i_board : " +p.getI_board());
		System.out.println("ctnt : " +p.getCtnt());
		
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.insCmt(p));

		return returnValue;
	}
	
	@ResponseBody
	@GetMapping("/cmtList")
	public List<BoardCmtDomain> selCmtList(BoardCmtEntity p, HttpSession hs) {
		System.out.println(p.getI_board());
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		return service.selCmtList(p);
	}
	
	@ResponseBody
	@DeleteMapping("/delCmt")
	public Map<String, Object> delCmt(BoardCmtEntity p, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		Map<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put(Const.KEY_RESULT, service.delCmt(p));
		return returnValue;
	}
}
