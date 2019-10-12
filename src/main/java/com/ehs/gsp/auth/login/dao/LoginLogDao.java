package com.ehs.gsp.auth.login.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.auth.entity.SysLoginLog;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;
@Repository
public interface LoginLogDao extends JpaRepository<SysLoginLog, String> {

	@Query(" select l from SysLoginLog l where l."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+" and (l."+SysLoginLog.ACCOUNT+" like %?1% or l."+SysLoginLog.NAME+" like %?1% ) order by "+BaseEntity.CREATION_TIME+" desc")
	public  Page<SysLoginLog> findLogs(String query, Pageable pageable);
}
