package com.koreait.matzip.user;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;


@Mapper
public interface UserMapper {
	public int insUser(UserParam p);
	public UserDMI selUser(UserParam param);
}
