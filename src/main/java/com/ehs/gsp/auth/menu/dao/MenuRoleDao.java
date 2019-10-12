package com.ehs.gsp.auth.menu.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.auth.entity.SysRoleMenu;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;

@Repository
public interface MenuRoleDao extends JpaRepository<SysRoleMenu, String>{


	
	
	@Query(" select r from SysRoleMenu r  where r."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE +" and r."+SysRoleMenu.MENU_KEY+" in ?1 and r."+SysRoleMenu.ROLE_KEY+"  in ?2 ")
	public List<SysRoleMenu> find(String[] menuKey,String[] roleKey);
	
	

}
