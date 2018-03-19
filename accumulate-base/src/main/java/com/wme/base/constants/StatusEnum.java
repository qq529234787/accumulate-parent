package com.wme.base.constants;

/**
 * 公共状态类
 */
public enum StatusEnum {
	/**
	 * 有效的
	 */
    VALID(1,"有效的"),
    
    /**
     * 禁用
     */
    DISABLE(0,"禁用"),
    
    /**
     * 无效的
     */
    IN_VALID(-1,"无效的");


    private Integer status;
    private String value;

    StatusEnum(Integer status, String value) {
        this.status = status;
        this.value = value;
    }


    public Integer getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public boolean isEqual(Integer status) {
        return this.status.equals(status);
    }
}
