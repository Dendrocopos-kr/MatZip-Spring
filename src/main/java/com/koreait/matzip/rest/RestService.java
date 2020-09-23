package com.koreait.matzip.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
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
	
	public List<CodeVO> selCategoryList(){
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
		String path = mReq.getServletContext().getRealPath("resources/img/rest/"+i_rest+"/rec_menu/");
		
		List<RestRecMenuVO> list = new ArrayList<RestRecMenuVO>();
		
		for(int i=0; i<menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);

			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);

			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) { continue; }
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String savaFileNm = UUID.randomUUID()+ext;
			
			try {
				mf.transferTo(new File(path + savaFileNm));
				vo.setMenu_pic(savaFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return mapper.insRecMenus();
	}
}
