package com.ymchen.rannibase.enums;

public enum SexEnum {
    MALE(1, "男"),
    FEMALE(0, "女");

    private Integer sex;
    private String sexDescribe;

    SexEnum(Integer sex, String sexDescribe) {
        this.sex = sex;
        this.sexDescribe = sexDescribe;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSexDescribe() {
        return sexDescribe;
    }

    public void setSexDescribe(String sexDescribe) {
        this.sexDescribe = sexDescribe;
    }
}
