package com.koreait.matzip.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.user.model.UserParam;
import com.koreait.matzip.user.model.UserVO;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute("title", "로그인");
		model.addAttribute("view", "/user/login");
		return ViewRef.TEMP_DEFAULT;
	}

	/*
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		model.addObject("title", "로그인");
		model.addObject("view", "/user/login");
		model.setViewName(ViewRef.TEMP_DEFAULT);
		return model;
	}
	*/

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(UserParam param,HttpSession hs, RedirectAttributes ra) {
		int result = service.login(param);
		if (result == Const.SUCCESS) {
			hs.setAttribute(Const.LOGIN_USER, param);
			return "redirect:/rest/map";
		}
		String msg = null;
		if(result == Const.NO_ID) {
			msg = "아이디를 확인해주세요.";
		}else if(result == Const.NG_PW) {
			msg = "비밀번호를 확인해주세요";
		}
		
		param.setMsg(msg);
		ra.addFlashAttribute("data",param);
		
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpSession hs) {
		hs.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(Model model, @RequestParam(required = false) Integer err) {
		//@RequestParam
		//required = 필수냐?
		//value="err" 받아올 쿼리 스트링명 변수명과 같으면 생략가능
		model.addAttribute("title", "회원가입");
		model.addAttribute("view", "/user/join");

		return ViewRef.TEMP_DEFAULT;
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(UserParam param, RedirectAttributes ra) {
		int result = service.join(param);
		if( result == Const.SUCCESS ) {
			return "redirect:/user/login";
		}
		ra.addFlashAttribute("err", result);
		return "redirect:/user/join";
	}
	
	@RequestMapping(value = "/ajaxIdChk", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxIdChk(@RequestBody UserParam param) {
		int result = service.login(param);
		return String.valueOf(result);
	}
}
