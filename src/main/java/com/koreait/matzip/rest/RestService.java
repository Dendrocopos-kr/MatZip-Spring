package com.koreait.matzip.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.koreait.matzip.model.CodeVO;
import com.koreait.matzip.model.CommonMapper;
import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestParam;

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
}
