package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestParam;
import com.koreait.matzip.rest.model.RestRecMenuVO;


@Mapper
public interface RestMapper {
	int insRest(RestParam param);
	List<RestDMI> selRestList(RestParam param);
	RestDMI selRest(RestParam param);
	int delRest(RestParam param);
	int delRestMenu(RestParam param);
	int delRestRecMenu(RestParam param);
	int insRecMenus(RestRecMenuVO param);
	List<RestRecMenuVO> selRecMenuList(RestRecMenuVO param);
}
