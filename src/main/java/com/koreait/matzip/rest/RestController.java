package com.koreait.matzip.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.koreait.matzip.Const;
import com.koreait.matzip.ViewRef;

@Controller
@RequestMapping("/rest")
public class RestController {
	@RequestMapping("/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE,"지도");
		model.addAttribute("view","restaurant/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
}
