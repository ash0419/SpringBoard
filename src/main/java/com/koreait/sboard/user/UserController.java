package com.koreait.sboard.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.AuthDto;
import com.koreait.sboard.model.AuthEntity;
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
		if (result == 1) {
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
	public String findPwProc(AuthEntity p) {
		System.out.println("user_id: " + p.getUser_id());
		service.findPwProc(p);

		return "user/findPw";
	}

	@GetMapping("/findPwAuth")
	public void findPwAuth() {

	}

	@ResponseBody
	@PostMapping("findPwAuth")
	public Map<String, Object> findPwAuth(@RequestBody AuthDto p) {
		System.out.println("cd : " + p.getCd());
		System.out.println("user_id : " + p.getUser_id());
		System.out.println("user_pw : " + p.getUser_pw());

		Map<String, Object> rVal = new HashMap<String, Object>();

		rVal.put(Const.KEY_RESULT, service.findPwAuthProc(p));

		return rVal;
	}
	
	@GetMapping("/profile")
	public void profile() {
		
	}
	
	@ResponseBody
	@GetMapping("/profileData")
	public UserEntity profileData(UserEntity p, HttpSession hs) {
		p.setI_user(SecurityUtils.getLoingUserPk(hs));
		return service.selUser(p);
	}
	
	@ResponseBody
	@PostMapping("/profileUpload")
	public int profileUpload(MultipartFile[] imgs, HttpSession hs) {
		System.out.println("imgs : " +imgs.length);
		return service.profileUpload(imgs, hs);
	}
}
