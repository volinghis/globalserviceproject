package com.ehs.gsp.base.controller.bean;

import com.ehs.common.auth.local.bean.LocalUser;

public class CurrentUserBean extends LocalUser{

	private String name;
	private String account;
	private String orgName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	

}
