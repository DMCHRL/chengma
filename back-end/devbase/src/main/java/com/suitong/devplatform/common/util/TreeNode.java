package com.suitong.devplatform.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {

	private Long id;

	private String text;
	
	private Long parentId;

	private String iconCls;
	
	private boolean checked;
	
	private String state;
	
	private List<TreeNode> children;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public TreeNode() {
		
	}

	public TreeNode(Long id, String text, Long parentId) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
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
