package com.koreait.matzip.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserDTO;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String _login(Model model) {
		model.addAttribute("title", "로그인");
		model.addAttribute("view", "/user/login");
		return ViewRef.TEMP_DEFAULT;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String _login_Proc(UserDTO param) {
		System.out.println(param.getUser_id());
		int result = 0;//service.login(param);
		if (result == 1) { // 로그인 성공
			return "redirect:/restaurant/restMap";
		}
		return "redirect:/user/login";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String _join(Model model, @RequestParam(required = false) Integer err) {
		//@RequestParam
		//required = 필수냐?
		//value="err" 받아올 쿼리 스트링명 변수명과 같으면 생략가능
		model.addAttribute("title", "회원가입");
		model.addAttribute("view", "/user/join");

		return ViewRef.TEMP_DEFAULT;
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String _join_Proc(UserDTO param) {
		int result = service.join(param);
		if( result == 1 ) {
			return "redirect:/user/login";
		}
		return "redirect:/user/join?err="+result;
	}
}
