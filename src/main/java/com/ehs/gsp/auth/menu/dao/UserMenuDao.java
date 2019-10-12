package com.ehs.gsp.auth.menu.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ehs.common.auth.entity.SysMenu;
import com.ehs.common.auth.entity.SysRoleMenu;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;

@Resource
public interface UserMenuDao extends JpaRepository<SysMenu, String>{
	
	@Query(" select distinct r from SysMenu r left join SysRoleMenu  srm on srm.menuKey=r.key  left join SysUserRole sur on srm.roleKey=sur.roleKey where (srm.menuKey is null or sur.sysUserKey= ?1 ) and r."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE +" and  (srm."+BaseEntity.VERSION_ID +" = "+DataConfig.VERSION_EFFECTIVE +" or srm."+BaseEntity.VERSION_ID+" is null) "+" and  (sur."+BaseEntity.VERSION_ID +" = "+DataConfig.VERSION_EFFECTIVE +" or sur."+BaseEntity.VERSION_ID+" is null) ")
	public List<SysMenu> findMenus(String sysUserKey);
}
