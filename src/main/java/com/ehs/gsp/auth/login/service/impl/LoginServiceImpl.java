package com.ehs.gsp.auth.login.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ehs.common.auth.entity.SysUser;
import com.ehs.common.base.dao.BaseCommonDao;
import com.ehs.common.base.data.DataModel;
import com.ehs.common.base.entity.BaseEntity;
import com.ehs.common.base.service.BaseCommonService;
import com.ehs.gsp.auth.login.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private BaseCommonDao baseCommonDao;
	
	
	@Override
	public SysUser login(String account) {
		StringBuilder builder=new StringBuilder(" select su from SysUser su  ");
		builder.append(" where su.");
		builder.append(BaseEntity.VERSION_ID);
		builder.append(" =?0 and  su.");
		builder.append(SysUser.ACCOUNT);
		builder.append(" =?1 ");
		List params=new LinkedList();
		params.add(0, 0l);
		params.add(1, account);
		List<BaseEntity> baseEntities=  baseCommonDao.find(builder.toString(), params);
		if(baseEntities==null||baseEntities.isEmpty()) {
			return null;
		}
		return (SysUser)baseEntities.get(0);
	}

}
