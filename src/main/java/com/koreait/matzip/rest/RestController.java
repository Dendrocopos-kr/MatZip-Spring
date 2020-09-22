package com.koreait.matzip.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestParam;
import com.sun.glass.ui.View;

@Controller
@RequestMapping("/rest")
public class RestController {
	@Autowired
	private RestService service;

	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public String map(Model model) {
		model.addAttribute(Const.TITLE, "지도");
		model.addAttribute("view", "rest/map");
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String restReg(Model model) {
		model.addAttribute(Const.TITLE, "식당 등록");
		model.addAttribute("view", "rest/reg");
		model.addAttribute("categoryList",service.selCategoryList());
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String restReg(RestParam param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		service.insRest(param);
		return "redirect:/rest/map";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model,RestParam param) {
		RestDMI data = service.selRest(param);
		model.addAttribute("data", data);
		model.addAttribute(Const.TITLE, data.getNm());
		model.addAttribute(Const.VIEW,"rest/detail");
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/ajaxGetList", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public List<RestDMI> ajaxGetList(RestParam param) {
		// System.out.println("southWest:"+param.getSw_lat()+":"+param.getSw_lng());
		// System.out.println("northEast:"+param.getNe_lat()+":"+param.getNe_lng());
		return service.getList(param);
	}
}
