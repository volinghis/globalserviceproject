/**   
 * Copyright © 2019 西安东恒鑫源软件开发有限公司版权所有.
 * 
 * 功能描述：
 * @Package: com.ehs.gsp.auth.role.dao 
 * @author: qjj   
 * @date: 2019年10月9日 上午10:39:20 
 */
package com.ehs.gsp.auth.role.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.base.config.DataConfig;
import com.ehs.common.base.entity.BaseEntity;

/**   
* Copyright: Copyright (c) 2019 西安东恒鑫源软件开发有限公司
* @ClassName: RoleDao.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: qjj
* @date: 2019年10月9日 上午10:39:20 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年10月9日     qjj           v1.0.0               修改原因
*/
@Repository
public interface RolesDao extends JpaRepository<SysRole, String> {
	
	@Query(" select sr from SysRole sr where sr."+BaseEntity.VERSION_ID+" = "+DataConfig.VERSION_EFFECTIVE+" and (sr."+SysRole.DATA_CODE+" like %?1% or sr."+SysRole.NAME+" like %?1% ) order by "+BaseEntity.CREATION_TIME+" desc")
	public  Page<SysRole> findRoles(String query, Pageable pageable);
}
