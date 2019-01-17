package com.chengma.devplatform.common.constant;

public enum EnumRoleName {
    user("普通用户"), // Data from the range of ChartInfo::from - ChartInfo::to.
    admin("超级管理员"),// Data from the range ChartInfo::from - the end of history.
    mobile("手机用户"),
    partner("合伙人"),
    originator("发起人"),
    manager("资金管理人"),
    service("客服"); // Data from the range ChartInfo::from - the end of history.

    private String value;

    private EnumRoleName(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
