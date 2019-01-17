package com.chengma.devplatform.common.constant;

public enum EnumGroup {
    demoTX("04"), // Data from the range of ChartInfo::from - ChartInfo::to.
    TXA2("03"), // Data from the ranges "the beginning of history" - ChartInfo::from and ChartInfo::to - the end of history.
    TXA3("03"), // Data from the range ChartInfo::from - the end of history.
    demoHPP("06"),
    recommendation("07"); //推荐码
    private String value;

    private EnumGroup(String value) { // 必须是private的，否则编译错误
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
