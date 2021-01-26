package com.koreait.sboard.board;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.sboard.common.Utils;
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
		model.addAttribute("list", service.selBoardList(p));
	}
	
	@GetMapping("/reg")
	public String reg() {
		return "board/regmod";
	}
	
	@PostMapping("/reg")
	public String reg(BoardEntity p, HttpSession hs) {

		service.insBoard(p);
		return "redirect:/board/detail?i_board=" +p.getI_board();
	}
}
