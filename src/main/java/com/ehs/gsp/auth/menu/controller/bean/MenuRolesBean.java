package com.ehs.gsp.auth.menu.controller.bean;

import java.util.List;

import com.ehs.common.auth.entity.SysRole;

public class MenuRolesBean {

	private String menuKey;
	
	private List<SysRole> roleList;

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	
	
	
}
