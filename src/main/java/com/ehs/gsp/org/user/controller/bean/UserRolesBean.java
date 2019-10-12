package com.ehs.gsp.org.user.controller.bean;

import java.util.List;

import com.ehs.common.auth.entity.SysRole;

public class UserRolesBean {

	private String userKey;
	
	private List<SysRole> roleList;


	

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public List<SysRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}
	
	
	
}
