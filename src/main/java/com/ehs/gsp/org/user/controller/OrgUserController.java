package com.ehs.gsp.org.user.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.auth.interfaces.RequestAuth;
import com.ehs.common.auth.service.RoleService;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.common.oper.bean.ResultBean;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.gsp.auth.login.bean.LoginInfoBean;
import com.ehs.gsp.org.user.controller.bean.OrgUserQueryBean;
import com.ehs.gsp.org.user.controller.bean.UserRolesBean;
import com.ehs.gsp.org.user.service.OrgUserService;

@Controller
public class OrgUserController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private OrgUserService orgUserService;
	
	@Resource
	private RoleService roleService;
	
	@RequestAuth(menuKeys={"ksksdf_key","asdfasdfasd"})
	@RequestMapping(value = "/org/orgUser/getOrgUser")
	@ResponseBody
	public String getOrgUser(HttpServletRequest request, HttpServletResponse response) {
		String key=request.getParameter("key");
		OrgUser orgUserInfo= baseCommonService.findByKey(OrgUser.class, key);
		return orgUserInfo==null?"{}":JsonUtils.toJsonString(orgUserInfo);
	}
	
	@RequestMapping(value = "/org/orgUser/getOrgUserList")
	@ResponseBody
	public String getOrgUserList(@RequestBody OrgUserQueryBean orgUserQueryBean,HttpServletRequest request,HttpServletResponse response) {
		PageInfoBean pib=orgUserService.findUsers(orgUserQueryBean);
		return (pib==null)?"{}":JsonUtils.toJsonString(pib);
	}
	
	/**
	 * 获取待选择角色列表
	 */
	@RequestMapping(value = "/org/orgUser/findAllRolesByUserKey")
	@ResponseBody
	public String findAllRolesByUserKey(HttpServletRequest request, HttpServletResponse response) {
		List<SysRole> allRoles=(List<SysRole>)baseCommonService.findAll(SysRole.class);
		if(allRoles==null||allRoles.isEmpty()) {
			return "[]";
		}
		
		String userKey=request.getParameter("userKey");
		OrgUser ou=baseCommonService.findByKey(OrgUser.class, userKey);
		List<SysRole> roles=roleService.findRoleBySysUser(ou.getSysUserKey());

		if(roles==null||roles.isEmpty()) {
			List roleList=allRoles.stream().filter(
					s->(!StringUtils.equals(s.getKey(), "sysAdminRoleKey"))
					&&(!StringUtils.equals(s.getKey(), "normalRoleKey"))
					).collect(Collectors.toList());
			return JsonUtils.toJsonString(roleList);
		}

		return JsonUtils.toJsonString(allRoles.stream().filter(
				s->roles.stream().allMatch(ss->(!StringUtils.equals(s.getKey(), ss.getKey())))
				&&(!StringUtils.equals(s.getKey(), "sysAdminRoleKey"))
				&&(!StringUtils.equals(s.getKey(), "normalRoleKey"))
				).collect(Collectors.toList()));
	}
	
	@RequestMapping(value = "/org/orgUser/saveUserRoles")
	@ResponseBody
	public String saveUserRoles(@RequestBody UserRolesBean userRolesBean, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		orgUserService.saveUserRoles(userRolesBean);
		return JsonUtils.toJsonString(resultBean.ok("授权成功！",""));
	}
	
	
	
	@RequestMapping(value = "/org/orgUser/saveOrgUser")
	@ResponseBody
	public String saveOrgUserList(@RequestBody OrgUser orgUserInfo, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		List<OrgUser> orgUserInfoList= (List<OrgUser>)baseCommonService.findAll(OrgUser.class);
		if(orgUserInfoList!=null&&orgUserInfoList.size()>0) {
			long c=orgUserInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),orgUserInfo.getDataCode())&&!StringUtils.equals(s.getKey(), orgUserInfo.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存用户失败:已存在相同用户编号"));
			}
		}
		OrgUser op= orgUserService.saveOrUpdateUser(orgUserInfo);
		return JsonUtils.toJsonString(resultBean.ok("保存用户成功！",op.getKey()));
	}
	
	
	@RequestMapping(value = "/org/orgUser/deleteOrgUser")
	@ResponseBody
	public String deleteOrgUser(HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
		baseCommonService.deleteByKey(OrgUser.class, key);
		return JsonUtils.toJsonString(resultBean.ok("删除用户成功！"));
	}
	
}
