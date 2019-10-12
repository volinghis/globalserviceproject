package com.ehs.gsp.auth.menu.utils;

import java.util.List;

public class MenuNode implements java.io.Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String[] authority;
	private List<MenuNode> children;// ?: MenuDataItem[];
	private String icon;//// ?: string;
	private String label;// ?: string;
	private String title;
	private String url;// : string;
	private String name;//
	private String parentName;
	
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String[] getAuthority() {
		return authority;
	}
	public void setAuthority(String[] authority) {
		this.authority = authority;
	}
	public List<MenuNode> getChildren() {
		return children;
	}
	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	


}
