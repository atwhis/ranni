package com.ymchen.rannibase.enums;

public enum YesOrNotEnum {
    YES(1, "是"),
    NO(0, "否");

    private Integer code;
    private String desc;

    YesOrNotEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
