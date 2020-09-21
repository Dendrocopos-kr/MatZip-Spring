package com.koreait.matzip.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest")
public class RestController {
	@RequestMapping("/map")
	public String restMap() {
		return "";
	}
}
