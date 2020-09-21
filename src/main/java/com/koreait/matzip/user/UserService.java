package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserParam;

@Service
public class UserService {

	@Autowired
	private UserMapper mapper;

	public int login(UserParam param) {
		if(param.getUser_id().equals("")) { return Const.NO_ID; }
		UserDMI dbUser = mapper.selUser(param);
		if(dbUser == null) { return Const.NO_ID; }
		
		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if(!cryptPw.equals(dbUser.getUser_pw())) { return Const.NG_PW; }
		
		param.setI_user(dbUser.getI_user());
		param.setUser_pw(null);
		param.setNm(dbUser.getNm());
		param.setProfile_img(dbUser.getProfile_img());
		return Const.SUCCESS;		
	}

	public int join(UserParam param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String encryptPw = SecurityUtils.getEncrypt(pw, salt);

		param.setUser_pw(encryptPw);
		param.setSalt(salt);

		return mapper.insUser(param);
	}

}
