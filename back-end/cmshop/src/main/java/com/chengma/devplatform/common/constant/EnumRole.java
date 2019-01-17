package com.chengma.devplatform.common.constant;

public enum EnumRole {
    USER("user"), // Data from the range of ChartInfo::from - ChartInfo::to.
    ADMIN("admin"),// Data from the range ChartInfo::from - the end of history.
    MOBILE("mobile"),// Data from the range ChartInfo::from - the end of history.
    OPERATE("operate"), //运营
    TEACH("teach"), //报教
    STRATEGY("strategy"), //策略
    MT4("mt4"), //云端管理员
    SERVICE("service"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRole(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
