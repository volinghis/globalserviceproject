package com.ehs.gsp.auth.login.service;

import com.ehs.common.auth.entity.SysUser;

public interface LoginService {

	public SysUser login(String account);
}
