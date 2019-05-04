package com.dengjk.common.enums;

/**
 * @author Dengjk
 * @create 2019-05-03 10:01
 * @desc 权限认证类型
 * 1->shiro anon
 * 2->shiro authc
 * 3->shiro perm[]
 **/
public enum PermAuthTypeEnum {

    shiro_anon(1, "anon"),

    shiro_authc(2, "authc"),

    shiro_perm(3, "perms[%s]"),

    shiro_roles(4, "roles[%s]");

    private Integer code;

    private String name;


    public static PermAuthTypeEnum getPermAuthTypeEnum(Integer code){
        PermAuthTypeEnum[] values = PermAuthTypeEnum.values();
        for (PermAuthTypeEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }



    PermAuthTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
