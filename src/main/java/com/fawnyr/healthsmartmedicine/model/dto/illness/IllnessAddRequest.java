package com.fawnyr.healthsmartmedicine.model.dto.illness;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class IllnessAddRequest implements Serializable {
    /**
     * 种类id
     */
    private Long kingId;

    /**
     * 疾病名称
     */
    private String illnessName;

    /**
     * 诱发因素
     */
    private String includeReason;

    /**
     * 疾病症状
     */
    private String illnessSymptom;

    /**
     * 特殊症状
     */
    private String specialSymptom;

    private static final long serialVersionUID = 1L;
}