package com.chengma.devplatform.common.constant;

public enum EnumRole {
    USER("user"), // Data from the range of ChartInfo::from - ChartInfo::to.
    PROXY("proxy"), // Data from the ranges "the beginning of history" - ChartInfo::from and ChartInfo::to - the end of history.
    COMPANY("company"),// Data from the range ChartInfo::from - the end of history.
    ADMIN("admin"),// Data from the range ChartInfo::from - the end of history.
    ACCOUNT("account"),// Data from the range ChartInfo::from - the end of history.
    SERVICE("service"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRole(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
