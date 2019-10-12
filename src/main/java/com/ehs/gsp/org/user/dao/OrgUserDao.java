package com.ehs.gsp.org.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;
import com.ehs.common.organization.entity.OrgUser;

@Repository
public interface OrgUserDao extends JpaRepository<OrgUser, String> {

	@Query(" select u from OrgUser u where u."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+" and (u."+OrgUser.DATA_CODE+" like %?1% or u."+OrgUser.NAME+" like %?1% ) order by "+BaseEntity.CREATION_TIME+" desc")
	public  Page<OrgUser> findUsers(String query, Pageable pageable);
	
	
	@Query(" select u from OrgUser u where u."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+"  and (u."+OrgUser.DATA_CODE+" like %?1% or u."+OrgUser.NAME+" like %?1% ) and "+OrgUser.ORG_KEY+"=?2 order by "+BaseEntity.CREATION_TIME+" desc")
	public  Page<OrgUser> findUsers(String query, String selectOrg,Pageable pageable);
	
	@Query(" select u from OrgUser u where u."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+"  and (u."+OrgUser.DATA_CODE+" like %?1% or u."+OrgUser.NAME+" like %?1% ) and ("+OrgUser.ORG_KEY+"=?2 or "+OrgUser.ORG_KEY+" in ?3 ) order by "+BaseEntity.CREATION_TIME+" desc")
	public  Page<OrgUser> findUsers(String query, String selectOrg,String[] subOrgs,Pageable pageable);
}
