/**   
 * Copyright © 2019 西安东恒鑫源软件开发有限公司版权所有.
 * 
 * 功能描述：
 * @Package: com.ehs.gsp.auth.role.controller 
 * @author: qjj   
 * @date: 2019年10月8日 下午1:53:05 
 */
package com.ehs.gsp.auth.role.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.common.oper.bean.ResultBean;
import com.ehs.gsp.auth.role.controller.bean.RoleQueryBean;
import com.ehs.gsp.auth.role.service.RolesService;

/**   
* Copyright: Copyright (c) 2019 西安东恒鑫源软件开发有限公司
* @ClassName: RoleController.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: qjj
* @date: 2019年10月8日 下午1:53:05 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年10月8日     qjj           v1.0.0               修改原因
*/
@RestController
public class RoleController {
	
	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private RolesService roleService;
	/**
	 * 
	* @Function:getRoleInfoList 
	* @Description: 角色管理数据集合
	* @return
	* @throws：异常描述
	* @version: v1.0.0
	* @author: qjj
	* @date: 2019年10月8日 下午2:21:04 
	*
	* Modification History:
	* Date        Author        Version      Description
	*---------------------------------------------------------*
	* 2019年10月8日     qjj        v1.0.0            修改原因
	 */
	@RequestMapping(value = "/auth/role/getRoleInfoList")
	public String getRoleInfoList(@RequestBody RoleQueryBean roleQueryBean) {
		 System.out.println("+++++++++++++++传入的参数为++++++++++++++++"+JsonUtils.toJsonString(roleQueryBean));
	     PageInfoBean pb = roleService.findRoles(roleQueryBean);
		 return (pb==null?"[]":JsonUtils.toJsonString(pb));
	}
	
	/**
	 * 
	* @Function:saveRoleInfo 
	* @Description: 角色信息保存
	* @param sysRole
	* @param request
	* @return
	* @throws：异常描述
	* @version: v1.0.0
	* @author: qjj
	* @date: 2019年10月8日 下午2:52:58 
	*
	* Modification History:
	* Date        Author        Version      Description
	*---------------------------------------------------------*
	* 2019年10月8日     qjj        v1.0.0            修改原因
	 */
	@RequestMapping(value = "/auth/role/saveRoleInfo")
	public String saveRoleInfo(@RequestBody SysRole sysRole,HttpServletRequest request) {
		ResultBean resultBean=new ResultBean();
		List<SysRole> roleInfoList=	(List<SysRole>)baseCommonService.findAll(SysRole.class);
		if (roleInfoList!=null&&roleInfoList.size()>0) {
			long c=roleInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),sysRole.getDataCode())&&!StringUtils.equals(s.getKey(), sysRole.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存角色失败:已存在相同角色编号"));
			}
		}
		SysRole sr = baseCommonService.saveOrUpdate(sysRole);
		return JsonUtils.toJsonString(resultBean.ok("保存角色成功",sr.getKey()));
	}
	
	/**
	 * 
	* @Function:getRoleInfo 
	* @Description: 获取当前的角色信息
	* @param request
	* @return
	* @throws：异常描述
	* @version: v1.0.0
	* @author: qjj
	* @date: 2019年10月8日 下午2:59:08 
	*
	* Modification History:
	* Date        Author        Version      Description
	*---------------------------------------------------------*
	* 2019年10月8日     qjj        v1.0.0            修改原因
	 */
	@RequestMapping(value="/auth/role/getRoleInfo")
	public String getRoleInfo(HttpServletRequest request) {
		String key=request.getParameter("key");
		SysRole sRole=	baseCommonService.findByKey(SysRole.class, key);
		return sRole==null?"{}":JsonUtils.toJsonString(sRole);
	}
	
	/**
	 * 
	* @Function:deleteRoleInfo 
	* @Description: 删除角色
	* @param request
	* @return
	* @throws：异常描述
	* @version: v1.0.0
	* @author: qjj
	* @date: 2019年10月8日 下午5:04:07 
	*
	* Modification History:
	* Date        Author        Version      Description
	*---------------------------------------------------------*
	* 2019年10月8日     qjj        v1.0.0            修改原因
	 */
	@RequestMapping(value = "/auth/role/deleteRoleInfo")
	public String deleteRoleInfo(HttpServletRequest request) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
	    baseCommonService.deleteByKey(SysRole.class, key);
		return JsonUtils.toJsonString(resultBean.ok("角色删除成功"));
	}
}
