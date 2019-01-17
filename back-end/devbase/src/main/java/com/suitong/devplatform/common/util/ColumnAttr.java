package com.suitong.devplatform.common.util;

import org.apache.poi.ss.usermodel.CellStyle;

public class ColumnAttr {

	private String headerName;
	
	private String attribute;
	
	private int width;
	
	private CellStyle cellStyle;
	
	public ColumnAttr() {
		
	}
	
	public ColumnAttr(String headerName, String attribute) {
		this.headerName = headerName;
		this.attribute = attribute;
	}
	
	public ColumnAttr(String headerName, String attribute, int width) {
		this.headerName = headerName;
		this.attribute = attribute;
		this.width = width;
	}

	public ColumnAttr(String headerName, String attribute, CellStyle cellStyle) {
		this.headerName = headerName;
		this.attribute = attribute;
		this.cellStyle = cellStyle;
	}
	
	public ColumnAttr(String headerName, String attribute, int width, CellStyle cellStyle) {
		this.headerName = headerName;
		this.attribute = attribute;
		this.width = width;
		this.cellStyle = cellStyle;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
}
