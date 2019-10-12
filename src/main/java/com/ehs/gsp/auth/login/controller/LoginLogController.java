package com.ehs.gsp.auth.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.gsp.auth.login.bean.LoginLogQueryBean;
import com.ehs.gsp.auth.login.service.LoginLogService;

@Controller
public class LoginLogController {
	
	@Resource
	private LoginLogService loginLogService;

	
	@RequestMapping(value = "/auth/login/loginLog")
	@ResponseBody
	public String loginLog(@RequestBody LoginLogQueryBean loginLog,HttpServletRequest request,HttpServletResponse response) {
		System.out.println("========准备查询登录日志========"+JsonUtils.toJsonString(loginLog));
		PageInfoBean pib=loginLogService.findLoginLog(loginLog);
		return (pib==null)?"{}":JsonUtils.toJsonString(pib);
	}
}
