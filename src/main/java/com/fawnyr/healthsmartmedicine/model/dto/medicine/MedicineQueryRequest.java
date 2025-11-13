package com.fawnyr.healthsmartmedicine.model.dto.medicine;

import com.baomidou.mybatisplus.annotation.*;
import com.fawnyr.healthsmartmedicine.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 药品
 * @TableName medicine
 */
@TableName(value ="medicine")
@Data
public class MedicineQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 药的名字
     */
    private String medicineName;

    /**
     * 关键字搜索
     */
    private String keyword;

    /**
     * 药的功效
     */
    private String medicineEffect;

    /**
     * 药的品牌
     */
    private String medicineBrand;

    /**
     * 药的相互作用
     */
    private String interaction;

    /**
     * 禁忌
     */
    private String taboo;

    /**
     * 用法用量
     */
    private String useAge;

    /**
     * 药的类型：0西药，1中药，2中成药
     */
    private String medicineType;

    /**
     * 药的最低价格
     */
    private BigDecimal MedicinePriceMin;

    /**
     * 药的最高价格
     */
    private BigDecimal MedicinePriceMax;

    private static final long serialVersionUID = 1L;
}