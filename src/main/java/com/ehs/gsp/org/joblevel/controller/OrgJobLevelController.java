package com.ehs.gsp.org.joblevel.controller;

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
import com.ehs.common.organization.entity.OrgJobLevelInfo;
import com.ehs.gsp.auth.login.bean.LoginInfoBean;

@Controller
public class OrgJobLevelController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@RequestMapping(value = "/org/orgJobLevel/getOrgJobLevelInfo")
	@ResponseBody
	public String getOrgJobLevelInfo(HttpServletRequest request, HttpServletResponse response) {
		String key=request.getParameter("key");
		OrgJobLevelInfo orgJobLevelInfo= baseCommonService.findByKey(OrgJobLevelInfo.class, key);
		return orgJobLevelInfo==null?"{}":JsonUtils.toJsonString(orgJobLevelInfo);
	}
	
	@RequestMapping(value = "/org/orgJobLevel/getOrgJobLevelInfoList")
	@ResponseBody
	public String getOrgJobLevelInfoList(HttpServletRequest request, HttpServletResponse response) {
		List<OrgJobLevelInfo> orgJobLevelInfoList= (List<OrgJobLevelInfo>)baseCommonService.findAll(OrgJobLevelInfo.class);
		System.out.println(JsonUtils.toJsonString(orgJobLevelInfoList));
		
		return (orgJobLevelInfoList==null||orgJobLevelInfoList.isEmpty())?"[]":JsonUtils.toJsonString(orgJobLevelInfoList);
	}
	
	
	@RequestMapping(value = "/org/orgJobLevel/saveOrgJobLevelInfo")
	@ResponseBody
	public String saveOrgJobLevelInfoList(@RequestBody OrgJobLevelInfo orgJobLevelInfo, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		List<OrgJobLevelInfo> orgJobLevelInfoList= (List<OrgJobLevelInfo>)baseCommonService.findAll(OrgJobLevelInfo.class);
		if(orgJobLevelInfoList!=null&&orgJobLevelInfoList.size()>0) {
			long c=orgJobLevelInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),orgJobLevelInfo.getDataCode())&&!StringUtils.equals(s.getKey(), orgJobLevelInfo.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存职级失败:已存在相同职级编号"));
			}
		}

		OrgJobLevelInfo op= baseCommonService.saveOrUpdate(orgJobLevelInfo);
		return JsonUtils.toJsonString(resultBean.ok("保存职级成功！",op.getKey()));
	}
	
	
	@RequestMapping(value = "/org/orgJobLevel/deleteOrgJobLevelInfo")
	@ResponseBody
	public String deleteOrgJobLevelInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
		baseCommonService.deleteByKey(OrgJobLevelInfo.class, key);
		return JsonUtils.toJsonString(resultBean.ok("删除职级成功！"));
	}
	
}
