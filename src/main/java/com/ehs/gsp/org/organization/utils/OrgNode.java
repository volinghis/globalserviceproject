package com.ehs.gsp.org.organization.utils;

import java.util.List;

public class OrgNode {

	private List<OrgNode> children;
	private String title;
	private String key;
	private String parentKey;
	private boolean expand;
	
	
	public boolean isExpand() {
		return expand;
	}
	public void setExpand(boolean expand) {
		this.expand = expand;
	}
	public List<OrgNode> getChildren() {
		return children;
	}
	public void setChildren(List<OrgNode> children) {
		this.children = children;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getParentKey() {
		return parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	
	
	
}
