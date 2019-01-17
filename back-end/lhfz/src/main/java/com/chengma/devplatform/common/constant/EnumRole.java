package com.chengma.devplatform.common.constant;

public enum EnumRole {
    USER("user"), // Data from the range of ChartInfo::from - ChartInfo::to.
    ADMIN("admin"),// Data from the range ChartInfo::from - the end of history.
    MOBILE("mobile"),// Data from the range ChartInfo::from - the end of history.
    PARTNER("partner"), //合伙人
    ORIGINATOR("originator"), //发起人
    MANAGER("manager"), //资金管理人
    SERVICE("service"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRole(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
