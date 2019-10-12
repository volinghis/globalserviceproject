package com.ehs.gsp.auth.login.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.auth.config.RoleConfig;
import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.auth.entity.SysUser;
import com.ehs.common.auth.entity.SysUserRole;
import com.ehs.common.auth.service.RoleService;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.BaseUtils;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.gsp.auth.login.bean.LoginInfoBean;
import com.ehs.gsp.auth.login.bean.LoginResultBean;
import com.ehs.gsp.auth.login.service.LoginLogService;
import com.ehs.gsp.auth.login.service.LoginService;


@Controller
public class LoginController {
	
	@Resource
	private LoginService loginService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private LoginLogService loginLogService;

	@Resource
	private com.ehs.common.auth.utils.SessionBean  sessionBean;
	
	@RequestMapping(value = "/auth/login/doLogin")
	@ResponseBody
	public String doLogin(@RequestBody LoginInfoBean loginInfo,HttpServletRequest request, HttpServletResponse response) {
		SysUser sysUser=loginService.login(loginInfo.getUserName());
		LoginResultBean loginResultBean=new LoginResultBean();
		if(sysUser==null) {
			return JsonUtils.toJsonString(loginResultBean.error("用户不存在"));
		}
		if(!StringUtils.equals(BaseUtils.string2MD5(loginInfo.getUserName()+loginInfo.getPassword()), sysUser.getPassword())) {
			return JsonUtils.toJsonString(loginResultBean.error("密码错误"));
		}
		if(sysUser.getState()==1) {
			return JsonUtils.toJsonString(loginResultBean.error("用户已被锁定"));
		}
		sessionBean.login(sysUser.getKey(), request);
		//添加登录日志 
		System.out.println("sysUser.getUserKey()=============="+sysUser.getKey());
		loginLogService.addLoginLog(sysUser.getKey(),BaseUtils.getIpAddress(request));
		return JsonUtils.toJsonString(loginResultBean.ok("认证成功"));
	}
	

	@RequestMapping(value = "/auth/login/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
		sessionBean.logout(request);
		LoginResultBean loginResultBean=new LoginResultBean();
		return JsonUtils.toJsonString(loginResultBean.ok("退出成功"));
	}
	
	

}
