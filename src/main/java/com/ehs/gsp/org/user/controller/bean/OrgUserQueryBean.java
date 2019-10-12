package com.ehs.gsp.org.user.controller.bean;

import com.ehs.common.oper.bean.PageBody;

public class OrgUserQueryBean extends PageBody {

	private String query;
	
	private boolean includeSubOrg ;

	private String selectedOrg;
	
	
	
	
	public String getSelectedOrg() {
		return selectedOrg;
	}

	public void setSelectedOrg(String selectedOrg) {
		this.selectedOrg = selectedOrg;
	}


	



	public boolean isIncludeSubOrg() {
		return includeSubOrg;
	}

	public void setIncludeSubOrg(boolean includeSubOrg) {
		this.includeSubOrg = includeSubOrg;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	
}
