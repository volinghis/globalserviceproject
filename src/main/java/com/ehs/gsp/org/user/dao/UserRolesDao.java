package com.ehs.gsp.org.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.auth.entity.SysUserRole;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;

@Repository
public interface UserRolesDao  extends JpaRepository<SysUserRole, String> {

	@Query(" select r from SysUserRole r  where r."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE +" and r."+SysUserRole.SYS_USER_KEY+" = ?1 and r."+SysUserRole.ROLE_KEY+"  in ?2 ")
	public List<SysUserRole> find(String sysUserKey,String[] roleKey);
}
