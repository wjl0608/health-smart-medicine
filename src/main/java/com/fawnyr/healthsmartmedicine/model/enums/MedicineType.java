package com.fawnyr.healthsmartmedicine.model.enums;

import cn.hutool.core.util.ObjUtil;
import lombok.Getter;

@Getter
public enum MedicineType {
    WesternMedicine("西药","0"),
    ChineseMedicine("中药","1"),
    PatentMedicine("中成药","2");

    private final String text;
    private final String value;

    MedicineType(String text, String value){
        this.text = text;
        this.value = value;
    }

    public static MedicineType getMedicineType(String value){
        if(ObjUtil.isEmpty(value)) {
            return null;
        }
        for(MedicineType roleEnum: MedicineType.values()){
            if(roleEnum.value.equals(value))
                return roleEnum;
        }
        return null;
    }

}
