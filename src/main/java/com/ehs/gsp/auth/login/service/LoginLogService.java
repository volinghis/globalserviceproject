package com.ehs.gsp.auth.login.service;

import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.gsp.auth.login.bean.LoginLogQueryBean;

public interface LoginLogService {
	
	public PageInfoBean findLoginLog(LoginLogQueryBean bean);
	
	public void addLoginLog(String userAccount,String ip);
}
