package com.koreait.sboard.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.sboard.model.UserEntity;

// mybatis에서 제공해주는 annotation
@Mapper
public interface UserMapper {
	// xml에 id값이랑 변수명이 동일해야 매핑이 가능하다.
	UserEntity selUser(UserEntity param);
	
	int insUser(UserEntity param);
}
