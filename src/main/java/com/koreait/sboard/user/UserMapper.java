package com.koreait.sboard.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.AuthEntity;
import com.koreait.sboard.model.UserEntity;
import com.koreait.sboard.model.UserImgEntity;

// mybatis에서 제공해주는 annotation
@Mapper
public interface UserMapper {
	// xml에 id값이랑 변수명이 동일해야 매핑이 가능하다.
	int insUser(UserEntity p);
	UserEntity selUser(UserEntity p);
	List<UserImgEntity> selUserImgList(UserEntity p);
	int updUser(UserEntity p);
	int insUserImg(UserImgEntity p);
	/*-------------------------------비밀번호 찾기--------------------*/
	int insAuth(AuthEntity p);
	AuthEntity selAuth(AuthEntity p);
	int delAuth(AuthEntity p);
}
