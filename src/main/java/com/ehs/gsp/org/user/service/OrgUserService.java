package com.ehs.gsp.org.user.service;

import org.springframework.data.domain.Pageable;

import com.ehs.common.oper.bean.PageInfoBean;
import com.ehs.common.organization.entity.OrgUser;
import com.ehs.gsp.org.user.controller.bean.OrgUserQueryBean;

public interface OrgUserService {

	public OrgUser saveOrUpdateUser(OrgUser orgUser);
	
	public PageInfoBean findUsers(OrgUserQueryBean query);
}
