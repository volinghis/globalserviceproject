/**   
 * Copyright © 2019 西安东恒鑫源软件开发有限公司版权所有.
 * 
 * 功能描述：
 * @Package: com.ehs.gsp.auth.user.dao 
 * @author: qjj   
 * @date: 2019年10月24日 上午10:14:20 
 */
package com.ehs.gsp.auth.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;
import com.ehs.common.organization.entity.OrgUser;

/**   
* Copyright: Copyright (c) 2019 西安东恒鑫源软件开发有限公司
* @ClassName: UserDao.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: qjj
* @date: 2019年10月24日 上午10:14:20 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年10月24日     qjj           v1.0.0               修改原因
*/
@Repository
public interface UserDao extends JpaRepository<OrgUser,String>{
	
	@Query(" select ou from OrgUser ou where ou."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+" and (ou."+OrgUser.DATA_CODE+" like %?1% or ou."+OrgUser.NAME+" like %?1% ) order by "+BaseEntity.CREATION_TIME+" desc")
	public Page<OrgUser> findOrgUsers(String query,Pageable pageable);
	
}
