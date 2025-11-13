package com.fawnyr.healthsmartmedicine.model.dto.medicine;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fawnyr.healthsmartmedicine.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 药品
 * @TableName medicine
 */
@TableName(value ="medicine")
@Data
public class MedicineUpdateRequest implements Serializable {
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
     * 相关图片路径
     */
    private String imgPath;

    /**
     * 药的价格
     */
    private BigDecimal medicinePrice;

    private static final long serialVersionUID = 1L;
}