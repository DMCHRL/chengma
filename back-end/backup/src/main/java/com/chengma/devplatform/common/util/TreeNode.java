package com.chengma.devplatform.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {

	private String id;

	private String text;
	
	private String parentId;

	private String iconCls;
	
	private boolean checked;
	
	private String state;
	
	private List<TreeNode> children;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public TreeNode() {
		
	}

	public TreeNode(String id, String text, String parentId) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
