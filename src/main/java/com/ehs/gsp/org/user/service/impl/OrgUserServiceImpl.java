package com.ehs.gsp.org.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ehs.common.auth.entity.SysMenu;
import com.ehs.common.auth.entity.SysRole;
import com.ehs.common.auth.entity.SysRoleMenu;
import com.ehs.common.auth.entity.SysUser;
import com.ehs.common.auth.entity.SysUserRole;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.common.base.utils.BaseUtils;
import com.ehs.common.base.utils.JsonUtils;
import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.common.organization.entity.OrganizationInfo;
import com.ehs.gsp.org.organization.utils.OrgNode;
import com.ehs.gsp.org.user.controller.bean.OrgUserQueryBean;
import com.ehs.gsp.org.user.controller.bean.UserRolesBean;
import com.ehs.gsp.org.user.dao.OrgUserDao;
import com.ehs.gsp.org.user.dao.UserRolesDao;
import com.ehs.gsp.org.user.service.OrgUserService;

@Service
public class OrgUserServiceImpl implements OrgUserService {

	@Resource
	private BaseCommonService baseCommonService;
	
	@Resource
	private OrgUserDao orgUserDao;
	
	@Resource
	private UserRolesDao userRolesDao;

	@Override
	public OrgUser saveOrUpdateUser(OrgUser orgUser) {

		String userKey = orgUser.getKey();
		if (StringUtils.isBlank(userKey)) {
			userKey = UUID.randomUUID().toString();
			String sysUserKey=UUID.randomUUID().toString();
			SysUser su = new SysUser();
			su.setKey(sysUserKey);
			su.setAccount(orgUser.getDataCode());
			su.setPassword(BaseUtils.string2MD5(su.getAccount() + "123456"));
			su.setState(0);
			su.setUserKey(userKey);
			baseCommonService.saveOrUpdate(su);
			orgUser.setKey(userKey);
			orgUser.setSysUserKey(sysUserKey);
			return baseCommonService.saveOrUpdate(orgUser);
		} else {
			SysUser su = baseCommonService.findByKey(SysUser.class, orgUser.getSysUserKey());
			su.setAccount(orgUser.getDataCode());
			su.setPassword(BaseUtils.string2MD5(su.getAccount() + "123456"));
			baseCommonService.saveOrUpdate(su);
			return baseCommonService.saveOrUpdate(orgUser);
		}
	}

	@Override
	public PageInfoBean findUsers(OrgUserQueryBean query) {
		PageRequest pageRequest =PageRequest.of(query.getPage()-1, query.getSize());
		Page<OrgUser> users=null;
		if(StringUtils.isBlank(query.getSelectedOrg())) {
			 users= orgUserDao.findUsers(query.getQuery(), pageRequest);
		}else {
			if(query.isIncludeSubOrg()) {
				List<OrganizationInfo> list= (List<OrganizationInfo> )baseCommonService.findAll(OrganizationInfo.class);
				List<String> orgKeys=new ArrayList<String>();
				createOrgNodes(orgKeys,list,query.getSelectedOrg());
				if(orgKeys.isEmpty()) {
					users= orgUserDao.findUsers(query.getQuery(),query.getSelectedOrg(), pageRequest);
				}else {
		
					String[] strings = new String[orgKeys.size()];
					orgKeys.toArray(strings);
					users= orgUserDao.findUsers(query.getQuery(),query.getSelectedOrg(), strings,pageRequest);
				}
				
			}else {
				users= orgUserDao.findUsers(query.getQuery(),query.getSelectedOrg(), pageRequest);
			}
		}
		if(users!=null) {
			PageInfoBean pi=new PageInfoBean();
			pi.setDataList(users.getContent());
			pi.setTotalCount(users.getTotalElements());
			return pi;
		}
		return null;
	}
	


	private void createOrgNodes(List<String> orgKeys, List<OrganizationInfo> orgs, String parentKey) {
		orgs.stream().filter(s -> StringUtils.equals(s.getParentKey(), parentKey)).forEach(c -> {
			orgKeys.add(c.getKey());
			createOrgNodes(orgKeys, orgs, c.getKey());
		});
	}

	@Transactional
	@Override
	public void saveUserRoles(UserRolesBean userRolesBean) {
		
		OrgUser our=baseCommonService.findByKey(OrgUser.class, userRolesBean.getUserKey());
		
		List<String> roleKeys=  userRolesBean.getRoleList().stream().map(SysRole::getKey).collect(Collectors.toList());
		List<SysUserRole> srmList= userRolesDao.find(our.getSysUserKey(), roleKeys.stream().toArray(String[]::new));

			for(SysRole ss:userRolesBean.getRoleList()) {
				if(srmList!=null&&!srmList.isEmpty()) {
					long c=srmList.stream().filter(sss->StringUtils.equals(sss.getSysUserKey(), our.getSysUserKey())&&StringUtils.equals(sss.getRoleKey(), ss.getKey())).count();
					if(c<=0) {
						SysUserRole srm=new SysUserRole();
						srm.setSysUserKey(our.getSysUserKey());
						srm.setRoleKey(ss.getKey());
						baseCommonService.saveOrUpdate(srm);
					}
				}else {
					SysUserRole srm=new SysUserRole();
					srm.setSysUserKey(our.getSysUserKey());
					srm.setRoleKey(ss.getKey());
					baseCommonService.saveOrUpdate(srm);
				}
			
			}

		
	}
}
