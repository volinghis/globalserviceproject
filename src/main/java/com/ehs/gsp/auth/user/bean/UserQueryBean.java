/**   
 * Copyright © 2019 西安东恒鑫源软件开发有限公司版权所有.
 * 
 * 功能描述：
 * @Package: com.ehs.gsp.auth.user.bean 
 * @author: qjj   
 * @date: 2019年10月24日 上午10:27:12 
 */
package com.ehs.gsp.auth.user.bean;

import com.ehs.common.oper.bean.PageBody;

/**   
* Copyright: Copyright (c) 2019 西安东恒鑫源软件开发有限公司
* @ClassName: UserQueryBean.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: qjj
* @date: 2019年10月24日 上午10:27:12 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年10月24日     qjj           v1.0.0               修改原因
*/
public class UserQueryBean extends PageBody {
	
	private String query;

	
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	
}
