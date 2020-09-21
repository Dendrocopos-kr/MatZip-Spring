package com.koreait.matzip.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestParam;

@Controller
@RequestMapping("/rest")
public class RestController {
	@Autowired
	private RestService service;

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map(Model model) {
		model.addAttribute(Const.TITLE, "지도");
		model.addAttribute("view", "restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute(Const.TITLE, "식당 등록");
		model.addAttribute("view", "restaurant/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestParam param, HttpServletRequest request) {
		param.setI_user(SecurityUtils.getLoginUser(request).getI_user());
		service.insRest(param);
		return "redirect:/rest/map";
	}

	@RequestMapping(value = "/ajaxGetList", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxGetList(RestParam param) {
		// System.out.println("southWest:"+param.getSw_lat()+":"+param.getSw_lng());
		// System.out.println("northEast:"+param.getNe_lat()+":"+param.getNe_lng());
		return String.valueOf(service.getList(param));
	}
}
