package com.chengma.devplatform.common.constant;

public enum EnumRoleName {
    user("普通用户"), // Data from the range of ChartInfo::from - ChartInfo::to.
    proxy("合作商"), // Data from the ranges "the beginning of history" - ChartInfo::from and ChartInfo::to - the end of history.
    company("城市合作伙伴"),// Data from the range ChartInfo::from - the end of history.
    admin("超级管理员"),// Data from the range ChartInfo::from - the end of history.
    account("会计"),// Data from the range ChartInfo::from - the end of history.
    service("客服"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRoleName(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
