package com.dengjk.common.enums;

/**
 * @author Dengjk
 * @create 2019-04-17 20:38
 * @desc  权限类型枚举
 **/
public enum PermissionTypeEnum {


    menu(1,"pePermissionMenu"),

    point(2,"pePermissionPoint"),

    api(3,"pePermissionApi")

    ;


    PermissionTypeEnum(Integer code, String fieldName) {
        this.code = code;
        this.fieldName = fieldName;
    }


    public static PermissionTypeEnum getPermissionTypeEnum(Integer code){
        PermissionTypeEnum[] values = PermissionTypeEnum.values();
        for (PermissionTypeEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }


    private Integer code;

    private String  fieldName;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
