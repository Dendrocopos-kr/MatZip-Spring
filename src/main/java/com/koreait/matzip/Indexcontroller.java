package com.koreait.matzip;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Indexcontroller {
	@RequestMapping(value = "/")
	public String index(HttpServletRequest req) {
		if(Const.realPath == null) {
			Const.realPath = req.getServletContext().getRealPath("");
		}
		return "redirect:/rest/map";
	}
}
