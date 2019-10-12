package com.ehs.gsp.org.job.controller;

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
import com.ehs.common.organization.entity.OrgJobInfo;
import com.ehs.gsp.auth.login.bean.LoginInfoBean;

@Controller
public class OrgJobController {

	@Resource
	private BaseCommonService baseCommonService;
	
	@RequestMapping(value = "/org/orgJob/getOrgJobInfo")
	@ResponseBody
	public String getOrgJobInfo(HttpServletRequest request, HttpServletResponse response) {
		String key=request.getParameter("key");
		OrgJobInfo orgJobInfo= baseCommonService.findByKey(OrgJobInfo.class, key);
		return orgJobInfo==null?"{}":JsonUtils.toJsonString(orgJobInfo);
	}
	
	@RequestMapping(value = "/org/orgJob/getOrgJobInfoList")
	@ResponseBody
	public String getOrgJobInfoList(HttpServletRequest request, HttpServletResponse response) {
		List<OrgJobInfo> orgJobInfoList= (List<OrgJobInfo>)baseCommonService.findAll(OrgJobInfo.class);
		System.out.println(JsonUtils.toJsonString(orgJobInfoList));
		
		return (orgJobInfoList==null||orgJobInfoList.isEmpty())?"[]":JsonUtils.toJsonString(orgJobInfoList);
	}
	
	
	@RequestMapping(value = "/org/orgJob/saveOrgJobInfo")
	@ResponseBody
	public String saveOrgJobInfoList(@RequestBody OrgJobInfo orgJobInfo, HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		List<OrgJobInfo> orgJobInfoList= (List<OrgJobInfo>)baseCommonService.findAll(OrgJobInfo.class);
		if(orgJobInfoList!=null&&orgJobInfoList.size()>0) {
			long c=orgJobInfoList.stream().filter(s->StringUtils.equals(s.getDataCode(),orgJobInfo.getDataCode())&&!StringUtils.equals(s.getKey(), orgJobInfo.getKey())).count();
			if(c>0) {
				return JsonUtils.toJsonString(resultBean.error("保存职务失败:已存在相同职务编号"));
			}
		}

		OrgJobInfo op= baseCommonService.saveOrUpdate(orgJobInfo);
		return JsonUtils.toJsonString(resultBean.ok("保存职务成功！",op.getKey()));
	}
	
	
	@RequestMapping(value = "/org/orgJob/deleteOrgJobInfo")
	@ResponseBody
	public String deleteOrgJobInfo(HttpServletRequest request, HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		String key=request.getParameter("key");
		baseCommonService.deleteByKey(OrgJobInfo.class, key);
		return JsonUtils.toJsonString(resultBean.ok("删除职务成功！"));
	}
	
}
