package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import com.koreait.matzip.user.model.UserVO;

public class SecurityUtils {
	
	public static int getLoginUserPK(HttpServletRequest request) {
		return getLoginUser(request).getI_user();
	}
	
	public static int getLoginUserPK(HttpSession hs) {
		return ((UserVO)hs.getAttribute(Const.LOGIN_USER)).getI_user();
	}

	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO)hs.getAttribute(Const.LOGIN_USER);
	}
	
	public static boolean isLogout(HttpServletRequest request) {				
		return getLoginUser(request) == null;
	}
	
	public static String generateSalt() {
		return BCrypt.gensalt();
	}

	public static String getEncrypt(String password, String salt) {

		return BCrypt.hashpw(password, salt);
	}

}
