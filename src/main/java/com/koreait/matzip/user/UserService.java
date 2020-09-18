package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.user.model.UserDMI;
import com.koreait.matzip.user.model.UserDTO;

@Service
public class UserService {

	@Autowired
	private UserMapper mapper;

	public int login(UserDTO param) {
		UserDMI dbUser = mapper.selUser(param);
		if (dbUser.getI_user() == 0) { // 아이디 없음
			return Const.NO_ID;
		} else {
			String salt = dbUser.getSalt();
			String encryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), salt);

			if (encryptPw.equals(dbUser.getUser_pw())) { // 로그인 성공
				param.setUser_pw(null);
				param.setI_user(dbUser.getI_user());
				param.setNm(dbUser.getNm());
				param.setProfile_img(dbUser.getProfile_img());

				return Const.SUCCESS;
			} else {
				return Const.NG_PW;
			}
		}
	}

	public int join(UserDTO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String encryptPw = SecurityUtils.getEncrypt(pw, salt);

		param.setUser_pw(encryptPw);
		param.setSalt(salt);

		return mapper.insUser(param);
	}

}
