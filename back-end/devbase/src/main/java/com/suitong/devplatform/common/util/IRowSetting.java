package com.suitong.devplatform.common.util;

import java.util.List;

public interface IRowSetting {

	//从0开始
	public int startRow();
	
	//true：显示列头  false：不显示列头
	public boolean columnSetting(List<ColumnAttr> list);
	
	public Object contentSetting(Object obj, int row);
	
}
