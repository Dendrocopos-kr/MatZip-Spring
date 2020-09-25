package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.SecurityUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestMenuVO;
import com.koreait.matzip.rest.model.RestMenusImgFile;
import com.koreait.matzip.rest.model.RestParam;
import com.koreait.matzip.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper;

	@Autowired
	private CommonMapper cMapper;

	public List<RestDMI> getList(RestParam param) {
		List<RestDMI> list = mapper.selRestList(param);
		return list;
	}

	public int insRest(RestParam param) {
		return mapper.insRest(param);
	}

	public List<CodeVO> selCategoryList() {
		CodeVO param = new CodeVO();
		param.setI_m(1);
		return cMapper.selCodeList(param);
	}

	public RestDMI selRest(RestParam param) {
		return mapper.selRest(param);
	}

	@Transactional
	public void delRestTrans(RestParam param) {
		delRestRecMenu(param);
		delRestMenu(param);
		delRest(param);
	}

	public int delRestRecMenu(RestParam param) {
		return mapper.delRestRecMenu(param);
	}

	public int delRestMenu(RestParam param) {
		System.out.println(param.getMenu_pic());
		if( param.getMenu_pic() != null && !param.getMenu_pic().equals("") ) {
			String path = Const.realPath + "/resources/img/rest/"+param.getI_rest()+"/menu/";
			System.out.println(path);
			if(FileUtils.delFile(path+param.getMenu_pic())) {
				return mapper.delRestMenu(param);
			}else {
				return Const.FAIL;
			}
		}
		return mapper.delRestMenu(param);
	}

	public int delRest(RestParam param) {
		return mapper.delRest(param);
	}

	public int insRecMenus(MultipartHttpServletRequest mReq) {
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		String path = Const.realPath + "resources/img/rest/" + i_rest + "/rec_menu/";

		List<RestRecMenuVO> list = new ArrayList<RestRecMenuVO>();

		for (int i = 0; i < menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			vo.setI_rest(i_rest);

			MultipartFile mf = fileList.get(i);
			String savaFileNm = FileUtils.saveFile(path, mf);
			vo.setMenu_pic(savaFileNm);
		}

		for (RestRecMenuVO vo : list) {
			mapper.insRecMenus(vo);
		}
		return i_rest;
	}

	public List<RestRecMenuVO> selRecMenus(RestParam param) {
		return mapper.selRecMenus(param);
	}

	public List<RestMenuVO> selMenus(RestParam param) {
		return mapper.selMenus(param);
	}
	

	public int delRecMenu(RestParam param, String path) {
		List<RestRecMenuVO> list = mapper.selRecMenus(param);

		if (list.size() != 1) {
			return 0;
		}

		RestRecMenuVO item = list.get(0);
		String item_nm = path + item.getMenu_pic();

		if (item.getMenu_pic() != null && !item.getMenu_pic().equals("")) {
			File file = new File(item_nm);
			if (file.exists()) {
				if (!file.delete()) {
					return 0;
				}
			}
		}

		return mapper.delRestRecMenu(param);
	}

	public int insRestMenu(RestMenusImgFile param, int i_user) {
		String path = Const.realPath + "resources/img/rest/" + param.getI_rest() + "/menu/";

		for (MultipartFile mf : param.getMenu_pic()) {
			if (mf.isEmpty()) {
				continue;
			}
			RestMenuVO vo = new RestMenuVO();
			vo.setI_rest(param.getI_rest());

			String savaFileNm = FileUtils.saveFile(path, mf);
			vo.setMenu_pic(savaFileNm);

			mapper.insRestMenu(vo);
		}

		return param.getI_rest();
	}

	public int delRestMenu(RestParam param, String path) {
		List<RestMenuVO> list = mapper.selMenus(param);

		if (list.size() != 1) {
			return 0;
		}

		RestMenuVO item = list.get(0);
		String item_nm = path + item.getMenu_pic();

		if (item.getMenu_pic() != null && !item.getMenu_pic().equals("")) {
			File file = new File(item_nm);
			if (file.exists()) {
				if (!file.delete()) {
					return 0;
				}
			}
		}

		return mapper.delRestMenu(param);
	}
}
