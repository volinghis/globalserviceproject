package com.ehs.gsp.auth.menu.service;

import java.util.List;

import com.ehs.common.auth.entity.SysMenu;
import com.ehs.common.auth.entity.SysRoleMenu;
import com.ehs.gsp.auth.menu.controller.bean.MenuRolesBean;

public interface MenuService {

	public void saveMenuRole(MenuRolesBean menuRoleBean);
	
	public void deleteMenuRole(MenuRolesBean menuRoleBean);
	
	public List<SysMenu> findSysMenus(String userKey);
}
