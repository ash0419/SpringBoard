package com.koreait.sboard.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.common.Utils;
import com.koreait.sboard.model.UserEntity;

// logic 담당
@Service
public class UserService {

	@Autowired // bean 등록된 것 중에 자동으로 등록 (Unique)
	private UserMapper mapper;
	
	// 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public int login(UserEntity param, HttpSession hs) {
		UserEntity dbData = mapper.selUser(param);
		
		if(dbData == null) { // 아이디 없음
			return 2;
		}
		String cryptLoginPw = SecurityUtils.hashPassword(param.getUser_pw(), dbData.getSalt());
		
		if(!cryptLoginPw.equals(dbData.getUser_pw())) { // 비밀번호 틀림
			return 3;
		}
		dbData.setSalt(null);
		dbData.setUser_pw(null);
		hs.setAttribute(Const.KEY_LOGINUSER, dbData);
		return 1;
	}
	
	public int insUser(UserEntity param) {
		String salt = SecurityUtils.genSalt();
		String encryptPw = SecurityUtils.hashPassword(param.getUser_pw(), salt);
		
		param.setSalt(salt);
		param.setUser_pw(encryptPw);
		
		return mapper.insUser(param);
	}
	
	public int findPwProc(String user_id) {
		String code = SecurityUtils.getPrivateCode(5);
		System.out.println("code: " +code);
		return 0;
	}
}
