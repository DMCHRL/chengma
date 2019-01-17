package com.chengma.devplatform.service.dto;


import com.chengma.devplatform.common.util.TreeNode;

public class MenuTreeNode extends TreeNode {

	private String url;

	private String menuNo;

	private String menuName;

	private String icon;

	private String parentName;

	private Integer sort;

	private String englishName;

	private String visible;

	private String langSwitch;

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getLangSwitch() {
		return langSwitch;
	}

	public void setLangSwitch(String langSwitch) {
		this.langSwitch = langSwitch;
	}
}
