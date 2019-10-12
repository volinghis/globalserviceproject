package com.ehs.gsp.auth.login.bean;

import com.ehs.common.oper.bean.ResultBean;

public class LoginResultBean extends ResultBean {

	private String[] currentAuthority=new String[0];

	public String[] getCurrentAuthority() {
		return currentAuthority;
	}

	public void setCurrentAuthority(String[] currentAuthority) {
		this.currentAuthority = currentAuthority;
	}
	
	@Override
	public ResultBean ok(String message) {
		super.ok(message);
		return this;
	}
	
	
}
