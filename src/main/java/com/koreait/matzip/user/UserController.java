package com.koreait.matzip.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String _login(Model model) {
		model.addAttribute("title","로그인");
		model.addAttribute("view", "/user/login");
		return ViewRef.TEMP_DEFAULT;
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String _join(Model model) {
		model.addAttribute("title","회원가입");
		model.addAttribute("view", "/user/join");
		return ViewRef.TEMP_DEFAULT;
	}
}
