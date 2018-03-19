package com.wme.base.constants;

/**
 * 性别类
 */
public enum GenderEnum {
    MALE("male","男"),

    FEMALE("female","女");


    private String status;
    private String value;

    GenderEnum(String status, String value) {
        this.status = status;
        this.value = value;
    }


    public String getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public boolean isEqual(Integer status) {
        return this.status.equals(status);
    }
}
