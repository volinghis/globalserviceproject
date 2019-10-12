package com.ehs.gsp.auth.login.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ehs.common.auth.entity.SysLoginLog;
import com.ehs.common.auth.entity.SysUser;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.BaseUtils;
import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.gsp.auth.login.bean.LoginLogQueryBean;
import com.ehs.gsp.auth.login.dao.LoginLogDao;
import com.ehs.gsp.auth.login.service.LoginLogService;

@Service
public class LoginLogServiceImpl implements LoginLogService {
	
	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private LoginLogDao loginLogDao;

	@Override
	public PageInfoBean findLoginLog(LoginLogQueryBean query) {
		PageRequest pageRequest =PageRequest.of(query.getPage()-1, query.getSize());
		Page<SysLoginLog> loginLogs=null;
		loginLogs= loginLogDao.findLogs(query.getQuery(), pageRequest);
		PageInfoBean pi=new PageInfoBean();
		pi.setDataList(loginLogs.getContent());
		pi.setTotalCount(loginLogs.getTotalElements());
		return pi;
	}

	@Override
	@Transactional
	public void addLoginLog(String sysUserKey,String ip) {
		SysUser su= baseCommonService.findByKey(SysUser.class, sysUserKey);
		String uName="";
		if(StringUtils.isNotBlank(su.getUserKey())) {
			OrgUser ou= baseCommonService.findByKey(OrgUser.class, su.getUserKey());
			if(ou!=null) {
				uName=ou.getName();
			}
		}
	    SysLoginLog logEntity=new SysLoginLog();
        logEntity.setAccount(su.getAccount());
        logEntity.setIp(ip);
        logEntity.setTime(BaseUtils.getNow());
        logEntity.setName(uName);
		baseCommonService.saveOrUpdate(logEntity);
	}

}
