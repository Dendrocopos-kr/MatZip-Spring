package com.koreait.matzip.model;

public class RestaurantMenuVO {
	private int i_rest;
	private int seq;
	public int getI_rest() {
		return i_rest;
	}
	public void setI_rest(int i_rest) {
		this.i_rest = i_rest;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getMenu_pic() {
		return menu_pic;
	}
	public void setMenu_pic(String menu_pic) {
		this.menu_pic = menu_pic;
	}
	private String menu_pic;
}