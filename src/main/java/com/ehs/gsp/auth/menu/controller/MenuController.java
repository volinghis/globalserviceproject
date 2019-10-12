package com.ehs.gsp.auth.menu.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.auth.config.RoleConfig;
import com.ehs.common.auth.entity.SysMenu;
import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.auth.entity.SysRoleMenu;
import com.ehs.common.auth.entity.SysUserRole;
import com.ehs.common.auth.local.SysAccessUser;
import com.ehs.common.auth.service.RoleService;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.ResultBean;
import com.ehs.common.organization.entity.OrganizationInfo;
import com.ehs.gsp.auth.menu.controller.bean.MenuRolesBean;
import com.ehs.gsp.auth.menu.service.MenuService;
import com.ehs.gsp.auth.menu.utils.MenuNode;

@Controller
public class MenuController {

	
	@Resource
	private RoleService roleService;
	
	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private MenuService menuService;
	
	/**
	 * 获取所有导航
	 */
	@RequestMapping(value = "/auth/menu/findAllMenus")
	@ResponseBody
	public String findAllMenus(HttpServletRequest request, HttpServletResponse response) {

		List<SysMenu> smList =(List<SysMenu>)baseCommonService.findAll(SysMenu.class);
		if (smList == null || smList.isEmpty()) {
			return "[]";
		}
		List<MenuNode> menus = new ArrayList<MenuNode>();
		createMenuNode(menus, smList, null);
		return JsonUtils.toJsonString(menus);
	}
	

	/**
	 * 获取待选择角色列表
	 */
	@RequestMapping(value = "/auth/menu/findAllRolesByMenuKey")
	@ResponseBody
	public String findAllRolesByMenuKey(HttpServletRequest request, HttpServletResponse response) {
		List<SysRole> allRoles=(List<SysRole>)baseCommonService.findAll(SysRole.class);
		if(allRoles==null||allRoles.isEmpty()) {
			return "[]";
		}
		
		String menuKey=request.getParameter("menuKey");
		List<SysRole> roles=roleService.findRoleByMenuKey(menuKey);

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
	
	/**
	 * 根据菜单获取角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/auth/menu/findMenuRoles")
	@ResponseBody
	public String findMenuRoles(HttpServletRequest request, HttpServletResponse response) {

		String menuKey=request.getParameter("menuKey");
		List<SysRole> roles=roleService.findRoleByMenuKey(menuKey);
		if(roles==null||roles.isEmpty()) {
			return "[]";
		}
		return JsonUtils.toJsonString(roles);
	}
	
	/**
	 * 获取用户导航
	 */
	@RequestMapping(value = "/auth/menu/findUserMenus")
	@ResponseBody
	public String findMenudata(HttpServletRequest request, HttpServletResponse response) {

		List<SysMenu> smList =(List<SysMenu>)baseCommonService.findAll(SysMenu.class);
		if (smList == null || smList.isEmpty()) {
			return "[]";
		}
		Collections.sort(smList, Comparator.comparing(SysMenu::getSort));
		String paramMenuKey = request.getParameter("paramMenuKey");
		if (StringUtils.isBlank(paramMenuKey)) {
			paramMenuKey = smList.stream().filter(s -> StringUtils.isBlank(s.getParentKey())).findFirst().get().getKey();
		}
		

		List<SysRole> userRoles=roleService.findRoleBySysUser(SysAccessUser.get().getSysUserKey());
		List<MenuNode> menus = new ArrayList<MenuNode>();
		if(userRoles!=null&&userRoles.stream().filter(s->StringUtils.equals(s.getKey(), "sysAdminRoleKey")).count()>0) {
			createMenuNode(menus, smList, paramMenuKey);
			return JsonUtils.toJsonString(menus);
		}else {
			System.out.println("666");
			System.out.println(SysAccessUser.get().getSysUserKey());
			List<SysMenu> list= menuService.findSysMenus(SysAccessUser.get().getSysUserKey());
			if(list==null||list.isEmpty()) {
				return "[]";
			}
			
			createMenuNode(menus, list, paramMenuKey);
			if(menus==null||menus.isEmpty()) {
				return "[]";
			}else {
				return JsonUtils.toJsonString(menus);
			}
			

		}
		
	}

	
	@RequestMapping(value = "/auth/menu/saveMenuRole")
	@ResponseBody
	public String saveMenuRole(@RequestBody MenuRolesBean menuRoleBean, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		menuService.saveMenuRole(menuRoleBean);
		return JsonUtils.toJsonString(resultBean.ok("授权成功！",""));
	}
	
	
	@RequestMapping(value = "/auth/menu/deleteMenuRole")
	@ResponseBody
	public String deleteMenuRole(@RequestBody MenuRolesBean menuRoleBean, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		menuService.deleteMenuRole(menuRoleBean);
		return JsonUtils.toJsonString(resultBean.ok("删除成功！",""));
	}
	
	@RequestMapping(value = "/auth/menu/findModelMenus")
	@ResponseBody
	public String findSystemdata(HttpServletRequest request, HttpServletResponse response) {

		List<SysMenu> smList =(List<SysMenu>)baseCommonService.findAll(SysMenu.class);
		if (smList == null || smList.isEmpty()) {
			return "[]";
		}
		List<MenuNode> menus = new ArrayList<MenuNode>();
		
		List<SysMenu> smItems=smList.stream().filter(s->StringUtils.isBlank(s.getParentKey())).collect(Collectors.toList());
		if(smItems!=null&&!smItems.isEmpty()) {
			smItems.forEach(s->{
				MenuNode mn=new MenuNode();
				mn.setName(s.getKey());
				mn.setLabel(s.getName());
				mn.setTitle(s.getName());
				mn.setUrl(s.getUrl());
				mn.setIcon(s.getIcon());
				menus.add(mn);
			});
		}
		return JsonUtils.toJsonString(menus);
	}
	
	private void createMenuNode(List<MenuNode> menuNodes, List<SysMenu> menus, String parentKey) {
		menus.stream().filter(s -> (StringUtils.isBlank(parentKey)?StringUtils.isBlank(s.getParentKey()):StringUtils.equals(s.getParentKey(), parentKey))).forEach(c -> {
			MenuNode menuNode = new MenuNode();
			menuNode.setIcon(c.getIcon());
			menuNode.setName(c.getKey());
			menuNode.setLabel(c.getName());
			menuNode.setTitle(c.getName());
			menuNode.setUrl(c.getUrl());
			menuNode.setParentName(c.getParentKey());
			List ll = new ArrayList();
			createMenuNode(ll, menus, c.getKey());
			if (ll.size() > 0) {
				menuNode.setChildren(ll);
				menuNodes.add(menuNode);
			}else {
				if(StringUtils.isNotBlank(c.getUrl())) {
					menuNodes.add(menuNode);
				}
			}
			
		});
	}
}
