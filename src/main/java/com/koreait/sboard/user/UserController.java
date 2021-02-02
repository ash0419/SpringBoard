package com.koreait.sboard.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.sboard.model.UserEntity;

@Controller
@RequestMapping("/user") // 1차 주소값
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("/login") // 2차 주소값
	public void login() {
//		model.addAttribute("page", Utils.myViewResolver("user/login"));
//		return "user/login";
		
	}
	
	@GetMapping("/logout") // 2차 주소값
	public String logout(HttpSession hs) {
		hs.invalidate();
		return "redirect: /user/login";
	}

	@RequestMapping("/join") // 2차 주소값, method=RequestMethod.POST 생략하면 get방식
	public String join() {
//		model.addAttribute("page", Utils.myViewResolver("user/join"));
		return "user/join";
	}

	@PostMapping("/login")
	public String loginProc(UserEntity param, HttpSession hs) {
		int result = service.login(param, hs);
		if(result == 1 ) {
			return "redirect:/board/home";
		}
		return null;
	}

	@PostMapping("/join")
	public String join(UserEntity param) {
		System.out.println("user_id : " + param.getUser_id());
		System.out.println("user_pw : " + param.getUser_pw());

		service.insUser(param);
		return "redirect:/user/login"; // sendRedirect
	}

	@GetMapping("/findPw") // 2차 주소값
	public void findPw() {
		
	}
	
	@GetMapping("/findPwProc") // 2차 주소값
	public String findPwProc(String user_id) {
		System.out.println("user_id: " +user_id);
		service.findPwProc(user_id);
		return "user/findPw";
	}
}
