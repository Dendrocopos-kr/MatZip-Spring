package com.koreait.matzip.rest;

import java.io.File;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.eclipse.jdt.internal.compiler.ast.SuperReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.matzip.Const;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestMenuVO;
import com.koreait.matzip.rest.model.RestMenusImgFile;
import com.koreait.matzip.rest.model.RestParam;
import com.koreait.matzip.rest.model.RestRecMenuVO;

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
	public String reg(Model model) {
		model.addAttribute(Const.TITLE, "식당 등록");
		model.addAttribute("view", "rest/reg");
		model.addAttribute("categoryList", service.selCategoryList());
		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String reg(RestParam param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		service.insRest(param);
		return "redirect:/rest/map";
	}

	@RequestMapping(value = "/addRecMenus", method = RequestMethod.POST)
	public String addRecMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		int i_rest = service.insRecMenus(mReq);
		ra.addAttribute("i_rest", i_rest);
		return "redirect:/rest/detail";
	}

	@RequestMapping(value = "/addMenus", method = RequestMethod.POST)
	public String addMenus(@ModelAttribute RestMenusImgFile param, HttpSession hs) {
		int i_user = SecurityUtils.getLoginUserPK(hs);
		service.insRestMenu(param,i_user);
		return "redirect:/rest/detail?i_rest=" + param.getI_rest();
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(RestParam param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		try {
			service.delRestTrans(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, RestParam param) {
		RestDMI data = service.selRest(param);
		List<RestRecMenuVO> recMenuList = service.selRecMenus(param);
		List<RestMenuVO> menuList = service.selMenus(param);

		model.addAttribute("css", new String[] { "restDetail" ,"Swiper-bundle.min"});
		model.addAttribute("data", data);
		model.addAttribute("recMenuList", recMenuList);
		model.addAttribute("menuList", menuList);
		model.addAttribute(Const.TITLE, data.getNm());
		model.addAttribute(Const.VIEW, "rest/detail");

		return ViewRef.TEMP_MENU_TEMP;
	}

	@RequestMapping(value = "/ajaxGetList", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public List<RestDMI> ajaxGetList(RestParam param) {
		return service.getList(param);
	}

	@RequestMapping(value = "/ajaxDelRecMenu", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public int ajaxDelRecMenu(RestParam param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPK(hs));
		String path = "resources/img/rest/" + param.getI_rest() + "/rec_menu/";
		return service.delRecMenu(param, path);
	}
	
	@RequestMapping(value = "/ajaxDelMenu", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public int ajaxDelMenu(RestParam param) {
		return service.delRestMenu(param);
	}
	
	@RequestMapping(value = "/ajaxSelMenuList", method = RequestMethod.GET, produces = "application/json; charset=utf8")
	@ResponseBody
	public List<RestMenuVO> ajaxSelMenu(RestParam param) {
		return service.selMenus(param);
	}
	
	

}
