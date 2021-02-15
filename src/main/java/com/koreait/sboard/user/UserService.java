package com.koreait.sboard.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.koreait.sboard.common.Const;
import com.koreait.sboard.common.FileUtils;
import com.koreait.sboard.common.MailUtils;
import com.koreait.sboard.common.SecurityUtils;
import com.koreait.sboard.model.AuthDto;
import com.koreait.sboard.model.AuthEntity;
import com.koreait.sboard.model.UserEntity;
import com.koreait.sboard.model.UserImgEntity;

// logic 담당
@Service
public class UserService {

	@Autowired // bean 등록된 것 중에 자동으로 등록 (Unique)
	private UserMapper mapper;

	@Autowired
	private MailUtils mailUtils;

	@Autowired
	private FileUtils fUtils;

	@Autowired
	private HttpSession hs;
	
	public UserEntity selUser(UserEntity p) {
		return mapper.selUser(p);
	}

	// 1: 로그인 성공, 2: 아이디 없음, 3: 비밀번호 틀림
	public int login(UserEntity param, HttpSession hs) {
		UserEntity dbData = mapper.selUser(param);

		if (dbData == null) { // 아이디 없음
			return 2;
		}
		String cryptLoginPw = SecurityUtils.hashPassword(param.getUser_pw(), dbData.getSalt());

		if (!cryptLoginPw.equals(dbData.getUser_pw())) { // 비밀번호 틀림
			return 3;
		}
		dbData.setSalt(null);
		dbData.setUser_pw(null);
		hs.setAttribute(Const.KEY_LOGINUSER, dbData);
		return 1;
	}

	public int insUser(UserEntity param) {
		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.hashPassword(param.getUser_pw(), salt);

		param.setSalt(salt);
		param.setUser_pw(encryptPw);

		return mapper.insUser(param);
	}
	
	public List<UserImgEntity> selUserImgList(UserEntity p) {
		int i_user = SecurityUtils.getLoingUserPk(hs);
		p.setI_user(i_user);
		return mapper.selUserImgList(p);
	}
	// 0: 메일전송 실패, 1: 성공 2: 아이디 확인
	public int findPwProc(AuthEntity p) {
		// 이메일 주소 얻어오기
		UserEntity p2 = new UserEntity();
		p2.setUser_id(p.getUser_id());
		UserEntity vo = mapper.selUser(p2);
		if (vo == null) {
			return 2;
		}
		String email = vo.getEmail();

		String code = SecurityUtils.getPrivateCode(10);
		System.out.println("code: " + code);

		mapper.delAuth(p); // 일단 삭제

		p.setCd(code);
		mapper.insAuth(p);

		System.out.println("email : " + email);
		return mailUtils.sendFindPwEmail(email, p.getUser_id(), code);
	}

	// 비밀번호 변경
	public int findPwAuthProc(AuthDto p) {
		// cd, user_id 확인 작업
		AuthEntity ae = mapper.selAuth(p);
		if (ae == null) {
			return 0; // id가 없었음
		} else if (ae.getRest_sec() > Const.AUTH_REST_SEC) {
			return 2; // 인증시간 초과
		}

		// 비밀번호 암호화
		String salt = SecurityUtils.getSalt();
		String encryptPw = SecurityUtils.hashPassword(p.getUser_pw(), salt);

		UserEntity p2 = new UserEntity();
		p2.setUser_id(p.getUser_id());
		p2.setUser_pw(encryptPw);
		p2.setSalt(salt);

		return mapper.updUser(p2);
	}

	public int profileUpload(MultipartFile[] imgs, HttpSession hs) {
		int i_user = SecurityUtils.getLoingUserPk(hs);
		if (i_user < 1 || imgs.length == 0) {
			return 0;
		}

		String folder = "/resources/img/user/" + i_user;

		try {
			for (int i = 0; i < imgs.length; i++) {
				MultipartFile file = imgs[i];
				String fileNm = fUtils.saveFile(file, folder);

				if (fileNm == null) {
					return 0;
				}
				
				if (i == 0) {// 메인 이미지 업데이트
					UserEntity p = new UserEntity();
					p.setI_user(i_user);
					p.setProfile_img(fileNm);
					mapper.updUser(p);
				}
				UserImgEntity p2 = new UserImgEntity();
				p2.setI_user(i_user);
				p2.setImg(fileNm);
				mapper.insUserImg(p2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
