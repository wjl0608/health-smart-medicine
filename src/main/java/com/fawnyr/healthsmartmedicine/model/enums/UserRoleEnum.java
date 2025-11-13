package com.fawnyr.healthsmartmedicine.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    USER("用户","user"),
    ADMIN("管理员","admin");

    private final String text;
    private final String value;

    UserRoleEnum(String text,String value){
        this.text = text;
        this.value = value;
    }

    public static UserRoleEnum getUserRoleEnum(String value){
        if(ObjUtil.isEmpty(value)) {
            return null;
        }
        for(UserRoleEnum roleEnum:UserRoleEnum.values()){
            if(roleEnum.value.equals(value))
                return roleEnum;
        }
        return null;
    }

}
