package com.ehs.gsp.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.auth.entity.SysUser;
import com.ehs.common.auth.local.SysAccessUser;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.organization.entity.OrganizationInfo;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.gsp.base.controller.bean.CurrentUserBean;

@Controller
public class BaseController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "/html/index.html";
	}
	
	@RequestMapping(value = "/base/user/getCurrentUser")
	@ResponseBody
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
		
		CurrentUserBean cub=new CurrentUserBean();
		cub.setSysUserKey(SysAccessUser.get().getSysUserKey());
		cub.setUserKey(SysAccessUser.get().getUserKey());
		cub.setOrgKey(SysAccessUser.get().getOrgKey());
		if(StringUtils.isNotBlank(cub.getUserKey())) {
			OrgUser user=baseCommonService.findByKey(OrgUser.class, cub.getUserKey());
			cub.setName(user.getName());
		}
		if(StringUtils.isNotBlank(cub.getOrgKey())) {
			OrganizationInfo oi=baseCommonService.findByKey(OrganizationInfo.class, cub.getOrgKey());
			cub.setOrgName(oi.getName());
		}
		SysUser sysUser=baseCommonService.findByKey(SysUser.class, cub.getSysUserKey());
		cub.setAccount(sysUser.getAccount());
		return JsonUtils.toJsonString(cub);
	}
}
