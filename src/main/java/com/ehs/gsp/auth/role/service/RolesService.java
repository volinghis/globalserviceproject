/**   
 * Copyright © 2019 西安东恒鑫源软件开发有限公司版权所有.
 * 
 * 功能描述：
 * @Package: com.ehs.gsp.auth.role.service 
 * @author: qjj   
 * @date: 2019年10月9日 上午10:10:39 
 */
package com.ehs.gsp.auth.role.service;

import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.gsp.auth.role.controller.bean.RoleQueryBean;

/**   
* Copyright: Copyright (c) 2019 西安东恒鑫源软件开发有限公司
* @ClassName: RoleService.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: qjj
* @date: 2019年10月9日 上午10:10:39 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年10月9日     qjj           v1.0.0               修改原因
*/
public interface RolesService {

	public PageInfoBean findRoles(RoleQueryBean queryBean);
}
