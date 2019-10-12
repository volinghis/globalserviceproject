package com.ehs.gsp.org.position.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.ResultBean;
import com.ehs.common.organization.entity.OrgPositionInfo;
import com.ehs.gsp.auth.login.bean.LoginInfoBean;

@Controller
public class OrgPositionController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@RequestMapping(value = "/org/orgPosition/getOrgPositionInfo")
	@ResponseBody
	public String getOrgPositionInfo(HttpServletRequest request, HttpServletResponse response) {
		String key=request.getParameter("key");
		OrgPositionInfo orgPositionInfo= baseCommonService.findByKey(OrgPositionInfo.class, key);
		return orgPositionInfo==null?"{}":JsonUtils.toJsonString(orgPositionInfo);
	}
	
	@RequestMapping(value = "/org/orgPosition/getOrgPositionInfoList")
	@ResponseBody
	public String getOrgPositionInfoList(HttpServletRequest request, HttpServletResponse response) {
		List<OrgPositionInfo> orgPositionInfoList= (List<OrgPositionInfo>)baseCommonService.findAll(OrgPositionInfo.class);
		
		return (orgPositionInfoList==null||orgPositionInfoList.isEmpty())?"[]":JsonUtils.toJsonString(orgPositionInfoList);
	}
	
	
	@RequestMapping(value = "/org/orgPosition/saveOrgPositionInfo")
	@ResponseBody
	public String saveOrgPositionInfoList(@RequestBody OrgPositionInfo orgPositionInfo, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		List<OrgPositionInfo> orgPositionInfoList= (List<OrgPositionInfo>)baseCommonService.findAll(OrgPositionInfo.class);
		if(orgPositionInfoList!=null&&orgPositionInfoList.size()>0) {
			long c=orgPositionInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),orgPositionInfo.getDataCode())&&!StringUtils.equals(s.getKey(), orgPositionInfo.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存职位失败:已存在相同职位编号"));
			}
		}

		OrgPositionInfo op= baseCommonService.saveOrUpdate(orgPositionInfo);
		return JsonUtils.toJsonString(resultBean.ok("保存职位成功！",op.getKey()));
	}
	
	
	@RequestMapping(value = "/org/orgPosition/deleteOrgPositionInfo")
	@ResponseBody
	public String deleteOrgPositionInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
		baseCommonService.deleteByKey(OrgPositionInfo.class, key);
		return JsonUtils.toJsonString(resultBean.ok("删除职位成功！"));
	}
	
}
