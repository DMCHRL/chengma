package com.chengma.devplatform.common.constant;

public enum EnumRoleName {
    user("普通用户"), // Data from the range of ChartInfo::from - ChartInfo::to.
    admin("超级管理员"),// Data from the range ChartInfo::from - the end of history.
    mobile("手机用户"),
    operate("运营管理员"),
    teach("投教管理员"),
    strategy("策略管理员"),
    mt4("云端管理员"),
    service("开户管理员"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRoleName(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
