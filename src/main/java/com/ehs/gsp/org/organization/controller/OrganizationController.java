package com.ehs.gsp.org.organization.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.auth.entity.SysMenu;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.ResultBean;
import com.ehs.common.organization.entity.OrganizationInfo;
import com.ehs.common.organization.entity.OrgPositionInfo;
import com.ehs.common.organization.entity.OrganizationInfo;
import com.ehs.gsp.auth.menu.utils.MenuNode;
import com.ehs.gsp.org.organization.utils.OrgNode;

@Controller
public class OrganizationController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@RequestMapping(value = "/org/organization/getOrgTreeData")
	@ResponseBody
	public String getOrgTreeData(HttpServletRequest request, HttpServletResponse response) {
		
		List<OrganizationInfo> list= (List<OrganizationInfo> )baseCommonService.findAll(OrganizationInfo.class);
		List<OrgNode> orgNodes=new ArrayList<OrgNode>();
		createOrgNodes(orgNodes,list,null);
		return (orgNodes==null||orgNodes.isEmpty())?"[]":JsonUtils.toJsonString(orgNodes);
	}
	
	
	private void createOrgNodes(List<OrgNode> orgNodes, List<OrganizationInfo> orgs, String parentKey) {
		orgs.stream().filter(s -> StringUtils.equals(s.getParentKey(), parentKey)).forEach(c -> {
			OrgNode orgNode = new OrgNode();
			orgNode.setKey(c.getKey());
			orgNode.setTitle(c.getName());
			orgNode.setParentKey(c.getParentKey());
			orgNode.setExpand(true);
			List ll = new ArrayList();
			createOrgNodes(ll, orgs, c.getKey());
			if (ll.size() > 0) {
				orgNode.setChildren(ll);
			}
			orgNodes.add(orgNode);
		});
	}
	
	
	@RequestMapping(value = "/org/organization/getOrganizationInfo")
	@ResponseBody
	public String getOrganizationInfo(HttpServletRequest request, HttpServletResponse response) {
		String key=request.getParameter("key");
		OrganizationInfo orgInfo= baseCommonService.findByKey(OrganizationInfo.class, key);
		System.out.println("333");
		System.out.println(JsonUtils.toJsonString(orgInfo));
		return orgInfo==null?"{}":JsonUtils.toJsonString(orgInfo);
	}
	

	
	
	@RequestMapping(value = "/org/organization/saveOrganizationInfo")
	@ResponseBody
	public String saveOrganizationInfoList(@RequestBody OrganizationInfo orgInfo, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		List<OrganizationInfo> orgInfoList= (List<OrganizationInfo>)baseCommonService.findAll(OrganizationInfo.class);
		if(orgInfoList!=null&&orgInfoList.size()>0) {
			long c=orgInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),orgInfo.getDataCode())&&!StringUtils.equals(s.getKey(), orgInfo.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存组织失败:已存在相同组织编号"));
			}
		}
		System.out.println(orgInfo.getId());
		OrganizationInfo op= baseCommonService.saveOrUpdate(orgInfo);
		return JsonUtils.toJsonString(resultBean.ok("保存组织成功！",op.getKey()));
	}
	
	
	@RequestMapping(value = "/org/organization/deleteOrganizationInfo")
	@ResponseBody
	public String deleteOrganizationInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
		baseCommonService.deleteByKey(OrganizationInfo.class, key);
		return JsonUtils.toJsonString(resultBean.ok("删除组织成功！"));
	}
}
