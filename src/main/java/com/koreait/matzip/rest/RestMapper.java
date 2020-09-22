package com.koreait.matzip.rest;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.matzip.rest.model.RestDMI;
import com.koreait.matzip.rest.model.RestParam;


@Mapper
public interface RestMapper {
	public List<RestDMI> selRestList(RestParam param);
	public int insRest(RestParam param);
	public RestDMI selRest(RestParam param);
}
